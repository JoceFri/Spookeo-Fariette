import javafx.scene.shape.Shape;

public class Collision {
	
	
	
	public int isColliding(Shape one, Shape two){
		if (one.equals(two)) {
			return 0;
		}
		else {
			return 1;
		}
	}
}
