package neon.entity.terrain;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.graphics.EntityGraphics;
import neon.graphics.Point;
import neon.graphics.Sprite;
import neon.physics.Collision;
import neon.physics.Physics;

public class Rock extends TerrainEntity {

	private EntityGraphics graphics;
	private String name;
	private float x;
	private float y;
	private float width;
	private float height;
	
	public Rock(float x, float y, float width, float height) {
		this.name = "Rock";
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		initGraphics();
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, this.width, this.height), 1.0f, 10f, true);
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		this.graphics.render(g, x + offsetX, y + offsetY, 0, false);
	}
	
	private void initGraphics() {
		this.graphics = new EntityGraphics(this.width);
		this.graphics.setColor(Color.yellow);
		this.graphics.setLineWidth(2.0f);
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(0, 0));
		points.add(new Point(this.width, 0));
		points.add(new Point(this.width, this.height));
		points.add(new Point(0, this.height));
		points.add(new Point(0, 0));
		Sprite sprite = new Sprite(points, this.width, this.height, this.name);
		this.graphics.setSprite(sprite);
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public float getWidth() {
		return 0;
	}

	@Override
	public void setWidth(float width) {
	}

	@Override
	public float getHeight() {
		return 0;
	}

	@Override
	public void setHeight(float height) {
	}

	@Override
	public void setSize(float width, float height) {

	}
}
