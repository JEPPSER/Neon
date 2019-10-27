package neon.entity.terrain;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.graphics.EntityGraphics;
import neon.graphics.Point;
import neon.graphics.Sprite;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class Bounds extends TerrainEntity {
	
	private CollisionDirection cd;

	public Bounds(CollisionDirection cd) {
		this.cd = cd;
		this.name = "Bounds";
		this.physics = new Physics(0f, 0f);
	}

	public float getWidth() {
		return width;
	}

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
	public void render(Graphics g, float offsetX, float offsetY) {
		this.graphics.render(g, x + offsetX, y + offsetY, 0, false);
	}

	private void initGraphics() {
		this.graphics = new EntityGraphics(this.width);
		this.graphics.setColor(Color.green);
		this.graphics.setLineWidth(2.0f);
		ArrayList<Point> points = new ArrayList<Point>();
		if (cd == CollisionDirection.DOWN) {
			points.add(new Point(0, this.height));
			points.add(new Point(this.width, this.height));
		} else if (cd == CollisionDirection.UP) {
			points.add(new Point(0, 0));
			points.add(new Point(this.width, 0));
		} else if (cd == CollisionDirection.LEFT) {
			points.add(new Point(0, 0));
			points.add(new Point(0, this.height));
		} else if (cd == CollisionDirection.RIGHT) {
			points.add(new Point(this.width, 0));
			points.add(new Point(this.width, this.height));
		}
		Sprite sprite = new Sprite(points, this.width, this.height, this.name);
		this.graphics.setSprite(sprite);
	}

	@Override
	public int getID() {
		return 0;
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
}
