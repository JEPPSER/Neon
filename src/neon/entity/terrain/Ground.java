package neon.entity.terrain;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import neon.graphics.EntityGraphics;
import neon.graphics.Sprite;
import neon.io.SpriteLoader;
import neon.level.LevelManager;
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
	
	private float scale;
	
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
		
		scale = LevelManager.getLevel().getCamera().getScale();
		g.scale(1f / scale, 1f / scale);
		int x = (int) ((this.x + offsetX) * scale);
		int y = (int) ((this.y + offsetY) * scale);
		int imgSize = (int) (50 * scale);
		
		if (width == 50 && height == 50) {
			drawImage(groundSmall.getImage(), x, y);
			g.scale(scale, scale);
			return;
		}
		
		if (width == 50) {
			drawImage(groundSmallUpCorner.getImage(), x,  y);
			drawImage(groundSmallDownCorner.getImage(), x, y + height * scale - imgSize);
			for (int i = imgSize; i < height * scale - imgSize; i+=imgSize) {
				drawImage(groundSmallSide.getImage(), x, y + i);
			}
			g.scale(scale, scale);
			return;
		}
		
		if (height == 50) {
			drawImage(groundSmallSideCorner.getImage().getFlippedCopy(true, false), x, y);
			drawImage(groundSmallSideCorner.getImage(), x + width * scale - imgSize, y);
			for (int i = imgSize; i < width * scale - imgSize; i+=imgSize) {
				drawImage(groundSmallUp.getImage(), x + i, y);
			}
			g.scale(scale, scale);
			return;
		}
		
		// Corners
		drawImage(groundUpCorner.getImage().getFlippedCopy(true, false), x, y);
		drawImage(groundUpCorner.getImage(), x + width * scale - imgSize, y);
		drawImage(groundDownCorner.getImage().getFlippedCopy(true, false), x, y + height * scale - imgSize);
		drawImage(groundDownCorner.getImage(), x + width * scale - imgSize, y + height * scale - imgSize);
		
		// Up/Down
		for (int i = imgSize; i < width * scale - imgSize; i+=imgSize) {
			drawImage(groundUp.getImage(), x + i, y);
			drawImage(groundDown.getImage(), x + i, y + height * scale - imgSize);
		}
		
		// Sides
		for (int i = imgSize; i < height * scale - imgSize; i+=imgSize) {
			drawImage(groundSide.getImage(), x + width * scale - imgSize, y + i);
			drawImage(groundSide.getImage().getFlippedCopy(true, false), x, y + i);
		}
		
		// Center
		for (int i = imgSize; i < width * scale - imgSize; i+=imgSize) {
			for (int j = imgSize; j < height * scale - imgSize; j+=imgSize) {
				drawImage(groundCenter.getImage(), i + x, j + y);
			}
		}
		
		g.scale(scale, scale);
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
	
	private void drawImage(Image img, float x, float y) {
		img.draw(x, y, scale);
	}
}
