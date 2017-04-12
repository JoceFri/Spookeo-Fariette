import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import DavidMohrhardt.animator.Animator;
import javafx.scene.image.ImageView;

public class MapLoader {

	int count = 0;
	int type = 0;

	// load all ImageViews once
	// grass
	ImageView dirt1 = new ImageView("Assets/Art/2side_ground.png");
	ImageView dirt2 = new ImageView("Assets/Art/leftedge_ground.png");
	ImageView dirt3 = new ImageView("Assets/Art/rightedge_ground.png");
	ImageView dirt4 = new ImageView("Assets/Art/fulldirt_block.png");
	ImageView dirt5 = new ImageView("Assets/Art/leftedge_dirt.png");
	ImageView dirt6 = new ImageView("Assets/Art/rightedge_dirt.png");
	ImageView dirt7 = new ImageView("Assets/Art/leftcorner.png");
	ImageView dirt8 = new ImageView("Assets/Art/rightcorner.png");

	// trasition
	ImageView dirt9 = new ImageView("Assets/Art/midtrans1.png");
	ImageView dirt10 = new ImageView("Assets/Art/midtrans1_bottom.png");
	ImageView dirt11 = new ImageView("Assets/Art/midtrans2.png");
	ImageView dirt12 = new ImageView("Assets/Art/midtrans2_bottom.png");

	// spooky

	ImageView dirt13 = new ImageView("Assets/Art/middirtop_spooky.png");
	ImageView dirt14 = new ImageView("Assets/Art/leftdirttop_spooky.png");
	ImageView dirt15 = new ImageView("Assets/Art/rightdirttop_spooky.png");
	ImageView dirt16 = new ImageView("Assets/Art/fulldirt_spooky.png");
	ImageView dirt17 = new ImageView("Assets/Art/leftdirtbottom_spooky.png");
	ImageView dirt18 = new ImageView("Assets/Art/rightdirtbottom_spooky.png");
	ImageView dirt19 = new ImageView("Assets/Art/leftcorner_spooky.png");
	ImageView dirt20 = new ImageView("Assets/Art/rightcorner_spooky.png");

	// movable stuff
	ImageView box = new ImageView("Assets/Art/pushable_box.png");
	ImageView rock = new ImageView("Assets/Art/skinny rock.png");
	ImageView flower = new ImageView("Assets/Art/tippableflower (1).png");
	ImageView winner = new ImageView("Assets/Art/sign.png");

	Nonmoveable[][] nmo = null;
	Moveable[][] mo = null;
	Enemy[][] enemies = null;
	int width = 0;
	int height = 0;
	winBox win = null;

	public void readIn(String path) {

		try {

			Scanner in = new Scanner(getResourceAsFile(path));

			width = in.nextInt();
			height = in.nextInt();
			type = in.nextInt();
			in.nextLine();
			nmo = new Nonmoveable[width][height];
			mo = new Moveable[width][height];
			enemies = new Enemy[width][height];
			int j = 0;

			while (in.hasNextLine()) {

				String temp = in.nextLine();
				char[] line = temp.toCharArray();

				for (int i = 0; i < line.length; i++) {

					// ------------------------------------HAPPY
					// MEADOWS--------------------------------------------------------------//
					// ground block
					if (line[i] == 'a') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt1, i * 64, j * 64 + 30, 62, 42);
					}

					// ground left edge
					else if (line[i] == 'b') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt2, i * 64 + 32, j * 64 + 30, 32, 42);
					}

