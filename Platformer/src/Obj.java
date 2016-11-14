import javafx.scene.image.ImageView;

public class Obj {
	
	private int width = 0;
	private int height = 0;
	private double x = 0.0;
	private double y = 0.0;
	private double hbex = 0.00;
	private double hbwhy = 0.0;
	private int hbwid = 0;
	private int hbht = 0;
	
	
	
	ImageView actor;
	
	// Set initial x and y coordinates and image associated with actor
	public Obj(double ex, double why, int sizeX, int sizeY, ImageView person, double hbx, double hby, int hbwidth, int hblength) {
		x = ex;
		y = why;
		width = sizeX;
		height = sizeY;
		actor = person;
		actor.setX(ex);
		actor.setY(why);
		actor.setFitWidth(sizeX);
		actor.setFitHeight(sizeY);
		hbex = hbx;
		hbwhy = hby;
		hbwid = hbwidth;
		hbht = hblength;
	}
	
	//--------------- Setters ----------------------//
	
	// set x coordinate of the actor
	public void setX(double newX) {
		x = newX;
	}
	
	// set y coordinate of the actor
	public void setY(double newY) {
		y = newY;
	}
	
	// set width
	public void setWidth(int sizeX) {
		width = sizeX;
	}
	
	//set height
	public void setHeight(int sizeY) {
		height = sizeY;
	}
	
	
	public void setHBY(double why){
		hbwhy = why;
	}
	
	public void setHBX(double ex){
		hbex = ex;
	}
	
	public void setHBWidth(int wd){
		hbwid = wd;
	}
	
	public void setHBHeight(int ht){
		hbht = ht;
	}
	
	//----------- Getters -------------------//
	
	//get x coordinate of the actor
	public double getX() {
		return x;
	}
	
	//get y coordinate of the actor
	public double getY() {
		return y;
	}
	
	// set width
	public int getWidth() {
		return width;
	}
	
	//set height
	public int getHeight() {
		return height;
	}
	
	// Imageview
	public ImageView getImageView() {	
		return actor;	
	}

	public double getHBX(){
		return hbex;
	}
	
	public double getHBY(){
		return hbwhy;
	}
	
	public int getHBWidth(){
		return hbwid;
	}
	
	public int getHBHeight(){
		return hbht;
	}	
}
