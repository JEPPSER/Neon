package neon.entity.terrain;

import neon.entity.PhysicalEntity;
import neon.entity.area.Trigger;
import neon.entity.controllable.Player;
import neon.physics.CollisionDirection;

public abstract class OneWay extends TerrainEntity {
	
	protected CollisionDirection enterDir;
	protected CollisionDirection orientation;
	private boolean isColliding = false;
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		if (other instanceof Trigger || other instanceof TerrainEntity) {
			return;
		}
		
		CollisionDirection cd = this.collisionDirection;
		PhysicalEntity pe = this.collidingEntity;
		
		if (other instanceof Player) {
			isColliding = false;
		}
		
		if (pe != null) {
			isColliding = true;
		}
		
		// Check if entered and set direction
		if (enterDir == null && other == pe) {
			enterDir = cd;
		} else if (enterDir != null && !isColliding) {
			enterDir = null;
		}
		
		if (enterDir == orientation && other == pe) {
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
