package neon.entity.collectable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.PhysicalEntity;
import neon.entity.controllable.Player;
import neon.entity.terrain.TerrainEntity;
import neon.graphics.EntityGraphics;
import neon.graphics.Sprite;
import neon.graphics.animation.Animation;
import neon.graphics.animation.Animator;
import neon.io.SpriteLoader;
import neon.level.LevelManager;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class Heart extends CollectableEntity {

	private float health = 3.0f;

	public Heart(float x, float y) {
		name = "Heart";
		color = Color.red;
		canCollect = false;
		initGraphics();
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 50, 50), 1.0f, 10f, true);
		this.x = x;
		this.y = y;
		this.height = collision.getHitbox().getHeight();
		this.width = collision.getHitbox().getWidth();
	}

	@Override
	public void collect(Player player) {
		if (canCollect) {
			player.setHealth(player.getHealth() + health);
			if (player.getHealth() > player.getMaxHealth()) {
				player.setHealth(player.getMaxHealth());
			}
			LevelManager.removeEntity(this);
		}
	}

	@Override
	public void handleCollision(PhysicalEntity other) {
		super.handleCollision(other);
		if (this.collidingEntity instanceof TerrainEntity) {
			if (!canCollect && this.collisionDirection != CollisionDirection.NONE) {
				canCollect = true;
			}
		}
	}

	@Override
	public float getWidth() {
		return this.width;
	}

	@Override
	public void setWidth(float width) {

	}

	@Override
	public float getHeight() {
		return this.height;
	}

	@Override
	public void setHeight(float height) {

	}

	@Override
	public void setSize(float width, float height) {

	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		graphics.render(g, x + offsetX, y + offsetY, 0, mirrored);
	}

	private void initGraphics() {
		Sprite one = SpriteLoader.getSprite("heart_1");
		Animation idle = new Animation(100, true);
		idle.getSprites().add(one);
		Animator anim = new Animator();
		anim.addAnimation(idle, "idle");
		anim.setState("idle");
		this.graphics = new EntityGraphics(width);
		this.graphics.setAnimator(anim);
		this.graphics.setColor(color);
	}

	@Override
	public int getID() {
		return 0;
	}
}
