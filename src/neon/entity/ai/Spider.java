package neon.entity.ai;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.controller.ai.SpiderController;
import neon.entity.PhysicalEntity;
import neon.entity.controllable.Player;
import neon.graphics.EntityGraphics;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class Spider extends AIEntity {
	
	private EntityGraphics graphics;
	private String name;
	private float x;
	private float y;
	private boolean mirrored = false;
	
	public Spider(float x, float y) {
		name = "Spider";
		initGraphics();
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 50, 50), 1.0f, 10f, true);
		this.x = x;
		this.y = y;
		this.ai = new SpiderController(this);
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		if (other instanceof Player && collisionDirection != CollisionDirection.NONE) {
			((SpiderController) ai).hurtPlayer();
		} else {
			super.handleCollision(other);
		}
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
	public float getWidth() {
		return collision.getHitbox().getWidth();
	}

	@Override
	public void setWidth(float width) {
	}

	@Override
	public float getHeight() {
		return collision.getHitbox().getHeight();
	}

	@Override
	public void setHeight(float height) {
	}

	@Override
	public void setSize(float width, float height) {
	}

	@Override
	public EntityGraphics getGraphics() {
		return this.graphics;
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.red);
		// Draw hitbox
		g.drawRect(x + collision.getHitbox().getX() + offsetX, y + collision.getHitbox().getY() + offsetY, 50, 50);
	}
	
	private void initGraphics() {
		
	}

	@Override
	public int getID() {
		return 0;
	}
}
