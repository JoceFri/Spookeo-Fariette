//imports
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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

	// movement modifiers
	public static final double acceleration = 8;
	private static final double gravity = 2.0;
	private int cooldown = 120;
	private boolean jumpPress = false;
	private boolean bottom = false;
	private boolean top = false;
	boolean right = false;
	boolean left = false;
	private Collision c;

	private Sounds bgNoise = new Sounds();
	private Sounds jumpSound = new Sounds();

	private Stage thestage;
	private Scene menuScene, gameScene, controlScene, igmenu, igcontrols;
	private Pane menuRoot, gameRoot, controlRoot, igmenuroot, igcontrolroot;

	private Player hero = new Player(300, 200, 65, 64, SPOOKEO_IDLE.getImageView(), 300, 200, 65, 64);
	private Actor box = new Actor(500, HEIGHT - 100, 100, 100, new ImageView("Assets/Art/pushable_box.png"), 500, HEIGHT - 100, 100, 100);
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
//	Image title = new Image("Assets/Art/titlescreen.png");

	AnimationTimer gameLoop;
	URL url = getClass().getResource("Assets/Json/characters.json");

	Canvas gameCanvas = new Canvas(WIDTH, HEIGHT);
	GraphicsContext gc = gameCanvas.getGraphicsContext2D();
	
	MenuButton start = new MenuButton(START.getImageView(), START_HOVER.getImageView(), (WIDTH/2) - 88, 500);
	MenuButton reset = new MenuButton(RESET.getImageView(), RESET_HOVER.getImageView(), 610, 500);
	MenuButton igMenu = new MenuButton(MENU.getImageView(), MENU_HOVER.getImageView(), 1440, 25);
	MenuButton resume = new MenuButton(RESUME.getImageView(), RESUME_HOVER.getImageView(), 810, 500);
	MenuButton menu = new MenuButton(MENU.getImageView(), MENU_HOVER.getImageView(), (WIDTH/2) - 94, 575);

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
		bg.setScaleX(-1);
		// Load Map
		m.readIn(WIDTH, HEIGHT, "map.txt");

		// Get nonmoveable objects
		nmo = m.getNMO();

		// Get moveable objects
		mo = m.getMO();

		// Load title screen animation
