package neon.graphics.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class VBox extends GUIElement {
	
	public VBox() {
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
		for (GUIElement e : children) {
			e.update(input, offsetX, offsetY);
		}
		
		if (!children.isEmpty()) {
			GUIElement elem = children.get(0);
			width = elem.width + paddingX * 2;
			height = elem.height + paddingY * 2;
			elem.x = x + paddingX;
			elem.y = y + paddingY;
			
			for (int i = 1; i < children.size(); i++) {
				GUIElement prev = children.get(i - 1);
				GUIElement curr = children.get(i);
				curr.setX(x + paddingX);
				curr.setY(prev.y + prev.height + spacing);
				height += curr.height + spacing;
				if (curr.width + 2 * paddingX > width) {
					width = curr.width + paddingX * 2;
				}
			}
		}
		super.update(input, offsetX, offsetY);
	}
}
