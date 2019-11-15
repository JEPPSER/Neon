package neon.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.newdawn.slick.Color;

import neon.entity.Entity;
import neon.entity.ai.enemy.Gorilla;
import neon.entity.ai.enemy.Gunman;
import neon.entity.ai.enemy.Skeleton;
import neon.entity.ai.enemy.Spider;
import neon.entity.area.ActivateMovableTrigger;
import neon.entity.area.CheckpointTrigger;
import neon.entity.area.TextTrigger;
import neon.entity.collectable.Heart;
import neon.entity.collectable.Portal;
import neon.entity.controllable.Player;
import neon.entity.event.ActivateMovableEvent;
import neon.entity.terrain.Bounds;
import neon.entity.terrain.Ground;
import neon.entity.terrain.Spikes;
import neon.entity.terrain.movable.MovableGround;
import neon.graphics.Point;
import neon.level.Level;
import neon.physics.CollisionDirection;

public class LevelLoader {
	
	public static Level readFile(String path) {
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

			ArrayList<Entity> objects = level.getObjects();
			for (int i = 3; i < lines.length; i++) {
				String[] parts = lines[i].split(",");
				Entity e = getEntity(parts);
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
	
	public static void setBounds(Level level, ArrayList<Entity> objects) {
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
		Player p = new Player(level.getSpawnPoint().getX(), level.getSpawnPoint().getY());
		objects.add(p);
		level.setPlayer(p);
	}
	
	private static Entity getEntity(String[] parts) {
		int id = Integer.parseInt(parts[0]);
		if (id == 0) {
			return createGround(parts);
		} else if (id == 1) {
			return createTextTrigger(parts);
		} else if (id == 2) {
			return createSpider(parts);
		} else if (id == 3) {
			return createGorilla(parts);
		} else if (id == 4) {
			return createHeart(parts);
		} else if (id == 5) {
			return createCheckpoint(parts);
		} else if (id == 6) {
			return createPortal(parts);
		} else if (id == 7) {
			return createSpikes(parts);
		} else if (id == 8) {
			return createMovableGround(parts);
		} else if (id == 9) {
			return createSkeleton(parts);
		} else if (id == 10) {
			return createActivateMovableEvent(parts);
		} else if (id == 11) {
			return createGunman(parts);
		} else if (id == 12) {
			return createActivateMovableTrigger(parts);
		}
		return null;
	}
	
	private static ActivateMovableTrigger createActivateMovableTrigger(String[] parts) {
		ActivateMovableTrigger t = new ActivateMovableTrigger(parts[5]);
		t.setX(Float.parseFloat(parts[1]));
		t.setY(Float.parseFloat(parts[2]));
		t.setSize(Float.parseFloat(parts[3]), Float.parseFloat(parts[4]));
		return t;
	}
	
	private static Gunman createGunman(String[] parts) {
		Gunman g = new Gunman(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
		return g;
	}
	
	private static ActivateMovableEvent createActivateMovableEvent(String[] parts) {
		ActivateMovableEvent e = new ActivateMovableEvent(parts[1], parts[2]);
		return e;
	}
	
	private static Skeleton createSkeleton(String[] parts) {
		Skeleton s = new Skeleton(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
		return s;
	}
	
	private static MovableGround createMovableGround(String[] parts) {
		float width = Float.parseFloat(parts[1]);
		float height = Float.parseFloat(parts[2]);
		String name = parts[3];
		boolean active = Boolean.parseBoolean(parts[4]);
		boolean looping = Boolean.parseBoolean(parts[5]);
		float speed = Float.parseFloat(parts[6]);
		ArrayList<Point> path = new ArrayList<Point>();
		for (int i = 7; i < parts.length; i+=2) {
			Point p = new Point(Float.parseFloat(parts[i]), Float.parseFloat(parts[i + 1]));
			path.add(p);
		}
		MovableGround mg = new MovableGround(name, active, looping, speed, path);
		mg.setSize(width, height);
		return mg;
	}
	
	private static Spikes createSpikes(String[] parts) {
		Spikes s = new Spikes(CollisionDirection.valueOf(parts[5]));
		s.setX(Float.parseFloat(parts[1]));
		s.setY(Float.parseFloat(parts[2]));
		s.setSize(Float.parseFloat(parts[3]), Float.parseFloat(parts[4]));
		return s;
	}
	
	private static Portal createPortal(String[] parts) {
		Portal p = new Portal(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
		return p;
	}
	
	private static CheckpointTrigger createCheckpoint(String[] parts) {
		CheckpointTrigger g = new CheckpointTrigger();
		g.setX(Float.parseFloat(parts[1]));
		g.setY(Float.parseFloat(parts[2]));
		g.setSize(Float.parseFloat(parts[3]), Float.parseFloat(parts[4]));
		g.setTrigger("", 0, 0);
		return g;
	}
	
	private static Heart createHeart(String[] parts) {
		Heart h = new Heart(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
		return h;
	}
	
	private static Gorilla createGorilla(String[] parts) {
		Gorilla s = new Gorilla(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
		return s;
	}
	
	private static Spider createSpider(String[] parts) {
		Spider s = new Spider(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
		return s;
	}
	
	private static Ground createGround(String[] parts) {
		Ground g = new Ground();
		g.setX(Float.parseFloat(parts[1]));
		g.setY(Float.parseFloat(parts[2]));
		g.setSize(Float.parseFloat(parts[3]), Float.parseFloat(parts[4]));
		return g;
	}
	
	private static TextTrigger createTextTrigger(String[] parts) {
		TextTrigger t = new TextTrigger();
		t.setX(Float.parseFloat(parts[1]));
		t.setY(Float.parseFloat(parts[2]));
		t.setSize(Float.parseFloat(parts[3]), Float.parseFloat(parts[4]));
		t.setTrigger(parts[5], 0, 100);
		return t;
	}
	
	public static Entity copyEntity(Entity e) {
		StringBuilder sb = new StringBuilder();
		sb.append(e.getID());
		sb.append(",");
		sb.append(e.getX());
		sb.append(",");
		sb.append(e.getY());
		sb.append(",");
		sb.append(e.getWidth());
		sb.append(",");
		sb.append(e.getHeight());
		if (e.getID() == 1) {
			sb.append(",");
			sb.append(((TextTrigger) e).getText());
		}
		String line = sb.toString();
		return getEntity(line.split(","));
	}
}
