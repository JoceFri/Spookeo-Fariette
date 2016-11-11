
public interface Images {
	static Animation SPOOKEO_IDLE = SpriteLoader.loadAnimation("characters", "spookeo_idle");
	static Animation SPOOKEO_PUSH = SpriteLoader.loadAnimation("characters", "spookeo_push");
	
	static void initAnimations() {
		SPOOKEO_IDLE.setCycleCount(Animation.INDEFINITE);
		SPOOKEO_PUSH.setCycleCount(Animation.INDEFINITE);
	}
	
	static void playAnimations() {
		SPOOKEO_IDLE.play();
		SPOOKEO_PUSH.play();
	}
}
