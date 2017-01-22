
public class Collision {
	Obj shapeOne = null;
	Obj shapeTwo = null;
	private boolean leftCollision;
	private boolean rightCollision;
	private boolean topCollision;
	private boolean bottomCollision;

	public Collision(Obj one, Obj two) {
		shapeOne = one;
		shapeTwo = two;
	}
	
	public void setObjs (Obj one, Obj two) {
		shapeOne = one;
		shapeTwo = two;
	}

	public boolean isColliding() {
		//System.out.println(shapeOne.getHBX());
		if (shapeOne.getHBX() + shapeOne.getHBWidth() <= shapeTwo.getHBX() && shapeOne.getHBY() + shapeOne.getHBHeight() > (shapeTwo.getHBY())
				&& shapeOne.getHBY() < (shapeTwo.getHBY() + shapeTwo.getHBHeight()) && (shapeTwo.getHBX() - (shapeOne.getHBX() + shapeOne.getHBWidth())) <= Platformer.acceleration) {
			leftCollision = true;
			return true;
		} else {
			leftCollision = false;
		}
		if (shapeOne.getHBX() >= shapeTwo.getHBX() + shapeTwo.getHBWidth() && shapeOne.getHBY()+ shapeOne.getHBHeight() > shapeTwo.getHBY()
				&& shapeOne.getHBY() < (shapeTwo.getHBY() + shapeTwo.getHBHeight()) && shapeOne.getHBX() - (shapeTwo.getHBX() + shapeTwo.getHBWidth()) <= Platformer.acceleration) {
			//System.out.println("TRUE");
			rightCollision = true;
			return true;
		} else {
			rightCollision = false;
		}
		if (((shapeOne.getHBX() + shapeOne.getHBWidth() >= shapeTwo.getHBX() && (shapeTwo.getHBX() - shapeOne.getHBX()) <= shapeOne.getHBWidth())
				&& ((shapeTwo.getHBX() + shapeTwo.getHBWidth() >= shapeOne.getHBX()) && (shapeOne.getHBX() - shapeTwo.getHBX()) <= shapeOne.getHBWidth()))
				&& shapeOne.getHBY() + shapeOne.getHBHeight() >= shapeTwo.getHBY() && shapeOne.getHBY() <= shapeTwo.getHBY() + shapeTwo.getHBHeight()) {
			topCollision = true;
			return true;
		} else {
			topCollision = false;
		}
		if(((shapeOne.getHBX() + shapeOne.getHBWidth() >= shapeTwo.getHBX() && (shapeTwo.getHBX() - shapeOne.getHBX()) <= shapeOne.getHBWidth())
				&& ((shapeTwo.getHBX() + shapeTwo.getHBWidth() >= shapeOne.getHBX()) && (shapeOne.getHBX() - shapeTwo.getHBX()) <= shapeOne.getHBWidth()))
				&& shapeOne.getHBY() + shapeOne.getHBHeight() <= shapeTwo.getHBY() && shapeOne.getHBY() >= shapeTwo.getHBY() + shapeTwo.getHBHeight()) {
			bottomCollision = true;
			return true;
		} else{
			bottomCollision = false;
		}

		return false;
	}

	public void moveObject() {
		if (isColliding()) {
			if (leftCollision) {
				shapeTwo.setX(shapeTwo.getX() + Platformer.acceleration);
				shapeTwo.setHBX(shapeTwo.getHBX() + Platformer.acceleration);
				shapeTwo.getImageView().setX(shapeTwo.getImageView().getX() + Platformer.acceleration);
			}
			if (rightCollision) {
				shapeTwo.setX(shapeTwo.getX() - Platformer.acceleration);
				shapeTwo.setHBX(shapeTwo.getHBX() - Platformer.acceleration);
				shapeTwo.getImageView().setX(shapeTwo.getImageView().getX() - Platformer.acceleration);
			}
		}
	}
	public boolean top(){
		return topCollision;
	}
	
	public boolean left(){
		return leftCollision;
	}
	
	public boolean right(){
		return rightCollision;
	}
	
	public boolean bottom(){
		return bottomCollision;
	}
}
