//imports
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Platformer extends Application {

	// global variables
	// resolution
	private static final double HEIGHT = 704.0;
	private static final double WIDTH = 1600.0;

	// movement modifiers
	public static final double acceleration = 8;
	private static final double gravity = 2.0;
	private int cooldown = 120;
	private boolean jumping = false;
	private boolean jumpPress = false;
	private Collision c;

	private Sounds bgNoise = new Sounds();
	private Sounds jumpSound = new Sounds();
	
//	private Scenes scene = new Scenes();

	private Stage thestage;
	private Scene menuScene, gameScene, controlScene, igmenu, igcontrols;
	private Pane menuRoot, gameRoot, controlRoot, igmenuroot, igcontrolroot;

	private Player hero = new Player(300, 200, 65, 64, new Image("Assets/Art/ghost_same.png"), 300, 200, 65, 64);
	private Actor box = new Actor(500, HEIGHT - 100, 100, 100, new Image("Assets/Art/pushable_box.png"), 500, HEIGHT - 100, 100, 100);
	private Nonmoveable rock = new Nonmoveable(100, HEIGHT - 128, 64, 128, new Image("Assets/Art/skinny rock.png"), 100, HEIGHT - 128, 64, 128);
	final Rectangle rectangle = makeRectangle(hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());
	final Rectangle secondrectangle = makeRectangle(500, HEIGHT - 100, 100, 100);
	final Rectangle thirdrectangle = makeRectangle(800, HEIGHT - 200, 442, 200);
	
	ArrayList<Nonmoveable> nmo = new ArrayList<Nonmoveable>();
	
	// image
	Image dirt1 = new Image("Assets/Art/2side_ground.png");
	Image dirt2 = new Image("Assets/Art/leftedge_ground.png");
	Image dirt3 = new Image("Assets/Art/rightedge_ground.png");
	Image dirt4 = new Image("Assets/Art/fulldirt_block.png");
	Image dirt5 = new Image("Assets/Art/leftedge_dirt.png");
	Image dirt6 = new Image("Assets/Art/rightedge_dirt.png");
	Image title = new Image("Assets/Art/titlescreen.png");
	ImageView bg = new ImageView("Assets/Art/BackGround.png");
	AnimationTimer gameLoop;
	URL url = getClass().getResource("Assets/Json/characters.json");
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
		
		bg.setFitHeight(HEIGHT);
		bg.setFitWidth(WIDTH * 2);
		
		nmo.add(new Nonmoveable(0, HEIGHT - 128, 64, 64, dirt2, 0, HEIGHT - 128, 64, 64));
		nmo.add(new Nonmoveable(WIDTH, HEIGHT - 128, 64, 64, dirt3, WIDTH, HEIGHT - 128, 64, 64));
		nmo.add(new Nonmoveable(0, HEIGHT - 64, 64, 64, dirt5, 0, HEIGHT - 64, 64, 64));
		nmo.add(new Nonmoveable(WIDTH, HEIGHT - 64, 64, 64, dirt6, WIDTH, HEIGHT - 64, 64, 64));
		
		
		for (int i = 64; i <= WIDTH; i += 64) {

			nmo.add(new Nonmoveable(i, HEIGHT - 128, 64, 64, dirt1, i, HEIGHT - 128, 64, 64));
			nmo.add(new Nonmoveable(i, HEIGHT - 64, 64, 64, dirt4, i, HEIGHT - 64, 64, 64));
		}

		for (int i = 64; i <= WIDTH; i += 60) {
			
			nmo.add(new Nonmoveable(i + 800 - 65, HEIGHT - 200, 64, 64, dirt4, i + 800 - 65, HEIGHT - 200, 64, 64));
			nmo.add(new Nonmoveable(i + 800 - 65, HEIGHT - 150, 64, 64, dirt4, i + 800 - 65, HEIGHT - 150, 64, 64));
			nmo.add(new Nonmoveable(i + 800 - 65, HEIGHT - 100, 64, 64, dirt4, i + 800 - 65, HEIGHT - 100, 64, 64));
			nmo.add(new Nonmoveable(i + 800 - 65, HEIGHT - 50, 64, 64, dirt4, i + 800 - 65, HEIGHT - 50, 64, 64));
			nmo.add(new Nonmoveable(i + 800 - 65, HEIGHT, 64, 64, dirt4, i + 800 - 65, HEIGHT, 64, 64));
				
		}
		
		nmo.add(rock);
		
//		Animation player = SpriteLoader.loadAnimation("characters", "spookeo");
//		player.setCycleCount(Animation.INDEFINITE);

		// make rectangle
		thirdrectangle.setFill(Color.TRANSPARENT);
		rectangle.setFill(Color.AQUAMARINE);

		// make backgrounds
		BackgroundFill menuBG = new BackgroundFill(Color.BLACK, null, null);
		BackgroundFill controlBG = new BackgroundFill(Color.BLACK, null, null);
		BackgroundFill gameBG = new BackgroundFill(Color.BLACK, null, null);
		BackgroundFill igControlBG = new BackgroundFill(Color.BLACK, null, null);
		BackgroundFill igMenuBG = new BackgroundFill(Color.BLACK, null, null);
		
		
		// make canvases
		Canvas menuCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext mc = menuCanvas.getGraphicsContext2D();
		mc.drawImage(title, 500, 100);
		
		Canvas iGMCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext igmc = iGMCanvas.getGraphicsContext2D();
		igmc.drawImage(title, 500, 100);
		
		Canvas controlCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext cc = controlCanvas.getGraphicsContext2D();
		cc.drawImage(title, 500, 100);
		
		Canvas iGCCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext igcc = iGCCanvas.getGraphicsContext2D();
		igcc.drawImage(title, 500, 100);
		
		Canvas gameCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		for (int i = 0; i < nmo.size(); i++) {
			gc.drawImage(nmo.get(i).getImage(), nmo.get(i).getX(), nmo.get(i).getY());
		}

	
		// -------- Menu ----------//

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
		menuRoot.getChildren().add(menuCanvas);
		menuRoot.getChildren().add(startButton());
		menuRoot.getChildren().add(controlButton());
		menuScene = new Scene(menuRoot, WIDTH, HEIGHT);

		// ---------- Game ----------//

		//in game menu
		igmenuroot = new Pane();
		igmenuroot.setBackground(new Background(igMenuBG));
		igmenuroot.getChildren().add(iGMCanvas);
		igmenuroot.getChildren().add(resetButton());
		igmenuroot.getChildren().add(menuButton());
		igmenuroot.getChildren().add(resumeButton());
		igmenuroot.getChildren().add(igControl());
		igmenu = new Scene(igmenuroot, WIDTH, HEIGHT);
		
		//in game controls
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
		//gameRoot.getChildren().add(secondrectangle);
		gameRoot.getChildren().add(igMenuButton());
		gameRoot.getChildren().add(thirdrectangle);
		gameRoot.getChildren().add(hero.getImageView());
		gameRoot.getChildren().add(box.getImageView());
		//gameRoot.getChildren().add(new Group(player.getImageView()));
		gameScene = new Scene(gameRoot, WIDTH, HEIGHT);
		
		c = new Collision(hero, box);
		moveRectangleOnKeyPress(gameScene, rectangle, hero.getImageView());

		// load sound stuff
		bgNoise.loadSound("Assets/Sound/background.wav");
		jumpSound.loadSound("Assets/Sound/jump1.wav");
		bgNoise.runLoop();

		// loop methods for game mechanics
		 gameLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				c.moveObject();
				if(!c.onTop()){
				gravity(rectangle, hero.getImageView());
				}
				jump(rectangle, hero.getImageView());
				cooldown++;

			}

		};
		gameLoop.start();
		//player.play();
		thestage.setTitle("Spookeo and Fariette");
		thestage.setScene(menuScene);
		thestage.show();
		
	}

	// ----------------------- Creating Objects
	// -----------------------------------//

	// creates rectangle
	private Rectangle makeRectangle(double x, double y, double width, double height) {
		Rectangle r1 = new Rectangle(x, y, width, height);
		r1.setStroke(Color.BLACK);
		r1.setFill(Color.LIMEGREEN);
		r1.setStrokeWidth(3);
		return r1;
	}

	// creates start button
	private Button startButton() {
		Button btn = new Button("", new ImageView("Assets/Art/play.png"));

		btn.relocate(650, 500);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				start(thestage);
				thestage.setTitle("Spookeo's Journey Yo");
				thestage.setScene(gameScene);
				
			}
		});
		return btn;
	}
	private Button resetButton(){
		Button btn = new Button("Retry");
		btn.relocate(650, 500);
		btn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				start(thestage);
				thestage.setScene(gameScene);
				
			}
			
			
		});
		return btn;
	}
	// creates button to reach controls screen
	private Button controlButton() {
		Button btn = new Button("", new ImageView("Assets/Art/controls.png"));
		btn.relocate(850, 500);

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
	
	private Button igMenuButton(){
		Button btn = new Button("Menu");
		btn.relocate(1500, 25);
		btn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				thestage.setScene(igmenu);
				gameLoop.stop();
			}
			
		});
		return btn;
	}
	
	private Button igMenuReturn(){
		Button btn = menuButton();
		btn.relocate(650, 500);
		return btn;
	}
	private Button igControl(){
		Button btn = new Button("Controls");
		btn.relocate(850, 600);
		btn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				thestage.setScene(igcontrols);
			}
			
		});
		return btn;
	}
	// creates button to reach menu
	private Button menuButton() {
		Button btn = new Button("", new ImageView("Assets/Art/menu.png"));
		btn.relocate(850, 500);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				thestage.setTitle("Spookeo and Fariette");
				thestage.setScene(menuScene);
				
			}
		});
		return btn;
	}
	private Button igMenuButton2(){
		Button btn = new Button("Go back");
		btn.relocate(850, 600);
		btn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				thestage.setScene(igmenu);
			}
			
		});
		return btn;
	}
	private Button resumeButton() {
		Button btn = new Button("resume");
		btn.relocate(650, 600);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
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

	private void jump(final Rectangle rectangle, final ImageView image) {
		if (jumpPress) {

			if (cooldown >= 120) {
				jumpSound.run();
				jumpmax = rectangle.getY() - 225;
				canJump = true;
			}
			if (canJump) {
				if (rectangle.getY() <= jumpmax) {
					canJump = false;
					jumping = false;
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

	// makes shape move....
	private void moveRectangleOnKeyPress(Scene scene, final Rectangle rectangle, final ImageView image) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				switch (event.getCode()) {
				case UP:
					jumpPress = true;
					break;
				case W:
					jumpPress = true;
					break;
				case RIGHT:
					if (!(rectangle.getX() + acceleration + rectangle.getWidth() >= WIDTH)) {
						rectangle.setX(rectangle.getX() + acceleration);
						image.setX(rectangle.getX());
						hero.setHBX(rectangle.getX());
					}
					break;
				case D:
					if (!(rectangle.getX() + acceleration + rectangle.getWidth() >= WIDTH)) {
						rectangle.setX(rectangle.getX() + acceleration);
						image.setX(rectangle.getX());
						hero.setHBX(rectangle.getX());
					}
					break;
				case LEFT:
					if (!(rectangle.getX() - acceleration <= 0)) {
						rectangle.setX(rectangle.getX() - acceleration);
						image.setX(rectangle.getX());
						hero.setHBX(rectangle.getX());
					}
					break;
				case A:
					if (!(rectangle.getX() - acceleration <= 0)) {
						rectangle.setX(rectangle.getX() - acceleration);
						image.setX(rectangle.getX());
						hero.setHBX(rectangle.getX());
					}
					break;
				}
			}
		});
	}
}