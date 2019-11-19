package neon.entity.terrain.movable;

public interface MovableTerrain {
	
	public void activate();
	public void deactivate();
	public void updateMovement();
	public void reset();
	public void resetWhenDone();
	public int getID();
	public boolean isActive();
}
