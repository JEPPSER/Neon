package neon;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import neon.entity.controllable.Player;
import neon.entity.terrain.Ground;
import neon.entity.terrain.Rock;
import neon.physics.PhysicsEngine;
import neon.entity.Entity;

public class BasicGame extends BasicGameState {
	
	public static int id = 1;
	
	private Player p;
	private ArrayList<Entity> playField;
	private PhysicsEngine physics;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		physics = new PhysicsEngine();
		playField = new ArrayList<Entity>();
		p = new Player(300, 100);
		Ground g1 = new Ground(100f, 450f, 400f, 100f);
		Ground g2 = new Ground(300f, 260f, 100f, 300f);
		Rock r1 = new Rock(370f, 100f, 50f, 50f);
		playField.add(p);
		playField.add(g1);
		playField.add(g2);
		playField.add(r1);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for (int i = 0; i < playField.size(); i++) {
			playField.get(i).render(g);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		p.control(input);
		physics.applyPhysics(playField, delta);
	}

	@Override
	public int getID() {
		return BasicGame.id;
	}
}
