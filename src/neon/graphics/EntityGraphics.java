package neon.graphics;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class EntityGraphics {
	
	private ArrayList<Point> points;
	private Color color;
	private float lineWidth;
	
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}
	
	public void render(Graphics g, float x, float y) {
		g.setLineWidth(this.lineWidth);
		g.setColor(this.color);
		for (int i = 0; i < this.points.size() - 1; i++) {
			Point p1 = this.points.get(i);
			Point p2 = this.points.get(i + 1);
			g.drawLine(p1.getX() + x, p1.getY() + y, p2.getX() + x, p2.getY() + y);
		}
	}
}
