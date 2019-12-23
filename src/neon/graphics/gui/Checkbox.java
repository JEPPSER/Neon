package neon.graphics.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Checkbox extends GUIElement {
	
	private boolean isChecked;
	private String text;
	
	public Checkbox(String text, boolean isChecked) {
		this.text = text;
		this.isChecked = isChecked;
		width = 30;
		height = 30;
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.white);
		g.fillRect(x + offsetX, y + offsetY, width, height);
		g.drawString(text, x + offsetX + 50, y + offsetY);
		if (isChecked) {
			g.setColor(Color.black);
			g.setLineWidth(4f);
			g.drawLine(x + offsetX + width / 4, y + offsetY + height / 2, x + offsetX + width / 2, y + offsetY + 3 * height / 4);
			g.drawLine(x + offsetX + width / 2, y + offsetY + height, x + offsetX + width, y + offsetY);
		}
	}
	
	@Override
	public void update(Input input, float offsetX, float offsetY, float scale) {
		
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public boolean isChecked() {
		return isChecked;
	}
}
