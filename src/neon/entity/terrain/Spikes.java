package neon.entity.terrain;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.PhysicalEntity;
import neon.entity.controllable.Player;
import neon.graphics.EntityGraphics;
import neon.graphics.Sprite;
import neon.io.SpriteLoader;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.time.TimeInfo;

public class Spikes extends TerrainEntity {
	
	protected CollisionDirection direction;
	private Sprite sprite;
	private int timer;
	private final int COLLISION_TIME = 6;
	
	public Spikes(CollisionDirection direction) {
		this.direction = direction;
		sprite = SpriteLoader.getSprite("spike");
		name = "Spikes";
		this.physics = new Physics(0f, 0f);
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		if (other instanceof Player) {
			if (other == this.collidingEntity) {
				timer += TimeInfo.getDelta();
				if (timer > COLLISION_TIME) {
					((Player) other).setHealth(0);
				}
			} else {
				timer = 0;
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
		this.width = width;
		this.height = height;
		this.collision = new Collision(new Rectangle(0, 0, this.width, this.height), 1.0f, 10f, false);
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
		drawSpikeRow(g, x + offsetX, y + offsetY, direction);
		
		if (direction == CollisionDirection.UP && height == 60) {
			drawSpikeRow(g, x + offsetX, y + offsetY + 30, CollisionDirection.DOWN);
		} else if (direction == CollisionDirection.DOWN && height == 60) {
			drawSpikeRow(g, x + offsetX, y + offsetY - 30, CollisionDirection.UP);
		} else if (direction == CollisionDirection.LEFT && width == 60) {
			drawSpikeRow(g, x + offsetX + 30, y + offsetY, CollisionDirection.RIGHT);
		} else if (direction == CollisionDirection.RIGHT && width == 60) {
			drawSpikeRow(g, x + offsetX - 30, y + offsetY, CollisionDirection.LEFT);
		}
	}
	
	private void drawSpikeRow(Graphics g, float x, float y, CollisionDirection direction) {
		if (direction == CollisionDirection.UP) {
			for (int i = 0; i < width / sprite.getWidth(); i++) {
				float tempX = x + i * sprite.getWidth();
				g.drawImage(sprite.getImage(), tempX, y);
			}
		} else if (direction == CollisionDirection.DOWN) {
			for (int i = 0; i < width / sprite.getWidth(); i++) {
				float tempX = x + i * sprite.getWidth();
				g.drawImage(sprite.getImage().getFlippedCopy(false, true), tempX, y);
			}
		} else if (direction == CollisionDirection.RIGHT) {
			sprite.getImage().setRotation(90);
			for (int i = 0; i < height / sprite.getWidth(); i++) {
				float tempY = y + i * sprite.getWidth();
				g.drawImage(sprite.getImage(), x, tempY);
			}
			sprite.getImage().setRotation(0);
		} else if (direction == CollisionDirection.LEFT) {
			sprite.getImage().setRotation(-90);
			for (int i = 0; i < height / sprite.getWidth(); i++) {
				float tempY = y + i * sprite.getWidth();
				g.drawImage(sprite.getImage(), x, tempY);
			}
			sprite.getImage().setRotation(0);
		}
	}
	
	@Override
	public String toString() {
		String str = getID() + "," + x + "," + y + "," + getWidth() + "," + getHeight() + "," + direction;
		return str;
	}

	@Override
	public int getID() {
		return 7;
	}
}
