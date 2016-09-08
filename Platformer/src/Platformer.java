package hgd;

//imports
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Platformer extends Application {
	 public static void main(String[] args) {
	        launch(args);
	    }
@Override
public void start(Stage primaryStage) {
    primaryStage.setTitle("Start");
    Button btn = new Button();
    btn.setText("Start");
    btn.setOnAction(new EventHandler<ActionEvent>() {

    	//action for the start button
        @Override
        public void handle(ActionEvent event) {
           
        }
    });
    
    StackPane root = new StackPane();
    root.getChildren().add(btn);
    primaryStage.setScene(new Scene(root, 500, 500));
    primaryStage.show();
}
}