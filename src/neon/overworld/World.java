package neon.overworld;

import java.util.ArrayList;

import org.newdawn.slick.Image;

public class World {
	
	private ArrayList<String> levels;
	private String name;
	private Image image;
	private float x;
	private float y;
	
	public World(ArrayList<String> levels, Image image, String name, float x, float y) {
		this.levels = levels;
		this.image = image;
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public World() {
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
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
