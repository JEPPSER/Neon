package neon;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import neon.entity.PhysicalEntity;
import neon.entity.ai.AIEntity;
import neon.entity.ai.Spider;
import neon.entity.collectable.CollectableEntity;
import neon.entity.collectable.Heart;
import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.io.LevelLoader;
import neon.io.SpriteLoader;
import neon.level.Level;
import neon.physics.PhysicsEngine;
import neon.time.TimeInfo;
import neon.camera.Camera;
import neon.combat.CombatEngine;

public class BasicGame extends BasicGameState {

	public static int id = 1;

	private Player p;
	private LevelLoader levelLoader;
	private Level level;
	private PhysicsEngine physics;
	private CombatEngine combat;
	private Camera camera;
	private TimeInfo timeInfo;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Log.setVerbose(false);
		SpriteLoader sl = new SpriteLoader("res");
		timeInfo = new TimeInfo();
		physics = new PhysicsEngine();
		combat = new CombatEngine();
		levelLoader = new LevelLoader();
		level = levelLoader.readFile("res/levels/level_3.nlvl");
		p = new Player(300, 100);
		camera = new Camera(p, gc);
		p.setX(level.getSpawnPoint().getX());
		p.setY(level.getSpawnPoint().getY());
		level.getObjects().add(p);

		Spider spider = new Spider(500, 500);
		level.getObjects().add(spider);
		Heart heart = new Heart(700, 500);
		level.getObjects().add(heart);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		camera.renderPlayField(level, g);
		p.drawHealthBar(g, gc.getWidth() / 2 - 50, 10);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		timeInfo.setDelta(delta);
		Input input = gc.getInput();
		physics.applyPhysics(level.getObjects());
		p.control(input);
		combat.updateCombat(level.getObjects(), p);

		// Updates timing for all animations
		for (int i = 0; i < level.getObjects().size(); i++) {
			if (level.getObjects().get(i) instanceof PhysicalEntity) {
				Animator anim = ((PhysicalEntity) level.getObjects().get(i)).getGraphics().getAnimator();
				if (anim != null) {
					anim.updateAnimations();
				}
			}
		}

		for (int i = 0; i < level.getObjects().size(); i++) {
			if (level.getObjects().get(i) instanceof AIEntity) { // control ai
				((AIEntity) level.getObjects().get(i)).control(p);
			} else if (level.getObjects().get(i) instanceof CollectableEntity) { // remove collected entities
				CollectableEntity ce = (CollectableEntity) level.getObjects().get(i);
				if (ce.isCollected()) {
					level.getObjects().remove(ce);
				}
			}
		}
		
		// Respawns player after death
		if (p.getHealth() <= 0) {
			p.setHealth(p.getMaxHealth());
			p.setX(level.getSpawnPoint().getX());
			p.setY(level.getSpawnPoint().getY());
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			Spider spider = new Spider(500, 500);
			level.getObjects().add(spider);
		}
		
		if (input.isKeyPressed(Input.KEY_TAB)) {
			Heart heart = new Heart(700, 500);
			level.getObjects().add(heart);
		}

		/*
		 * try { Thread.sleep(4); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}

	@Override
	public int getID() {
		return BasicGame.id;
	}
}
