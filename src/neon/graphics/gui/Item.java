package neon.graphics.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

public class Item extends GUIElement {

	private String text;
	private float textX;
	private float textY;
	private TrueTypeFont font;
	
	public Item(String text, int fontSize, float width, float height) {
		this.width = width;
		this.height = height;
		this.text = text;
		this.color = Color.white;
		font = GUI.getFont();
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
	public void update(Input input, float offsetX, float offsetY, float scale) {
		if (isMouseOver) {
			color = Color.gray;
		} else {
			color = Color.white;
		}
		super.update(input, offsetX, offsetY, scale);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getTextX() {
		return textX;
	}

	public void setTextX(float textX) {
		this.textX = textX;
	}

	public float getTextY() {
		return textY;
	}

	public void setTextY(float textY) {
		this.textY = textY;
	}
}
