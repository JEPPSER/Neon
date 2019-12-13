package neon.entity.collectable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.PhysicalEntity;
import neon.entity.controllable.Player;
import neon.graphics.EntityGraphics;
import neon.graphics.Sprite;
import neon.graphics.animation.Animation;
import neon.graphics.animation.Animator;
import neon.physics.Collision;
import neon.physics.Physics;
import neon.time.TimeInfo;

public class Portal extends CollectableEntity {
	
	public int dir = -1;
	public int bobTime = 0;
	
	public Portal(float x, float y) {
		name = "Portal";
		color = Color.white;
		canCollect = true;
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 50, 50), 1.0f, 10f, true);
		this.x = x;
		this.y = y;
		this.height = collision.getHitbox().getHeight();
		this.width = collision.getHitbox().getWidth();
		initGraphics();
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		if (other instanceof Player) {
			y += (0.02f * TimeInfo.getDelta() * dir);
			bobTime += TimeInfo.getDelta();
			if (bobTime > 2000) {
				bobTime = 0;
				dir = dir * -1;
			}
		}
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
		graphics.render(g, x + offsetX, y + offsetY, 0, false);
	}

	@Override
	public int getID() {
		return 6;
	}

	@Override
	public void collect(Player player) {
		player.enterPortal();
	}
	
	private void initGraphics() {
		this.graphics = new EntityGraphics(width);
		graphics.setColor(this.color);
		Animator anim = new Animator();
		graphics.setAnimator(anim);
		
		Sprite sun = new Sprite("portal_1");
		Animation idle = new Animation(100, true);
		idle.getSprites().add(sun);
		anim.addAnimation(idle, "idle");
		
		anim.setState("idle");
	}
}
