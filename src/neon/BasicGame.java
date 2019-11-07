package neon;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import neon.entity.PhysicalEntity;
import neon.entity.ai.AIEntity;
import neon.entity.ai.enemy.Spider;
import neon.entity.collectable.Heart;
import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.io.LevelLoader;
import neon.io.SpriteLoader;
import neon.level.LevelManager;
import neon.physics.PhysicsEngine;
import neon.time.TimeInfo;
import neon.camera.Camera;
import neon.combat.CombatEngine;
import neon.cutscene.CutsceneManager;

public class BasicGame extends BasicGameState {

	public static int id = 1;

	private LevelLoader levelLoader;
	private CutsceneManager cutsceneManager;
	private PhysicsEngine physics;
	private CombatEngine combat;
	private Camera camera;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setShowFPS(false);
		float defaultZoom = (float) gc.getHeight() / 900f; // 1600 is the base width
		defaultZoom *= (float) Display.getHeight() / (float) gc.getHeight();
		Log.setVerbose(true);
		SpriteLoader sl = new SpriteLoader();
		sl.searchFolder("res");
		
		physics = new PhysicsEngine();
		combat = new CombatEngine();
		
		levelLoader = new LevelLoader();
		LevelManager.setLevel(levelLoader.readFile("res/levels/level_1.nlvl"));
		
		camera = new Camera(LevelManager.getLevel().getPlayer(), gc);
		LevelManager.getLevel().setCamera(camera);
		camera.zoom(defaultZoom);
		camera.pan(0, gc.getHeight() - Display.getHeight());

		cutsceneManager = new CutsceneManager();
		/*
		 * Cutscene cutscene = new Cutscene(5000); CameraScript cs = new
		 * CameraScript(); cs.addFocalPoint(1000, 500, 500, 1);
		 * cs.addFocalPoint(2000, 500, 500, 2); cs.addFocalPoint(3000, 400, 500,
		 * 2); cs.addFocalPoint(4000, 400, 400, 1); cs.addFocalPoint(5000, 200,
		 * 500, 1); cutscene.setCameraScript(cs);
		 * cutsceneManager.getCutscenes().add(cutscene);
		 * cutscene.startCutscene();
		 */
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		camera.renderPlayField(LevelManager.getLevel(), g);
		camera.renderStaticElements(gc, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (delta > 50) {
			delta = 0;
		}
		TimeInfo.setDelta(delta);
		Player p = LevelManager.getLevel().getPlayer();
		Input input = gc.getInput();
		physics.applyPhysics(LevelManager.getLevel().getObjects());
		cutsceneManager.updateCutScenes(camera);
		if (!cutsceneManager.isCutsceneRunnging()) {
			p.control(input);
		}
		combat.updateCombat(LevelManager.getLevel().getObjects(), p);

		// Updates timing for all animations
		for (int i = 0; i < LevelManager.getLevel().getObjects().size(); i++) {
			if (LevelManager.getLevel().getObjects().get(i) instanceof PhysicalEntity) {
				Animator anim = ((PhysicalEntity) LevelManager.getLevel().getObjects().get(i)).getGraphics().getAnimator();
				if (anim != null) {
					anim.updateAnimations();
				}
			}
		}

		for (int i = 0; i < LevelManager.getLevel().getObjects().size(); i++) {
			if (LevelManager.getLevel().getObjects().get(i) instanceof AIEntity) { // control ai
				((AIEntity) LevelManager.getLevel().getObjects().get(i)).control(p);
			}
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
