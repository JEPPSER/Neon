package neon;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import neon.entity.Entity;
import neon.entity.PhysicalEntity;
import neon.entity.ai.AIEntity;
import neon.entity.ai.enemy.Serpent;
import neon.entity.collectable.GunCollector;
import neon.entity.collectable.JumpItem;
import neon.entity.controllable.Player;
import neon.entity.event.Event;
import neon.entity.weapon.Gun;
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

	private CutsceneManager cutsceneManager;
	private PhysicsEngine physics;
	private CombatEngine combat;
	private Camera camera;
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		float defaultZoom = (float) gc.getHeight() / 900f; // 1600 is the base width
		defaultZoom *= (float) Display.getHeight() / (float) gc.getHeight();
		camera = new Camera(LevelManager.getLevel().getPlayer(), gc);
		LevelManager.getLevel().setCamera(camera);
		camera.zoom(defaultZoom);
		camera.pan(0, gc.getHeight() - Display.getHeight());
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setShowFPS(false);
		Log.setVerbose(true);
		SpriteLoader sl = new SpriteLoader();
		sl.searchFolder("res");
		
		physics = new PhysicsEngine();
		combat = new CombatEngine();
		
		//Serpent s = new Serpent(500, 500);
		//LevelManager.addEntity(s);

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
		ArrayList<Entity> objects = LevelManager.getLevel().getObjects();
		Player p = LevelManager.getLevel().getPlayer();
		Input input = gc.getInput();
		physics.applyPhysics(objects);
		cutsceneManager.updateCutScenes(camera);
		if (!cutsceneManager.isCutsceneRunnging()) {
			p.control(input);
		}
		combat.updateCombat(objects, p);
		
		if (p.exitWorld()) {
			p.setExitWorld(false);
			sbg.enterState(3);
		}

		// Updates timing for all animations
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i) instanceof PhysicalEntity) {
				Animator anim = ((PhysicalEntity) objects.get(i)).getGraphics().getAnimator();
				if (anim != null) {
					anim.updateAnimations();
				}
			}
		}

		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i) instanceof AIEntity) { // control ai
				((AIEntity) objects.get(i)).control(p);
			} else if (objects.get(i) instanceof Event) { // fire events
				if (((Event) objects.get(i)).meetsCondition()) {
					((Event) objects.get(i)).fireEvent();
				}
			}
		}

//		try {
//			Thread.sleep(4);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public int getID() {
		return BasicGame.id;
	}
}
