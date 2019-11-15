package neon.entity.ai.enemy;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.combat.Combat;
import neon.controller.ai.SkeletonController;
import neon.entity.PhysicalEntity;
import neon.entity.collectable.CollectableEntity;
import neon.entity.controllable.Player;
import neon.graphics.EntityGraphics;
import neon.graphics.Sprite;
import neon.graphics.animation.Animation;
import neon.graphics.animation.Animator;
import neon.io.SpriteLoader;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class Skeleton extends Enemy {
	
	public Skeleton(float x, float y) {
		name = "Skeleton";
		health = 6f;
		maxHealth = 6f;
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 50, 100), 1.0f, 10f, true);
		this.x = x;
		this.y = y;
		initGraphics();
		this.ai = new SkeletonController(this);
		this.combat = new Combat();
	}
	
	private void initGraphics() {
		// Moving animation
		Sprite one = SpriteLoader.getSprite("skeleton_1");
		Sprite two = SpriteLoader.getSprite("skeleton_2");
		Animation moving = new Animation(200, true);
		moving.getSprites().add(one);
		moving.getSprites().add(two);
		
		Animator anim = new Animator();
		anim.addAnimation(moving, "moving");
		anim.setState("moving");
		
		this.graphics = new EntityGraphics(this.getWidth());
		this.graphics.setAnimator(anim);
		this.color = Color.white;
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		if (other instanceof Player && collisionDirection != CollisionDirection.NONE && !isDead) {
			((SkeletonController) ai).hurtPlayer((Player) other);
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
		if (((SkeletonController) ai).isInvulnerable()) {
			graphics.setColor(Color.red);
		} else {
			graphics.setColor(this.color);
		}
		graphics.render(g, x + offsetX, y + offsetY, 0, mirrored);
		
		if (!isDead) {
			drawHealthBar(g, x + offsetX + (getWidth() - 100) / 2, y + offsetY - 40);
		}
	}
	
	private void drawHealthBar(Graphics g, float x, float y) {
		g.setColor(Color.white);
		g.drawRect(x, y, 100, 20);
		g.setColor(Color.red);
		g.fillRect(x + 2, y + 2, (health / maxHealth) * 100 - 4, 20 - 4);
	}

	@Override
	public int getID() {
		return 9;
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {
		ai.takeDamage(damage, cd);
	}
	
	@Override
	public String toString() {
		String str = this.getID() + "," + x + "," + y;
		return str;
	}
}
