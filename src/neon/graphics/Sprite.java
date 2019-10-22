package neon.graphics;

import java.util.ArrayList;

public class Sprite {
	
	private ArrayList<Point> points;
	private String name;
	private float width;
	private float height;
	
	public Sprite(ArrayList<Point> points, float width, float height, String name) {
		this.points = points;
		this.name = name;
		this.width = width;
		this.height = height;
	}
	
	public ArrayList<Point> getPoints() {
		return this.points;
	}
	
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
}
