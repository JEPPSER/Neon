package neon.entity.terrain;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.graphics.EntityGraphics;
import neon.graphics.Sprite;
import neon.io.SpriteLoader;
import neon.physics.Collision;
import neon.physics.Physics;

public class Ground extends TerrainEntity {
	
	private Sprite groundUp;
	private Sprite groundUpCorner;
	private Sprite groundSide;
	private Sprite groundDown;
	private Sprite groundDownCorner;
	private Sprite groundCenter;
	private Sprite groundSmall;
	private Sprite groundSmallUpCorner;
	private Sprite groundSmallDownCorner;
	private Sprite groundSmallSideCorner;
	private Sprite groundSmallSide;
	private Sprite groundSmallUp;
	
	public Ground() {
		this.name = "Ground";
		this.physics = new Physics(0f, 0f);
	}
	
	private void initGraphics() {
		this.graphics = new EntityGraphics(this.width);
		groundUp = SpriteLoader.getSprite("ground_up");
		groundUpCorner = SpriteLoader.getSprite("ground_up_corner");
		groundSide = SpriteLoader.getSprite("ground_side");
		groundDown = SpriteLoader.getSprite("ground_down");
		groundDownCorner = SpriteLoader.getSprite("ground_down_corner");
		groundCenter = SpriteLoader.getSprite("ground_center");
		groundSmall = SpriteLoader.getSprite("ground_small");
		groundSmallUpCorner = SpriteLoader.getSprite("ground_small_up_corner");
		groundSmallDownCorner = SpriteLoader.getSprite("ground_small_down_corner");
		groundSmallSideCorner = SpriteLoader.getSprite("ground_small_side_corner");
		groundSmallSide = SpriteLoader.getSprite("ground_small_side");
		groundSmallUp = SpriteLoader.getSprite("ground_small_up");
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
		
		int x = (int) (this.x + offsetX);
		int y = (int) (this.y + offsetY);
		if (width == 50 && height == 50) {
			g.drawImage(groundSmall.getImage(), x, y);
			return;
		}
		
		if (width == 50) {
			g.drawImage(groundSmallUpCorner.getImage(), x,  y);
			g.drawImage(groundSmallDownCorner.getImage(), x, y + height - 50);
			for (int i = 50; i < height - 50; i+=50) {
				g.drawImage(groundSmallSide.getImage(), x, y + i);
			}
			return;
		}
		
		if (height == 50) {
			g.drawImage(groundSmallSideCorner.getImage().getFlippedCopy(true, false), x, y);
			g.drawImage(groundSmallSideCorner.getImage(), x + width - 50, y);
			for (int i = 50; i < width - 50; i+=50) {
				g.drawImage(groundSmallUp.getImage(), x + i, y);
			}
			return;
		}
		
		// Corners
		g.drawImage(groundUpCorner.getImage().getFlippedCopy(true, false), x, y);
		g.drawImage(groundUpCorner.getImage(), x + width - 50, y);
		g.drawImage(groundDownCorner.getImage().getFlippedCopy(true, false), x, y + height - 50);
		g.drawImage(groundDownCorner.getImage(), x + width - 50, y + height - 50);
		
		// Up/Down
		for (int i = 50; i < width - 50; i+=50) {
			g.drawImage(groundUp.getImage(), x + i, y);
			g.drawImage(groundDown.getImage(), x + i, y + height - 50);
		}
		
		// Sides
		for (int i = 50; i < height - 50; i+=50) {
			g.drawImage(groundSide.getImage(), x + width - 50, y + i);
			g.drawImage(groundSide.getImage().getFlippedCopy(true, false), x, y + i);
		}
		
		// Center
		for (int i = 50; i < width - 50; i+=50) {
			for (int j = 50; j < height - 50; j+=50) {
				g.drawImage(groundCenter.getImage(), i + x, j + y);
			}
		}
	}

	@Override
	public int getID() {
		return 0;
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
