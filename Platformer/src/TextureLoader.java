import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;

public class TextureLoader {
	
	public static Image character;
	int frame = 0;
	
	//initializes spookeo
	public static void load(File file){
		try{
			//texture
			character = ImageIO.read(new File(file));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void updateDraw(Graphics g, Moveable obj){
		
		
		
		//draw sprite
	TextureLoader.character.draw(g, (int)obj.getX(), (int)obj.getY(),0);
	frame++;
	frame %= 9;
	}

	public class Box{
		public int cornerY1;
		public int cornerY2;
		public int cornerX1;
		public int cornerX2;
		
		public int width;
		public int height;
		
		public Box(int cornerX1, int cornerY1, int cornerX2, int cornerY2){
			this.cornerX1 = cornerX1;
			this.cornerX2 = cornerX2;
			this.cornerY1 = cornerY1;
			this.cornerY2 = cornerY2;
			
			width = cornerX2-cornerX1;
			height = cornerY2-cornerY1;
		}
	}
	
	
	public class Texture{
		private java.awt.Image source;
		private Box[] frames;
	
	
	public Texture(String path){
		try{
			source = ImageIO.read(new File(path));
			
			//read in from file
			Scanner s = new Scanner(new File(path + ".map"));
			
			//read in image data and store in frame
			int size = s.nextInt();
			frames = new Box[size];
			
			for(int i = 0; 1< size; i++){
				//box corners
				frames[i] = new Box(s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt());
			}
		}catch (IOException e){
			e.printStackTrace();
		}
	}
		

	
	public void Draw(Graphics gar, int x, int y, int frameID){
		Box frame = frames[frameID];
		gar.drawImage(source, x, y, x+frame.width, y+frame.height, frame.cornerX1, frame.cornerY1, frame.cornerX2, frame.cornerY2, null);
	}
	}
	
	
	
	
	
	
	
}
