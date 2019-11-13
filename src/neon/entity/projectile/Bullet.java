package neon.entity.projectile;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.graphics.EntityGraphics;
import neon.physics.Collision;
import neon.physics.Physics;

public class Bullet extends ProjectileEntity {
	
	public Bullet(float x, float y, float angle, String sourceEntity) {
		this.x = x;
		this.y = y;
		this.damage = 4;
		this.angle = angle;
		this.speed = 2f;
		this.weight = 0;
		this.sourceEntity = sourceEntity;
		this.collision = new Collision(new Rectangle(0, 0, 10, 10), 1, 10, true);
		float velX = (float) (Math.cos(angle) * speed);
		float velY = (float) (Math.sin(angle) * speed);
		this.physics = new Physics(velX, velY);
		this.graphics = new EntityGraphics(10);
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

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.white);
		g.fillOval(x + offsetX, y + offsetY, 10, 10);
	}

	@Override
	public int getID() {
		return -1;
	}
}
