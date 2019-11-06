package neon.level;

import neon.entity.Entity;
import neon.graphics.Point;

public class LevelManager {
	
	private static Level level;
	private static Checkpoint checkpoint;
	
	public static void setLevel(Level level) {
		LevelManager.level = level;
	}
	
	public static void setCheckpoint(Checkpoint checkpoint) {
		LevelManager.checkpoint = checkpoint;
	}
	
	public static void resetFromCheckpoint() {
		level = checkpoint.getCopyOfLevel(checkpoint.getLevel());
		level.getCamera().setFocusedEntity(level.getPlayer());
		
	}
	
	public static void addEntity(Entity entity) {
		level.getObjects().add(entity);
	}
	
	public static void removeEntity(Entity entity) {
		level.getObjects().remove(entity);
	}
	
	public static Point getSpawnPoint() {
		return level.getSpawnPoint();
	}
	
	public static Level getLevel() {
		return level;
	}
	
	public static Checkpoint getCheckpoint() {
		return checkpoint;
	}
}
