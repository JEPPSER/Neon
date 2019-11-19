package neon.level;

import neon.camera.Camera;
import neon.entity.Entity;
import neon.graphics.Point;
import neon.io.LevelLoader;

public class LevelManager {
	
	private static Level level;
	private static Checkpoint checkpoint;
	private static String[] levels = { "level_1", "level_2", "level_3", "level_4", "level_5", "level_6", "level_7", "level_8", "level_9" };
	private static int currentLevel = 0;
	
	public static void setLevel(Level level) {
		LevelManager.level = level;
	}
	
	public static void nextLevel() {
		currentLevel++;
		Camera c = level.getCamera();
		level = LevelLoader.readFile("res/levels/" + levels[currentLevel] + ".nlvl");
		c.setFocusedEntity(level.getPlayer());
		level.setCamera(c);
		checkpoint = null;
		level.getPlayer().resetLevel();
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
