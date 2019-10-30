package neon.entity.collectable;

import neon.entity.PhysicalEntity;
import neon.entity.controllable.Player;
import neon.entity.terrain.TerrainEntity;
import neon.physics.CollisionDirection;

public abstract class CollectableEntity extends PhysicalEntity {
	
	protected boolean isCollected = false;
	protected boolean canCollect = true;
	
	public abstract void collect(Player player);
	
	public boolean canCollect() {
		return canCollect;
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		CollisionDirection cd = this.collisionDirection;
		PhysicalEntity pe = this.collidingEntity;
		
		if (other instanceof TerrainEntity){
			if (cd == CollisionDirection.DOWN) {
				this.setY(pe.getY() - this.getCollision().getHitbox().getHeight());
				physics.setYVelocity(0f);
				physics.setXVelocity(0f);
			} else if (cd == CollisionDirection.UP) {
				this.setY(pe.getY() + pe.getCollision().getHitbox().getHeight());
				physics.setYVelocity(0f);
				physics.setXVelocity(0f);
			} else if (cd == CollisionDirection.RIGHT) {
				this.setX(pe.getX() - this.getCollision().getHitbox().getWidth());
				physics.setYVelocity(0f);
				physics.setXVelocity(0f);
			} else if (cd == CollisionDirection.LEFT) {
				this.setX(pe.getX() + pe.getCollision().getHitbox().getWidth());
				physics.setYVelocity(0f);
				physics.setXVelocity(0f);
			}
		}
	}
}