//		System.out.println("WIDTH/2 = " + WIDTH/2);
		TITLE_SCREEN.getImageView().setX((WIDTH/2) - 170);
		TITLE_SCREEN.getImageView().setY(100);
	}

	private void animation() {
		gameLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {

				gc.clearRect(0, 0, WIDTH, HEIGHT);

				for (int i = 0; i < mo.size(); i++) {
					if (mo.get(i).isAnimated()) {
						
					}
					gc.drawImage(mo.get(i).getImageView().getImage(), mo.get(i).getX(), mo.get(i).getY());
				}

				for (int i = 0; i < nmo.size(); i++) {
					gc.drawImage(nmo.get(i).getImageView().getImage(), nmo.get(i).getX(), nmo.get(i).getY());
				}

				resetCollision();
				collisionCheck();
				if(!top){
					gravity(rectangle, hero.getImageView());
				}
				jump(rectangle, hero.getImageView());

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
		bgNoise.loadSound("Assets/Sound/background.wav");
		jumpSound.loadSound("Assets/Sound/jump1.wav");
		bgNoise.runLoop();		
	}

	private void draw() {		
		// -------- make buttons -------- //		
		start.setAction(startAction());
		reset.setAction(retryAction());
		igMenu.setAction(igMenuAction());
		resume.setAction(resumeAction());
		menu.setAction(menuAction());
	
		// -------- Menu ----------//

		// make control
		controlRoot = new Pane();
		controlRoot.setBackground(new Background(controlBG));
		controlRoot.getChildren().add(menu.getButton());
//		controlRoot.getChildren().add(startButton());
		controlScene = new Scene(controlRoot, WIDTH, HEIGHT);

		// make menu
		menuRoot = new Pane();
		menuRoot.setBackground(new Background(menuBG));
		menuRoot.getChildren().add(TITLE_SCREEN.getImageView());
		menuRoot.getChildren().add(start.getButton());
//		menuRoot.getChildren().add(controlButton());
		menuScene = new Scene(menuRoot, WIDTH, HEIGHT);

		// ---------- Game ----------//

		//in game menu
		igmenuroot = new Pane();
		igmenuroot.setBackground(new Background(igMenuBG));
		igmenuroot.getChildren().add(reset.getButton());
		igmenuroot.getChildren().add(resume.getButton());
//		igmenuroot.getChildren().add(menu.getButton());
		igmenuroot.getChildren().add(RESUME.getImageView());
//		igmenuroot.getChildren().add(igControl());
		igmenu = new Scene(igmenuroot, WIDTH, HEIGHT);

		//in game controls
		igcontrolroot = new Pane();
		igcontrolroot.setBackground(new Background(igControlBG));
//		igcontrolroot.getChildren().add(igMenuButton2());
//		igcontrolroot.getChildren().add(resume.getButton());
		igcontrols = new Scene(igcontrolroot, WIDTH, HEIGHT);

		// make game
		gameRoot = new Pane();
		gameRoot.setBackground(new Background(gameBG));
		gameRoot.getChildren().add(bg);
		gameRoot.getChildren().add(gameCanvas);
		gameRoot.getChildren().add(rectangle);
		gameRoot.getChildren().add(igMenu.getButton());
		gameRoot.getChildren().add(hero.getImageView());
		gameRoot.getChildren().add(SPOOKEO_PUSH.getImageView());
		gameScene = new Scene(gameRoot, WIDTH, HEIGHT);

		c = new Collision(hero, box);
		moveRectangleOnKeyPress(gameScene, rectangle, hero.getImageView());

		if (zzzz) {
			mo.remove(1);
			count++;
		}
	}
	
	// -------- Create Action Events -------- //
	private EventHandler<ActionEvent> startAction() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				menuRoot.getChildren().clear();
				start(thestage);
				thestage.setTitle("Spookeo's Journey Yo");
				thestage.setScene(gameScene);
			}
		};
	}
	
	private EventHandler<ActionEvent> retryAction() {
		return new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				if (count == 0) {
					zzzz = true; 
					
				}
				else {
					zzzz = false;
				}
				
				isTrue(zzzz);
				gameRoot.getChildren().remove(hero.getImageView());
				gameRoot.getChildren().remove(rectangle);

				hero.setX(300);
				hero.getImageView().setX(300);
				rectangle.setX(300);

				gameRoot.getChildren().add(hero.getImageView());
				gameRoot.getChildren().add(rectangle);


				for(int i = 1; i<= mo.size(); i++){
					mo.remove(i);
					box.setX(500);
					box.getImageView().setX(500);
				}
				mo.remove(1);

				thestage.hide();
				start(thestage);
				thestage.setScene(gameScene);
			}
		};
	}
	
	private EventHandler<ActionEvent> igMenuAction() {
		return new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				igmenuroot.getChildren().add(TITLE_SCREEN.getImageView());
				igmenuroot.getChildren().add(menu.getButton());
				gameRoot.getChildren().remove(igMenu.getButton());
				thestage.setScene(igmenu);
				gameLoop.stop();
			}

		};
	}
	
	private EventHandler<ActionEvent> menuAction() {
		return new EventHandler<ActionEvent>() {
			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				menuRoot.getChildren().add(TITLE_SCREEN.getImageView());
				thestage.setTitle("Spookeo and Fariette");
				thestage.setScene(menuScene);

			}
		};
	}
	
	private EventHandler<ActionEvent> resumeAction() {
		return new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				igmenuroot.getChildren().remove(TITLE_SCREEN.getImageView());
				gameRoot.getChildren().add(igMenu.getButton());
				thestage.setScene(gameScene);
				gameLoop.start();
			}
		};
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
//	private Button startButton() {
//		Button btn = new Button("", START.getImageView());
//		btn.setBackground(new Background(transparent));
//		btn.relocate((WIDTH/2) - 88, 500);
//
//		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				btn.setGraphic(Images.START_HOVER.getImageView());
//			}
//		});
//		
//		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				btn.setGraphic(Images.START.getImageView());
//			}
//		});
//		
//		btn.setOnAction(new EventHandler<ActionEvent>() {
//
//			// action for the start button
//			// sets button to false and creates a rectangle that appears after
//			@Override
//			public void handle(ActionEvent event) {
//				menuRoot.getChildren().remove(TITLE_SCREEN.getImageView());
//				start(thestage);
//				thestage.setTitle("Spookeo's Journey Yo");
//				thestage.setScene(gameScene);
//			}
//		});
//		return btn;
//	}
//	
//	private Button resetButton(){
//		Button btn = new Button("", RESET.getImageView());
//		btn.setBackground(new Background(transparent));
//		btn.relocate(610, 500);
//		
//		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				btn.setGraphic(RESET_HOVER.getImageView());
//			}
//		});
//		
//		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				btn.setGraphic(RESET.getImageView());
//			}
//		});
//		btn.setOnAction(new EventHandler<ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent event) {
//				if (count == 0) {
//					zzzz = true; 
//					
//				}
//				else {
//					zzzz = false;
//				}
//				
//				isTrue(zzzz);
//				gameRoot.getChildren().remove(hero.getImageView());
//				gameRoot.getChildren().remove(rectangle);
//
//				hero.setX(300);
//				hero.getImageView().setX(300);
//				rectangle.setX(300);
//
//				gameRoot.getChildren().add(hero.getImageView());
//				gameRoot.getChildren().add(rectangle);
//
//
//				for(int i = 1; i<= mo.size(); i++){
//					mo.remove(i);
//					box.setX(500);
//					box.getImageView().setX(500);
//				}
//				mo.remove(1);
//
//				thestage.hide();
//				start(thestage);
//				thestage.setScene(gameScene);
//			}
//		});
//		return btn;
//	}
	private boolean isTrue(boolean b) {
		return b;
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

//	private Button igMenuButton(){
//		Button btn = new Button("", MENU.getImageView());
//		btn.setBackground(new Background(transparent));
//		btn.relocate(1440, 25);
//		
//		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				btn.setGraphic(MENU_HOVER.getImageView());
//			}
//		});
//		
//		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				btn.setGraphic(MENU.getImageView());
//			}
//		});
//		
//		btn.setOnAction(new EventHandler<ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent event) {
//				igmenuroot.getChildren().add(TITLE_SCREEN.getImageView());
//				igmenuroot.getChildren().add(menuButton());
//				thestage.setScene(igmenu);
//				gameLoop.stop();
//			}
//
//		});
//		return btn;
//	}

	private Button igControl(){
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
		
		btn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				thestage.setScene(igcontrols);
			}

		});
		return btn;
	}
	// creates button to reach menu
