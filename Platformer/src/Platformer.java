//imports
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Platformer extends Application implements Images {

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

	ArrayList<Nonmoveable> nmo = new ArrayList<Nonmoveable>();
	ArrayList<Moveable> mo = new ArrayList<Moveable>();
	MapLoader m = new MapLoader();

	ImageView bg = new ImageView("Assets/Art/BackGround.png");
	Image title = new Image("Assets/Art/titlescreen.png");

	AnimationTimer gameLoop;
	URL url = getClass().getResource("Assets/Json/characters.json");

	Canvas gameCanvas = new Canvas(WIDTH, HEIGHT);
	GraphicsContext gc = gameCanvas.getGraphicsContext2D();

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

		// Load Map
		m.readIn(WIDTH, HEIGHT, "map.txt");

		// Get nonmoveable objects
		nmo = m.getNMO();

		// Get moveable objects
		mo = m.getMO();


	}

	private void animation() {
		gameLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {

				gc.clearRect(0, 0, WIDTH, HEIGHT);

				for (int i = 0; i < mo.size(); i++) {
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
		gameRoot.getChildren().add(igMenuButton());
		gameRoot.getChildren().add(hero.getImageView());
		gameRoot.getChildren().add(SPOOKEO_PUSH.getImageView());
		gameScene = new Scene(gameRoot, WIDTH, HEIGHT);

		c = new Collision(hero, box);
		moveRectangleOnKeyPress(gameScene, rectangle, hero.getImageView());
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
	boolean lefty = false;
	boolean righty = false;

	private void jump(final Rectangle rectangle, final ImageView image) {
		if (jumpPress) {

			if (cooldown >= 120) {
				jumpSound.run();
				jumpmax = rectangle.getY() - 225;
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
		}
	}

// makes shape move....
private void moveRectangleOnKeyPress(Scene scene, final Rectangle rectangle, final ImageView image) {
	scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent event) {

			if (event.getCode().equals(KeyCode.D) || event.getCode().equals(KeyCode.RIGHT)) {
				if (!left) {
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