package neon.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import neon.graphics.gui.Button;
import neon.graphics.gui.GUI;
import neon.graphics.gui.HBox;
import neon.graphics.gui.VBox;

public class StartMenu extends BasicGameState {
	
	private static int id = 4;
	
	private GUI gui;
	private VBox root;
	private HBox one;
	private HBox two;
	private HBox three ;
	private HBox four;
	private HBox five;
	private Button button;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		gui = new GUI(200, 100, 500, 500);
		
		root = new VBox();
		root.setPaddingX(50);
		root.setPaddingY(50);
		root.setSpacing(20);
		root.setColor(Color.pink);
		
		button = new Button("Close", 100, 50);
		one = new HBox();
		two = new HBox();
		three = new HBox();
		four = new HBox();
		five = new HBox();
		four.setColor(Color.magenta);
		five.setColor(Color.yellow);
		
		one.setSpacing(10);
		one.setPaddingX(10);
		one.setPaddingY(10);
		two.setWidth(50);
		two.setHeight(60);
		one.getChildren().add(three);
		one.getChildren().add(two);
		
		four.setWidth(300);
		four.setHeight(20);
		five.getChildren().add(four);
		five.getChildren().add(button);
		five.setSpacing(10);
		
		root.getChildren().add(one);
		root.getChildren().add(five);
		
		gui.getElements().add(root);
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
