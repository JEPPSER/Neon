package neon.level;

import neon.entity.Entity;
import neon.graphics.Point;

public class LevelManager {
	
	private static Level level;
	
	public static void setLevel(Level level) {
		LevelManager.level = level;
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
}
