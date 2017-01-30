import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.image.ImageView;

public class MapLoader {

	int count = 0;

	// load all ImageViews once
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
	
	//trasition
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
	//ImageView flower = new ImageView("Assets/Art/tippableflower.png");
	
	// Store all elements into either list of nonmoveables or moveables
	ArrayList<Nonmoveable> nmo;
	ArrayList<Moveable> mo;

	public void readIn(double width, double height, String path, double xOffset) {

		// Reload arraylists
		nmo = new ArrayList<Nonmoveable>();
		if (count == 0) {
			mo = new ArrayList<Moveable>();
		}
		int start = 0;
		double j = 0;
		int off = (int) (xOffset % 64);
		// Figure out which "chunk" to load

		if (xOffset >= 64) {
		
			start = (int) (xOffset / 64);
		}

		int end = start + 26;

		// Keep track of position in the map
		double k = height;

		try {
			
			Scanner in = new Scanner(getResourceAsFile(path));
			while (in.hasNextLine()) {
				String temp = in.nextLine();
				char[] line = temp.toCharArray();

				if (count == 0) {
					for (int i = 0; i < line.length; i++) {
						// box
						if (line[i] == 'x') {
							mo.add(new Box(j, height - k + 23, 65, 65, box, j, height - k + 23, 60, 60));
						}

						// rock
						else if (line[i] == 'y') {
							mo.add(new Rock(j, height - k + 28, 64, 128, rock, j, height - k + 28, 64, 128));
						}

						// flower
						else if (line[i] == 'z') {
							//mo.add(new Flower(j, height - k + 28, 64, 192, flower, j, height - k + 28, 64, 192));
						}
						j += 64;
					}
				}
				for (int i = start; i < end; i++) {

					//------------------------------------HAPPY MEADOWS--------------------------------------------------------------//
					// ground block
					if (line[i] == 'a') {
						nmo.add(new Nonmoveable(j - off, height - k, 64, 64, dirt1, j - off + 1, height - k + 20, 62, 42));
					}
					
					// ground left edge
					else if (line[i] == 'b') {
						nmo.add(new Nonmoveable(j - off, height - k, 64, 64, dirt2, j - off + 30, height - k + 20, 32, 42));
					}

					// ground right edge
					else if (line[i] == 'c') {
						nmo.add(new Nonmoveable(j - off, height - k, 64, 64, dirt3, j - off + 2, height - k + 20, 36, 42));
					}

					// dirt block
					else if (line[i] == 'd') {
						nmo.add(new Nonmoveable(j - off, height - k, 64, 64, dirt4, j - off + 1, height - k, 62, 62));
					}
					
					// dirt left edge
					else if (line[i] == 'e') {
						nmo.add(new Nonmoveable(j - off, height - k, 64, 64, dirt5, j - off + 30, height - k, 32, 62));
					}

					// dirt right edge
					else if (line[i] == 'f') {
						nmo.add(new Nonmoveable(j - off, height - k, 64, 64, dirt6, j - off + 2, height - k, 36, 62));
					} 
					
					// left corner
					else if (line[i] == 'g') {
						nmo.add(new Nonmoveable(j - off, height - k, 32, 64, dirt7, j - off + 1, height - k + 28, 62, 32));
					} 
					
					// right corner
					else if (line[i] == 'h') {
						nmo.add(new Nonmoveable(j - off, height - k, 32, 64, dirt8, j - off + 1, height - k + 28, 62, 32));
					} 
					
					
					//------------------------------------TRANSITIONS---------------------------------------------------------------//
					// transition top
					else if (line[i] == 'i') {
						nmo.add(new Nonmoveable(j - off, height - k, 32, 64, dirt9, j - off + 1, height - k + 20, 62, 42));
					} 
					
					// transition dirt
					else if (line[i] == 'j') {
						nmo.add(new Nonmoveable(j - off, height - k, 32, 64, dirt10, j - off + 1, height - k, 62, 62));
					}
					
					else if (line[i] == 'k') {
						nmo.add(new Nonmoveable(j - off, height - k, 32, 64, dirt11, j - off + 1, height - k + 20, 62, 42));
					} 
					
					// transition dirt
					else if (line[i] == 'l') {
						nmo.add(new Nonmoveable(j - off, height - k, 32, 64, dirt12, j - off + 1, height - k, 62, 62));
					}
					
					
					//------------------------------------SPOOKY FOREST-------------------------------------------------------------//
					
					// ground block spooky
					else if (line[i] == 'm') {
						nmo.add(new Nonmoveable(j - off, height - k, 64, 64, dirt13, j - off + 1, height - k + 20, 62, 42));
					}
					
					// ground left edge spooky
					else if (line[i] == 'n') {
						nmo.add(new Nonmoveable(j - off, height - k, 64, 64, dirt14, j - off + 30, height - k + 20, 32, 42));
					}

					// ground right edge spooky
					else if (line[i] == 'p') {
						nmo.add(new Nonmoveable(j - off, height - k, 64, 64, dirt15, j - off + 2, height - k + 20, 36, 42));
					}	
					
					// dirt block spooky
					else if (line[i] == 'q') {
						nmo.add(new Nonmoveable(j - off, height - k, 64, 64, dirt16, j - off + 1, height - k, 62, 62));
					} 

					// dirt left edge spooky
					else if (line[i] == 'r') {
						nmo.add(new Nonmoveable(j - off, height - k, 64, 64, dirt17, j - off + 30, height - k, 32, 62));
					}

					// dirt right edge spooky
					else if (line[i] == 's') {
						nmo.add(new Nonmoveable(j - off, height - k, 64, 64, dirt18, j - off + 2, height - k, 36, 62));
					}
				
					// left corner spooky
					else if (line[i] == 't') {
						nmo.add(new Nonmoveable(j - off, height - k, 32, 64, dirt19, j - off + 1, height - k + 28, 62, 32));
					} 
					
					// right corner spooky
					else if (line[i] == 'u') {
						nmo.add(new Nonmoveable(j - off, height - k, 32, 64, dirt20, j - off + 1, height - k + 28, 62, 32));
					} 
					else {
					}

					// Go right on x axis
					j += 64;
				}

				// Reset x axis
				j = 0;

				// Go down y axis
				k -= 64;
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		count++;
	}

	public ArrayList<Moveable> getMO() {
		return mo;
	}

	public ArrayList<Nonmoveable> getNMO() {
		return nmo;
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
	            //copy stream
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
