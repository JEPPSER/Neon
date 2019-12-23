package neon.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import neon.graphics.GraphicsUtil;
import neon.graphics.gui.Button;
import neon.graphics.gui.Checkbox;
import neon.graphics.gui.GUIElement;
import neon.graphics.gui.TabPane;
import neon.graphics.gui.VBox;

public class SettingsMenu extends GUIElement {
	
	private TabPane root;
	private Checkbox fsCheckbox;
	
	public SettingsMenu() {
		root = new TabPane(1000, 1000);
		
		VBox grTab = new VBox();
		grTab.setPaddingX(10);
		grTab.setPaddingY(10);
		grTab.setSpacing(10);
		fsCheckbox = new Checkbox("Fullscreen", true);
		grTab.getChildren().add(new Button("test", 14, 100, 50));
		grTab.getChildren().add(fsCheckbox);
		
		VBox iTab = new VBox();
		VBox gaTab = new VBox();
		root.addTab(grTab, "Graphics");
		root.addTab(iTab, "Input");
		root.addTab(gaTab, "Gameplay");
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		root.render(g, offsetX, offsetY);
		g.setLineWidth(2f);
		g.setColor(GraphicsUtil.darkGray);
		g.drawRect(root.getX() + offsetX, root.getY() + offsetY, root.getWidth(), root.getHeight());
	}
	
	@Override
	public void update(Input input, float offsetX, float offsetY, float scale) {
		root.update(input, offsetX, offsetY, scale);
		super.update(input, offsetX, offsetY, scale);
	}
	
	@Override
	public float getWidth() {
		return root.getWidth();
	}
	
	@Override
	public float getHeight() {
		return root.getHeight();
	}
}
