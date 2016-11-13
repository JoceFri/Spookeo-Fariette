import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.image.Image;

public class MapLoader {

	
	// load all images once
	Image dirt1 = new Image("Assets/Art/2side_ground.png");
	Image dirt2 = new Image("Assets/Art/leftedge_ground.png");
	Image dirt3 = new Image("Assets/Art/rightedge_ground.png");
	Image dirt4 = new Image("Assets/Art/fulldirt_block.png");
	Image dirt5 = new Image("Assets/Art/leftedge_dirt.png");
	Image dirt6 = new Image("Assets/Art/rightedge_dirt.png");
	Image hero = new Image("Assets/Art/ghost_same.png");
	Image rock = new Image("Assets/Art/skinny rock.png");
	Image flower = new Image("Assets/Art/tippableflower (1).png");
	Image box = new Image("Assets/Art/pushable_box.png");
	
	// Store all elements into either list of nonmoveables or moveables
	ArrayList<Nonmoveable> nmo = new ArrayList<Nonmoveable>();
	ArrayList<Moveable> mo = new ArrayList<Moveable>();

	public void readIn(double width, double height, String path) {
		
		// Keep track of position in the map
		double j = 0 ;
		double k = height;
		
		try {

			Scanner in = new Scanner(new File(path));

			while (in.hasNextLine()) {
				String temp = in.nextLine();
				char[] line = temp.toCharArray();

				for (int i = 0; i < line.length; i++) {
					
					// ground left edge
					if (line[i] == 'a') {
						nmo.add(new Nonmoveable(j, height - k, 64, 64, dirt2, j, height - k, 64, 64));
					}
					
					// ground right edge
					else if (line[i] == 'b') {
						nmo.add(new Nonmoveable(j, height - k, 64, 64, dirt3, j, height - k, 64, 64));
					}
					
					// ground block
					else if (line[i] == 'c') {
						nmo.add(new Nonmoveable(j, height - k, 64, 64, dirt1, j, height - k, 64, 64));
					}
					
					// dirt left edge
					else if (line[i] == 'd') {
						nmo.add(new Nonmoveable(j, height - k, 64, 64, dirt5, j, height - k, 64, 64));
					}
					
					// dirt right edge
					else if (line[i] == 'e') {
						nmo.add(new Nonmoveable(j, height - k, 64, 64, dirt6, j, height - k, 64, 64));
					}
					
					// dirt block
					else if (line[i] == 'f') {
						nmo.add(new Nonmoveable(j, height - k, 64, 64, dirt4, j, height - k, 64, 64));
					}
					
					// spookeo
					else if (line[i] == 'g') {
						mo.add(new Moveable(j, height - k, 64, 64, hero, j, height - k, 64, 64));
					}
					
					// box
					else if (line[i] == 'h') {
						mo.add(new Moveable(j, height - k, 64, 64, box, j, height - k, 64, 64));
					}
					
					// rock
					else if (line[i] == 'i') {
						mo.add(new Moveable(j, height - k, 64, 128, rock, j, height - k, 64, 128));
					}
					
					// flower
					else if (line[i] == 'j') {
						mo.add(new Moveable(j, height - k, 64, 192, flower, j, height - k, 64, 192));
					}
					
					// button
					else if (line[i] == 'k') {

					}
					
					// unmoveable bee trap
					else if (line[i] == 'l') {

					}
					
					else {}
					
					// Go right on x axis
					j += 64;
				}
				
				// Reset x axis
				j = 0;
				
				// Go down y axis
				k -= 64;
			}
			in.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Moveable> getMO() {
		return mo;
	}

	public ArrayList<Nonmoveable> getNMO() {
		return nmo;
	}
}