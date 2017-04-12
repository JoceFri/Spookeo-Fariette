import DavidMohrhardt.animator.Animator;
import javafx.scene.image.ImageView;

public class Enemy extends Actor {

	private int count = 240;
	private int wait = 240;
	private boolean right = false;
	private int type = 0;
	private double speed = 0;
	boolean swapLeft = false;
	boolean swapRight = true;
	boolean detected = false;
	ImageView e = null;
	String action = "IDLE";

	public Enemy(double ex, double why, int sizeX, int sizeY, ImageView person, double hbx, double hby, int hbwidth,
			int hblength, Animator enemy, FrameSetter enemySetter, int typ, double spee) {
		super(ex, why, sizeX, sizeY, person, hbx, hby, hbwidth, hblength, enemy, enemySetter);
		type = typ;
		speed = spee;
	}


	
	public String getAction() {
		return action;
	}
	
	public void setAction(String act) {
		action = act;
	}
	
	public int getType() {
		return type;
	}
	
	public boolean getDetected() {
		return detected;
	}

	public void setDetected(boolean val) {
		detected = val;
	}
	
	public void track(double ex) {
			if (ex < this.getX() && !(this.getRight())) {
				this.setX(this.getX() - speed);
				this.setHBX(this.getX());
			}

			if (ex > this.getX() && !(this.getLeft())) {
				this.setX(this.getX() + speed);
				this.setHBX(this.getX());
			}
		}

	public void move() {

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
					this.setX(this.getX() + speed);
					this.setHBX(this.getX());
					//e.setScaleX(-1);
				}

				if (!right && !(this.getRight())) {
					
					this.setX(this.getX() - speed);
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
