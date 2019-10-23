package neon.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.newdawn.slick.Color;

import neon.entity.Entity;
import neon.entity.terrain.Ground;
import neon.graphics.Point;
import neon.level.Level;

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
				if (parts.length == 5) {
					width = Float.parseFloat(parts[3]);
					height = Float.parseFloat(parts[4]);
					e.setSize(width, height);
				}
				objects.add(e);
			}
			level.setObjects(objects);
			return level;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Entity getEntityFromID(int id) {
		if (id == 0) {
			return new Ground();
		}
		return null;
	}
}
