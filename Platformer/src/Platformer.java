package hgd;


//imports
import com.sun.xml.internal.ws.api.server.Container;

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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Platformer extends Application {
	
	//global variables
	private static final double movement = 25.0;
	private Stage thestage;
	private Scene scene1, scene2;
	private Pane root1, root2;
	
	//image
	Image background = new Image("https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwjF6NmO0ojPAhVHJiYKHejHBpcQjBwIBA&url=https%3A%2F%2Fimage.freepik.com%2Ffree-vector%2Fmodern-abstract-background_1048-1003.jpg&psig=AFQjCNEMWzy-0tpH-OyXLNUjeo4QCkp-UA&ust=1473728900524583&cad=rjt");
	Image hero = new Image("http://zeldawiki.org/images/thumb/2/23/ALBW_Triforce.png/200px-ALBW_Triforce.png");

	private ImageView image(){
		 		String Images =
		 				"file:///Users/marissawalther/Desktop/desktop/triforce.png";
		 		Image heroImage;
		 		heroImage = new Image(Images, 70, 60, false, false);
		 	ImageView hero = new ImageView(heroImage);
		 
		 	hero = new ImageView(heroImage);
		 	hero.setTranslateX(50);
		 	hero.setTranslateY(50);
		 	return hero;
		 	}
	
	public static void main(String[] args) {
		launch(args);
	}

	// sets scene and adds objects
	@Override
	public void start(Stage primaryStage) {
		
		thestage = primaryStage;
		
		//make rectangle
		final Rectangle rectangle = makeRectangle();
		
		final ImageView image = image();
		
		//make button
		final Button button = startButton();

		
		//make canvas
		Canvas canvas = new Canvas(800, 600);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(background, 0, 0);
		gc.drawImage(hero, rectangle.getX(), rectangle.getY());
		
		//menu		
		//make root
		root1 = new Pane();	
		root1.getChildren().add(canvas);
		root1.getChildren().add(button);
		//make scene
		scene1 = new Scene(root1, 800, 600, Color.AQUAMARINE);
	
		
			
		//game
		//make root
		root2 = new Pane();
		root2.getChildren().add(canvas);
		root2.getChildren().add(rectangle);
		root2.getChildren().add(image);
		//make scene
		scene2 = new Scene(root2, 800, 600, Color.AQUAMARINE);
		moveRectangleOnKeyPress(scene2, rectangle, image);
		
		thestage.setTitle("Spookeo's Journey Yo");
		thestage.setScene(scene1);	
		thestage.show();
	}

	// creates shape that is added when button pressed
	private Rectangle makeRectangle() {
		Rectangle r1 = new Rectangle(300, 200, 100, 150);
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

	// makes shape move....
	private void moveRectangleOnKeyPress(Scene scene, final Rectangle rectangle, ImageView image) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					rectangle.setY(rectangle.getY() - movement);
					image.setY(rectangle.getY() - movement);
					break;
				case RIGHT:
					rectangle.setX(rectangle.getX() + movement);
					image.setX(rectangle.getX() + movement);
					break;
				case DOWN:
					rectangle.setY(rectangle.getY() + movement);
					image.setY(rectangle.getY() + movement);
					break;
				case LEFT:
					rectangle.setX(rectangle.getX() - movement);
					image.setX(rectangle.getX() - movement);
					break;
				}
			}
		});
	}
}
