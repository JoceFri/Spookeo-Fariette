import java.net.URL;
import DavidMohrhardt.animator.Animator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Platformer extends Application {
	boolean zzzz = false;
	int count = 0;
	// global variables
	// resolution
	private static final double HEIGHT = 704.0;
	private static final double WIDTH = 1600.0;

	// Offset for scrolling
	public double xOffset = 0;
	private int soundCount = 0;

	// Used for animation
	String action = "IDLE";

	// movement modifiers
	public static final double acceleration = 2.0;
	private static final double gravity = 2.0;
	private int cooldown = 120;
	private int lives = 3;
	private int gameState = 0;
	private boolean bottom = false;
	private boolean top = false;
	boolean right = false;
	boolean left = false;
	boolean movingLeft = false;
	boolean movingRight = false;
	boolean movingUp = false;
	boolean flipped = false;
	boolean flowerFlip = false;
	boolean leftFlowerFlip = false;
	boolean rightFlowerFlip = false;
	boolean boxLeft = false;
	boolean boxRight = false;
	private Collision c;
	private Collision c2;
	private Collision c3;
	int cur = 0;
	private Sounds bgNoise = new Sounds();
	private Sounds farietteUgh = new Sounds();
	private Sounds ohYeah = new Sounds();
	private Sounds spookeoTheme = new Sounds();
	private Sounds farietteTheme = new Sounds();
	private Sounds deathSong = new Sounds();
	private Sounds fairyAha = new Sounds();
	private Sounds gotem = new Sounds();
	private Sounds borkbork = new Sounds();
	private Sounds laugh = new Sounds();
	private Sounds playagain = new Sounds();
	private Sounds jumpSound = new Sounds();
	boolean checks = false;
	boolean fachecks = false;
	boolean farietteDead = false;
	int asdf = 0;
	int storyNext = 0;

	boolean s = false;
	private Stage thestage;
	private Scene menuScene, gameScene, controlScene, igmenu, igcontrols, winScene, nextLevel, deathScene,
			characterScene, storyScene, storyScene2, storyScene3, storySceneS, storySceneF;
	private Pane menuRoot, gameRoot, controlRoot, igmenuroot, igcontrolroot, winRoot, nextLevelroot, deathRoot,
			characterRoot, storyRoot, storyRoot2, storyRoot3, storyRootS, storyRootF;

	private Player hero = new Player(300, 200, 65, 64, new ImageView("Assets/Art/triforce.png"), 300, 200, 64, 64);
	private Actor box = new Actor(500, HEIGHT - 100, 100, 100, new ImageView("Assets/Art/pushable_box.png"), 500,
			HEIGHT - 100, 100, 100);
	final Rectangle rectangle = makeRectangle(hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());
	Rectangle rectanglez = new Rectangle(WIDTH / 2 - 25,HEIGHT / 2 + 100,64,64);
	BackgroundFill menuBG = new BackgroundFill(Color.BLACK, null, null);
	BackgroundFill controlBG = new BackgroundFill(Color.BLACK, null, null);
	BackgroundFill gameBG = new BackgroundFill(Color.BLACK, null, null);
	BackgroundFill igControlBG = new BackgroundFill(Color.BLACK, null, null);
	BackgroundFill igMenuBG = new BackgroundFill(Color.BLACK, null, null);
	BackgroundFill transparent = new BackgroundFill(null, null, null);
	Canvas storyCanvas;
	MapLoader m = new MapLoader();
	Moveable[][] mo = null;
	Nonmoveable[][] nmo = null;
	Enemy[][] enemies = null;
	Canvas gameCanvas = null;
	GraphicsContext gc = null;
	Canvas charCanvas = new Canvas(WIDTH, HEIGHT);
	GraphicsContext chc = charCanvas.getGraphicsContext2D();
	Background gameBack = null;
	Rectangle no = new Rectangle(WIDTH / 2 - 110, 150, 377, 150);
	ImageView bg = new ImageView("Assets/Art/BackGround.png");
	Image winScreen = new Image("Assets/Art/endgame.png");
	Image controls = new Image("Assets/Art/controls_sheet2.png");
	Image gameover = new Image("Assets/Art/game_over.png");
	Image fariette = new Image("Assets/Animations/Fariette_IDLE.png");
	Image spookeo = new Image("Assets/Art/Spookeo_IDLE.png");
	
	int places = 0;
	int place = 0;
	boolean spookeoDead = false;
	// Load all the backgrounds
	// Meadows
	ImageView meadows = new ImageView("Assets/Art/happy_meadows.png");
	ImageView clouds = new ImageView("Assets/Art/clouds.png");
	ImageView clouds2 = new ImageView("Assets/Art/clouds.png");
	// Forest
	ImageView backTrees = new ImageView("Assets/Art/backgroundTREES.png");
	ImageView midTrees = new ImageView("Assets/Art/midgroundTREES.png");
	ImageView midTrees2 = new ImageView("Assets/Art/midgroundTREES.png");
	ImageView foreTrees = new ImageView("Assets/Art/foregroundTREES.png");
	ImageView foreTrees2 = new ImageView("Assets/Art/foregroundTREES.png");
	// Transition
	ImageView trans = new ImageView("Assets/Art/transition.png");

	// Tracker
	double check = 0.0;

	Image story1 = new Image("Assets/Art/storyboard_panel1.png");
	Image story2 = new Image("Assets/Art/storyboard_panel2.png");
	Image story3 = new Image("Assets/Art/storyboard_panel3.png");
	Image story4f = new Image("Assets/Art/Storyboard_panel4_fariette.png");
	Image story4s = new Image("Assets/Art/storyboard_panel4_spookeo.png");
	
	String path = System.getProperty("user.dir");

	AnimationTimer gameLoop;
	URL url = getClass().getResource("./src/Assets/Json/characters.json");

	Animator heroAnimation = new Animator(path + "/src/Assets/Animations/fariette.png", path + "/src/Assets/Animations/fariette.ssc");
	FrameSetter heroFrame = new FrameSetter(9);

	Animator farietteSelect = new Animator(path + "/src/Assets/Animations/fariette.png", path + "/src/Assets/Animations/fariette.ssc");
	FrameSetter farietteFrame = new FrameSetter(9);
	Actor farietteImage = new Actor(1000, 252, 192, 192, new ImageView("Assets/Art/k.png"), 1000, 252, 192, 192);

	Animator farietteSelectz = new Animator(path + "/src/Assets/Animations/fariette.png", path + "/src/Assets/Animations/fariette.ssc");
	FrameSetter farietteFramez = new FrameSetter(9);
	
	Animator spookeoSelect = new Animator(path + "/src/Assets/Animations/spookeo_sheet.png",
			path + "/src/Assets/Animations/Spookeo.ssc");
	FrameSetter spookeoFrame = new FrameSetter(9);
	
	Animator spookeoSelectz = new Animator(path + "/src/Assets/Animations/spookeo_sheet.png",
			path + "/src/Assets/Animations/Spookeo.ssc");
	FrameSetter spookeoFramez = new FrameSetter(9);
	
	Actor spookeoImage = new Actor(450, 252, 192, 192, new ImageView("Assets/Art/joey.png"), 450, 252, 192, 192);

	//
	// Animator dog = new Animator("src/Assets/Animations/ghostdog.png",
	// "src/Assets/Animations/ghostdog.ssc");
	// FrameSetter doggo = new FrameSetter(8);
	// Enemy enemy1 = new Enemy(900, 300, 64, 64, new
	// ImageView("Assets/Art/joey.png"), 900, 300, 64, 64);
	String action2 = "IDLE";

	Animator startButton = new Animator(path + "/src/Assets/Animations/buttons.png", path + "/src/Assets/Animations/buttons.ssc");
	FrameSetter play = new FrameSetter(2);
	
	GraphicsContext sc;
	Animator titleScreen = new Animator(path + "/src/Assets/Animations/title_screen.png",
			path + "/src/Assets/Animations/title_screen.ssc");
	FrameSetter title = new FrameSetter(2);
	Actor titleImage = new Actor(1000, 252, 192, 192, new ImageView("Assets/Art/titlescreen.png"), 1000, 252, 192, 192);

	Animator rocks = new Animator(path + "/src/Assets/Animations/rock.png", path + "/src/Assets/Animations/rock.ssc");
	FrameSetter roc = new FrameSetter(2);

	Animator dogtest;

	// --------------------------- Methods to run everything
	// -----------------------------//

	public static void main(String[] args) {
		launch(args);
	}

	// sets scene and adds objects
	@Override
	public void start(Stage primaryStage) {

		thestage = primaryStage;

		// dog.startActionAnimation(action2);
		// enemy1.getImageView().setImage(doggo.getFrame(dog, action2));
		// Set background positions
		clouds.setX(0);
		clouds2.setX(1800);
		midTrees.setX(0);
		midTrees2.setX(1800);
		foreTrees.setX(0);
		foreTrees2.setX(2000);

		gameCanvas = new Canvas(m.getWidth(), m.getHeight());
		gc = gameCanvas.getGraphicsContext2D();

		heroAnimation.startActionAnimation("IDLE");
		hero.getImageView().setImage(heroFrame.getFrame(heroAnimation, action));

		spookeoSelect.startActionAnimation("IDLE");
		spookeoImage.getImageView().setImage(spookeoFrame.getFrame(spookeoSelect, "IDLE"));
		
		

		farietteSelect.startActionAnimation("IDLE");
		farietteImage.getImageView().setImage(farietteFrame.getFrame(farietteSelect, "IDLE"));

		titleScreen.startActionAnimation("FLASH");
		titleImage.getImageView().setImage(title.getFrame(titleScreen, "FLASH"));

		load();

		// make backgrounds
		draw();

		// load sound stuff
		sound();
		
		// loop methods for game mechanics
		gameStart();
	}

	private void load() {
		bg.setFitHeight(HEIGHT);
		bg.setFitWidth(WIDTH);

		// Load Map
		LevelBuilder();
		mo = m.getMO();
		nmo = m.getNMO();
		enemies = m.getEnemies();

		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[i].length; j++) {
				if (enemies[i][j] != null) {
					enemies[i][j].getAnimator().startActionAnimation(enemies[i][j].getAction());
					enemies[i][j].getImageView()
							.setImage(enemies[i][j].getFrameSetter().getFrame(enemies[i][j].getAnimator(), enemies[i][j].getAction()));
				}
			}
		}
		for (int i = 0; i < nmo.length; i++) {
			for (int j = 0; j < nmo[i].length; j++) {
				if (nmo[i][j] != null) {
					gc.drawImage(nmo[i][j].getImageView().getImage(), nmo[i][j].getX(), nmo[i][j].getY());
					// c3 = new Collision(enemies[i][j], nmo[i][j]);

				}
			}
		}
		for (int i = 0; i < mo.length; i++) {
			for (int j = 0; j < mo[i].length; j++) {
				if (mo[i][j] != null) {
					if (mo[i][j] instanceof Rock) {

					} else {
						gc.drawImage(mo[i][j].getImageView().getImage(), mo[i][j].getX(), mo[i][j].getY());
					}
					if (mo[i][j] instanceof Box) {
						c2 = new Collision(mo[i][j], hero);
					}
				}
			}
		}
		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[i].length; j++) {
				if (enemies[i][j] != null) {
					c3 = new Collision(enemies[i][j], nmo[i][j]);
					// dogtest = enemies[i][j].getAnimator();
					gc.drawImage(enemies[i][j].getImageView().getImage(), enemies[i][j].getX(), enemies[i][j].getY());
				}
			}
		}

	}

	private void gameStart() {
		gameLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				deathCheck();
				if (gameState == 0) {
					animations();
					chc.drawImage(titleImage.getImageView().getImage(), titleImage.getX(), titleImage.getY(),
							titleImage.getWidth(), titleImage.getHeight());
				}
				if (gameState == 1) {
					animations();
					chc.clearRect(0, 0, WIDTH, HEIGHT);
					chc.drawImage(farietteImage.getImageView().getImage(), farietteImage.getX(), farietteImage.getY(),
							farietteImage.getWidth(), farietteImage.getHeight());
					chc.drawImage(spookeoImage.getImageView().getImage(), spookeoImage.getX(), spookeoImage.getY(),
							spookeoImage.getWidth(), spookeoImage.getHeight());

				}

				if (gameState == 2) {
					fachecks = false;
					farietteDead = false;
					checks = false;
					spookeoDead = false;
					resetCollision();
					collisionCheck();
					cameraScroll(xOffset, 0);
					animations();
					update();
					detection();
					backgroundHelper();
					if (cur == 3 || cur == 8) {
						autoScroll();
					}
					if (!top) {
						gravity(rectangle, hero.getImageView());
					}
					if (top) {
						jumping = false;
						cooldown = 120;
					}
					if (movingRight && !left) {
						moveRight(rectangle, hero.getImageView());
					}
					if (movingLeft && !right) {
						moveLeft(rectangle, hero.getImageView());
					}
					if (movingUp) {
						jump(rectangle, hero.getImageView());
					}
					cooldown++;
				}
				if (gameState == 5) {
					if (cur <= 3) {
						animations();
						spookeoDead = true;
					}
					else if (cur >= 5) {
						animations();
						farietteDead = true;
					}
				}
			}
		};

		gameLoop.start();
		thestage.setTitle("Spookeo and Fariette");
		thestage.setScene(menuScene);

		thestage.show();
	}

	public void backgroundHelper() {

		if (clouds.getX() % 1800 == 0 && clouds.getX() < 0) {
			clouds.setX(1800);
		}
		if (clouds2.getX() % 1800 == 0 && clouds2.getX() < 0) {
			clouds2.setX(1800);
		}
		clouds.setX(clouds.getX() - .25);
		clouds2.setX(clouds2.getX() - .25);

		if (check < xOffset) {
			if (midTrees.getX() % 1800 == 0 && midTrees.getX() < 0) {
				midTrees.setX(1800);
			}

			if (midTrees2.getX() % 1800 == 0 && midTrees2.getX() < 0) {
				midTrees2.setX(1800);
			}

			midTrees.setX(midTrees.getX() - .2);
			midTrees2.setX(midTrees2.getX() - .2);

			if (foreTrees.getX() % 2000 == 0 && foreTrees.getX() < 0) {
				foreTrees.setX(2000);
			}

			if (foreTrees2.getX() % 2000 == 0 && foreTrees.getX() < 0) {
				foreTrees2.setX(2000);
			}

			foreTrees.setX(foreTrees.getX() - .5);
			foreTrees2.setX(foreTrees2.getX() - .5);

			trans.setX(trans.getX() - .5);
		}

		if (check > xOffset) {
			if (midTrees.getX() % 1800 == 0 && midTrees.getX() > 0) {
				midTrees.setX(-1800);
			}

			if (midTrees2.getX() % 1800 == 0 && midTrees2.getX() < 0) {
				midTrees2.setX(-1800);
			}

			midTrees.setX(midTrees.getX() + .2);
			midTrees2.setX(midTrees2.getX() + .2);

			if (foreTrees.getX() % 2000 == 0 && foreTrees.getX() > 0) {
				foreTrees.setX(-2000);
			}

			if (foreTrees2.getX() % 2000 == 0 && foreTrees.getX() > 0) {
				foreTrees2.setX(-2000);
			}

			foreTrees.setX(foreTrees.getX() + .5);
			foreTrees2.setX(foreTrees2.getX() + .5);

			trans.setX(trans.getX() + .5);
		}
		check = xOffset;
	}

	public void animations() {
		hero.getImageView().setImage(heroFrame.getFrame(heroAnimation, action));
		if (checks && asdf <= 55) {
			spookeoImage.getImageView().setImage(spookeoFrame.getFrame(spookeoSelect, "UNF"));
			asdf++;
		} else if (checks) {
			spookeoImage.getImageView().setImage(spookeoFrame.getFrame(spookeoSelect, "UNF_IDLE"));
		} else {
			spookeoImage.getImageView().setImage(spookeoFrame.getFrame(spookeoSelect, "IDLE"));

		}

		if (fachecks && asdf <= 55) {
			farietteImage.getImageView().setImage(farietteFrame.getFrame(farietteSelect, "COFFEE_DRINK"));
			asdf++;

		} else if (fachecks) {
			farietteImage.getImageView().setImage(farietteFrame.getFrame(farietteSelect, "HAPPY"));
		} else {
			farietteImage.getImageView().setImage(farietteFrame.getFrame(farietteSelect, "IDLE"));

		}
		if (spookeoDead && asdf < 60) {
				
				rectanglez.setFill(new ImagePattern(spookeoFramez.getFrame(spookeoSelectz, "DEATH")));
				asdf++;
		
		}
		else if (spookeoDead) {
			rectanglez.setFill(new ImagePattern(spookeoFramez.getFrame(spookeoSelectz, "DEATH_IDLE")));
		}
		if (farietteDead && asdf < 60) {
			
			rectanglez.setFill(new ImagePattern(farietteFramez.getFrame(farietteSelectz, "DEATH")));
			asdf++;
	
	}
	else if (farietteDead) {
		rectanglez.setFill(new ImagePattern(farietteFramez.getFrame(farietteSelectz, "DEATH_IDLE")));
	}

		titleImage.getImageView().setImage(title.getFrame(titleScreen, "FLASH"));

		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[i].length; j++) {
				if (enemies[i][j] != null) {
					enemies[i][j].getImageView()
							.setImage(enemies[i][j].getFrameSetter().getFrame(enemies[i][j].getAnimator(), enemies[i][j].getAction()));
				}
			}
		}
		
		for (int i = 0; i < nmo.length; i++) {
			for (int j = 0; j < nmo[i].length; j++) {
				if (nmo[i][j] != null & nmo[i][j] instanceof winBox && nmo[i][j].getAnimator() != null) {
					nmo[i][j].getImageView().setImage(nmo[i][j].getFrameSetter().getFrame(nmo[i][j].getAnimator(), "IDLE"));
					nmo[i][j].getImageView().setScaleX(-1);
				}
			}
		}

	}

	private void cameraScroll(double xOffset, double yOffset) {
		gameCanvas.setTranslateX(-xOffset);
	}

	private void update() {
		gc.clearRect(0, 0, m.getWidth(), m.getHeight());
		for (int i = 0; i < nmo.length; i++) {
			for (int j = 0; j < nmo[i].length; j++) {
				if (nmo[i][j] != null) {
					gc.drawImage(nmo[i][j].getImageView().getImage(), nmo[i][j].getX(), nmo[i][j].getY());
				}
			}
		}
		for (int i = 0; i < mo.length; i++) {
			for (int j = 0; j < mo[i].length; j++) {
				if (mo[i][j] != null) {
					if (mo[i][j] instanceof Rock) {

					} else {
						gc.drawImage(mo[i][j].getImageView().getImage(), mo[i][j].getX(), mo[i][j].getY());
					}
				}
			}
		}
		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[i].length; j++) {
				if (enemies[i][j] != null) {
					gc.drawImage(enemies[i][j].getImageView().getImage(), enemies[i][j].getX(), enemies[i][j].getY());
				}
			}
		}
		// gc.drawImage(enemy1.getImageView().getImage(), enemy1.getX(),
		// enemy1.getY());
	}

	private void sound() {

		bgNoise.loadSound("Assets/Sound/Main_Theme.wav");
		jumpSound.loadSound("Assets/Sound/jump1.wav");
		farietteUgh.loadSound("Assets/Sound/ugh.wav");
		ohYeah.loadSound("Assets/Sound/ohYeah.wav");
		borkbork.loadSound("Assets/Sound/Dog_Bark_3.wav");
		fairyAha.loadSound("Assets/Sound/-Ah-ha!-.wav");
		gotem.loadSound("Assets/Sound/-I_Got_You!-.wav");
		deathSong.loadSound("Assets/Sound/Spookeo_and_Fairiette_Death.wav");
		laugh.loadSound("Assets/Sound/Evil_Laughter_2.wav");
		playagain.loadSound("Assets/Sound/Spookeo_and_Fairiette_Play_Again-.wav");
		if (soundCount == 0) {
			bgNoise.runLoop();
			soundCount++;
		}
	}

	private void draw() {
		Canvas winCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext wc = winCanvas.getGraphicsContext2D();
		wc.drawImage(winScreen, WIDTH / 2 - 200, HEIGHT / 2 - 250);

		// make canvases
		Canvas menuCanvas = new Canvas(WIDTH, HEIGHT);
		//GraphicsContext mc = menuCanvas.getGraphicsContext2D();
		// mc.drawImage(, 0, 100);

		menuCanvas.getGraphicsContext2D();
		Canvas deathCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext dc = deathCanvas.getGraphicsContext2D();
		dc.drawImage(gameover, WIDTH / 2 - 250, HEIGHT / 2 - 250);

		Canvas iGMCanvas = new Canvas(WIDTH, HEIGHT);
		Canvas controlCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext cc = controlCanvas.getGraphicsContext2D();
		cc.drawImage(controls, 0, 100);
		iGMCanvas.getGraphicsContext2D();

		Canvas iGCCanvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext igcc = iGCCanvas.getGraphicsContext2D();
		igcc.drawImage(controls, 0, 100);

		storyCanvas = new Canvas(WIDTH, HEIGHT);
		sc = storyCanvas.getGraphicsContext2D();
		sc.drawImage(story1, WIDTH / 2 - 500, HEIGHT / 2 - 250);

		Canvas storyCanvas2 = new Canvas(WIDTH, HEIGHT);
		GraphicsContext sc2 = storyCanvas2.getGraphicsContext2D();
		sc2.drawImage(story2, WIDTH / 2 - 500, HEIGHT / 2 - 250);

		Canvas storyCanvas3 = new Canvas(WIDTH, HEIGHT);
		GraphicsContext sc3 = storyCanvas3.getGraphicsContext2D();
		sc3.drawImage(story3, WIDTH / 2 - 500, HEIGHT / 2 - 250);

		Canvas storyCanvasS = new Canvas(WIDTH, HEIGHT);
		GraphicsContext scS = storyCanvasS.getGraphicsContext2D();
		scS.drawImage(story4s, WIDTH / 2 - 500, HEIGHT / 2 - 250);

		Canvas storyCanvasF = new Canvas(WIDTH, HEIGHT);
		GraphicsContext scF = storyCanvasF.getGraphicsContext2D();
		scF.drawImage(story4f, WIDTH / 2 - 500, HEIGHT / 2 - 250);
		// -------- Menu ----------//
		// story scene
		storyRoot = new Pane();
		storyRoot.setBackground(new Background(menuBG));
		storyRoot.getChildren().add(storyCanvas);
		storyRoot.getChildren().add(nextStoryButton());
		storyScene = new Scene(storyRoot, WIDTH, HEIGHT);

		storyRoot2 = new Pane();
		storyRoot2.setBackground(new Background(menuBG));
		storyRoot2.getChildren().add(storyCanvas2);
		storyRoot2.getChildren().add(nextStoryButton2());
		storyScene2 = new Scene(storyRoot2, WIDTH, HEIGHT);

		storyRoot3 = new Pane();
		storyRoot3.setBackground(new Background(menuBG));
		storyRoot3.getChildren().add(storyCanvas3);
		storyRoot3.getChildren().add(nextStoryButton3());
		storyScene3 = new Scene(storyRoot3, WIDTH, HEIGHT);

		storyRootS = new Pane();
		storyRootS.setBackground(new Background(menuBG));
		storyRootS.getChildren().add(storyCanvasS);
		storyRootS.getChildren().add(playB());
		storySceneS = new Scene(storyRootS, WIDTH, HEIGHT);

		storyRootF = new Pane();
		storyRootF.setBackground(new Background(menuBG));
		storyRootF.getChildren().add(storyCanvasF);
		storyRootF.getChildren().add(playB());
		storySceneF = new Scene(storyRootF, WIDTH, HEIGHT);

		// make win scene
		winRoot = new Pane();
		winRoot.setBackground(new Background(menuBG));
		winRoot.getChildren().add(winCanvas);
		winRoot.getChildren().add(new ImageView("Assets/Art/endgame_creditsLEFT.png"));
		winRoot.getChildren().add(new ImageView("Assets/Art/endgame_creditsRIGHT.png"));
		winRoot.getChildren().get(1).setLayoutX(50);
		winRoot.getChildren().get(1).setLayoutY(0);
		winRoot.getChildren().get(2).setLayoutX(WIDTH / 2 + 300);
		winRoot.getChildren().get(2).setLayoutY(0);	
		winRoot.getChildren().add(winMenuButton());
		winScene = new Scene(winRoot, WIDTH, HEIGHT);

		// make death scene
		deathRoot = new Pane();
		deathRoot.setBackground(new Background(menuBG));
		deathRoot.getChildren().add(deathCanvas);
		deathRoot.getChildren().add(heroDeath());
		deathRoot.getChildren().add(menuButton());
		deathScene = new Scene(deathRoot, WIDTH, HEIGHT);

		// make control
		controlRoot = new Pane();
		controlRoot.setBackground(new Background(controlBG));
		controlRoot.getChildren().add(controlCanvas);
		controlRoot.getChildren().add(menuButton());
		controlRoot.getChildren().add(startButton());
		controlScene = new Scene(controlRoot, WIDTH, HEIGHT);

		// make menu
		menuRoot = new Pane();
		menuRoot.setBackground(new Background(menuBG));

		menuRoot.getChildren().add(new ImageView(title.getFrame(titleScreen, "FLASH")));
		menuRoot.getChildren().get(0).setTranslateX(WIDTH / 2 - 150);
		menuRoot.getChildren().get(0).setTranslateY(100);
		menuRoot.getChildren().add(menuCanvas);
		menuRoot.getChildren().add(startButton2());
		menuRoot.getChildren().add(controlButton());
		menuScene = new Scene(menuRoot, WIDTH, HEIGHT);

		// make character select
		characterRoot = new Pane();
		characterRoot.setBackground(new Background(menuBG));
		characterRoot.getChildren().add(charCanvas);
		characterRoot.getChildren().add(spookeoButton());
		characterRoot.getChildren().add(farietteButton());
		characterScene = new Scene(characterRoot, WIDTH, HEIGHT);

		// next
		nextLevelroot = new Pane();
		nextLevelroot.getChildren().add(nextButton());
		nextLevelroot.getChildren().add(nextcongrats());
		nextLevelroot.setBackground(new Background(menuBG));
		nextLevel = new Scene(nextLevelroot, WIDTH, HEIGHT);
		// ---------- Game ----------//

		// in game menu
		igmenuroot = new Pane();
		
		igmenuroot.getChildren().add(new ImageView(title.getFrame(titleScreen, "FLASH")));
		igmenuroot.getChildren().get(0).setTranslateX(WIDTH / 2 - 150);
		igmenuroot.getChildren().get(0).setTranslateY(100);
		igmenuroot.setBackground(new Background(igMenuBG));
		igmenuroot.getChildren().add(iGMCanvas);
		igmenuroot.getChildren().add(resetButton());
		igmenuroot.getChildren().add(menuButton2());
		igmenuroot.getChildren().add(resumeButton());
		igmenuroot.getChildren().add(igControl());
		igmenu = new Scene(igmenuroot, WIDTH, HEIGHT);

		// in game controls
		igcontrolroot = new Pane();
		igcontrolroot.setBackground(new Background(igControlBG));
		igcontrolroot.getChildren().add(iGCCanvas);
		igcontrolroot.getChildren().add(igMenuButton2());
		igcontrolroot.getChildren().add(resumeButton());
		igcontrols = new Scene(igcontrolroot, WIDTH, HEIGHT);

		// make game
		gameRoot = new Pane();
		if (m.getType() == 1) {
			gameRoot.getChildren().add(clouds);
			gameRoot.getChildren().add(clouds2);
			gameRoot.getChildren().add(meadows);
		} else if (m.getType() == 2) {
			gameRoot.getChildren().add(backTrees);
			gameRoot.getChildren().add(midTrees);
			gameRoot.getChildren().add(midTrees2);
			gameRoot.getChildren().add(foreTrees);
			gameRoot.getChildren().add(foreTrees2);
		} else if (m.getType() == 3) {
			trans.setScaleX(1);
			gameRoot.getChildren().add(clouds);
			gameRoot.getChildren().add(clouds2);
			gameRoot.getChildren().add(trans);
		} else if (m.getType() == 4) {
			trans.setScaleX(-1);
			gameRoot.getChildren().add(clouds);
			gameRoot.getChildren().add(clouds2);
			gameRoot.getChildren().add(trans);
		}
		gameRoot.getChildren().add(gameCanvas);
		gameRoot.getChildren().add(rectangle);
		gameRoot.getChildren().add(igMenuButton());
		gameRoot.getChildren().add(hero.getImageView());
		gameScene = new Scene(gameRoot, WIDTH, HEIGHT);

		c = new Collision(hero, box);
		moveRectangleOnKeyPress(gameScene, rectangle, hero.getImageView());
	}

	// ----------------------- Creating Objects
	// -----------------------------------//

	// creates rectangle
	private Rectangle makeRectangle(double x, double y, double width, double height) {
		Rectangle r1 = new Rectangle(x, y, width, height);
		r1.setStroke(Color.TRANSPARENT);
		r1.setFill(Color.TRANSPARENT);
		r1.setStrokeWidth(3);
		return r1;
	}

	// creates start button
	private Button startButton() {
		asdf = 0;
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "PLAY")));
		btn.setBackground(new Background(transparent));

		btn.relocate((WIDTH / 2) - 88, 500);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "PLAY2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "PLAY")));
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				// menuRoot.getChildren().remove(TITLE_SCREEN.getImageView());
				start(thestage);
				reset();
				lives = 3;
				thestage.setTitle("Spookeo's Journey Yo");
				thestage.setScene(gameScene);

			}
		});
		return btn;
	}

	// start button for title
	private Button startButton2() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "PLAY")));
		btn.setBackground(new Background(transparent));

		btn.relocate(550, 500);
		startButton = new Animator("src/Assets/Animations/buttons.png", "src/Assets/Animations/buttons.ssc");
		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "PLAY2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "PLAY")));
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				// menuRoot.getChildren().remove(TITLE_SCREEN.getImageView());
				// start(thestage);
				thestage.setTitle("Spookeo's Journey Yo");
				gameState = 1;
				thestage.setScene(characterScene);

			}
		});
		return btn;
	}

	// spookeo's character select button
	private Button spookeoButton() {
		
		asdf = 0;
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "JOEY")));

		btn.setBackground(new Background(transparent));
		btn.relocate((WIDTH / 2) - 300, (HEIGHT / 2) + 128);
		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "JOEY2")));
				checks = true;
				// asdfcounter(asdf);

			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				checks = false;
				btn.setGraphic(new ImageView(play.getFrame(startButton, "JOEY")));
				asdf = 0;

			}
		});
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				cur = 0;
				reset();
				s = true;
				bgNoise.stop();
				bgNoise.reset();
				spookeoTheme.loadSound("Assets/Sound/Main_Theme.wav");
				spookeoTheme.runLoop();
				// hero.getImageView().setImage(spookeo);
				heroAnimation = new Animator("src/Assets/Animations/spookeo_sheet.png",
						"src/Assets/Animations/Spookeo.ssc");
				heroAnimation.startActionAnimation("IDLE");
				hero.getImageView().setImage(heroFrame.getFrame(heroAnimation, "IDLE"));

				gameState = 2;
				thestage.setScene(storyScene);
			}
		});

		return btn;
	}

	// fariette's character select button
	private Button farietteButton() {
		asdf = 0;
		fachecks = false;
		// Load animation as button
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "ALICIA")));
		btn.setBackground(new Background(transparent));
		btn.relocate((WIDTH / 2) + 250, (HEIGHT / 2) + 128);
		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				fachecks = true;
				btn.setGraphic(new ImageView(play.getFrame(startButton, "ALICIA2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				fachecks = false;
				btn.setGraphic(new ImageView(play.getFrame(startButton, "ALICIA")));
				asdf = 0;
			}
		});
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				s = false;
				cur = 5;
				resetF();
				bgNoise.stop();
				bgNoise.reset();
				farietteTheme.loadSound("Assets/Sound/Spooky_Forrest_Theme.wav");
				farietteTheme.runLoop();
				// hero.getImageView().setImage(fariette);
				heroAnimation = new Animator("src/Assets/Animations/fariette.png",
						"src/Assets/Animations/fariette.ssc");
				heroAnimation.startActionAnimation("IDLE");
				hero.getImageView().setImage(heroFrame.getFrame(heroAnimation, "IDLE"));

				gameState = 2;
				thestage.setScene(storyScene);
			}
		});
		return btn;
	}

	private Button nextStoryButton() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "NEXT")));
		btn.setBackground(new Background(transparent));
		btn.relocate(WIDTH / 2 - 100, HEIGHT - 100);
		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "NEXT2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "NEXT")));
			}
		});
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				thestage.setScene(storyScene2);
			}

		});
		return btn;
	}

	private Button nextStoryButton2() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "NEXT")));
		btn.setBackground(new Background(transparent));
		btn.relocate(WIDTH / 2 - 100, HEIGHT - 100);
		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "NEXT2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "NEXT")));
			}
		});
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				thestage.setScene(storyScene3);
			}

		});
		return btn;
	}

	private Button nextStoryButton3() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "NEXT")));
		btn.setBackground(new Background(transparent));
		btn.relocate(WIDTH / 2 - 100, HEIGHT - 100);
		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "NEXT2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "NEXT")));
			}
		});
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (s) {
					thestage.setScene(storySceneS);
				} else {
					thestage.setScene(storySceneF);
				}
			}

		});
		return btn;
	}

	private Button playB() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "PLAY")));
		btn.setBackground(new Background(transparent));
		btn.relocate(WIDTH / 2 - 100, HEIGHT - 100);
		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "PLAY2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "PLAY")));
			}
		});
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				start(thestage);
				thestage.setScene(gameScene);
			}

		});
		return btn;
	}

	// next level button
	private Button nextButton() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "LEVEL")));
		btn.setBackground(new Background(transparent));
		btn.relocate(WIDTH / 2, HEIGHT / 2);
		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "LEVEL2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "LEVEL")));
			}
		});
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				cur++;
				reset();
				start(thestage);
				thestage.setScene(gameScene);
			}

		});
		return btn;
	}

	private Button winMenuButton() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "MENU")));
		btn.setBackground(new Background(transparent));
		// btn.setBackground(new Background(transparent));
		btn.relocate((WIDTH / 2), 625);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "MENU2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "MENU")));
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				reset();
				lives = 3;
				/*
				 * for(int i = 0; i<= mo.size(); i++){ mo.remove(i);
				 * box.setX(500); box.getImageView().setX(500); }
				 * //mo.remove(1);
				 */
				load();
				gameState = 0;
				start(thestage);
				thestage.setScene(menuScene);
			}
		});
		return btn;
	}

	private Button resetButton() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "RESET")));
		btn.setBackground(new Background(transparent));
		btn.relocate(610, 500);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "RESET2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "RESET")));
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				reset();
				lives = 3;
				thestage.hide();
				load();
				start(thestage);
				thestage.setScene(gameScene);
			}
		});
		return btn;
	}

	// creates button to reach controls screen
	private Button controlButton() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "CONTROLS")));
		btn.setBackground(new Background(transparent));

		btn.relocate(850, 500);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "CONTROLS2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "CONTROLS")));
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				thestage.setTitle("Controls");
				thestage.setScene(controlScene);
			}
		});
		return btn;
	}

	private Button igMenuButton() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "MENU")));
		btn.setBackground(new Background(transparent));
		btn.relocate(1440, 25);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "MENU2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "MENU")));
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//igmenuroot.getChildren().add(new ImageView(title.getFrame(titleScreen, "FLASH")));
				thestage.setScene(igmenu);
				gameLoop.stop();
			}

		});
		return btn;
	}

	private Button igControl() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "CONTROLS")));
		btn.setBackground(new Background(transparent));
		btn.relocate(810, 600);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "CONTROLS2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "CONTROLS")));
			}
		});
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				gameLoop.stop();
				thestage.setScene(igcontrols);
			}

		});
		return btn;
	}

	// creates button to reach menu
	private Button menuButton() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "MENU")));
		btn.setBackground(new Background(transparent));
		btn.relocate((WIDTH / 2) - 94, 575);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "MENU2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "MENU")));
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				// menuRoot.getChildren().add(TITLE_SCREEN.getImageView());
				gameLoop.stop();
				gameState = 0;
				thestage.setTitle("Spookeo and Fariette");
				thestage.setScene(menuScene);

			}
		});
		return btn;
	}

	private Button menuButton2() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "MENU")));
		btn.setBackground(new Background(transparent));
		btn.relocate(600, 600);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "MENU2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "MENU")));
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				//menuRoot.getChildren().add(new ImageView(title.getFrame(titleScreen, "FLASH")));
				thestage.setTitle("Spookeo and Fariette");
				thestage.setScene(menuScene);
				if( cur<= 3){
					spookeoTheme.stop();
					spookeoTheme.reset();
				}
				if( cur >=5){
					farietteTheme.stop();
					farietteTheme.reset();
				}
				lives = 3;
				reset();
				bgNoise.runLoop();
				gameState = 0;
				gameLoop.stop();

			}
		});
		return btn;
	}

	private Button igMenuButton2() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "BACK")));
		btn.setBackground(new Background(transparent));
		btn.relocate(550, 500);
		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "BACK2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "BACK")));
			}
		});
		
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			
				thestage.setScene(igmenu);
			}
		});
		return btn;
	}

	private Button resumeButton() {
		Button btn = new Button("", new ImageView(play.getFrame(startButton, "RESUME")));
		btn.setBackground(new Background(transparent));
		btn.relocate(810, 500);

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setGraphic(new ImageView(play.getFrame(startButton, "RESUME2")));
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btn.setGraphic(new ImageView(play.getFrame(startButton, "RESUME")));
			}
		});

		btn.setOnAction(new EventHandler<ActionEvent>() {

			// action for the start button
			// sets button to false and creates a rectangle that appears after
			@Override
			public void handle(ActionEvent event) {
				igmenuroot.getChildren().remove(new ImageView(title.getFrame(titleScreen, "FLASH")));
				gameRoot.getChildren().add(igMenuButton());
				thestage.setScene(gameScene);
				gameLoop.start();
			}
		});
		return btn;
	}
	// ---------------------- Methods for moving/Colliding
	// --------------------------------------------------//

	// Method for implementing gravity
	private void gravity(final Rectangle rectangle, final ImageView image) {
		if (rectangle.getY() + rectangle.getHeight() + gravity >= HEIGHT) {
			lives--;
			reset();

		}

		if (!(rectangle.getY() + gravity + rectangle.getHeight() >= HEIGHT)) {
			rectangle.setY(rectangle.getY() + gravity);
			image.setY(rectangle.getY());
			hero.setHBY(rectangle.getY());
		}
	}

	private void Objgravity(boolean onTop, Obj grav) {
		if (!onTop) {
			if (!(grav.getY() + gravity + grav.getHeight() >= HEIGHT)) {
				grav.setY(grav.getY() + gravity);
				grav.getImageView().setY(grav.getY());
				grav.setHBY(grav.getY());
			}
		}
	}

	// jump
	double jumpmax = 0;
	boolean canJump = true;
	boolean lefty = false;
	boolean righty = false;
	boolean jumping = false;

	private void jump(final Rectangle rectangle, final ImageView image) {
		// if (jumpPress) {
		// System.out.println(jumping);;
		if (cooldown >= 120) { // Stop the jump
			// jumpSound.run();
			jumpmax = rectangle.getY() - 185;
			if (top) {
				canJump = true;
				jumping = false;
			}
		}

		if (canJump) { // First frame of Jump
			cooldown = 0;
			canJump = false;
			jumping = true;
		}

		if (jumping) { // Every other frame of jump
			if (rectangle.getY() <= jumpmax || bottom) {
				// jumpPress = false;
				jumping = false;
			}

			if (rectangle.getY() >= jumpmax) {

				// jumping = true;
				rectangle.setY(rectangle.getY() - acceleration * 4);
				hero.setHBY(rectangle.getY());
			}
		}
	}

	// Check for death
	public void deathCheck() {
		if (lives <= 0) {
			gameState = 5;
			if (cur <= 3) {
				spookeoTheme.stop();
				spookeoTheme.reset();
			} else if (cur >= 5) {
				farietteTheme.stop();
				farietteTheme.reset();
			}
			deathSong.runOnce();
		//	System.out.println("hi");
			gameLoop.stop();
			deathSong.reset();
			thestage.setScene(deathScene);
			lives = 3;
		}
	}

	// Reset collision variables
	public void resetCollision() {
		bottom = false;
		top = false;
		left = false;
		right = false;

	}

	// Checks for collision with all objects
	public void collisionCheck() {
		for (int i = 0; i < nmo.length; i++) {
			for (int j = 0; j < nmo[i].length; j++) {
				if (nmo[i][j] != null) {
					c.setObjs(hero, nmo[i][j]);
					c.isColliding();
					if (c.left()) {
						left = true;
					}
					if (c.right()) {
						right = true;
					}
					if (c.top()) {
						top = true;
					}
					if (c.bottom()) {
						bottom = true;
					}
					if (c.isColliding() && nmo[i][j] instanceof winBox) {
						winCheck(gameLoop);
					}
				}
			}
		}

		for (int i = 0; i < mo.length; i++) {
			for (int j = 0; j < mo[i].length; j++) {

				if (mo[i][j] != null) {
					if (mo[i][j] instanceof Box) {
						mo[i][j].resetCollision();
						for (int k = 0; k < nmo.length; k++) {
							for (int l = 0; l < nmo[k].length; l++) {

								c2.isColliding();
								if (nmo[k][l] != null) {
									c2.setObjs(mo[i][j], nmo[k][l]);
									if (c2.top()) {
										mo[i][j].setTop(true);
									}

								}
							}
						}
						for (int k = 0; k < mo.length; k++) {
							for (int l = 0; l < mo[k].length; l++) {
								if (mo[k][l] instanceof Rock) {
									c2.isColliding();
									c2.setObjs(mo[i][j], mo[k][l]);
									if (c2.top()) {
										mo[i][j].setTop(true);
									}
								}
							}
						}
						Objgravity(mo[i][j].getTop(), mo[i][j]);
					}
					c.setObjs(hero, mo[i][j]);
					if (c.isColliding()) {
						if (mo[i][j] instanceof Box) {

							if (c.left()) {
								mo[i][j].setX(hero.getAbsX() + hero.getWidth() + 1);
								mo[i][j].setHBX(mo[i][j].getX());
								mo[i][j].getImageView().setX(mo[i][j].getX());
								if (!action.equals("PUSH")) {
									action = "PUSH";
								}
							}
							if (c.right()) {
								mo[i][j].setX(hero.getAbsX() - mo[i][j].getWidth() - 1);
								mo[i][j].setHBX(mo[i][j].getX());
								mo[i][j].getImageView().setX(mo[i][j].getX());
								if (!action.equals("PUSH")) {
									action = "PUSH";
								}
							}
							if (c.top()) {
								top = true;
							}
							for (int k = 0; k < nmo.length; k++) {
								for (int l = 0; l < nmo[i].length; l++) {
									c2.isColliding();
									if (nmo[k][l] != null) {
										c2.setObjs(mo[i][j], nmo[k][l]);

										if (c2.left()) {
											left = true;
										}
										if (c2.right()) {
											right = true;
										}
									}
								}
							}
						}

						if (mo[i][j] instanceof Rock) {
							Actor rockImage = new Actor(mo[i][j].getX(), mo[i][j].getY(), mo[i][j].getWidth(),
									mo[i][j].getHeight(), new ImageView("Assets/Animations/rock.png"),
									mo[i][j].getHBX(), mo[i][j].getHBY(), mo[i][j].getHBWidth(),
									mo[i][j].getHBHeight());
							rocks.startActionAnimation("UP");
							rockImage.getImageView().setImage(roc.getFrame(rocks, "UP"));
							c.isColliding();
							if (c.left() && !mo[i][j].getFlipped()) {
								mo[i][j].setFlipped(true);
								int temp = mo[i][j].getHBHeight();
								mo[i][j].setHBHeight(mo[i][j].getHBWidth());
								mo[i][j].setHBWidth(temp);
								mo[i][j].setHBX(mo[i][j].getHBX() + 64);
								mo[i][j].setHBY(mo[i][j].getHBY() + 94);
								rockImage.getImageView().setImage(roc.getFrame(rocks, "DOWN"));
							}
							if (c.right() && !mo[i][j].getFlipped()) {
								mo[i][j].setFlipped(true);
								int temp = mo[i][j].getHBHeight();
								mo[i][j].setHBHeight(mo[i][j].getHBWidth());
								mo[i][j].setHBWidth(temp);
								mo[i][j].setHBX(mo[i][j].getHBX() - 64);
								mo[i][j].setHBY(mo[i][j].getHBY() + 93);
								rockImage.getImageView().setImage(roc.getFrame(rocks, "DOWN"));
							}
							if (c.top()) {
								top = true;
							}
						}
						flipped = false;
						if (mo[i][j] instanceof Flower) {
							c.isColliding();
							if (c.left() && !flowerFlip) {
								flowerFlip = true;
								leftFlowerFlip = true;
								int temp = mo[i][j].getHBHeight();
								mo[i][j].setHBHeight(mo[i][j].getHBWidth());
								mo[i][j].setHBWidth(temp);
								// mo[i][j].setHBX(mo[i][j].getHBX() + 64);
								mo[i][j].setHBY(mo[i][j].getHBY() + 148);
								if (c.top()) {
									top = true;
								}
							}
							if (c.right() && !flowerFlip) {
								flowerFlip = true;
								rightFlowerFlip = true;
								int temp = mo[i][j].getHBHeight();
								mo[i][j].setHBHeight(mo[i][j].getHBWidth());
								mo[i][j].setHBWidth(temp);
								// mo[i][j].setHBX(mo[i][j].getHBX() - 64);
								mo[i][j].setHBY(mo[i][j].getHBY() + 148);
								if (c.top()) {
									top = true;
								}
							}

						}
					}
				}
			}
		}
		
		// Collision check with enemies
		for (int i =0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[i].length; j++) {
				if (enemies[i][j] != null) {
					c.setObjs(hero, enemies[i][j]);
					if (c.isColliding()) {
						lives--;
						reset();
					}
				}
			}
		}
	}

	// makes shape move....
	private void moveRectangleOnKeyPress(Scene scene, final Rectangle rectangle, final ImageView image) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				if (event.getCode().equals(KeyCode.D) || event.getCode().equals(KeyCode.RIGHT)) {
					if (!action.equals("IDLE")) {
						heroAnimation.startActionAnimation("IDLE");
						heroFrame.changeCount(9);
						action = "IDLE";
					}
					if (!left) {
						hero.getImageView().setScaleX(1);
						movingRight = true;
						// Check for right scrolling
						scrollCheckRight(hero.getHBX());
						// moveRight(rectangle, image, righty);

					}
				}

				if (event.getCode().equals(KeyCode.W) || event.getCode().equals(KeyCode.UP)) {
					if (!action.equals("JUMP")) {
						heroAnimation.startActionAnimation("JUMP");
						heroFrame.changeCount(7);
						action = "JUMP";
					}
					action = "JUMP";
					if (!jumping) {
						movingUp = true;
					}

				}

				if (event.getCode().equals(KeyCode.A) || event.getCode().equals(KeyCode.LEFT)) {
					if (!action.equals("IDLE")) {
						heroAnimation.startActionAnimation("IDLE");
						heroFrame.changeCount(9);
						action = "IDLE";
					}
					if (!right) {
						hero.getImageView().setScaleX(-1);
						movingLeft = true;
						// Check for left scrolling
						scrollCheckLeft(hero.getHBX());
						// moveLeft(rectangle, image, lefty);

					}
				}

				if (event.getCode().equals(KeyCode.S) || event.getCode().equals(KeyCode.DOWN)) {
				}

			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.D) || event.getCode().equals(KeyCode.RIGHT)) {
					movingRight = false;
				}
				if (event.getCode().equals(KeyCode.A) || event.getCode().equals(KeyCode.LEFT)) {
					movingLeft = false;
				}
				if (event.getCode().equals(KeyCode.W) || event.getCode().equals(KeyCode.UP)) {
					movingUp = false;
					if (!action.equals("IDLE")) {
						heroAnimation.startActionAnimation("IDLE");
						heroFrame.changeCount(9);
						action = "IDLE";
					}
				}
			}

		});

	}

	public void scrollCheckLeft(double x) {
		if (cur != 3 && cur != 8) {
			if (x <= (0.2 * WIDTH) && xOffset - acceleration > 0) {
				xOffset = xOffset - acceleration;
				hero.setAbsX(hero.getAbsX() - acceleration);
				lefty = false;
				thestage.setScene(gameScene);
			} else {
				lefty = true;
			}
		}
	}

	public void scrollCheckRight(double x) {
		if (cur != 3 && cur != 8) {
			if (x >= (0.6 * WIDTH) && xOffset + acceleration < (0.76 * m.getWidth())) {

				xOffset = xOffset + acceleration;
				hero.setAbsX(hero.getAbsX() + acceleration);
				righty = false;
				thestage.setScene(gameScene);
			}

			else {
				righty = true;
			}
		}
	}

	private void moveRight(Rectangle rectangle, ImageView image) {
		if (hero.getHBX() > 0.5 * WIDTH && xOffset + acceleration < (0.76 * m.getWidth()) && (cur != 3 && cur != 8)) {
			xOffset = xOffset + acceleration;
			hero.setAbsX(hero.getAbsX() + acceleration);
			hero.setHBX(hero.getAbsX());
		} else {
			if (!(rectangle.getX() + acceleration + rectangle.getWidth() >= WIDTH)) {
				rectangle.setX(rectangle.getX() + acceleration);
				hero.setAbsX(hero.getAbsX() + acceleration);
				image.setX(rectangle.getX());
				hero.setHBX(hero.getAbsX());
			}
		}
	}

	private void moveLeft(Rectangle rectangle, ImageView image) {
		if (hero.getX() < 0.5 * WIDTH && xOffset - acceleration > 0 && (cur != 3 && cur != 8)) {
			xOffset = xOffset - acceleration;
			hero.setAbsX(hero.getAbsX() - acceleration);
			hero.setHBX(hero.getAbsX());
		} else {
			if (!(rectangle.getX() - acceleration <= 0)) {
				rectangle.setX(rectangle.getX() - acceleration);
				hero.setAbsX(hero.getAbsX() - acceleration);
				image.setX(rectangle.getX());
				hero.setHBX(hero.getAbsX());
			}
		}
	}

	public void winCheck(AnimationTimer loop) {
		gameLoop.stop();
		if (cur >= 5) {
			ohYeah.runOnce();
		}
		thestage.setScene(nextLevel);
		// farietteAdded = false;
		if (cur == 3 || cur == 8) {
			//gameLoop.stop();
			thestage.setScene(winScene);
		}
	}

	public void LevelBuilder() {
		if (cur == 0) {
			m.readIn("Assets/Json/map1.txt");
		} else if (cur == 1) {
			m.readIn("Assets/Json/map2.txt");
		} else if (cur == 2) {
			m.readIn("Assets/Json/map3.txt");
		} else if (cur == 3) {
			m.readIn("Assets/Json/map4.txt");
		} else if (cur == 5) {
			m.readIn("Assets/Json/map5.txt");
		} else if (cur == 6) {
			m.readIn("Assets/Json/map6.txt");
		} else if (cur == 7) {
			m.readIn("Assets/Json/map7.txt");
		} else if (cur == 8) {
			m.readIn("Assets/Json/map8.txt");
		}
	}

	// Reset game
	public void reset() {
		
		gameRoot.getChildren().remove(hero.getImageView());
		gameRoot.getChildren().remove(rectangle);
		xOffset = 0;
		m.readIn("Assets/Json/map1.txt");
		movingUp = false;
		movingRight = false;
		movingLeft = false;
		load();
		hero.setX(300);
		hero.setY(300);
		hero.setHBX(300);
		hero.setHBY(300);
		hero.setAbsX(300);
		hero.getImageView().setX(300);
		hero.getImageView().setY(300);
		rectangle.setX(300);
		rectangle.setY(300);
		clouds.setX(0);
		clouds2.setX(1800);
		midTrees.setX(0);
		midTrees2.setX(1800);
		foreTrees.setX(0);
		foreTrees2.setX(2000);
		trans.setX(0);
		gameRoot.getChildren().add(hero.getImageView());
		gameRoot.getChildren().add(rectangle);
	}

	public void resetF() {

		gameRoot.getChildren().remove(hero.getImageView());
		gameRoot.getChildren().remove(rectangle);
		xOffset = 0;
		m.readIn("Assets/Json/map5.txt");
		movingUp = false;
		movingRight = false;
		movingLeft = false;
		load();
		hero.setX(300);
		hero.setY(300);
		hero.setHBX(300);
		hero.setHBY(300);
		hero.setAbsX(300);
		hero.getImageView().setX(300);
		hero.getImageView().setY(300);
		rectangle.setX(300);
		rectangle.setY(300);
		clouds.setX(0);
		clouds2.setX(1800);
		midTrees.setX(0);
		midTrees2.setX(1800);
		foreTrees.setX(0);
		foreTrees2.setX(2000);
		trans.setX(0);
		gameRoot.getChildren().add(hero.getImageView());
		gameRoot.getChildren().add(rectangle);
	}
	public void detection() {

		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[i].length; j++) {
				if (enemies[i][j] != null) {
					enemies[i][j].resetCollision();
					enemies[i][j].setDetected(false);

					// enemy1.getImageView().setScaleX(-1);
					for (int h = 0; h < nmo.length; h++) {
						for (int k = 0; k < nmo[h].length; k++) {
							if (nmo[h][k] != null) {
								// System.out.println(h + " :: " + k + " ::
								// " + enemies[h][k]);
								c3.setObjs(enemies[i][j], nmo[h][k]);
								c3.isColliding();
								if (c3.right()) {
									enemies[i][j].setRight(true);
									// System.out.println("HELLO FROM THE
									// RIGHT");
								}

								if (c3.left()) {
									enemies[i][j].setLeft(true);
									// System.out.println("HELLO FROM THE
									// LEFT");
								}
								if (c3.top()) {
									enemies[i][j].setTop(true);
								}
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[i].length; j++) {
				if (enemies[i][j] != null) {
					
					Objgravity(enemies[i][j].getTop(), enemies[i][j]);
					enemies[i][j].setDetected(enemies[i][j].detected(hero));
					if (enemies[i][j].getDetected()) {
						// Check collision here
					
						if (enemies[i][j].getType() == 1) {
							int borkborkcd = 40;
							if (!enemies[i][j].getAction().equals("BARK")) {
								enemies[i][j].setAction("BARK");
								
								if (borkborkcd >= 40) {
									borkbork.runOnce();
									borkborkcd = 0;
								}
								borkbork.reset();
								borkborkcd++;
							}
						} else if (enemies[i][j].getType() == 2) {
							if (!enemies[i][j].getAction().equals("GOTYOU")) {
								enemies[i][j].setAction("GOTYOU");
							}
							if (!enemies[i][j].getAhaPlayed()) {
								fairyAha.runOnce();
								enemies[i][j].setAhaPlayed(true);
							}
							fairyAha.reset();
						} else {
						}
						enemies[i][j].track(hero.getAbsX());
					} else if(!enemies[i][j].getDetected()){
						if (!enemies[i][j].getAction().equals("IDLE")) {
							enemies[i][j].setAction("IDLE");
						}
						enemies[i][j].setAhaPlayed(false);
						enemies[i][j].move();
					}

				}
			}
		}
	}
	public Rectangle heroDeath() {
	
		//Button btn = new Button("", new ImageView(play.getFrame(startButton, "JOEY")));
		
		return rectanglez;
	}
	
	public Rectangle nextcongrats() {
		Image i = new Image("Assets/Art/congrats.png");
		no.setFill((new ImagePattern(i)));
		return no;
	}
	public void autoScroll() {
		if (xOffset + acceleration < (0.76 * m.getWidth())) {
			xOffset = xOffset + acceleration / 2;
			hero.setX(hero.getAbsX() - xOffset);
			rectangle.setX(hero.getAbsX() - xOffset);
			hero.getImageView().setX(hero.getAbsX() - xOffset);
			if (hero.getAbsX() - xOffset < 0) {
				hero.setAbsX(hero.getAbsX() + acceleration);
				hero.setHBX(hero.getAbsX());
				rectangle.setX(hero.getAbsX() - xOffset);
				hero.getImageView().setX(hero.getAbsX() - xOffset);
				if (left) {
					lives--;
					reset();
				}
			}
		}
	}
}
