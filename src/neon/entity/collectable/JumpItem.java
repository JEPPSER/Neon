package neon.entity.collectable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.PhysicalEntity;
import neon.entity.controllable.Player;
import neon.graphics.EntityGraphics;
import neon.io.SpriteLoader;
import neon.level.LevelManager;
import neon.physics.Physics;
import neon.time.TimeInfo;
import neon.physics.Collision;

public class JumpItem extends CollectableEntity {
	
	private Image img;
	private final int SPAWN_TIME = 3000;
	private int timer = 0;
	private int bopTimer = 0;
	private final int BOP_TIME = 1000;
	
	public JumpItem(float x, float y) {
		name = "JumpItem";
		color = Color.yellow;
		canCollect = true;
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 30, 30), 1.0f, 10f, false);
		this.width = 30;
		this.height = 30;
		this.x = x;
		this.y = y;
		this.graphics = new EntityGraphics(width);
		img = SpriteLoader.getSprite("jump_item").getImage();
	}
	
	@Override
	public void collect(Player player) {
		if (canCollect) {
			player.activateAirJump();
			canCollect = false;
		}
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		if (other instanceof Player) {
			if (!canCollect) {
				if (timer < SPAWN_TIME) {
					timer += TimeInfo.getDelta();
				} else {
					timer = 0;
					canCollect = true;
				}
			} else {
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
		float difY = Math.abs(bopTimer) / 50f;
		g.setColor(Color.white);
		if (canCollect) {
			g.drawImage(img, x + offsetX - 30, y + offsetY - 30 + difY);
		}
	}
	
	@Override
	public String toString() {
		String str = getID() + "," + x + "," + y;
		return str;
	}

	@Override
	public int getID() {
		return 17;
	}
}
