package neon.graphics;

public class Point {
	
	private float x;
	private float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getX() {
		return this.x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getY() {
		return this.y;
	}
	
	public float distanceTo(Point p) {
		float distance = (float) Math.hypot(this.x - p.getX(), this.y - p.getY());
		return distance;
	}
	
	public float angleTo(Point p) {
		return (float) Math.atan2(p.y - y, p.x - x);
	}
}
