package neon;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import neon.entity.controllable.Player;
import neon.io.LevelLoader;
import neon.level.Level;
import neon.physics.PhysicsEngine;
import neon.time.TimeInfo;
import neon.camera.Camera;

public class BasicGame extends BasicGameState {
	
	public static int id = 1;
	
	private Player p;
	private LevelLoader levelLoader;
	private Level level;
	private PhysicsEngine physics;
	private Camera camera;
	private TimeInfo timeInfo;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		timeInfo = new TimeInfo();
		physics = new PhysicsEngine();
		levelLoader = new LevelLoader();
		level = levelLoader.readFile("res/levels/level_2.nlvl");
		p = new Player(300, 100);
		camera = new Camera(p, gc);
		p.setX(level.getSpawnPoint().getX());
		p.setY(level.getSpawnPoint().getY());
		level.getObjects().add(p);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setAntiAlias(true);
		camera.renderPlayField(level, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		timeInfo.setDelta(delta);
		Input input = gc.getInput();
		physics.applyPhysics(level.getObjects());
		p.control(input);
		/*try {
			Thread.sleep(4);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	@Override
	public int getID() {
		return BasicGame.id;
	}
}
