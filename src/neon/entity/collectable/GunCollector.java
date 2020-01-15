package neon.entity.collectable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.PhysicalEntity;
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
import neon.time.TimeInfo;

public class GunCollector extends CollectableEntity {
	
	private Image img;
	private final int BOP_TIME = 1000;
	private int bopTimer = 0;
	
	public GunCollector(float x, float y) {
		name = "GunCollector";
		color = Color.white;
		canCollect = true;
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 30, 30), 1.0f, 10f, false);
		this.x = x;
		this.y = y;
		this.height = collision.getHitbox().getHeight();
		this.width = collision.getHitbox().getWidth();
		this.graphics = new EntityGraphics(width);
		img = SpriteLoader.getSprite("gun_collector").getImage();
	}
	
	@Override
	public void collect(Player player) {
		player.setWeapon(new Gun());
		LevelManager.removeEntity(this);
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		if (other instanceof Player) {
			if (canCollect) {
				bopTimer += TimeInfo.getDelta();
				if (bopTimer > BOP_TIME) {
					bopTimer = -BOP_TIME;
				}
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
		float difY = Math.abs(bopTimer) / 50f;
		g.setColor(Color.white);
		if (canCollect) {
			g.drawImage(img, x + offsetX - 50, y + offsetY - 50 + difY);
		}
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
