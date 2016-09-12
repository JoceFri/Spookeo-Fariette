package hgd;

//imports
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Platformer extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private static final int movement = 5;
	//creates a new window pane
	Pane root = new Pane();

	//creates shape that is added when button pressed
	private Rectangle makeRectangle(){
		Rectangle r1 = new Rectangle(50,25,100,140);
		r1.setStroke(Color.BLACK);
		r1.setFill(null);
		r1.setStrokeWidth(3);
		return r1;
	}

	//creates start button
	private Button startButton(){
		Button btn = new Button();
		btn.setText("Start");
		btn.setTranslateX(100);
		btn.setTranslateY(200);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			//action for the start button
			//sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				btn.setVisible(false);
				root.getChildren().add(makeRectangle());
			}
		});
		return btn;
	}



	//makes shape move....
	private void moveRectangleOnKeyPress(Pane root, final Rectangle rectangle) {
		root.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:    rectangle.setTranslateY(movement + 100); break;
				case RIGHT: rectangle.setTranslateX(movement + 100); break;
				case DOWN:  rectangle.setTranslateY(movement - 100); break;
				case LEFT:  rectangle.setTranslateX(movement - 100); break;
				}
			}
		});
	}
	//adds picture for sprite
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


	//sets scene and adds objects
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Start");

		//  


		root.getChildren().add(startButton());
		root.getChildren().add(image());
		primaryStage.setScene(new Scene(root, 500, 500));
		moveRectangleOnKeyPress(root, makeRectangle());
		primaryStage.show();
	}
}