package neon.entity.ai.enemy;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import neon.combat.Combat;
import neon.controller.ai.GunmanController;
import neon.entity.PhysicalEntity;
import neon.entity.terrain.TerrainEntity;
import neon.graphics.EntityGraphics;
import neon.graphics.Sprite;
import neon.graphics.animation.Animation;
import neon.graphics.animation.Animator;
import neon.io.SpriteLoader;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class Gunman extends Enemy {
	
	private Sprite arms;
	
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
		arms = SpriteLoader.getSprite("gunman_aim_arms");
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
		Sprite i = SpriteLoader.getSprite("gunman_idle");
		Animation idle = new Animation(100, true);
		idle.getSprites().add(i);
		
		Sprite m1 = SpriteLoader.getSprite("gunman_move_1");
		Sprite m2 = SpriteLoader.getSprite("gunman_move_2");
		Sprite m3 = SpriteLoader.getSprite("gunman_move_3");
		Animation moving = new Animation(100, true);
		moving.getSprites().add(m1);
		moving.getSprites().add(m2);
		moving.getSprites().add(m1);
		moving.getSprites().add(m3);
		
		Sprite a = SpriteLoader.getSprite("gunman_aim_body");
		Animation aiming = new Animation(100, true);
		aiming.getSprites().add(a);
		
		Sprite d1 = SpriteLoader.getSprite("gunman_death_1");
		Sprite d2 = SpriteLoader.getSprite("gunman_death_2");
		Sprite d3 = SpriteLoader.getSprite("gunman_death_3");
		Sprite d4 = SpriteLoader.getSprite("gunman_death_4");
		Sprite d5 = SpriteLoader.getSprite("gunman_death_5");
		Animation death = new Animation(200, false);
		death.getSprites().add(d1);
		death.getSprites().add(d2);
		death.getSprites().add(d3);
		death.getSprites().add(d4);
		death.getSprites().add(d5);
		
		Animator anim = new Animator();
		anim.addAnimation(idle, "idle");
		anim.addAnimation(moving, "moving");
		anim.addAnimation(aiming, "aiming");
		anim.addAnimation(death, "death");
		anim.setState("idle");
		
		this.graphics = new EntityGraphics(this.getWidth());
		this.graphics.setAnimator(anim);
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
			graphics.setColor(Color.red);
		} else {
			graphics.setColor(this.color);
		}
		graphics.render(g, x + offsetX, y + offsetY, 0, mirrored);
		
		GunmanController gc = (GunmanController) ai;
		if (gc.isAiming()) {
			arms.getImage().setRotation((float) Math.toDegrees(gc.getAimAngle()));
			Image img = arms.getImage();
			int i = 1;
			if (isMirrored()) {
				img = img.getFlippedCopy(true, false);
				i = -1;
			}
			g.drawImage(img, x + offsetX + 6 * i, y + offsetY, graphics.getColor());
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
	
	@Override
	public String toString() {
		String str = this.getID() + "," + x + "," + y;
		return str;
	}
}
