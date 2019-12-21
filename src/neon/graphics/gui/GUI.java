package neon.graphics.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class GUI {
	
	private float x;
	private float y;
	private float width;
	private float height;
	
	private ArrayList<GUIElement> elements;
	
	public GUI(float x, float y, float width, float height) {
		elements = new ArrayList<GUIElement>();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.drawRect(x, y, width, height);
		for (GUIElement e : elements) {
			e.render(g, x, y);
		}
	}
	
	public void update(Input input) {
		for (GUIElement e : elements) {
			e.update(input, x, y);
		}
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

	public ArrayList<GUIElement> getElements() {
		return elements;
	}
}
