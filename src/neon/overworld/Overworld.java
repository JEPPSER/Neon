package neon.overworld;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import neon.io.OverworldLoader;
import neon.time.TimeInfo;

public class Overworld extends BasicGameState {
	
	public static int id = 3;
	
	private OverworldFile owf;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		owf = OverworldLoader.readFile("res/overworlds/overworld.now");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(owf.getBackground(), 0, 0);
		for (int i = 0; i < owf.getWorlds().size(); i++) {
			World w = owf.getWorlds().get(i);
			g.drawImage(w.getImage().getScaledCopy(100, 100), w.getX(), w.getY());
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		TimeInfo.setDelta(delta);
	}

	@Override
	public int getID() {
		return Overworld.id;
	}
}
