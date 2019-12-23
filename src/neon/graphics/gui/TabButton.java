package neon.graphics.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import neon.graphics.GraphicsUtil;

public class TabButton extends Button {
	
	private boolean isActivated = false;

	public TabButton(String text, int fontSize, float width, float height) {
		super(text, fontSize, width, height);
		color = GraphicsUtil.darkGray;
	}
	
	@Override
	public void update(Input input, float offsetX, float offsetY, float scale) {
		super.update(input, offsetX, offsetY, scale);
		if (isMouseOver || isActivated) {
			color = Color.black;
		} else if (!isActivated) {
			color = GraphicsUtil.darkGray;
		}
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(color);
		g.fillRect(x + offsetX, y + offsetY, width, height);
		g.setColor(Color.white);
		g.setFont(super.getFont());
		g.drawString(super.getText(), x + offsetX + super.getTextX(), y + offsetY + super.getTextY());
	}
	
	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}
	
	public boolean isActivated() {
		return isActivated;
	}
}
