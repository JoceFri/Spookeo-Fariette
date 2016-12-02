import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Moveable extends Obj {
	private boolean isAnimated;
	private List<Image> images = new ArrayList<>();
	
	public Moveable(double ex, double why, int sizeX, int sizeY, ImageView person, double hbx, double hby, int hbwidth,
			int hblength, Image... spriteCells) {
		super(ex, why, sizeX, sizeY, person, hbx, hby, hbwidth, hblength);
		images.addAll(Arrays.asList(spriteCells));
	}
	
	public boolean isAnimated() {
		return isAnimated;
	}
	
	public void setIsAnimated(boolean isAnimated) {
		this.isAnimated = isAnimated;
	}
}
