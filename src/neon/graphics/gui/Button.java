package neon.graphics.gui;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

public class Button extends GUIElement {
	
	private String text;
	private float textX;
	private float textY;
	private TrueTypeFont font;
	
	public Button(String text, int fontSize, float width, float height) {
		this.width = width;
		this.height = height;
		this.text = text;
		this.color = Color.gray;
		font = new TrueTypeFont(new Font("Helvetica", Font.BOLD, fontSize), true);
		textX = width / 2f - font.getWidth(text) / 2f;
		textY = height / 2f - font.getHeight(text) / 2f;
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(color);
		g.fillRect(x + offsetX, y + offsetY, width, height);
		g.setColor(Color.black);
		g.setFont(font);
		g.drawString(text, x + offsetX + textX, y + offsetY + textY);
	}
	
	@Override
	public void update(Input input, float offsetX, float offsetY) {
		if (isMouseOver) {
			color = Color.white;
		} else {
			color = Color.gray;
		}
		super.update(input, offsetX, offsetY);
	}
}
