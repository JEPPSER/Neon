package neon.entity.terrain;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.graphics.EntityGraphics;
import neon.graphics.Point;
import neon.physics.Collision;
import neon.physics.Physics;

public class Ground extends TerrainEntity {
	
	private EntityGraphics graphics;
	private String name;
	private float x;
	private float y;
	private float width;
	private float height;
	
	public Ground(float x, float y, float width, float height) {
		this.name = "Ground";
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		initGraphics();
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, this.width, this.height), 1.0f, 10f, false);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return this.y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public EntityGraphics getGraphics() {
		return this.graphics;
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		this.graphics.render(g, x + offsetX, y + offsetY);
	}
	
	private void initGraphics() {
		this.graphics = new EntityGraphics();
		this.graphics.setColor(Color.green);
		this.graphics.setLineWidth(2.0f);
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(0, 0));
		points.add(new Point(this.width, 0));
		points.add(new Point(this.width, this.height));
		points.add(new Point(0, this.height));
		points.add(new Point(0, 0));
		this.graphics.setPoints(points);
	}
}
