package neon.entity.ai.enemy;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.combat.Combat;
import neon.controller.ai.GunmanController;
import neon.entity.PhysicalEntity;
import neon.entity.terrain.TerrainEntity;
import neon.graphics.EntityGraphics;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class Gunman extends Enemy {
	
	public Gunman(float x, float y) {
		name = "Gunman";
		health = 15f;
		maxHealth = 15f;
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 50, 100), 1.0f, 10f, true);
		this.width = collision.getHitbox().getWidth();
		this.height = collision.getHitbox().getHeight();
		this.x = x;
		this.y = y;
		initGraphics();
		this.ai = new GunmanController(this);
		this.combat = new Combat();
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		CollisionDirection cd = this.collisionDirection;
		PhysicalEntity pe = this.collidingEntity;
		if (pe == other && other instanceof TerrainEntity) {
			if (cd == CollisionDirection.DOWN) {
				this.setY(pe.getY() - this.getCollision().getHitbox().getHeight());
				physics.setYVelocity(0f);
			} else if (cd == CollisionDirection.UP) {
				this.setY(pe.getY() + pe.getCollision().getHitbox().getHeight());
				physics.setYVelocity(0f);
			} else if (cd == CollisionDirection.RIGHT) {
				this.setX(pe.getX() - this.getCollision().getHitbox().getWidth());
				physics.setXVelocity(0f);
			} else if (cd == CollisionDirection.LEFT) {
				this.setX(pe.getX() + pe.getCollision().getHitbox().getWidth());
				physics.setXVelocity(0f);
			}
		}
	}
	
	private void initGraphics() {
		// Moving animation
		/*Sprite one = SpriteLoader.getSprite("skeleton_1");
		Sprite two = SpriteLoader.getSprite("skeleton_2");
		Animation moving = new Animation(200, true);
		moving.getSprites().add(one);
		moving.getSprites().add(two);
		
		Animator anim = new Animator();
		anim.addAnimation(moving, "moving");
		anim.setState("moving");*/
		
		this.graphics = new EntityGraphics(this.getWidth());
		//this.graphics.setAnimator(anim);
		this.color = Color.white;
	}

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
		
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		if (((GunmanController) ai).isInvulnerable()) {
			g.setColor(Color.red);
		} else {
			g.setColor(this.color);
		}
		g.drawRect(x + offsetX, y + offsetY, width, height);
		
		GunmanController gc = (GunmanController) ai;
		if (gc.isAiming()) {
			float x1 = (float) (Math.cos(gc.getAimAngle()) * 100);
			float y1 = (float) (Math.sin(gc.getAimAngle()) * 100);
			g.setColor(Color.red);
			g.drawLine(x + offsetX, y + offsetY, x + offsetX + x1, y + offsetY + y1);
		}
		
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
		return 11;
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {
		ai.takeDamage(damage, cd);
	}
}
