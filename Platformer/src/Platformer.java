
//imports
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.sun.javafx.scene.control.skin.TitledPaneSkin;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Platformer extends Application implements Images {
	boolean zzzz = false;
	int count = 0;
	// global variables
	// resolution
	private static final double HEIGHT = 704.0;
	private static final double WIDTH = 1600.0;
	private static final double TOTALWIDTH = 6400;

	// Offset for scrolling
	public double xOffset = 0;

	// movement modifiers
	public static final double acceleration = 2.0;
	private static final double gravity = 2.0;
	private int cooldown = 120;
	private boolean jumpPress = false;
	private boolean bottom = false;
	private boolean farietteAdded = false;
	private boolean top = false;
	boolean right = false;
	boolean left = false;
	boolean movingLeft = false;
	boolean movingRight = false;
	boolean movingUp = false;
	private Collision c;
	

	private Sounds bgNoise = new Sounds();
	private Sounds jumpSound = new Sounds();

	private Stage thestage;
	private Scene menuScene, gameScene, controlScene, igmenu, igcontrols, winScene;
	private Pane menuRoot, gameRoot, controlRoot, igmenuroot, igcontrolroot, winRoot;

	private Player hero = new Player(300, 200, 65, 64, SPOOKEO_IDLE.getImageView(), 300, 200, 64, 64);
	private Actor fairy = new Actor(1408, 512, 64, 64, new ImageView("Assets/Art/triforce.png"), 1408, 512, 64, 64);
	private Actor box = new Actor(500, HEIGHT - 100, 100, 100, new ImageView("Assets/Art/pushable_box.png"), 500,
			HEIGHT - 100, 100, 100);
	final Rectangle rectangle = makeRectangle(hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());

	BackgroundFill menuBG = new BackgroundFill(Color.BLACK, null, null);
	BackgroundFill controlBG = new BackgroundFill(Color.BLACK, null, null);
	BackgroundFill gameBG = new BackgroundFill(Color.BLACK, null, null);
	BackgroundFill igControlBG = new BackgroundFill(Color.BLACK, null, null);
	BackgroundFill igMenuBG = new BackgroundFill(Color.BLACK, null, null);
	BackgroundFill transparent = new BackgroundFill(null, null, null);

	ArrayList<Nonmoveable> nmo = new ArrayList<Nonmoveable>();
	ArrayList<Moveable> mo = new ArrayList<Moveable>();
	MapLoader m = new MapLoader();

	ImageView bg = new ImageView("Assets/Art/BackGround.png");
	Image winScreen = new Image("Assets/Art/endgame_pic.png");
	Image controls = new Image("Assets/Art/controls_sheet2.png");

	AnimationTimer gameLoop;
	URL url = getClass().getResource("Assets/Json/characters.json");

	Canvas gameCanvas = new Canvas(WIDTH, HEIGHT);
	GraphicsContext gc = gameCanvas.getGraphicsContext2D();
	Collision win = new Collision(hero, fairy);

	public Platformer() throws IOException {
	}

	// --------------------------- Methods to run everything
	// -----------------------------//

	public static void main(String[] args) {
		launch(args);
	}

	// sets scene and adds objects
	@Override
	public void start(Stage primaryStage) {
		thestage = primaryStage;
		load();

		// make backgrounds
		draw();

		// load sound stuff
		sound();

		// loop methods for game mechanics
		animation();
	}

	private void load() {
		bg.setFitHeight(HEIGHT);
		bg.setFitWidth(WIDTH * 2);
		bg.setX(-xOffset / 4);
		// Load Map
		m.readIn(WIDTH, HEIGHT, "Assets/Json/map.txt", xOffset);

		// Get nonmoveable objects
		nmo = m.getNMO();

		// Get moveable objects
		mo = m.getMO();

		gc.clearRect(0, 0, WIDTH, HEIGHT);

		for (int i = 0; i < mo.size(); i++) {
			gc.drawImage(mo.get(i).getImageView().getImage(), mo.get(i).getX(), mo.get(i).getY());
		}

		for (int i = 0; i < nmo.size(); i++) {
			gc.drawImage(nmo.get(i).getImageView().getImage(), nmo.get(i).getX(), nmo.get(i).getY());
			// gc.fillRect(nmo.get(i).getHBX(), nmo.get(i).getHBY(),
			// nmo.get(i).getHBWidth(), nmo.get(i).getHBHeight());
		}

		// Load title screen animation
		// System.out.println("WIDTH/2 = " + WIDTH/2);
		TITLE_SCREEN.getImageView().setX((WIDTH / 2) - 170);
		TITLE_SCREEN.getImageView().setY(100);

	}

	private void animation() {
		gameLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				resetCollision();
				winCheck(gameLoop);
				
				collisionCheck();
				
				if (!top) {
					gravity(rectangle, hero.getImageView());
				}
				if(movingRight && !left){
					moveRight(rectangle, hero.getImageView());
				} 
				if(movingLeft && !right){
					moveLeft(rectangle, hero.getImageView());
				}
				if(movingUp){
					jump(rectangle, hero.getImageView());
				}
				if(xOffset>4600 && !farietteAdded){
					gameRoot.getChildren().add(fairy.getImageView());
					farietteAdded = true;
				}
				cooldown++;
				
			}

		};

		Images.initAnimations();
		gameLoop.start();
		Images.playAnimations();
		thestage.setTitle("Spookeo and Fariette");
		thestage.setScene(menuScene);
		thestage.show();
	}

	private void sound() {
		bgNoise.loadSound("Assets/Sound/Main_Theme.wav");
		jumpSound.loadSound("Assets/Sound/jump1.wav");
		bgNoise.runLoop();
	}

	private void draw() {
		Canvas winCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext wc = winCanvas.getGraphicsContext2D();
		wc.drawImage(winScreen, 0, 0);
		
		// make canvases
		Canvas menuCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext mc = menuCanvas.getGraphicsContext2D();
		//mc.drawImage(controls, 0, 100);

		Canvas iGMCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext igmc = iGMCanvas.getGraphicsContext2D();
		//igmc.drawImage(controls, 0, 100);

		Canvas controlCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext cc = controlCanvas.getGraphicsContext2D();
		cc.drawImage(controls, 0, 100);

		Canvas iGCCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext igcc = iGCCanvas.getGraphicsContext2D();
		igcc.drawImage(controls, 0, 100);

		// -------- Menu ----------//
		// make win scene
		winRoot = new Pane();
		winRoot.getChildren().add(winCanvas);
		winRoot.getChildren().add(winMenuButton());
		winScene = new Scene(winRoot, WIDTH, HEIGHT);
		// make control
		controlRoot = new Pane();
		controlRoot.setBackground(new Background(controlBG));
		controlRoot.getChildren().add(controlCanvas);
		controlRoot.getChildren().add(menuButton());
		controlRoot.getChildren().add(startButton());
		controlScene = new Scene(controlRoot, WIDTH, HEIGHT);

		// make menu
		menuRoot = new Pane();
		menuRoot.setBackground(new Background(menuBG));
		menuRoot.getChildren().add(TITLE_SCREEN.getImageView());
		menuRoot.getChildren().add(menuCanvas);
		menuRoot.getChildren().add(startButton2());
		menuRoot.getChildren().add(controlButton());
		menuScene = new Scene(menuRoot, WIDTH, HEIGHT);

		// ---------- Game ----------//

		// in game menu
		igmenuroot = new Pane();
		igmenuroot.setBackground(new Background(igMenuBG));
		igmenuroot.getChildren().add(iGMCanvas);
		igmenuroot.getChildren().add(resetButton());
		igmenuroot.getChildren().add(menuButton2());
		igmenuroot.getChildren().add(resumeButton());
		igmenuroot.getChildren().add(igControl());
		igmenu = new Scene(igmenuroot, WIDTH, HEIGHT);

		// in game controls
		igcontrolroot = new Pane();
		igcontrolroot.setBackground(new Background(igControlBG));
		igcontrolroot.getChildren().add(iGCCanvas);
		igcontrolroot.getChildren().add(igMenuButton2());
		igcontrolroot.getChildren().add(resumeButton());
		igcontrols = new Scene(igcontrolroot, WIDTH, HEIGHT);

		// make game
		gameRoot = new Pane();
		gameRoot.setBackground(new Background(gameBG));
		gameRoot.getChildren().add(bg);
		gameRoot.getChildren().add(gameCanvas);
		gameRoot.getChildren().add(rectangle);
		gameRoot.getChildren().add(igMenuButton());
		gameRoot.getChildren().add(hero.getImageView());
		//gameRoot.getChildren().add(SPOOKEO_PUSH.getImageView());
		gameScene = new Scene(gameRoot, WIDTH, HEIGHT);

		c = new Collision(hero, box);
		moveRectangleOnKeyPress(gameScene, rectangle, hero.getImageView());

		/*
		 * if (zzzz) { mo.remove(1); count++; }
		 */
	}

	// ----------------------- Creating Objects
	// -----------------------------------//

	// creates rectangle
	private Rectangle makeRectangle(double x, double y, double width, double height) {
		Rectangle r1 = new Rectangle(x, y, width, height);
		r1.setStroke(Color.TRANSPARENT);
		r1.setFill(Color.TRANSPARENT);
		r1.setStrokeWidth(3);
		return r1;
	}

	// creates start button
	private Button startButton() {
		Button btn = new Button("", START.getImageView());
		btn.setBackground(new Background(transparent));

		btn.relocate((WIDTH / 2) - 88, 500);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(Images.START_HOVER.getImageView());
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(Images.START.getImageView());
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				menuRoot.getChildren().remove(TITLE_SCREEN.getImageView());
				start(thestage);
				thestage.setTitle("Spookeo's Journey Yo");
				thestage.setScene(gameScene);
			}
		});
		return btn;
	}
	private Button startButton2() {
		Button btn = new Button("", START.getImageView());
		btn.setBackground(new Background(transparent));

		btn.relocate(550, 500);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(Images.START_HOVER.getImageView());
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(Images.START.getImageView());
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				menuRoot.getChildren().remove(TITLE_SCREEN.getImageView());
				start(thestage);
				thestage.setTitle("Spookeo's Journey Yo");
				thestage.setScene(gameScene);
			}
		});
		return btn;
	}
	private Button winMenuButton(){
		Button btn = new Button("", MENU.getImageView());
		btn.setBackground(new Background(transparent));
		btn.relocate((WIDTH / 2) - 94, 575);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(MENU_HOVER.getImageView());
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(MENU.getImageView());
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				gameRoot.getChildren().remove(hero.getImageView());
				gameRoot.getChildren().remove(rectangle);
				xOffset = 0;
				m.readIn(WIDTH, HEIGHT, "Assets/Json/map.txt", xOffset);
				hero.setX(300);
				hero.setHBX(300);
				hero.getImageView().setX(300);
				rectangle.setX(300);
				farietteAdded = false;
				gameRoot.getChildren().add(hero.getImageView());
				gameRoot.getChildren().add(rectangle);

				/*
				 * for(int i = 0; i<= mo.size(); i++){ mo.remove(i);
				 * box.setX(500); box.getImageView().setX(500); }
				 * //mo.remove(1);
				 */
				thestage.hide();
				load();
				start(thestage);
				thestage.setScene(menuScene);

			}
		});
		return btn;
	}
	private Button resetButton() {
		Button btn = new Button("", RESET.getImageView());
		btn.setBackground(new Background(transparent));
		btn.relocate(610, 500);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(RESET_HOVER.getImageView());
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(RESET.getImageView());
			}
		});
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				// isTrue(zzzz);
				gameRoot.getChildren().remove(hero.getImageView());
				gameRoot.getChildren().remove(rectangle);
				xOffset = 0;
				m.readIn(WIDTH, HEIGHT, "map.txt", xOffset);
				hero.setX(300);
				hero.setHBX(300);
				hero.getImageView().setX(300);
				rectangle.setX(300);
				farietteAdded = false;
				gameRoot.getChildren().add(hero.getImageView());
				gameRoot.getChildren().add(rectangle);

				/*
				 * for(int i = 0; i<= mo.size(); i++){ mo.remove(i);
				 * box.setX(500); box.getImageView().setX(500); }
				 * //mo.remove(1);
				 */
				thestage.hide();
				load();
				start(thestage);
				thestage.setScene(gameScene);
			}
		});
		return btn;
	}


	// creates button to reach controls screen
	private Button controlButton() {
		Button btn = new Button("", CONTROL.getImageView());
		btn.setBackground(new Background(transparent));

		btn.relocate(850, 500);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(CONTROL_HOVER.getImageView());
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(CONTROL.getImageView());
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				thestage.setTitle("Controls");
				thestage.setScene(controlScene);
			}
		});
		return btn;
	}

	private Button igMenuButton() {
		Button btn = new Button("", MENU.getImageView());
		btn.setBackground(new Background(transparent));
		btn.relocate(1440, 25);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(MENU_HOVER.getImageView());
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(MENU.getImageView());
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				igmenuroot.getChildren().add(TITLE_SCREEN.getImageView());
				igmenuroot.getChildren().add(menuButton());
				thestage.setScene(igmenu);
				gameLoop.stop();
			}

		});
		return btn;
	}

	private Button igControl() {
		Button btn = new Button("", CONTROL.getImageView());
		btn.setBackground(new Background(transparent));
		btn.relocate(810, 600);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(CONTROL_HOVER.getImageView());
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(CONTROL.getImageView());
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				thestage.setScene(igcontrols);
			}

		});
		return btn;
	}

	// creates button to reach menu
	private Button menuButton() {
		Button btn = new Button("", MENU.getImageView());
		btn.setBackground(new Background(transparent));
		btn.relocate((WIDTH / 2) - 94, 575);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(MENU_HOVER.getImageView());
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(MENU.getImageView());
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				//menuRoot.getChildren().add(TITLE_SCREEN.getImageView());
				thestage.setTitle("Spookeo and Fariette");
				thestage.setScene(menuScene);
			

			}
		});
		return btn;
	}

	private Button menuButton2() {
		Button btn = new Button("", MENU.getImageView());
		btn.setBackground(new Background(transparent));
		btn.relocate(550, 575);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(MENU_HOVER.getImageView());
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(MENU.getImageView());
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				//menuRoot.getChildren().add(TITLE_SCREEN.getImageView());
				thestage.setTitle("Spookeo and Fariette");
				thestage.setScene(menuScene);
			

			}
		});
		return btn;
	}

	
	 private Button igMenuButton2(){
	 Button btn = new Button("Go back");
	 btn.relocate(550, 500);
	 btn.setOnAction(new EventHandler<ActionEvent>(){
	
	 @Override
	 public void handle(ActionEvent event) {
	 thestage.setScene(igmenu);
	 }
	 });
	 return btn;
	 }
	private Button resumeButton() {
		Button btn = new Button("", RESUME.getImageView());
		btn.setBackground(new Background(transparent));
		btn.relocate(810, 500);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(RESUME_HOVER.getImageView());
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(RESUME.getImageView());
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				igmenuroot.getChildren().remove(TITLE_SCREEN.getImageView());
				gameRoot.getChildren().add(igMenuButton());
				thestage.setScene(gameScene);
				gameLoop.start();
			}
		});
		return btn;
	}
	// ---------------------- Methods for moving/Colliding
	// --------------------------------------------------//

	// Method for implementing gravity
	private void gravity(final Rectangle rectangle, final ImageView image) {
		if (!(rectangle.getY() + gravity + rectangle.getHeight() >= HEIGHT)) {
			rectangle.setY(rectangle.getY() + gravity);
			image.setY(rectangle.getY());
			hero.setHBY(rectangle.getY());
		}
	}

	// jump
	double jumpmax = 0;
	boolean canJump = false;
	boolean lefty = false;
	boolean righty = false;

	private void jump(final Rectangle rectangle, final ImageView image) {
		if (jumpPress) {

			if (cooldown >= 120) {
				jumpSound.run();
				jumpmax = rectangle.getY() - 185;
				canJump = true;
			}
			if (canJump) {
				if (rectangle.getY() <= jumpmax || bottom) {
					canJump = false;
					jumpPress = false;
				}
				if (canJump) {
					//jumping = true;
					cooldown = 0;

					if (rectangle.getY() >= jumpmax) {
						rectangle.setY(rectangle.getY() - acceleration*4);
						hero.setHBY(rectangle.getY());
					}

				}
			}

		}
	}

	// Reset collision variables
	public void resetCollision() {
		bottom = false;
		top = false;
		left = false;
		right = false;
	}

	// Checks for collision with all objects
	public void collisionCheck() {

		// Check every background object
		// Check for able to move
		for (Nonmoveable nm : nmo) {
			c.setObjs(hero, nm);
			c.isColliding();
			if (c.bottom()) {
				bottom = true;
			}
			if (c.top()) {
				top = true;

			}
			if (c.left()) {
				left = true;
			}
			if (c.right()) {
				right = true;
			}
		}

		// Check every interactable object
		// Check for ineractions
		for (Moveable em : mo) {
			c.setObjs(hero, em);
			c.isColliding();
			boolean boxLeft = false;
			boolean boxRight = false;
			//System.out.println(c.isColliding());

			if (em instanceof Box && c.isColliding()) {
				System.out.println("Collision");
				for (Nonmoveable nm : nmo) {
					Collision c2 = new Collision(em, nm);
					c2.isColliding();
					if (c2.left()) {
						boxLeft = true;
						break;
					}
					if (c2.right()) {
						boxRight = true;
						break;
					}
				}
				if (!boxLeft && !boxRight) {
					c.moveObject();
				}

				if (boxLeft) {
					left = true;
				}

				if (boxRight) {
					right = true;
				}

			}
			if (em instanceof Rock && c.isColliding()) {
				if (c.right()) {
					int l = em.getHBHeight();
					em.getImageView().setImage(hero.getImageView().getImage());

					em.getImageView().setRotate(90);
					em.setHBHeight(em.getHBWidth());
					em.setHBWidth(l);
				}

			}

		}

	}

	// makes shape move....
	private void moveRectangleOnKeyPress(Scene scene, final Rectangle rectangle, final ImageView image) {
		scene.setOnKeyPressed( new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				if (event.getCode().equals(KeyCode.D) || event.getCode().equals(KeyCode.RIGHT)) {
					if (!left) {
						hero.getImageView().setScaleX(1);
						movingRight = true;
						// Check for right scrolling
						scrollCheckRight(hero.getHBX());
						//moveRight(rectangle, image, righty);
						

					}
				}

				if (event.getCode().equals(KeyCode.W) || event.getCode().equals(KeyCode.UP)) {
					jumpPress = true;
					movingUp = true;

				}

				if (event.getCode().equals(KeyCode.A) || event.getCode().equals(KeyCode.LEFT)) {
					if (!right) {
						hero.getImageView().setScaleX(-1);
						movingLeft = true;
						// Check for left scrolling
						scrollCheckLeft(hero.getHBX());
						//moveLeft(rectangle, image, lefty);

					}
				}

			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.D) || event.getCode().equals(KeyCode.RIGHT)){
					movingRight = false;
				}
				if(event.getCode().equals(KeyCode.A) || event.getCode().equals(KeyCode.LEFT)){
					movingLeft = false;
				}
				if(event.getCode().equals(KeyCode.W) || event.getCode().equals(KeyCode.UP)){
					movingUp = false;
				}
			}
			
		});

	}

	// Scrolling
	public void scrollCheckLeft(double x) {

		if (x <= (0.2 * WIDTH) && xOffset - acceleration > 0) {
			xOffset = xOffset - acceleration;
			//hero.setHBX(0.2 * WIDTH);
			//rectangle.setX(hero.getHBX());
			//hero.getImageView().setX(hero.getHBX());
			//hero.setX(hero.getHBX());
			
			lefty = false;
			load();
			thestage.setScene(gameScene);
		} else {
			lefty = true;
		}
	}

	public void scrollCheckRight(double x) {

		if (x >= (0.6 * WIDTH) && xOffset + acceleration < (0.76 * TOTALWIDTH)) {
			
			xOffset = xOffset + acceleration;
			//hero.setHBX(0.6 * WIDTH);
			//rectangle.setX(hero.getHBX());
			//hero.getImageView().setX(hero.getHBX());
			//hero.setX(hero.getHBX());
			righty = false;
			load();
			thestage.setScene(gameScene);
		}

		else {
			righty = true;
		}
	}

	private void moveRight(Rectangle rectangle, ImageView image) {
			if(hero.getHBX() > 0.8*WIDTH && xOffset + acceleration < (0.76 *TOTALWIDTH)){
				xOffset = xOffset + acceleration;
				//System.out.println(xOffset + " " + acceleration);
				load();
			}
			else {
				if (!(rectangle.getX() + acceleration + rectangle.getWidth() >= WIDTH)) {
				rectangle.setX(rectangle.getX() + acceleration);
				//System.out.println(rectangle.getX() + " " + acceleration);
				image.setX(rectangle.getX());
				hero.setHBX(rectangle.getX());
		}
			}
	}

	private void moveLeft(Rectangle rectangle, ImageView image) {
		if(hero.getHBX() < 0.2*WIDTH && xOffset - acceleration > 0){
			xOffset = xOffset - acceleration;
			//System.out.println(xOffset + " " + acceleration);
			load();
		}
		else{ 
			if (!(rectangle.getX() - acceleration <= 0)) {
				rectangle.setX(rectangle.getX() - acceleration);

				image.setX(rectangle.getX());
				hero.setHBX(rectangle.getX());
			}
		}
	}
	public void winCheck(AnimationTimer loop) {
		if(win.isColliding()) {
			loop.stop();
			thestage.setScene(winScene);
		}
	}
}
