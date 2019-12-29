package neon.entity.collectable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.controllable.Player;
import neon.entity.weapon.Gun;
import neon.graphics.EntityGraphics;
import neon.graphics.Sprite;
import neon.graphics.animation.Animation;
import neon.graphics.animation.Animator;
import neon.io.SpriteLoader;
import neon.level.LevelManager;
import neon.physics.Collision;
import neon.physics.Physics;

public class GunCollector extends CollectableEntity {
	
	public GunCollector(float x, float y) {
		name = "GunCollector";
		color = Color.white;
		canCollect = true;
		initGraphics();
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 30, 30), 1.0f, 10f, false);
		this.x = x;
		this.y = y;
		this.height = collision.getHitbox().getHeight();
		this.width = collision.getHitbox().getWidth();
	}
	
	@Override
	public void collect(Player player) {
		player.setWeapon(new Gun());
		LevelManager.removeEntity(this);
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
	public float getWidth() {
		return width;
	}

	@Override
	public void setWidth(float width) {
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void setHeight(float height) {
	}

	@Override
	public void setSize(float width, float height) {
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(color);
		g.drawRect(x + offsetX, y + offsetY, width, height);
		graphics.render(g, x + offsetX, y + offsetY, 0, false);
	}
	
	@Override
	public String toString() {
		String str = getID() + "," + x + "," + y;
		return str;
	}

	@Override
	public int getID() {
		return 16;
	}
}
