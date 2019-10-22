package neon.graphics;

import java.util.ArrayList;

public class Sprite {
	
	private ArrayList<Point> points;
	private String name;
	
	public Sprite(ArrayList<Point> points, String name) {
		this.points = points;
		this.name = name;
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
}
