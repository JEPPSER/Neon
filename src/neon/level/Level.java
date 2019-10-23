package neon.level;

import neon.entity.Entity;
import neon.graphics.Point;

import java.util.ArrayList;

import org.newdawn.slick.Color;

public class Level {
	
	private Point spawnPoint;
	private Color background;
	private float width;
	private float height;
	private String name;
	private ArrayList<Entity> objects;

	public Point getSpawnPoint() {
		return spawnPoint;
	}

	public void setSpawnPoint(Point spawnPoint) {
		this.spawnPoint = spawnPoint;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Entity> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<Entity> objects) {
		this.objects = objects;
	}
}
