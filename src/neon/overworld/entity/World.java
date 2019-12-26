package neon.overworld.entity;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import neon.entity.area.InputPromptTrigger;

public class World extends InputPromptTrigger {
	
	private ArrayList<String> levels;
	private String name;
	private Image image;
	private float x;
	private float y;
	
	public World(ArrayList<String> levels, Image image, String name, float x, float y, float width, float height) {
		this.levels = levels;
		this.image = image.getScaledCopy((int) width, (int) height);
		this.name = name;
		this.x = x;
		this.y = y;
		this.text = name;
		this.setSize(width, height);
	}
	
	public World(float width, float height) {
		this.setSize(width, height);
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.drawImage(this.image, x + offsetX, y + offsetY);
		if (isTriggered) {
			g.setColor(Color.white);
			g.drawString(this.text, offsetX + x, offsetY + y - 50);
		}
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image.getScaledCopy((int) width, (int) height);
	}

	public ArrayList<String> getLevels() {
		return levels;
	}

	public void setLevels(ArrayList<String> levels) {
		this.levels = levels;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
