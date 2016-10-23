//imports
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

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
	private static final double HEIGHT = 800.0;
	private static final double WIDTH = 1200.0;

	// movement modifiers
	public static final double acceleration = 8;
	private static final double gravity = 2.0;
	private int cooldown = 120;
	private boolean jumping = false;
	private boolean jumpPress = false;

	private Sounds bgNoise = new Sounds();
	private Sounds jumpSound = new Sounds();
	
//	private Scenes scene = new Scenes();

	private Stage thestage;
	private Scene menuScene, gameScene, controlScene, igmenu, igcontrols;
	private Pane menuRoot, gameRoot, controlRoot, igmenuroot, igcontrolroot;

	private Player hero = new Player(300, 200, 96, 96, new Image("Assets/Art/joey.png"), 300, 200, 96, 96);

	// image
	Image dirt1 = new Image("Assets/Art/2side_ground.png");
	Image dirt2 = new Image("Assets/Art/leftedge_ground.png");
	Image dirt3 = new Image("Assets/Art/rightedge_ground.png");
	Image dirt4 = new Image("Assets/Art/fulldirt_block.png");
	Image dirt5 = new Image("Assets/Art/leftedge_dirt.png");
	Image dirt6 = new Image("Assets/Art/rightedge_dirt.png");
	Image title = new Image("Assets/Art/titlescreen.png");
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

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readTree(new File(url.getPath()));
			ArrayNode framesNode = (ArrayNode)rootNode.get("spookeo").get("frames");
			Iterator<JsonNode> framesIterator =  framesNode.elements();
			List<Sprite> frames = new ArrayList<>();
			while (framesIterator.hasNext()) {
				JsonNode tempNode = framesIterator.next();
				Sprite tempTexture = new Sprite.Builder()
						.spriteSheet(tempNode.get("spriteSheet").textValue())
						.frameNumber(tempNode.get("frameNumber").asInt())
						.height(tempNode.get("height").asInt())
						.width(tempNode.get("width").asInt())
						.xOffset(tempNode.get("xOffset").asInt())
						.yOffset(tempNode.get("yOffset").asInt())
						.build();
				frames.add(tempTexture);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// make rectangle
		final Rectangle rectangle = makeRectangle(hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());
		final Rectangle secondrectangle = makeRectangle(500, HEIGHT - 100, 100, 100);
		// final Rectangle thirdrectangle = makeRectangle(0 , HEIGHT, 1245,
		// 400);
		rectangle.setFill(Color.AQUAMARINE);

		// make backgrounds
		BackgroundFill menuBG = new BackgroundFill(Color.BLACK, null, null);
		BackgroundFill controlBG = new BackgroundFill(Color.AQUAMARINE, null, null);
		BackgroundFill gameBG = new BackgroundFill(Color.SKYBLUE, null, null);
		BackgroundFill igControlBG = new BackgroundFill(Color.BLUE, null, null);
		
		// make canvases
		Canvas menuCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext mc = menuCanvas.getGraphicsContext2D();
		mc.drawImage(title, 300, 100);
		
		Canvas controlCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext cc = controlCanvas.getGraphicsContext2D();
		
		Canvas gameCanvas = new Canvas(WIDTH + 64, HEIGHT + 64);
		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		gc.drawImage(dirt2, 0, HEIGHT - 21);
		gc.drawImage(dirt3, WIDTH, HEIGHT - 21);
		gc.drawImage(dirt5, 0, HEIGHT + 43);
		gc.drawImage(dirt6, WIDTH, HEIGHT + 43);
		for (int i = 64; i <= WIDTH; i += 64) {
			gc.drawImage(dirt1, i, HEIGHT - 21);
			gc.drawImage(dirt4, i, HEIGHT + 43);
		}

		// -------- Menu ----------//

		// make control
		controlRoot = new Pane();
		controlRoot.setBackground(new Background(controlBG));
		controlRoot.getChildren().add(menuButton());
		controlRoot.getChildren().add(startButton());
		controlScene = new Scene(controlRoot, WIDTH, HEIGHT, Color.AQUAMARINE);
		
		// make menu
		menuRoot = new Pane();
		menuRoot.setBackground(new Background(menuBG));
		menuRoot.getChildren().add(menuCanvas);
		menuRoot.getChildren().add(startButton());
		menuRoot.getChildren().add(controlButton());
		
		// make scene
		menuScene = new Scene(menuRoot, WIDTH, HEIGHT, Color.AQUAMARINE);

		// ---------- Game ----------//
		//in game menu
		igmenuroot = new Pane();
		igmenuroot.getChildren().add(resetButton());
		igmenuroot.getChildren().add(igMenuReturn());
		igmenuroot.getChildren().add(resumeButton());
		igmenuroot.getChildren().add(igControl());
		igmenu = new Scene(igmenuroot, WIDTH + 64, HEIGHT + 64, Color.TRANSPARENT);
		//in game controls
		igcontrolroot = new Pane();
		igcontrolroot.setBackground(new Background(igControlBG));
		igcontrolroot.getChildren().add(igMenuButton2());
		igcontrolroot.getChildren().add(resumeButton());
		igcontrols = new Scene(igcontrolroot, WIDTH + 64, HEIGHT + 64, Color.ALICEBLUE);
		// make game
		gameRoot = new Pane();
		gameRoot.setBackground(new Background(gameBG));
		gameRoot.getChildren().add(gameCanvas);
		gameRoot.getChildren().add(rectangle);
		gameRoot.getChildren().add(secondrectangle);
		gameRoot.getChildren().add(igMenuButton());
		// gameRoot.getChildren().add(thirdrectangle);
		gameRoot.getChildren().add(hero.getImageView());
		// make scene
		gameScene = new Scene(gameRoot, WIDTH + 64, HEIGHT + 64, Color.AQUAMARINE);
		moveRectangleOnKeyPress(gameScene, rectangle, hero.getImageView());

		// load sound stuff
		bgNoise.loadSound("Assets/Sound/background.wav");
		jumpSound.loadSound("Assets/Sound/jump1.wav");
		bgNoise.runLoop();

		// loop methods for game mechanics
		 gameLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				gravity(rectangle, hero.getImageView());
				collision(rectangle, secondrectangle, hero.getImageView());
				jump(rectangle, hero.getImageView());
				cooldown++;

			}

		};
		gameLoop.start();
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

		btn.relocate(450, 500);

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
		btn.relocate(600, 400);
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
		btn.relocate(650, 500);

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
		btn.relocate(1200, 25);
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
		btn.relocate(600, 450);
		return btn;
	}
	private Button igControl(){
		Button btn = new Button("Controls");
		btn.relocate(600, 550);
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
		btn.relocate(650, 500);

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
		btn.relocate(600, 400);
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
		btn.relocate(600, 350);

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
		}
	}

	// Method for collision between two rectangles
	private void collision(final Rectangle rectangle, final Rectangle secondrectangle, final ImageView image) {
		// System.out.println(secondrectangle.getX() + " " + (rectangle.getX()
		// +rectangle.getWidth() ));
		if (rectangle.getX() + rectangle.getWidth() <= secondrectangle.getX()
				&& rectangle.getY() >= secondrectangle.getY()
				&& (secondrectangle.getX() - (rectangle.getX() + rectangle.getWidth())) <= acceleration) {

			rectangle.setX(secondrectangle.getX() - rectangle.getWidth() - 1);
			image.setX(rectangle.getX());
			secondrectangle.setX(secondrectangle.getX() + acceleration);

		}
		if (secondrectangle.getX() + secondrectangle.getWidth() <= rectangle.getX()
				&& rectangle.getY() >= secondrectangle.getY()
				&& (rectangle.getX() - (secondrectangle.getX() + secondrectangle.getWidth()) <= acceleration)) {

			rectangle.setX(secondrectangle.getX() + secondrectangle.getWidth() + 1);
			image.setX(rectangle.getX());
			secondrectangle.setX(secondrectangle.getX() - acceleration);

		}

		// check for on top of box

	}

	// jump
	double jumpmax = 0;
	boolean canJump = false;

	private void jump(final Rectangle rectangle, final ImageView image) {
		if (jumpPress) {

			if (cooldown >= 120) {
				jumpSound.run();
				jumpmax = rectangle.getY() - 150;
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
					}
					break;
				case D:
					if (!(rectangle.getX() + acceleration + rectangle.getWidth() >= WIDTH)) {
						rectangle.setX(rectangle.getX() + acceleration);
						image.setX(rectangle.getX());
					}
					break;
				case LEFT:
					if (!(rectangle.getX() - acceleration <= 0)) {
						rectangle.setX(rectangle.getX() - acceleration);
						image.setX(rectangle.getX());
					}
					break;
				case A:
					if (!(rectangle.getX() - acceleration <= 0)) {
						rectangle.setX(rectangle.getX() - acceleration);
						image.setX(rectangle.getX());
					}
					break;
				}
			}
		});
	}
}
