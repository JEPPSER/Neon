package neon;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import neon.editor.Editor;
import neon.overworld.Overworld;
import neon.settings.InputSettings;

public class Neon extends StateBasedGame {

	public Neon(String name) {
		super(name);
	}
	
	public static void main(String[] args) {
		try {
			InputSettings.init();
			AppGameContainer agc = new AppGameContainer(new Neon("Neon"));
			//agc.setDisplayMode(1920, 1080, true);
			//agc.setDisplayMode(1280, 720, false);
			agc.setDisplayMode(1600, 900, false);
			//agc.setDisplayMode(1900, 900, false);
			//agc.setDisplayMode(800, 600, false);
			agc.setAlwaysRender(true);
			agc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new Overworld());
		addState(new BasicGame());
		addState(new Editor());
		enterState(3);
	}
}
