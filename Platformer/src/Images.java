
public interface Images {
	static Animation SPOOKEO_IDLE 	= SpriteLoader.loadAnimation("characters", "spookeo_idle");
	static Animation SPOOKEO_PUSH 	= SpriteLoader.loadAnimation("characters", "spookeo_push");
	static Animation CONTROL 		= SpriteLoader.loadAnimation("buttons", "control");
	static Animation CONTROL_HOVER 	= SpriteLoader.loadAnimation("buttons", "controlHover");
	static Animation MENU 			= SpriteLoader.loadAnimation("buttons", "menu");
	static Animation MENU_HOVER 	= SpriteLoader.loadAnimation("buttons", "menuHover");
	static Animation START 			= SpriteLoader.loadAnimation("buttons", "start");
	static Animation START_HOVER 	= SpriteLoader.loadAnimation("buttons", "startHover");
	static Animation RESUME 		= SpriteLoader.loadAnimation("buttons", "resume");
	static Animation RESUME_HOVER 	= SpriteLoader.loadAnimation("buttons", "resumeHover");
	static Animation RESET			= SpriteLoader.loadAnimation("buttons", "reset");
	static Animation RESET_HOVER	= SpriteLoader.loadAnimation("buttons", "resetHover");
	static Animation TITLE_SCREEN 	= SpriteLoader.loadAnimation("buttons", "titleScreen");
	
	static void initAnimations() {
		SPOOKEO_IDLE.setCycleCount(Animation.INDEFINITE);
		SPOOKEO_PUSH.setCycleCount(Animation.INDEFINITE);
		TITLE_SCREEN.setCycleCount(Animation.INDEFINITE);
	}
	
	static void playAnimations() {
		SPOOKEO_IDLE.play();
		SPOOKEO_PUSH.play();
		TITLE_SCREEN.play();
	}
}
