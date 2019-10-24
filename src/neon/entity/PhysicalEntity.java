package neon.entity;

import org.newdawn.slick.geom.Rectangle;

import neon.entity.area.Trigger;
import neon.graphics.EntityGraphics;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public abstract class PhysicalEntity implements Entity {

	protected Collision collision;
	protected Physics physics;
	protected CollisionDirection collisionDirection;
	protected PhysicalEntity collidingEntity;
	
	protected EntityGraphics graphics;
	protected String name;
	protected float x;
	protected float y;
	protected float width;
	protected float height;

	public Collision getCollision() {
		return collision;
	}

	public Physics getPhysics() {
		return physics;
	}
	
	public CollisionDirection getCollisionDirection() {
		return this.collisionDirection;
	}
	
	public PhysicalEntity getCollidingEntity() {
		return this.collidingEntity;
	}
	
	public void handleCollision(PhysicalEntity other) {
		CollisionDirection cd = collisionDirection;
		PhysicalEntity pe = collidingEntity;
		
		if (!(pe instanceof Trigger)) {
			if (cd == CollisionDirection.DOWN) {
				this.setY(pe.getY() - this.getCollision().getHitbox().getHeight());
				this.getPhysics().setYVelocity(0f);
			} else if (cd == CollisionDirection.UP) {
				this.setY(pe.getY() + pe.getCollision().getHitbox().getHeight());
				this.getPhysics().setYVelocity(0f);
			} else if (cd == CollisionDirection.RIGHT) {
				this.setX(pe.getX() - this.getCollision().getHitbox().getWidth());
				this.getPhysics().setXVelocity(0f);
			} else if (cd == CollisionDirection.LEFT) {
				this.setX(pe.getX() + pe.getCollision().getHitbox().getWidth());
				this.getPhysics().setXVelocity(0f);
			}
		}
	}

	public void checkCollision(PhysicalEntity other) {
		Rectangle thisRect = this.getCollision().getHitbox();
		thisRect = new Rectangle(thisRect.getX() + this.getX(), thisRect.getY() + this.getY(), thisRect.getWidth(),
				thisRect.getHeight());
		Rectangle otherRect = other.getCollision().getHitbox();
		otherRect = new Rectangle(otherRect.getX() + other.getX(), otherRect.getY() + other.getY(),
				otherRect.getWidth(), otherRect.getHeight());
		
		this.collisionDirection = CollisionDirection.NONE;
		this.collidingEntity = null;

		if (thisRect.intersects(otherRect)) {
			if (thisRect.getY() >= otherRect.getY() && thisRect.getY() <= otherRect.getY() + otherRect.getHeight()) {
				float deltaX = 1000;
				if (thisRect.getX() <= otherRect.getX()) {
					deltaX = thisRect.getX() + thisRect.getWidth() - otherRect.getX();
				} else if (thisRect.getX() + thisRect.getWidth() >= otherRect.getX() + otherRect.getWidth()) {
					deltaX = thisRect.getX() - (otherRect.getX() + otherRect.getWidth());
				}
				float deltaY = otherRect.getY() + otherRect.getHeight() - thisRect.getY();
				if (deltaY <= Math.abs(deltaX)) {
					this.collisionDirection = CollisionDirection.UP;
				} else if (deltaX > 0) {
					this.collisionDirection = CollisionDirection.RIGHT;
				} else if (deltaX < 0) {
					this.collisionDirection = CollisionDirection.LEFT;
				}
			} else if (thisRect.getY() + thisRect.getHeight() >= otherRect.getY()
					&& thisRect.getY() + thisRect.getHeight() <= otherRect.getY() + otherRect.getHeight()) {
				float deltaX = 1000;
				if (thisRect.getX() <= otherRect.getX()) {
					deltaX = thisRect.getX() + thisRect.getWidth() - otherRect.getX();
				} else if (thisRect.getX() + thisRect.getWidth() >= otherRect.getX() + otherRect.getWidth()) {
					deltaX = thisRect.getX() - (otherRect.getX() + otherRect.getWidth());
				}
				float deltaY = thisRect.getY() + thisRect.getHeight() - otherRect.getY();
				if (deltaY <= Math.abs(deltaX)) {
					this.collisionDirection = CollisionDirection.DOWN;
				} else if (deltaX > 0) {
					this.collisionDirection = CollisionDirection.RIGHT;
				} else if (deltaX < 0) {
					this.collisionDirection = CollisionDirection.LEFT;
				}
			} else {
				if (thisRect.getX() < otherRect.getX()) {
					this.collisionDirection = CollisionDirection.RIGHT;
				} else if (thisRect.getX() + thisRect.getWidth() > otherRect.getX() + otherRect.getWidth()) {
					this.collisionDirection = CollisionDirection.LEFT;
				}
			}
			this.collidingEntity = other;
		}
	}
}
