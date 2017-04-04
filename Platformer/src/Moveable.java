import javafx.scene.image.ImageView;

public class Moveable extends Obj {
	boolean top = false;
	boolean right = false;
	boolean left = false;
	boolean bottom = false;
	boolean flipped = false;
	public Moveable(double ex, double why, int sizeX, int sizeY, ImageView person, double hbx, double hby, int hbwidth,
			int hblength) {
		super(ex, why, sizeX, sizeY, person, hbx, hby, hbwidth, hblength);
	}
	public boolean getTop(){
		return top;
	}
	
	public void setTop(boolean newTop){
		top = newTop;
	}
	
	public boolean getRight(){
		return right;
	}
	
	public void setRight(boolean newRight){
		right = newRight;
	}
	
	public boolean getLeft(){
		return left;
	}
	
	public void setLeft(boolean newLeft){
		left = newLeft;
	}
	public void resetCollision() {
		top = false;
		right = false;
		left = false;
		bottom = false;
	}
	
	public void setFlipped(boolean flip){
		flipped = flip;
	}
	
	public boolean getFlipped(){
		return flipped;
	}
}
