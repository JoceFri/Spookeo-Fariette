
//imports
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Platformer extends Application {

	// global variables
	// resolution
	private static final double HEIGHT = 600.0;
	private static final double WIDTH = 800.0;

	// movement modifiers
	private static final double acceleration = 8;
	private static final double gravity = 2.0;
	private int cooldown = 120;
	private boolean jumping = false;
	private boolean jumpPress = false;

	private Sounds bgNoise = new Sounds();
	private Sounds jumpSound = new Sounds();

	private Stage thestage;
	private Scene scene1, scene2;
	private Pane root1, root2;

	private Player hero = new Player(300, 200, 96, 96, new Image("Assets/Art/joey.png"));

	// image
	// Image hero = new Image("Assets/Art/joey.png");
	Image dirt1 = new Image("Assets/Art/2side_ground.png");
	Image dirt2 = new Image("Assets/Art/leftedge_ground.png");
	Image dirt3 = new Image("Assets/Art/rightedge_ground.png");
	Image dirt4 = new Image("Assets/Art/fulldirt_block.png");
	Image dirt5 = new Image("Assets/Art/leftedge_dirt.png");
	Image dirt6 = new Image("Assets/Art/rightedge_dirt.png");

	// --------------------------- Methods to run everything
	// -----------------------------//

	public static void main(String[] args) {
		launch(args);
	}

	// sets scene and adds objects
	@Override
	public void start(Stage primaryStage) {

		thestage = primaryStage;

		// make rectangle
		final Rectangle rectangle = makeRectangle(hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());
		final Rectangle secondrectangle = makeRectangle(500, HEIGHT - 100, 100, 100);
		// final Rectangle thirdrectangle = makeRectangle(0 , HEIGHT, 1245,
		// 400);
		rectangle.setFill(Color.AQUAMARINE);

		// final ImageView image = image();

		// make button
		final Button button = startButton();

		// make canvas
		Canvas canvas = new Canvas(864, 664);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(dirt2, 0, HEIGHT - 21);
		gc.drawImage(dirt3, WIDTH, HEIGHT - 21);
		gc.drawImage(dirt5, 0, HEIGHT + 43);
		gc.drawImage(dirt6, WIDTH, HEIGHT + 43);
		for (int i = 64; i <= WIDTH; i += 64) {
			gc.drawImage(dirt1, i, HEIGHT - 21);
			gc.drawImage(dirt4, i, HEIGHT + 43);
		}

		// -------- Menu ----------//

		// make root
		root1 = new Pane();
		root1.getChildren().add(button);

		// make scene
		scene1 = new Scene(root1, 800, 600, Color.AQUAMARINE);

		// ---------- Game ----------//

		// make root
		root2 = new Pane();
		root2.getChildren().add(canvas);
		root2.getChildren().add(rectangle);
		root2.getChildren().add(secondrectangle);
		// root2.getChildren().add(thirdrectangle);
		root2.getChildren().add(hero.getImageView());
		// make scene
		scene2 = new Scene(root2, 864, 664, Color.AQUAMARINE);
		moveRectangleOnKeyPress(scene2, rectangle, hero.getImageView());

		// load sound stuff
		bgNoise.loadSound("Assets/Sound/background.wav");
		jumpSound.loadSound("Assets/Sound/jump1.wav");
		bgNoise.runLoop();

		// loop methods for game mechanics
		AnimationTimer gameLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				gravity(rectangle, hero.getImageView());
				collision(rectangle, secondrectangle, hero.getImageView());
				jump(rectangle, hero.getImageView());
				cooldown++;

			}

		};
		gameLoop.start();

		thestage.setTitle("Spookeo's Journey Yo");
		thestage.setScene(scene1);
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
		Button btn = new Button("Click here to start your adventure");
		btn.setTranslateX(300);
		btn.setTranslateY(300);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				thestage.setScene(scene2);
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
