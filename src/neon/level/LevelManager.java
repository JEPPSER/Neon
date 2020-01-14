package neon.level;

import neon.camera.Camera;
import neon.entity.Entity;
import neon.graphics.Point;
import neon.io.LevelLoader;
import neon.overworld.entity.World;

public class LevelManager {
	
	private static Level level;
	private static Checkpoint checkpoint;
	private static String[] levels;
	private static int currentLevel = 0;
	private static String theme;
	
	public static void setWorld(World world) {
		String[] levels = new String[world.getLevels().size()];
		for (int i = 0; i < levels.length; i++) {
			levels[i] = world.getLevels().get(i);
		}
		theme = world.getTheme();
		LevelManager.levels = levels;
		currentLevel = 0;
		checkpoint = null;
		LevelManager.level = LevelLoader.readFile(levels[currentLevel]);
	}
	
	public static void nextLevel() {
		if (currentLevel >= levels.length - 1) {
			level.getPlayer().setExitWorld(true);
			return;
		}
		currentLevel++;
		Camera c = level.getCamera();
		level = LevelLoader.readFile(levels[currentLevel]);
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
	
	public static String getTheme() {
		return theme;
	}
}
