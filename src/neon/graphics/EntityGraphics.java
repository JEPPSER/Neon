package neon.graphics;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import neon.graphics.animation.Animator;

public class EntityGraphics {
	
	private Animator animator;
	private Sprite currentSprite;
	private Color color;
	private float lineWidth;
	
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
	
	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}
	
	public void render(Graphics g, float x, float y, float angle, boolean mirrored) {
		g.setLineWidth(this.lineWidth);
		g.setColor(this.color);
		if (animator != null) {
			currentSprite = animator.getCurrentSprite();
		}
		ArrayList<Point> points = currentSprite.getPoints();
		if (mirrored) {
			points = mirrorPoints(points);
		}
		for (int i = 0; i < points.size() - 1; i++) {
			Point p1 = points.get(i);
			Point p2 = points.get(i + 1);
			g.drawLine(p1.getX() + x, p1.getY() + y, p2.getX() + x, p2.getY() + y);
		}
	}
	
	public ArrayList<Point> mirrorPoints(ArrayList<Point> points) {
		ArrayList<Point> result = new ArrayList<Point>();
		float width = animator.getCurrentSprite().getWidth();
		for (int i = 0; i < points.size(); i++) {
			Point p = new Point(width - points.get(i).getX(), points.get(i).getY());
			result.add(p);
		}
		return result;
	}
}
