package neon.physics;

public class Physics {
	
	private float xVelocity;
	private float yVelocity;
	
	public Physics(float xVelocity, float yVelocity) {
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
	}

	public float getXVelocity() {
		return this.xVelocity;
	}
	
	public void setXVelocity(float xVelocity) {
		this.xVelocity = xVelocity;
	}
	
	public float getYVelocity() {
		return this.yVelocity;
	}
	
	public void setYVelocity(float yVelocity) {
		this.yVelocity = yVelocity;
	}
}
