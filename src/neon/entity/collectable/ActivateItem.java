package neon.entity.collectable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.Entity;
import neon.entity.PhysicalEntity;
import neon.entity.controllable.Player;
import neon.entity.terrain.TerrainEntity;
import neon.entity.terrain.movable.MovableTerrain;
import neon.graphics.EntityGraphics;
import neon.graphics.Sprite;
import neon.graphics.animation.Animation;
import neon.graphics.animation.Animator;
import neon.io.SpriteLoader;
import neon.level.LevelManager;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.time.TimeInfo;

public class ActivateItem extends CollectableEntity {

	private String activateName;
	private final int COOLDOWN = 5000;
	private int timer = 0;
	private boolean resetWhenDone;

	public ActivateItem(float x, float y, String activateName, boolean resetWhenDone) {
		name = "ActivateItem";
		canCollect = true;
		this.resetWhenDone = resetWhenDone;
		this.x = x;
		this.y = y;
		this.activateName = activateName;
		color = Color.yellow;
		width = 50;
		height = 50;
		this.physics = new Physics(0, 0);
		this.collision = new Collision(new Rectangle(0, 0, 50, 50), 1f, 10f, false);
		initGraphics();
	}

	private void initGraphics() {
		this.graphics = new EntityGraphics(width);

		// Idle animation
		Sprite idle1 = SpriteLoader.getSprite("activate_item_1");
		Sprite idle2 = SpriteLoader.getSprite("activate_item_2");
		Sprite idle3 = SpriteLoader.getSprite("activate_item_3");
		Sprite idle4 = SpriteLoader.getSprite("activate_item_4");
		Sprite idle5 = SpriteLoader.getSprite("activate_item_5");
		Animation idle = new Animation(100, true);
		idle.getSprites().add(idle1);
		idle.getSprites().add(idle2);
		idle.getSprites().add(idle3);
		idle.getSprites().add(idle4);
		idle.getSprites().add(idle5);
		idle.getSprites().add(idle4);
		idle.getSprites().add(idle3);
		idle.getSprites().add(idle2);
		
		Animator anim = new Animator();
		anim.addAnimation(idle, "idle");
		anim.setState("idle");
		graphics.setAnimator(anim);
		graphics.setColor(Color.white);
	}

	public String getActivateName() {
		return activateName;
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
		if (canCollect) {
			graphics.render(g, x + offsetX, y + offsetY, 0, false);
		}
	}

	@Override
	public String toString() {
		String str = getID() + "," + x + "," + y + "," + activateName + "," + resetWhenDone;
		return str;
	}

	@Override
	public int getID() {
		return 19;
	}

	@Override
	public void collect(Player player) {
		if (canCollect) {
			for (Entity e : LevelManager.getLevel().getObjects()) {
				if (e instanceof CollectableEntity && e.getName().equals(activateName)) {
					((CollectableEntity) e).canCollect = true;
				} else if (e instanceof MovableTerrain && e.getName().equals(activateName)) {
					((MovableTerrain) e).activate();
					if (resetWhenDone) {
						((MovableTerrain) e).resetWhenDone();
					}
				}
			}
			this.canCollect = false;
		}
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		super.handleCollision(other);
		if (other instanceof Player) {
			if (!canCollect) {
				timer += TimeInfo.getDelta();
				if (timer > COOLDOWN) {
					timer = 0;
					canCollect = true;
				}
			}
		}
	}
}
