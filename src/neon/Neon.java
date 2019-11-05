package neon;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Neon extends StateBasedGame{

	public Neon(String name) {
		super(name);
	}
	
	public static void main(String[] args) {
		try {
			AppGameContainer agc = new AppGameContainer(new Neon("Neon"));
			//agc.setDisplayMode(1920, 1080, true);
			//agc.setDisplayMode(1280, 720, false);
			agc.setDisplayMode(1600, 900, false);
			//agc.setDisplayMode(720, 720, false);
			//agc.setDisplayMode(800, 600, false);
			agc.setAlwaysRender(true);
			agc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new BasicGame());
	}
}
