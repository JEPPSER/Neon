package neon.entity.ai;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.controller.ai.SpiderController;
import neon.entity.PhysicalEntity;
import neon.entity.controllable.Player;
import neon.graphics.EntityGraphics;
import neon.graphics.Sprite;
import neon.graphics.animation.Animation;
import neon.graphics.animation.Animator;
import neon.io.SpriteLoader;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class Spider extends Enemy {

	public Spider(float x, float y) {
		name = "Spider";
		health = 2f;
		maxHealth = 2f;
		initGraphics();
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 50, 35), 1.0f, 10f, true);
		this.x = x;
		this.y = y;
		this.ai = new SpiderController(this);
	}

	@Override
	public void handleCollision(PhysicalEntity other) {
		if (other instanceof Player && collisionDirection != CollisionDirection.NONE) {
			((SpiderController) ai).hurtPlayer((Player) other);
		} else {
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
		//g.setColor(Color.red);
		//g.drawRect(x + collision.getHitbox().getX() + offsetX, y + collision.getHitbox().getY() + offsetY, this.getWidth(), this.getHeight());
		if (((SpiderController) ai).isInvulnerable()) {
			this.graphics.setColor(Color.red);
		} else {
			this.graphics.setColor(this.color);
		}
		this.graphics.render(g, x + offsetX, y + offsetY, 0, mirrored);
	}

	private void initGraphics() {
		// Idle animation
		Sprite one = SpriteLoader.getSprite("spider_1");
		Animation idle = new Animation(150, true);
		idle.getSprites().add(one);
		
		// Moving animation
		Sprite two = SpriteLoader.getSprite("spider_2");
		Animation moving = new Animation(150, true);
		moving.getSprites().add(one);
		moving.getSprites().add(two);

		Animator anim = new Animator();
		anim.addAnimation(moving, "moving");
		anim.addAnimation(idle, "idle");
		anim.setState("idle");

		this.graphics = new EntityGraphics();
		this.graphics.setAnimator(anim);
		this.color = Color.gray;
		this.graphics.setLineWidth(2.0f);
		this.graphics.setOffset(-10, 0);
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public void takeDamage(float damage) {
		((SpiderController) ai).takeDamage(damage);
		System.out.println(this.health);
	}
}
