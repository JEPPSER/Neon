package neon.entity.terrain;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import neon.graphics.EntityGraphics;
import neon.level.LevelManager;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class Bounds extends TerrainEntity {
	
	private CollisionDirection cd;
	private Image image;

	public Bounds(CollisionDirection cd) {
		this.layer = 1;
		this.cd = cd;
		this.name = "Bounds";
		this.physics = new Physics(0f, 0f);
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public Image getImage() {
		return image;
	}

	public float getWidth() {
		return width;
	}

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
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.white);
		
		if (cd == CollisionDirection.DOWN) {
			for (int i = -50; i < width + 50; i+=50) {
				g.drawImage(image, x + offsetX + i, y + height + offsetY - 50);
			}
		} else if (cd == CollisionDirection.UP) {
			for (int i = -50; i < width + 50; i+=50) {
				g.drawImage(image, x + offsetX + i, y + offsetY);
			}
		} else if (cd == CollisionDirection.LEFT) {
			for (int i = 0; i < height; i+=50) {
				g.drawImage(image, x + offsetX, y + offsetY + i);
			}
		} else if (cd == CollisionDirection.RIGHT) {
			for (int i = 0; i < height; i+=50) {
				g.drawImage(image, x + width + offsetX - 50, y + offsetY + i);
			}
		}
	}

	private void initGraphics() {
		this.graphics = new EntityGraphics(this.width);
	}

	@Override
	public int getID() {
		return -1;
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
}
