package neon.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import neon.graphics.gui.Button;
import neon.graphics.gui.VBox;

public class StartMenu extends BasicGameState {
	
	private static int id = 4;
	
	private VBox root;
	private Button newBtn;
	private Button loadBtn;
	private Button settingsBtn;

	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		root = new VBox();
		root.setPaddingX(50);
		root.setPaddingY(50);
		root.setSpacing(50);
		
		newBtn = new Button("New", 40, 200, 100);
		loadBtn = new Button("Load", 40, 200, 100);
		settingsBtn = new Button("Settings", 40, 200, 100);
		
		root.getChildren().add(newBtn);
		root.getChildren().add(loadBtn);
		root.getChildren().add(settingsBtn);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) throws SlickException {
		root.render(g, gc.getWidth() / 2f - root.getWidth() / 2f, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		root.update(gc.getInput(), gc.getWidth() / 2f - root.getWidth() / 2f, 100);
		
		if (newBtn.wasClicked()) {
			sbg.enterState(3);
		}
	}

	@Override
	public int getID() {
		return id;
	}
}
