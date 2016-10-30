import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Actor {

	private int width = 0;
	private int height = 0;
	private double x = 0.0;
	private double y = 0.0;
	private double angle = 0.0;
	private double hbex = 0.00;
	private double hbwhy = 0.0;
	private int hbwid = 0;
	private int hbht = 0;
	
	
	
	private Image character = null;
	ImageView actor;
	
	private double speed = 0.0;
	private double acceleration = 0.0;	
	
	// Set initial x and y coordinates and image associated with actor
	public Actor(double ex, double why, int sizeX, int sizeY, Image person, double hbx, double hby, int hbwidth, int hblength) {
		x = ex;
		y = why;
		width = sizeX;
		height = sizeY;
		character = person;
		actor = new ImageView(person);
		actor.setX(ex);
		actor.setY(why);
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
	
	// set angle of movement
	public void setAngle (double ang) {
		angle = ang;
	}
	
	// set image associated with the actor
	public void setImage (Image actor) {
		character = actor;
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
	
	// get angle of movement
	public double getAngle() {
		return angle;
	}
	
	// get speed of the actor
	public double getSpeed() {
		return speed;
	}
	
	// get velocity in the x direction
	public double getVelX() {
		return speed * acceleration;
	}
	
	// get velocity in the y direction
	public double getVelY() {
		return speed * acceleration;
	}
	
	// get image associated with the actor
	public Image getImage() {
		return character;
	}
	
	// Imageview
	public ImageView getImageView() {
		
		return actor;
		
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
	//draw box
	
}

