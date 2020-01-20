package neon.entity.terrain;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.PhysicalEntity;
import neon.entity.controllable.Player;
import neon.graphics.EntityGraphics;
import neon.physics.Collision;
import neon.physics.Physics;
import neon.time.TimeInfo;

public class BreakableGround extends TerrainEntity {
	
	private boolean isBroken = false;
	private boolean isBreaking = false;
	private final int BREAK_TIME = 300;
	private final int RECOVER_TIME = 2000;
	private int timer = 0;
	
	public BreakableGround(float x, float y, float width, float height) {
		this.name = "BreakableGround";
		this.physics = new Physics(0f, 0f);
		setSize(width, height);
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void handleCollision(PhysicalEntity pe) {
		if (pe == this.collidingEntity && pe instanceof Player && !isBroken && !isBreaking) {
			isBreaking = true;
		} else if (isBreaking && pe instanceof Player) {
			timer += TimeInfo.getDelta();
			for (int i = 1; i < 4; i++) {
				if (timer > BREAK_TIME / i) {
					// Set image
				}
			}
			if (timer > BREAK_TIME) {
				isBroken = true;
				isBreaking = false;
				// Set image
				this.collision.getHitbox().setX(-100000);
				this.collision.getHitbox().setY(-100000);
				timer = 0;
			}
		} else if (isBroken && pe instanceof Player) {
			timer += TimeInfo.getDelta();
			if (timer > RECOVER_TIME) {
				this.collision.getHitbox().setX(0);
				this.collision.getHitbox().setY(0);
				isBroken = false;
				timer = 0;
			}
		}
	}
	
	@Override
	public String toString() {
		return getID() + "," + x + "," + y + "," + width + "," + height;
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
		this.width = width;
		this.height = height;
		this.collision = new Collision(new Rectangle(0, 0, width, height), 1, 10, false);
		this.collision.getHitbox().setHeight(height);
		this.collision.getHitbox().setWidth(width);
		initGraphics();
	}
	
	private void initGraphics() {
		this.graphics = new EntityGraphics(width);
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.white);
		if (!isBroken) {
			g.drawRect(offsetX + x, offsetY + y, width, height);
		}
	}

	@Override
	public int getID() {
		return 27;
	}
}
