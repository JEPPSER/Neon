package neon.entity.area;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.PhysicalEntity;
import neon.graphics.EntityGraphics;
import neon.graphics.Point;
import neon.graphics.Sprite;
import neon.physics.Collision;
import neon.physics.Physics;

public abstract class Trigger extends PhysicalEntity {
	
	public Trigger() {
		this.name = "Trigger";
		this.physics = new Physics(0f, 0f);
	}
	
	public abstract void triggered();
	
	public abstract void setTrigger(String text, float textX, float textY);
	
	public abstract void unTriggered();

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public void setWidth(float width) {
		this.width = width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void setHeight(float height) {
		this.height = height;
	}

	@Override
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
		this.collision = new Collision(new Rectangle(0, 0, this.width, this.height), 1.0f, 10f, false);
		this.collision.getHitbox().setHeight(height);
		this.collision.getHitbox().setWidth(width);
		initGraphics();
	}

	@Override
	public EntityGraphics getGraphics() {
		return this.graphics;
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		graphics.render(g, x + offsetX, y + offsetY, 0, false);
	}

	@Override
	public int getID() {
		return 1;
	}

	private void initGraphics() {
		this.graphics = new EntityGraphics(this.width);
		this.graphics.setColor(Color.red);
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
}
