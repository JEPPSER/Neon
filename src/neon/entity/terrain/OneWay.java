package neon.entity.terrain;

import org.newdawn.slick.Color;

import neon.entity.PhysicalEntity;
import neon.physics.CollisionDirection;

public abstract class OneWay extends TerrainEntity {
	
	protected CollisionDirection enterDir;
	protected CollisionDirection orientation;
	protected Color temp;
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		CollisionDirection cd = this.collisionDirection;
		PhysicalEntity pe = this.collidingEntity;
		
		// Check if entered and set direction
		if (enterDir == null && other == pe) {
			enterDir = cd;
		} else if (enterDir != null && pe == null) {
			enterDir = null;
		}
		
		if (enterDir == orientation) {
			if (enterDir == CollisionDirection.UP && pe.getPhysics().getYVelocity() > 0) {
				pe.setY(y - pe.getCollision().getHitbox().getHeight());
				pe.getPhysics().setYVelocity(0f);
			} else if (enterDir == CollisionDirection.DOWN && pe.getPhysics().getYVelocity() < 0) {
				pe.setY(y + height);
				pe.getPhysics().setYVelocity(0f);
			} else if (enterDir == CollisionDirection.LEFT && pe.getPhysics().getXVelocity() > 0) {
				pe.setX(x - pe.getCollision().getHitbox().getWidth());
				pe.getPhysics().setXVelocity(0f);
			} else if (enterDir == CollisionDirection.RIGHT && pe.getPhysics().getXVelocity() < 0) {
				pe.setX(x + width);
				pe.getPhysics().setXVelocity(0f);
			}
		}
	}

	public CollisionDirection getEnterDir() {
		return enterDir;
	}

	public void setEnterDir(CollisionDirection enterDir) {
		this.enterDir = enterDir;
	}

	public CollisionDirection getOrientation() {
		return orientation;
	}

	public void setOrientation(CollisionDirection orientation) {
		this.orientation = orientation;
	}
}
