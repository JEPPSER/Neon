package neon.level;

import java.util.ArrayList;

import neon.entity.Entity;
import neon.entity.area.Trigger;
import neon.entity.controllable.Player;
import neon.entity.terrain.TerrainEntity;
import neon.graphics.Point;
import neon.io.LevelLoader;

public class Checkpoint {
	
	private Level level;
	
	public Checkpoint(float spawnX, float spawnY) {
		level = getCopyOfLevel(LevelManager.getLevel());
		level.setSpawnPoint(new Point(spawnX, spawnY));
	}
	
	public Level getLevel() {
		return level;
	}
	
	public Level getCopyOfLevel(Level level) {
		Level result = new Level();
		result.setName(level.getName());
		result.setBackground(level.getBackground());
		result.setWidth(level.getWidth());
		result.setHeight(level.getHeight());
		result.setSpawnPoint(new Point(level.getSpawnPoint().getX(), level.getSpawnPoint().getY()));
		result.setCamera(level.getCamera());
		
		ArrayList<Entity> objects = level.getObjects();
		for (int i = 0; i < objects.size(); i++) {
			if (!(objects.get(i) instanceof Player)) {
				if (!(objects.get(i) instanceof TerrainEntity) && !(objects.get(i) instanceof Trigger)) {
					Entity e = LevelLoader.copyEntity(objects.get(i));
					result.getObjects().add(e);
				} else {
					result.getObjects().add(objects.get(i));
				}
			}
		}
		
		LevelLoader.setBounds(result, result.getObjects());
		
		return result;
	}
}