					// ground right edge
					else if (line[i] == 'c') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt3, i * 64, j * 64 + 30, 36, 42);
					}

					// dirt block
					else if (line[i] == 'd') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt4, i * 64, j * 64, 62, 62);
					}

					// dirt left edge
					else if (line[i] == 'e') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt5, i * 64 + 32, j * 64, 32, 42);
					}

					// dirt right edge
					else if (line[i] == 'f') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt6, i * 64, j * 64, 36, 62);
					}

					// left corner
					else if (line[i] == 'g') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt7, i * 64, j * 64 + 32, 32, 32);
					}

					// right corner
					else if (line[i] == 'h') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt8, i * 64 + 32, j * 64 + 32, 32, 32);
					}

					// ------------------------------------TRANSITIONS---------------------------------------------------------------//
					// transition top
					else if (line[i] == 'i') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt9, i * 64, j * 64 + 30, 62, 42);
					}

					// transition dirt
					else if (line[i] == 'j') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt10, i * 64, j * 64, 62, 62);
					}

					else if (line[i] == 'k') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt11, i * 64, j * 64 + 30, 62, 42);
					}

					// transition dirt
					else if (line[i] == 'l') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt12, i * 64, j * 64, 62, 62);
					}

					// ------------------------------------SPOOKY
					// FOREST-------------------------------------------------------------//

					// ground block spooky
					else if (line[i] == 'm') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt13, i * 64, j * 64 + 30, 62, 42);
					}

					// ground left edge spooky
					else if (line[i] == 'n') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt14, i * 64 + 32, j * 64 + 30, 32, 42);
					}

					// ground right edge spooky
					else if (line[i] == 'p') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt15, i * 64, j * 64 + 30, 36, 42);
					}

					// dirt block spooky
					else if (line[i] == 'q') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt16, i * 64, j * 64, 62, 62);
					}

					// dirt left edge spooky
					else if (line[i] == 'r') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt17, i * 64 + 32, j * 64, 32, 42);
					}

					// dirt right edge spooky
					else if (line[i] == 's') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt18, i * 64, j * 64, 36, 62);
					}

					// left corner spooky
					else if (line[i] == 't') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt19, i * 64, j * 64 + 32, 32, 32);
					}

					// right corner spooky
					else if (line[i] == 'u') {
						nmo[i][j] = new Nonmoveable(i * 64, j * 64, 64, 64, dirt20, i * 64, j * 64 + 32, 32, 32);
					}
					// winBox
					else if (line[i] == 'w') {
						System.out.println("added" + " " + i + " " + j);
						nmo[i][j] = new winBox(i * 64, j * 64 + 23, 64, 64, winner, i * 64, j * 64 + 23, 64, 64);
					}
					// box
					else if (line[i] == 'x') {
						mo[i][j] = new Box(i * 64, j * 64 + 32, 64, 64, box, i * 64, j * 64 + 32, 62, 62);
					}

					// rock
					else if (line[i] == 'y') {
						mo[i][j] = new Rock(i * 64, j * 64, 64, 128,  i * 64, j * 64, 64, 128);
					}

					// flower
					else if (line[i] == 'z') {
						mo[i][j] = new Flower(i * 64, j * 64, 64, 64, flower, i * 64, j * 64, 64, 192);
					} 
					
					//ghost dog enemy
					else if (line[i] == 'D') {
						enemies[i][j] = new Enemy(i * 64, j * 64, 64, 64, new ImageView("Assets/Animations/ghostdog.png"), i * 64, j * 64, 64, 64, 
								new Animator("src/Assets/Animations/ghostdog.png", "src/Assets/Animations/ghostdog.ssc"), new FrameSetter(8), 1, 0.5);
					} 
					
					//fairy guard enemy
					else if (line[i] == 'F') {
						enemies[i][j] = new Enemy(i * 64, j * 64, 64, 64, new ImageView("Assets/Animations/fairyguard.png"), i * 64, j * 64, 64, 64, 
								new Animator("src/Assets/Animations/fairyguard.png", "src/Assets/Animations/fairyguard.ssc"), new FrameSetter(9), 2, 0.5);
					} 
					
					else {		
					}

				}
				j++;
			}
			in.close();
		} catch (

		FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Moveable[][] getMO() {
		return mo;
	}

	public Nonmoveable[][] getNMO() {
		return nmo;
	}
	
	public Enemy[][] getEnemies() {
		return enemies;
	}

	public double getWidth() {
		return (double) 64 * width;
	}

	public double getHeight() {
		return (double) 64 * height;
	}

	public int getType() {
		return type;
	}

	private static File getResourceAsFile(String resourcePath) {
		try {
			InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
			if (in == null) {
				return null;
			}

			File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
			tempFile.deleteOnExit();

			try (FileOutputStream out = new FileOutputStream(tempFile)) {
				// copy stream
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = in.read(buffer)) != -1) {
					out.write(buffer, 0, bytesRead);
				}
			}
			return tempFile;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
