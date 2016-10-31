
public class Collision {
	Actor shapeOne = null;
	Actor shapeTwo = null;
	private boolean leftCollision;
	private boolean rightCollision;
	private boolean topCollision;
	private boolean bottomCollision;

	public Collision(Actor one, Actor two) {
		shapeOne = one;
		shapeTwo = two;
	}

	public boolean isColliding() {
		System.out.println(topCollision);
		if (shapeOne.getHBX() + shapeOne.getHBWidth() <= shapeTwo.getHBX() && shapeOne.getHBY() >= shapeTwo.getHBY()
				&& (shapeTwo.getHBX() - (shapeOne.getHBX() + shapeOne.getHBWidth())) <= Platformer.acceleration) {
			leftCollision = true;
			return true;
		}
		if (shapeOne.getHBX() >= shapeTwo.getHBX() + shapeTwo.getHBWidth() && shapeOne.getHBY() >= shapeTwo.getHBY()
				&& shapeOne.getHBX() - (shapeTwo.getHBX() + shapeTwo.getHBWidth()) <= Platformer.acceleration) {
			System.out.println("TRUE");
			rightCollision = true;
			return true;
		}
		if (((shapeOne.getHBX() + shapeOne.getHBWidth() >= shapeTwo.getHBX() && (shapeTwo.getHBX() - shapeOne.getHBX()) <= shapeOne.getHBWidth())
				|| (shapeTwo.getHBX() + shapeTwo.getHBWidth() >= shapeOne.getHBX()) && (shapeOne.getHBX() - shapeTwo.getHBX()) <= shapeOne.getHBWidth())
				&& shapeOne.getHBY() - shapeOne.getHBHeight() <= shapeTwo.getHBY()) {
			topCollision = true;
			return true;
		}
		if(((shapeOne.getHBX() + shapeOne.getHBWidth() >= shapeTwo.getHBX() && (shapeTwo.getHBX() - shapeOne.getHBX()) <= shapeOne.getHBWidth())
				|| (shapeTwo.getHBX() + shapeTwo.getHBWidth() >= shapeOne.getHBX()) && (shapeOne.getHBX() - shapeTwo.getHBX()) <= shapeOne.getHBWidth())
				&& shapeOne.getHBY() >= shapeTwo.getHBY()) {
			bottomCollision = true;
			return true;
		}

		return false;
	}

	public void moveObject() {
		if (isColliding()) {
			if (leftCollision) {
				shapeTwo.setX(shapeTwo.getX() + Platformer.acceleration);
				shapeTwo.setHBX(shapeTwo.getHBX() + Platformer.acceleration);
				shapeTwo.getImageView().setX(shapeTwo.getImageView().getX() + Platformer.acceleration);
				leftCollision = false;
			}
			if (rightCollision) {
				shapeTwo.setX(shapeTwo.getX() - Platformer.acceleration);
				shapeTwo.setHBX(shapeTwo.getHBX() - Platformer.acceleration);
				shapeTwo.getImageView().setX(shapeTwo.getImageView().getX() - Platformer.acceleration);
				rightCollision = false;
			}
		}
	}
	public boolean onTop(){
		if(topCollision){
			topCollision = false;
			return true;
		}
		return false;
	}
}

