import DavidMohrhardt.animator.Animator;
import javafx.scene.image.ImageView;

public class winBox extends Nonmoveable {
	
	Animator animator;
	FrameSetter frames;
	
	public winBox(double ex, double why, int sizeX, int sizeY, ImageView person, double hbx, double hby, int hbwidth,
			int hblength, Animator anim, FrameSetter frame) {
		super(ex, why, sizeX, sizeY, person, hbx, hby, hbwidth, hblength);
		animator = anim;
		frames = frame;
	}
	
	public winBox(double ex, double why, int sizeX, int sizeY, ImageView person, double hbx, double hby, int hbwidth,
			int hblength) {
		super(ex, why, sizeX, sizeY, person, hbx, hby, hbwidth, hblength);
	}

	@Override
	public Animator getAnimator() {
		return animator;
	}
	
	@Override
	public FrameSetter getFrameSetter() {
		return frames;
	}
}
