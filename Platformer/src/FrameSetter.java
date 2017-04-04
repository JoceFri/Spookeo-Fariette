import DavidMohrhardt.animator.Animator;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class FrameSetter {

	int spriteCount = 0;
	int cooldown = 0;
	
	// Sets the frames of each animation to look smooth at 60 fps
	public FrameSetter (int numSprites) {
		spriteCount = numSprites;
		cooldown = 60/spriteCount;
	}
	
	public Image getFrame(Animator anim, String action) {
	
		Image temp = null;
		try {
		if (cooldown == 0) {
			temp = SwingFXUtils.toFXImage(anim.getNextFrame(), null);
			cooldown = 60 / spriteCount;
		}
		else {
			
			temp = SwingFXUtils.toFXImage(anim.getFrameAtIndex(action, anim.getCurrentFrameIndex()), null);
			cooldown--;	
		}
		} catch(Exception e) {
			temp = SwingFXUtils.toFXImage(anim.getNextFrame(), null);
			//System.out.println("i broke here");
		}
		return temp;
	}
	
	public void changeCount(int count){
		spriteCount = count;
	}
	
}
