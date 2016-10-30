

public class Collision {
	Actor shapeOne = null;
	Actor shapeTwo = null;
	private boolean leftCollision;
	private boolean rightCollision;
	public Collision(Actor one, Actor two){
		shapeOne = one;
		shapeTwo = two;
	}
	public boolean isColliding(){
				System.out.println(shapeOne.getHBX() + " " + shapeTwo.getHBX());
				if( shapeOne.getHBX()+shapeOne.getHBWidth() <= shapeTwo.getHBX() 
					&& shapeOne.getHBY() >= shapeTwo.getHBY() 
					&& (shapeTwo.getHBX() - (shapeOne.getHBX()+shapeOne.getHBWidth())) <= Platformer.acceleration){
						//System.out.println("TRUE");	
						leftCollision = true;
						return true;
						}
				if( shapeOne.getHBX() >= shapeTwo.getHBX() + shapeTwo.getHBWidth()
					&& shapeOne.getHBY() >= shapeTwo.getHBY()
					&& shapeOne.getHBX() - (shapeTwo.getHBX() + shapeTwo.getHBWidth()) <= Platformer.acceleration){
						System.out.println("TRUE");
						rightCollision = true;
						return true;
				}
					
		return false;
	}
	
	public void moveObject(){
		if(isColliding()){
			if(leftCollision){
				shapeTwo.setX(shapeTwo.getX() + Platformer.acceleration);
				shapeTwo.setHBX(shapeTwo.getHBX() + Platformer.acceleration);
			}
			if(rightCollision){
				shapeTwo.setX(shapeTwo.getX() - Platformer.acceleration);
				shapeTwo.setHBX(shapeTwo.getHBX() - Platformer.acceleration);
			}
		}
	}
}
