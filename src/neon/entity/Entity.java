package neon.entity;

import org.newdawn.slick.Graphics;

import neon.graphics.EntityGraphics;

public interface Entity {
	public String getName();
	public void setName(String name);
	public float getX();
	public void setX(float x);
	public float getY();
	public void setY(float y);
	public EntityGraphics getGraphics();
	public void render(Graphics g, float offsetX, float offsetY);
}
