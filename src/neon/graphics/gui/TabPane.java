package neon.graphics.gui;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import neon.graphics.GraphicsUtil;

public class TabPane extends GUIElement {
	
	private HashMap<String, GUIElement> tabs;
	private HBox tabBox;
	private VBox root;
	
	public TabPane(float width, float height) {
		this.width = width;
		this.height = height;
		tabs = new HashMap<String, GUIElement>();
		tabBox = new HBox();
		root = new VBox();
		root.getChildren().add(tabBox);
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.black);
		g.fillRect(x + offsetX, y + offsetY, width, height);
		g.setColor(GraphicsUtil.darkGray);
		g.fillRect(x + offsetX, y + offsetY, width, tabBox.getHeight());
		root.render(g, offsetX, offsetY);
	}
	
	@Override
	public void update(Input input, float offsetX, float offsetY, float scale) {
		root.update(input, offsetX, offsetY, scale);
		for (GUIElement e : tabBox.children) {
			if (e.wasClicked) {
				for (GUIElement f : tabBox.children) {
					((TabButton) f).setActivated(false);
				}
				root.getChildren().set(1, tabs.get(((TabButton) e).getText()));
				((TabButton) e).setActivated(true);
			}
		}
		super.update(input, offsetX, offsetY, scale);
	}
	
	public void addTab(GUIElement e, String title) {
		tabs.put(title, e);
		TabButton tb = new TabButton(title, 20, 150, 50);
		tabBox.children.add(tb);
		if (tabBox.children.size() == 1) {
			root.getChildren().add(e);
			tb.setActivated(true);
		}
	}
}
