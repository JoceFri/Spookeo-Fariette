import javafx.scene.image.ImageView;

public class Player extends Actor {

	double absoluteX = 0;
	public Player(double ex, double why, int sizeX, int sizeY, ImageView person, double hbx, double hby, int hbwidth,
			int hblength) {
		super(ex, why, sizeX, sizeY, person, hbx, hby, hbwidth, hblength);
		absoluteX = hbx;
	}
	
	
	public void setAbsX (double ex) {
		absoluteX = ex;
	}
	
	public double getAbsX() {
		return absoluteX;
	}
}
