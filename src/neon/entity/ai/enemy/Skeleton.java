package neon.entity.ai.enemy;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.combat.Combat;
import neon.controller.ai.SkeletonController;
import neon.controller.ai.SpiderController;
import neon.entity.PhysicalEntity;
import neon.entity.collectable.CollectableEntity;
import neon.entity.controllable.Player;
import neon.graphics.EntityGraphics;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class Skeleton extends Enemy {
	
	public Skeleton(float x, float y) {
		name = "Skeleton";
		health = 7f;
		maxHealth = 7f;
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 50, 100), 1.0f, 10f, true);
		this.x = x;
		this.y = y;
		this.ai = new SkeletonController(this);
		this.combat = new Combat();
		initGraphics();
	}
	
	private void initGraphics() {
		this.graphics = new EntityGraphics(this.getWidth());
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		if (other instanceof Player && collisionDirection != CollisionDirection.NONE && !isDead) {
			((SpiderController) ai).hurtPlayer((Player) other);
		} else if (other instanceof CollectableEntity) {
			
		} else if (!(other instanceof Enemy) && !(other instanceof Player)){
			super.handleCollision(other);
		}
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
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.white);
		g.drawRect(offsetX + x, offsetY + y, getWidth(), getHeight());
	}

	@Override
	public int getID() {
		return 9;
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {
		ai.takeDamage(damage, cd);
	}
}
