package neon.graphics.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import neon.io.SpriteLoader;

public class ColorPicker extends GUIElement {
	
	private ArrayList<Color> colors;
	
	private Color selectedColor;
	private Color hoverColor;
	
	private Image rainbow;
	private boolean isRainbow = false;
	private boolean hover;
	
	private final float COLOR_BOX_SIZE = 50;
	private final float COLOR_BOX_SPACING = 20;
	
	public  ColorPicker() {
		colors = new ArrayList<Color>();
		colors.add(new Color(1f, 1f, 1f));
		colors.add(new Color(1f, 0, 0));
		colors.add(new Color(0, 1f, 0f));
		colors.add(new Color(0, 0, 1f));
		colors.add(new Color(1f, 1f, 0));
		colors.add(new Color(1f, 0f, 1f));
		selectedColor = colors.get(0);
		rainbow = SpriteLoader.getSprite("rainbow").getImage();
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		for (int i = 0; i < colors.size(); i++) {
			Color c = colors.get(i);
			g.setColor(c);
			g.fillRect(offsetX + x + i * (COLOR_BOX_SIZE + COLOR_BOX_SPACING), offsetY + y, COLOR_BOX_SIZE, COLOR_BOX_SIZE);
		}
		
		g.drawImage(rainbow, offsetX + x + colors.size() * (COLOR_BOX_SIZE + COLOR_BOX_SPACING), offsetY + y);
		
		g.setColor(Color.white);
		g.setLineWidth(2f);
		int index = colors.indexOf(selectedColor);
		if (isRainbow) {
			index = colors.size();
		}
		g.drawRect(offsetX + x + index * (COLOR_BOX_SIZE + COLOR_BOX_SPACING) - 5, offsetY + y - 5, COLOR_BOX_SIZE + 10, COLOR_BOX_SIZE + 10);
		
		if (hoverColor != null) {
			g.setColor(Color.blue);
			index = colors.indexOf(hoverColor);
			g.drawRect(offsetX + x + index * (COLOR_BOX_SIZE + COLOR_BOX_SPACING) - 5, offsetY + y - 5, COLOR_BOX_SIZE + 10, COLOR_BOX_SIZE + 10);
		} else if (hover) {
			g.setColor(Color.blue);
			index = colors.size();
			g.drawRect(offsetX + x + index * (COLOR_BOX_SIZE + COLOR_BOX_SPACING) - 5, offsetY + y - 5, COLOR_BOX_SIZE + 10, COLOR_BOX_SIZE + 10);
		}
	}

	@Override
	public void update(Input input, float offsetX, float offsetY, float scale) {
		hover = false;
		for (int i = 0; i < colors.size(); i++) {
			float colorX = offsetX + x + i * (COLOR_BOX_SIZE + COLOR_BOX_SPACING);
			float colorY = offsetY + y;
			Rectangle r = new Rectangle(scale * (colorX), scale * (colorY), scale * COLOR_BOX_SIZE, scale * COLOR_BOX_SIZE);
			if (r.contains(input.getMouseX(), input.getMouseY())) {
				hoverColor = colors.get(i);
				hover = true;
				if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
					selectedColor = colors.get(i);
					isRainbow = false;
				}
			}
		}
		
		float colorX = offsetX + x + colors.size() * (COLOR_BOX_SIZE + COLOR_BOX_SPACING);
		float colorY = offsetY + y;
		Rectangle r = new Rectangle(scale * (colorX), scale * (colorY), scale * COLOR_BOX_SIZE, scale * COLOR_BOX_SIZE);
		if (r.contains(input.getMouseX(), input.getMouseY())) {
			hoverColor = null;
			hover = true;
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				selectedColor = null;
				isRainbow = true;
			}
		}
		
		if (!hover) {
			hoverColor = null;
		}
	}
}
