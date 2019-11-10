package neon.entity.terrain;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.graphics.EntityGraphics;
import neon.graphics.Sprite;
import neon.io.SpriteLoader;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class Spikes extends TerrainEntity {
	
	private CollisionDirection direction;
	private Sprite sprite;
	
	public Spikes(CollisionDirection direction) {
		this.direction = direction;
		sprite = SpriteLoader.getSprite("spike");
		name = "Spikes";
		this.physics = new Physics(0f, 0f);
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
		if (direction == CollisionDirection.UP) {
			for (int i = 0; i < width / sprite.getWidth() - 1; i++) {
				float x = this.x + offsetX + i * sprite.getWidth();
				g.drawImage(sprite.getImage(), x, y + offsetY);
			}
		} else if (direction == CollisionDirection.DOWN) {
			for (int i = 0; i < width / sprite.getWidth() - 1; i++) {
				float x = this.x + offsetX + i * sprite.getWidth();
				g.drawImage(sprite.getImage().getFlippedCopy(false, true), x, y + offsetY);
			}
		} else if (direction == CollisionDirection.RIGHT) {
			sprite.getImage().setRotation(90);
			for (int i = 0; i < height / sprite.getWidth() - 1; i++) {
				float y = this.y + offsetY + i * sprite.getWidth();
				
				g.drawImage(sprite.getImage(), x + offsetX, y);
			}
			sprite.getImage().setRotation(0);
		} else if (direction == CollisionDirection.LEFT) {
			sprite.getImage().setRotation(-90);
			for (int i = 0; i < height / sprite.getWidth() - 1; i++) {
				float y = this.y + offsetY + i * sprite.getWidth();
				g.drawImage(sprite.getImage(), x + offsetX, y);
			}
			sprite.getImage().setRotation(0);
		}
		
	}

	@Override
	public int getID() {
		return 0;
	}

}