//	private Button menuButton() {
//		Button btn = new Button("", MENU.getImageView());
//		btn.setBackground(new Background(transparent));
//		btn.relocate((WIDTH/2) - 94, 575);
//		
//		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				btn.setGraphic(MENU_HOVER.getImageView());
//			}
//		});
//		
//		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				btn.setGraphic(MENU.getImageView());
//			}
//		});
//
//		btn.setOnAction(new EventHandler<ActionEvent>() {
//
//			// action for the start button
//			// sets button to false and creates a rectangle that appears after
//			@Override
//			public void handle(ActionEvent event) {
//				menuRoot.getChildren().add(TITLE_SCREEN.getImageView());
//				thestage.setTitle("Spookeo and Fariette");
//				thestage.setScene(menuScene);
//
//			}
//		});
//		return btn;
//	}
//	private Button igMenuButton2(){
//		Button btn = new Button("Go back");
//		btn.relocate(810, 600);
//		btn.setOnAction(new EventHandler<ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent event) {
//				thestage.setScene(igmenu);
//			}
//		});
//		return btn;
//	}
//	private Button resumeButton() {
//		Button btn = new Button("", RESUME.getImageView());
//		btn.setBackground(new Background(transparent));
//		btn.relocate(810, 500);
//		
//		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				btn.setGraphic(RESUME_HOVER.getImageView());
//			}
//		});
//		
//		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				btn.setGraphic(RESUME.getImageView());
//			}
//		});
//
//		btn.setOnAction(new EventHandler<ActionEvent>() {
//
//			// action for the start button
//			// sets button to false and creates a rectangle that appears after
//			@Override
//			public void handle(ActionEvent event) {
//				igmenuroot.getChildren().remove(TITLE_SCREEN.getImageView());
//				gameRoot.getChildren().add(igMenuButton());
//				thestage.setScene(gameScene);
//				gameLoop.start();
//			}
//		});
//		return btn;
//	}
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
				jumpmax = rectangle.getY() - 175;
				canJump = true;
			}
			if (canJump) {
				if (rectangle.getY() <= jumpmax || bottom) {
					canJump = false;
					jumpPress = false;
				}
				if (canJump) {
					// jumping = true;
					cooldown = 0;

					if (rectangle.getY() >= jumpmax) {
						rectangle.setY(rectangle.getY() - acceleration);
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
			
			if (em instanceof Box && c.isColliding()) {
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
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				if (event.getCode().equals(KeyCode.D) || event.getCode().equals(KeyCode.RIGHT)) {
					if (!left) {
						hero.getImageView().setScaleX(1);
						righty = true;
						moveRight(rectangle, image, righty);
					}
				}

				if (event.getCode().equals(KeyCode.W) || event.getCode().equals(KeyCode.UP)) {
					jumpPress = true;


				}

				if (event.getCode().equals(KeyCode.A) || event.getCode().equals(KeyCode.LEFT)) {
					if (!right) {	
						lefty = true;
						moveLeft(rectangle, image, lefty);
						hero.getImageView().setScaleX(-1);
					}
				}

			}




		});

	}
	private boolean moveRight(Rectangle rectangle, ImageView image, Boolean rights) {
		if (rights == false) {
			return righty;
		}
		else if (!(rectangle.getX() + acceleration + rectangle.getWidth() >= WIDTH)) {
			rectangle.setX(rectangle.getX() + acceleration);
			image.setX(rectangle.getX());
			hero.setHBX(rectangle.getX());

		}

		return righty;
	}
	private boolean moveLeft(Rectangle rectangle, ImageView image, Boolean lefts) {
		if (lefts == false) {
			return lefty;
		}
		else if (!(rectangle.getX() - acceleration <= 0)) {
			rectangle.setX(rectangle.getX() - acceleration);
			image.setX(rectangle.getX());
			hero.setHBX(rectangle.getX());
		}


		return lefty;
	}



}