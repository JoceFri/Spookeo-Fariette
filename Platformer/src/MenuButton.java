import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

public class MenuButton extends Button {
	private final BackgroundFill TRANSPARENT = new BackgroundFill(null, null, null);
	
	private Button btn;
	private ImageView regView;
	private ImageView hoverView;
	private double x;
	private double y;
	
	public MenuButton(ImageView regView, ImageView hoverView, double x, double y) {
		this.regView = regView;
		this.hoverView = hoverView;
		this.x = x;
		this.y = y;
		create();
	}
	
	public Button create() {
		btn = new Button("", regView);
		btn.setBackground(new Background(TRANSPARENT));
		btn.relocate(x, y);
		
		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				btn.setGraphic(hoverView);
			}
		});
		
		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn.setGraphic(regView);
			}
		});
		return btn;
	}
	
	public void setAction(EventHandler<ActionEvent> action) {
		btn.setOnAction(action);
	}
	
	public Button getButton() {
		return btn;
	}
}
