package neon.entity;

import org.newdawn.slick.geom.Rectangle;

import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public abstract class PhysicalEntity implements Entity {

	protected Collision collision;
	protected Physics physics;

	public Collision getCollision() {
		return collision;
	}

	public Physics getPhysics() {
		return physics;
	}

	public CollisionDirection isColliding(PhysicalEntity other) {
		Rectangle thisRect = this.getCollision().getHitbox();
		thisRect = new Rectangle(thisRect.getX() + this.getX(), thisRect.getY() + this.getY(), thisRect.getWidth(),
				thisRect.getHeight());
		Rectangle otherRect = other.getCollision().getHitbox();
		otherRect = new Rectangle(otherRect.getX() + other.getX(), otherRect.getY() + other.getY(),
				otherRect.getWidth(), otherRect.getHeight());

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
					return CollisionDirection.UP;
				} else if (deltaX > 0) {
					return CollisionDirection.RIGHT;
				} else if (deltaX < 0) {
					return CollisionDirection.LEFT;
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
					return CollisionDirection.DOWN;
				} else if (deltaX > 0) {
					return CollisionDirection.RIGHT;
				} else if (deltaX < 0) {
					return CollisionDirection.LEFT;
				}
			}
		}

		return CollisionDirection.NONE;
	}
}
