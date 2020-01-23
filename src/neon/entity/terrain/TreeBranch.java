package neon.entity.terrain;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import neon.graphics.EntityGraphics;
import neon.io.SpriteLoader;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class TreeBranch extends OneWay {
	
	private Image one;
	private Image two;
	private Image three;
	
	public TreeBranch(float x, float y, float width, boolean mirrored) {
		this.name = "TreeBranch";
		this.physics = new Physics(0f, 0f);
		setSize(width, 25);
		this.x = x;
		this.y = y;
		this.orientation = CollisionDirection.UP;
		this.mirrored = mirrored;
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		float realX = offsetX + x;
		float realY = offsetY + y;
		
		if (!mirrored) {
			if (width == 50) {
				g.drawImage(three, realX, realY);
			} else if (width == 100) {
				g.drawImage(two, realX, realY);
				g.drawImage(three, realX + 50, realY);
			} else if (width > 100) {
				g.drawImage(one, realX, realY);
				for (int i = 50; i < width - 50; i+=50) {
					g.drawImage(two, realX + i, realY);
				}
				g.drawImage(three, realX + width - 50, realY);
			}
		} else {
			if (width == 50) {
				g.drawImage(three.getFlippedCopy(true, false), realX, realY);
			} else if (width == 100) {
				g.drawImage(two.getFlippedCopy(true, false), realX + 50, realY);
				g.drawImage(three.getFlippedCopy(true, false), realX, realY);
			} else if (width > 100) {
				g.drawImage(one.getFlippedCopy(true, false), realX + width - 50, realY);
				for (int i = 50; i < width - 50; i+=50) {
					g.drawImage(two.getFlippedCopy(true, false), realX + width - 50 - i, realY);
				}
				g.drawImage(three.getFlippedCopy(true, false), realX, realY);
			}
		}
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
		this.one = SpriteLoader.getSprite("branch_1").getImage();
		this.two = SpriteLoader.getSprite("branch_2").getImage();
		this.three = SpriteLoader.getSprite("branch_3").getImage();
	}
	
	@Override
	public String toString() {
		return getID() + "," + x + "," + y + "," + width + "," + mirrored;
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
	public int getID() {
		return 28;
	}
}
