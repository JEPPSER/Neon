package neon.level;

import java.util.ArrayList;

import neon.entity.Entity;
import neon.entity.area.Trigger;
import neon.entity.collectable.Portal;
import neon.entity.controllable.Player;
import neon.entity.event.Event;
import neon.entity.projectile.ProjectileEntity;
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
			if (!(objects.get(i) instanceof Player) && objects.get(i) != null && !(objects.get(i) instanceof ProjectileEntity)) {
				if (!(objects.get(i) instanceof TerrainEntity) && !(objects.get(i) instanceof Trigger) && !(objects.get(i) instanceof Event)) {
					Entity e = LevelLoader.copyEntity(objects.get(i));
					result.getObjects().add(e);
				} else {
					result.getObjects().add(objects.get(i));
				}
			}
		}
		
		Player p = new Player(level.getSpawnPoint().getX(), level.getSpawnPoint().getY(), level.getPlayer().getWeapon());
		result.getObjects().add(p);
		result.setPlayer(p);
		
		return result;
	}
}
