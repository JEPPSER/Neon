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
			g.drawLine(x + offsetX + width / 6, y + offsetY + height / 2, x + offsetX + width / 3, y + offsetY + 3 * height / 4);
			g.drawLine(x + offsetX + width / 3, y + offsetY + 3 * height / 4, x + offsetX + 3 * width / 4, y + offsetY + height / 4);
		}
	}
	
	@Override
	public void update(Input input, float offsetX, float offsetY, float scale) {
		if (wasClicked) {
			if (isChecked) {
				isChecked = false;
			} else {
				isChecked = true;
			}
		}
		super.update(input, offsetX, offsetY, scale);
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
