import DavidMohrhardt.animator.Animator;
import javafx.scene.image.ImageView;

public class Actor extends Moveable {	
	
	public Actor(double ex, double why, int sizeX, int sizeY, ImageView person, double hbx, double hby, int hbwidth,
			int hblength) {
		super(ex, why, sizeX, sizeY, person, hbx, hby, hbwidth, hblength);
	}

	public Actor(double ex, double why, int sizeX, int sizeY, ImageView person, double hbx, double hby, int hbwidth,
			int hblength, Animator enemy, FrameSetter enemySetter) {
		super(ex, why, sizeX, sizeY, person, hbx, hby, hbwidth, hblength, enemy, enemySetter);
	}
}
