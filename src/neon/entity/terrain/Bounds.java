package neon.entity.terrain;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.graphics.EntityGraphics;
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
		//this.graphics.render(g, x + offsetX, y + offsetY, 0, false);
		g.setColor(Color.green);
		g.setLineWidth(2.0f);
		if (cd == CollisionDirection.DOWN) {
			g.drawLine(x + offsetX, y + height + offsetY, x + width + offsetX, y + height + offsetY);
		} else if (cd == CollisionDirection.UP) {
			g.drawLine(x + offsetX, y + offsetY, x + width + offsetX, y + offsetY);
		} else if (cd == CollisionDirection.LEFT) {
			g.drawLine(x + offsetX, y + offsetY, x + offsetX, y + height + offsetY);
		} else if (cd == CollisionDirection.RIGHT) {
			g.drawLine(x + width + offsetX, y + offsetY, x + width + offsetX, y + height + offsetY);
		}
	}

	private void initGraphics() {
		this.graphics = new EntityGraphics(this.width);
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
