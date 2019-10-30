package neon.entity.collectable;

import neon.entity.PhysicalEntity;
import neon.entity.controllable.Player;
import neon.entity.terrain.TerrainEntity;

public abstract class CollectableEntity extends PhysicalEntity {
	
	protected boolean isCollected = false;
	
	public abstract void collect(Player player);
	
	public boolean isCollected() {
		return isCollected;
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		if (other instanceof TerrainEntity){
			super.handleCollision(other);
		}
	}
}
