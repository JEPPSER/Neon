package neon.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import neon.graphics.gui.GUI;
import neon.graphics.gui.HBox;

public class StartMenu extends BasicGameState {
	
	private static int id = 4;
	
	private GUI gui;
	private HBox one;
	private HBox two;
	private HBox three ;
	private HBox four;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		gui = new GUI(200, 100, 500, 500);
		one = new HBox();
		two = new HBox();
		three = new HBox();
		four = new HBox();
		
		two.setWidth(50);
		two.setHeight(60);
		four.setWidth(300);
		four.setHeight(20);
		one.getChildren().add(three);
		one.getChildren().add(two);
		one.getChildren().add(four);
		
		gui.getElements().add(one);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		gui.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int arg2) throws SlickException {
		gui.update(gc.getInput());
		
		if (two.isMouseOver()) {
			two.setColor(Color.blue);
		} else {
			two.setColor(Color.white);
		}
		
		if (three.isMouseOver()) {
			three.setColor(Color.blue);
		} else {
			three.setColor(Color.white);
		}
		
		if (four.wasClicked()) {
			four.setColor(Color.magenta);
		} else if (!four.isMouseOver()) {
			four.setColor(Color.white);
		}
	}

	@Override
	public int getID() {
		return id;
	}
}
