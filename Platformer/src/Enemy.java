import javafx.scene.image.ImageView;

public class Enemy extends Actor {

	private int count = 240;
	private int wait = 240;
	private boolean right = false;

	public Enemy(double ex, double why, int sizeX, int sizeY, ImageView person, double hbx, double hby, int hbwidth,
			int hblength) {
		
		super(ex, why, sizeX, sizeY, person, hbx, hby, hbwidth, hblength);
		//move(Platformer.acceleration);

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
			}
			else {
				wait--;
			}

		}

		else {

			if (right) {
				this.setX(this.getX() - accel);
			}

			else {
				this.setX(this.getX() + accel);
			}

			this.getImageView().setX(this.getX());
			count--;
		}
	}
	public boolean detected(Player hero){
		if(Math.abs(hero.getHBX() - this.getHBX()) <= 196 && Math.abs(hero.getHBY() - this.getHBY()) <= 196){
			return true;
		}
		else {
			return false;
		}
	}
}
