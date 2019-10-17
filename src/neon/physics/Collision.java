package neon.physics;

import org.newdawn.slick.geom.Rectangle;

public class Collision {
	
	private float friction;
	private Rectangle hitbox;
	private float weight;
	private boolean movable;
	
	public Collision(Rectangle hitbox, float friction, float weight, boolean movable) {
		this.hitbox = hitbox;
		this.friction = friction;
		this.weight = weight;
		this.movable = movable;
	}
	
	public Collision() {
		
	}
	
	public float getFriction() {
		return this.friction;
	}
	
	public void setFriction(float friction) {
		this.friction = friction;
	}
	
	public Rectangle getHitbox() {
		return this.hitbox;
	}
	
	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}
	
	public float getWeight() {
		return this.weight;
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public boolean isMovable() {
		return movable;
	}
	
	public void setMovable(boolean movable) {
		this.movable = movable;
	}
}
