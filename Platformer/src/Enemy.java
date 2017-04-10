import DavidMohrhardt.animator.Animator;
import javafx.scene.image.ImageView;

public class Enemy extends Actor {

	private int count = 240;
	private int wait = 240;
	private boolean right = false;
	boolean swapLeft = false;
	boolean swapRight = true;
	ImageView e = null;

	public Enemy(double ex, double why, int sizeX, int sizeY, ImageView person, double hbx, double hby, int hbwidth,
			int hblength, Animator enemy, FrameSetter enemySetter) {
		super(ex, why, sizeX, sizeY, person, hbx, hby, hbwidth, hblength, enemy, enemySetter);
	}



	public void track(double ex, double accel) {
			if (ex < this.getX() && !(this.getRight())) {
				this.setX(this.getX() - accel);
				this.setHBX(this.getX());
			}

			if (ex > this.getX() && !(this.getLeft())) {
				this.setX(this.getX() + accel);
				this.setHBX(this.getX());
			}
		}

	public void move(double accel) {

		if (count <= 0) {
			if (right) {
				right = false;
			}

			else {
				right = true;
			}

			if (wait <= 0) {
				count = 240;
				wait = 240;
			} else {
				wait--;
			}

		}

		else {
				if (right && !(this.getLeft())) {
					System.out.println("Moved Right. Left collision is " + this.getRight());
					this.setX(this.getX() + accel);
					this.setHBX(this.getX());
					//e.setScaleX(-1);
				}

				if (!right && !(this.getRight())) {
					System.out.println("Moved left. Right collision is " + this.getLeft());
					this.setX(this.getX() - accel);
					this.setHBX(this.getX());
					//e.setScaleX(-1);
				}

				this.getImageView().setX(this.getX());
				count--;
		}
	}

	public boolean detected(Player hero) {
		if (Math.abs(hero.getHBX() - this.getHBX()) <= 196 && Math.abs(hero.getHBY() - this.getHBY()) <= 196) {
			return true;
		} else {
			return false;
		}
	}
}
