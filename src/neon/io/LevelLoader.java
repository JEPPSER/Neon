package neon.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.newdawn.slick.Color;

import neon.entity.Entity;
import neon.entity.area.TextTrigger;
import neon.entity.area.Trigger;
import neon.entity.terrain.Bounds;
import neon.entity.terrain.Ground;
import neon.graphics.Point;
import neon.level.Level;
import neon.physics.CollisionDirection;

public class LevelLoader {
	
	public Level readFile(String path) {
		try {
			File file = new File(path);
			String str = new String(Files.readAllBytes(Paths.get(path)));
			str = str.replaceAll("\r", "");
			String[] lines = str.split("\n");
			
			Level level = new Level();
			level.setName(file.getName().replace(".nlvl", ""));
			
			String[] c = lines[0].split(",");
			level.setBackground(new Color(Float.parseFloat(c[0]), Float.parseFloat(c[1]), Float.parseFloat(c[2])));
			
			String[] size = lines[1].split(",");
			level.setWidth(Float.parseFloat(size[0]));
			level.setHeight(Float.parseFloat(size[1]));
			
			String[] point = lines[2].split(",");
			Point p = new Point(Float.parseFloat(point[0]), Float.parseFloat(point[1]));
			level.setSpawnPoint(p);

			ArrayList<Entity> objects = new ArrayList<Entity>();
			for (int i = 3; i < lines.length; i++) {
				String[] parts = lines[i].split(",");
				int id = Integer.parseInt(parts[0]);
				Entity e = getEntityFromID(id);
				
				float x = Float.parseFloat(parts[1]);
				float y = Float.parseFloat(parts[2]);
				e.setX(x);
				e.setY(y);
				
				float height = 0;
				float width = 0;
				if (parts.length >= 5) {
					width = Float.parseFloat(parts[3]);
					height = Float.parseFloat(parts[4]);
					e.setSize(width, height);
				}
				
				if (e instanceof Trigger) {
					((Trigger) e).setTrigger(parts[5], Float.parseFloat(parts[6]), Float.parseFloat(parts[7]));
				}
				
				objects.add(e);
			}
			
			setBounds(level, objects);
			level.setObjects(objects);
			return level;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void setBounds(Level level, ArrayList<Entity> objects) {
		Bounds ceil = new Bounds(CollisionDirection.DOWN);
		ceil.setX(0);
		ceil.setY(-100);
		ceil.setSize(level.getWidth(), 100f);
		objects.add(ceil);
		Bounds floor = new Bounds(CollisionDirection.UP);
		floor.setX(0);
		floor.setY(level.getHeight());
		floor.setSize(level.getWidth(), 100f);
		objects.add(floor);
		Bounds left = new Bounds(CollisionDirection.RIGHT);
		left.setX(-100);
		left.setY(0);
		left.setSize(100, level.getHeight());
		objects.add(left);
		Bounds right = new Bounds(CollisionDirection.LEFT);
		right.setX(level.getWidth());
		right.setY(0);
		right.setSize(100, level.getHeight());
		objects.add(right);
	}
	
	private Entity getEntityFromID(int id) {
		if (id == 0) {
			return new Ground();
		} else if (id == 1) {
			return new TextTrigger();
		}
		return null;
	}
}
