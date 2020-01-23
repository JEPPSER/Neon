package neon.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import neon.entity.Entity;
import neon.entity.ai.enemy.Gorilla;
import neon.entity.ai.enemy.Gunman;
import neon.entity.ai.enemy.RageStick;
import neon.entity.ai.enemy.Serpent;
import neon.entity.ai.enemy.ShootableButton;
import neon.entity.ai.enemy.Skeleton;
import neon.entity.ai.enemy.Spider;
import neon.entity.area.ActivateMovableTrigger;
import neon.entity.area.CameraChangeTrigger;
import neon.entity.area.CheckpointTrigger;
import neon.entity.area.RandomActivateTrigger;
import neon.entity.area.TextTrigger;
import neon.entity.area.minigame.FlappyArea;
import neon.entity.area.minigame.PongArea;
import neon.entity.area.minigame.SnakeArea;
import neon.entity.collectable.ActivateItem;
import neon.entity.collectable.GunCollector;
import neon.entity.collectable.Heart;
import neon.entity.collectable.JumpItem;
import neon.entity.collectable.Portal;
import neon.entity.controllable.Player;
import neon.entity.event.ActivateMovableEvent;
import neon.entity.graphics.GraphicsEntity;
import neon.entity.graphics.TreeTrunk;
import neon.entity.terrain.BouncingGround;
import neon.entity.terrain.Bounds;
import neon.entity.terrain.BreakableGround;
import neon.entity.terrain.Ground;
import neon.entity.terrain.Spikes;
import neon.entity.terrain.TreeBranch;
import neon.entity.terrain.movable.MovableGround;
import neon.entity.terrain.movable.MovableSpikes;
import neon.entity.weapon.Gun;
import neon.graphics.Point;
import neon.level.Level;
import neon.level.LevelManager;
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
			level.setBackground(new Color(Integer.parseInt(c[0]), Integer.parseInt(c[1]), Integer.parseInt(c[2])));
			
			String[] size = lines[1].split(",");
			level.setWidth(Float.parseFloat(size[0]));
			level.setHeight(Float.parseFloat(size[1]));
			
			String[] point = lines[2].split(",");
			Point p = new Point(Float.parseFloat(point[0]), Float.parseFloat(point[1]));
			level.setSpawnPoint(p);

			ArrayList<Entity> objects = level.getObjects();
			for (int i = 4; i < lines.length; i++) {
				String[] parts = lines[i].split(",");
				Entity e = getEntity(parts);
				objects.add(e);
			}
			
			// Combine grounds
			ArrayList<Ground> grounds = new ArrayList<Ground>();
			for (Entity e : objects) {
				if (e.getID() == 0 && !e.getName().equals("Ground")) {
					grounds.add((Ground) e);
				}
			}
			for (Ground e : grounds) {
				e.adjustImageMatrix(grounds);
			}
			
			String[] paths = lines[3].split(",");
			setBounds(level, objects, paths);
			level.setObjects(objects);
			return level;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void setBounds(Level level, ArrayList<Entity> objects, String[] imgPaths) {
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
		Player p = new Player(level.getSpawnPoint().getX(), level.getSpawnPoint().getY(), null);
		objects.add(p);
		level.setPlayer(p);
		
		try {
			String res = "res/images/";
			String theme = LevelManager.getTheme();
			if (theme == null || theme.equals("")) {
				theme = "forest";
			}
			ceil.setImage(new Image(res + imgPaths[0] + "_" + theme + ".png"));
			right.setImage(new Image(res + imgPaths[1] + "_" + theme + ".png").getFlippedCopy(true, false));
			floor.setImage(new Image(res + imgPaths[2] + "_" + theme + ".png"));
			left.setImage(new Image(res + imgPaths[3] + "_" + theme + ".png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
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
		} else if (id == 13) {
			return createMovableSpikes(parts);
		} else if (id == 14) {
			return createBouncingGround(parts);
		} else if (id == 15) {
			return createRandomActivateTrigger(parts);
		} else if (id == 16) {
			return createGunCollector(parts);
		} else if (id == 17) {
			return createJumpItem(parts);
		} else if (id == 18) {
			return createSerpent(parts);
		} else if (id == 19) {
			return createActivateItem(parts);
		} else if (id == 20) {
			return createCameraChangeTrigger(parts);
		} else if (id == 21) {
			return createShootableButton(parts);
		} else if (id == 22) {
			return createSnakeArea(parts);
		} else if (id == 23) {
			return createPongArea(parts);
		} else if (id == 24) {
			return createFlappyArea(parts);
		} else if (id == 25) {
			return createRageStick(parts);
		} else if (id == 26) {
			return createGraphicsEntity(parts);
		} else if (id == 27) {
			return createBreakableGround(parts);
		} else if (id == 28) {
			return createTreeBranch(parts);
		} else if (id == 29) {
			return createTreeTrunk(parts);
		}
		return null;
	}
	
	private static TreeTrunk createTreeTrunk(String[] parts) {
		float x = Float.parseFloat(parts[1]);
		float y = Float.parseFloat(parts[2]);
		float height = Float.parseFloat(parts[3]);
		return new TreeTrunk(x, y, height);
	}
	
	private static TreeBranch createTreeBranch(String[] parts) {
		float x = Float.parseFloat(parts[1]);
		float y = Float.parseFloat(parts[2]);
		float width = Float.parseFloat(parts[3]);
		boolean mirrored = Boolean.valueOf(parts[4]);
		return new TreeBranch(x, y, width, mirrored);
	}
	
	private static BreakableGround createBreakableGround(String[] parts) {
		float x = Float.parseFloat(parts[1]);
		float y = Float.parseFloat(parts[2]);
		float width = Float.parseFloat(parts[3]);
		float height = Float.parseFloat(parts[4]);
		return new BreakableGround(x, y, width, height);
	}
	
	private static GraphicsEntity createGraphicsEntity(String[] parts) {
		float x = Float.parseFloat(parts[1]);
		float y = Float.parseFloat(parts[2]);
		int layer = Integer.parseInt(parts[3]);
		float scale = Float.parseFloat(parts[4]);
		int frameRate = Integer.parseInt(parts[5]);
		
		ArrayList<String> frames = new ArrayList<String>();
		for (int i = 6; i < parts.length; i++) {
			frames.add(parts[i]);
		}
		
		return new GraphicsEntity(x, y, layer, scale, frameRate, frames);
	}
	
	private static RageStick createRageStick(String[] parts) {
		RageStick rs = new RageStick(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
		return rs;
	}
	
	private static FlappyArea createFlappyArea(String[] parts) {
		float x = Float.parseFloat(parts[1]);
		float y = Float.parseFloat(parts[2]);
		float width = Float.parseFloat(parts[3]);
		float height = Float.parseFloat(parts[4]);
		int score = Integer.parseInt(parts[6]);
		FlappyArea sa = new FlappyArea(x, y, width, height, parts[5], score);
		return sa;
	}
	
	private static PongArea createPongArea(String[] parts) {
		float x = Float.parseFloat(parts[1]);
		float y = Float.parseFloat(parts[2]);
		float width = Float.parseFloat(parts[3]);
		float height = Float.parseFloat(parts[4]);
		PongArea sa = new PongArea(x, y, width, height, parts[5]);
		return sa;
	}
	
	private static SnakeArea createSnakeArea(String[] parts) {
		float x = Float.parseFloat(parts[1]);
		float y = Float.parseFloat(parts[2]);
		float width = Float.parseFloat(parts[3]);
		float height = Float.parseFloat(parts[4]);
		SnakeArea sa = new SnakeArea(x, y, width, height, parts[5]);
		return sa;
	}
	
	private static ShootableButton createShootableButton(String[] parts) {
		ShootableButton sb = new ShootableButton(CollisionDirection.valueOf(parts[3]), parts[4], Boolean.valueOf(parts[5]));
		sb.setX(Float.parseFloat(parts[1]));
		sb.setY(Float.parseFloat(parts[2]));
		return sb;
	}
	
	private static CameraChangeTrigger createCameraChangeTrigger(String[] parts) {
		CameraChangeTrigger t = new CameraChangeTrigger(Float.parseFloat(parts[5]), Float.parseFloat(parts[6]));
		t.setX(Float.parseFloat(parts[1]));
		t.setY(Float.parseFloat(parts[2]));
		t.setSize(Float.parseFloat(parts[3]), Float.parseFloat(parts[4]));
		return t;
	}
	
	private static ActivateItem createActivateItem(String[] parts) {
		ActivateItem ai = new ActivateItem(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), parts[3]);
		return ai;
	}
	
	private static Serpent createSerpent(String[] parts) {
		Serpent s = new Serpent(Float.parseFloat(parts[1]), Float.parseFloat((parts[2])));
		return s;
	}
	
	private static JumpItem createJumpItem(String[] parts) {
		JumpItem ji = new JumpItem(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
		return ji;
	}
	
	private static GunCollector createGunCollector(String[] parts) {
		GunCollector gc = new GunCollector(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
		return gc;
	}
	
	private static RandomActivateTrigger createRandomActivateTrigger(String[] parts) {
		RandomActivateTrigger t = new RandomActivateTrigger(parts[5], Integer.parseInt(parts[6]));
		t.setX(Float.parseFloat(parts[1]));
		t.setY(Float.parseFloat(parts[2]));
		t.setSize(Float.parseFloat(parts[3]), Float.parseFloat(parts[4]));
		return t;
	}
	
	private static BouncingGround createBouncingGround(String[] parts) {
		BouncingGround g = new BouncingGround();
		g.setX(Float.parseFloat(parts[1]));
		g.setY(Float.parseFloat(parts[2]));
		g.setSize(Float.parseFloat(parts[3]), Float.parseFloat(parts[4]));
		return g;
	}
	
	private static MovableSpikes createMovableSpikes(String[] parts) {
		float width = Float.parseFloat(parts[1]);
		float height = Float.parseFloat(parts[2]);
		CollisionDirection cd = CollisionDirection.valueOf(parts[3]);
		String name = parts[4];
		boolean active = Boolean.parseBoolean(parts[5]);
		boolean looping = Boolean.parseBoolean(parts[6]);
		boolean canReset = Boolean.parseBoolean(parts[7]);
		float speed = Float.parseFloat(parts[8]);
		ArrayList<Point> path = new ArrayList<Point>();
		for (int i = 9; i < parts.length; i+=2) {
			Point p = new Point(Float.parseFloat(parts[i]), Float.parseFloat(parts[i + 1]));
			path.add(p);
		}
		MovableSpikes mg = new MovableSpikes(cd, name, active, looping, canReset, speed, path);
		mg.setSize(width, height);
		return mg;
	}
	
	private static ActivateMovableTrigger createActivateMovableTrigger(String[] parts) {
		boolean reset = false;
		if (parts.length == 7) {
			reset = Boolean.parseBoolean(parts[6]);
		}
		ActivateMovableTrigger t = new ActivateMovableTrigger(parts[5], reset);
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
		String type = "dead";
		if (parts.length == 4) {
			type = parts[3];
		}
		ActivateMovableEvent e = new ActivateMovableEvent(parts[1], parts[2], type);
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
		boolean canReset = Boolean.parseBoolean(parts[6]);
		float speed = Float.parseFloat(parts[7]);
		ArrayList<Point> path = new ArrayList<Point>();
		for (int i = 8; i < parts.length; i+=2) {
			Point p = new Point(Float.parseFloat(parts[i]), Float.parseFloat(parts[i + 1]));
			path.add(p);
		}
		MovableGround mg = new MovableGround(name, active, looping, canReset, speed, path);
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
		boolean canCollect = true;
		if (parts.length == 4 && parts[3].equals("false")) {
			canCollect = false;
		}
		Portal p = new Portal(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), canCollect);
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
		if (parts.length == 6) {
			g.setName(parts[5]);
		}
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
		return getEntity(e.toString().split(","));
	}
}
