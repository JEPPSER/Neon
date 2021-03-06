package neon.overworld;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import neon.camera.Camera;
import neon.controller.PlayerOverworldController;
import neon.entity.controllable.Player;
import neon.graphics.gui.GUI;
import neon.io.OverworldLoader;
import neon.level.LevelManager;
import neon.overworld.entity.World;
import neon.time.TimeInfo;

public class Overworld extends BasicGameState {
	
	public static int id = 3;
	
	private OverworldModel owm;
	private Player player;
	private Camera camera;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		owm = OverworldLoader.readFile("res/overworlds/overworld.now");
		player = new Player(100, 100, null);
		player.setController(new PlayerOverworldController(player));
		
		float defaultZoom = (float) gc.getHeight() / 900f;
		defaultZoom *= (float) Display.getHeight() / (float) gc.getHeight();
		camera = new Camera(player, gc);
		camera.zoom(defaultZoom);
		camera.pan(0, gc.getHeight() - Display.getHeight());
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		camera.renderOverworld(owm, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		TimeInfo.setDelta(delta);
		player.control(gc.getInput());
		player.getGraphics().getAnimator().updateAnimations();

		World colliding = null;
		for (int i = 0; i < owm.getWorlds().size(); i++) {
			World w = owm.getWorlds().get(i);
			player.checkCollision(w);
			player.handleCollision(w);
			if (player.getCollidingEntity() != null) {
				colliding = (World) player.getCollidingEntity();
			}
		}
		player.setCollidingEntity(colliding);
		
		if (player.enterWorld()) {
			player.setEnterWorld(false);
			sbg.enterState(1);
		}
	}

	@Override
	public int getID() {
		return Overworld.id;
	}
}
