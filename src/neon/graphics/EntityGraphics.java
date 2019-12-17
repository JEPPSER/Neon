package neon.graphics;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import neon.graphics.animation.Animator;

public class EntityGraphics {
	
	private Animator animator;
	private Sprite currentSprite;
	private Color color;
	private float offsetX = 0f;
	private float offsetY = 0f;
	private float entityWidth;
	
	public EntityGraphics(float width) {
		this.entityWidth = width;
	}
	
	public Animator getAnimator() {
		return this.animator;
	}
	
	public void setAnimator(Animator animator) {
		this.animator = animator;
	}
	
	public void setSprite(Sprite sprite) {
		this.currentSprite = sprite;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setOffset(float x, float y) {
		this.offsetX = x;
		this.offsetY = y;
	}
	
	public void render(Graphics g, float x, float y, float angle, boolean mirrored) {
		currentSprite = animator.getCurrentSprite();
		Image image = currentSprite.getImage();
		float offset = currentSprite.getOffsetX() + offsetX;
		
		if (mirrored) {
			image = currentSprite.getImage().getFlippedCopy(true, false);
			offset = -1 * (currentSprite.getWidth() - (entityWidth - offset));
		}
		
		g.drawImage(image, x + offset, y + offsetY + currentSprite.getOffsetY(), this.color);
	}
	
	public ArrayList<Point> mirrorPoints(ArrayList<Point> points) {
		ArrayList<Point> result = new ArrayList<Point>();
		float width = animator.getCurrentSprite().getWidth();
		for (int i = 0; i < points.size(); i++) {
			Point p = new Point(width - points.get(i).getX() + offsetX, points.get(i).getY());
			result.add(p);
		}
		return result;
	}
}
