package neon.graphics.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class HBox extends GUIElement {
	
	public HBox() {
		super();
		width = 200;
		height = 50;
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(color);
		g.drawRect(offsetX + x, offsetY + y, width, height);
		
		for (GUIElement e : children) {
			e.render(g, offsetX, offsetY);
		}
	}

	@Override
	public void update(Input input, float offsetX, float offsetY) {
		if (!children.isEmpty()) {
			GUIElement elem = children.get(0);
			width = elem.width;
			height = elem.height;
			elem.x = x;
			elem.y = y;
			
			for (int i = 1; i < children.size(); i++) {
				GUIElement prev = children.get(i - 1);
				GUIElement curr = children.get(i);
				curr.setX(prev.x + prev.width);
				curr.setY(y);
				width += curr.width;
				if (curr.height > height) {
					height = curr.height;
				}
			}
			
			for (GUIElement e : children) {
				e.update(input, offsetX, offsetY);
			}
		}
		super.update(input, offsetX, offsetY);
	}
}
