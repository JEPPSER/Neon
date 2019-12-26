package neon.entity.terrain;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import neon.graphics.EntityGraphics;
import neon.graphics.GraphicsUtil;
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
		if (this.graphics == null) {
			g.setColor(Color.green);
			g.drawRect(x + offsetX, y + offsetY, width, height);
			return;
		}
		g.setColor(Color.white);
		
		float scale = 1f;
		if (LevelManager.getLevel() != null) {
			scale = LevelManager.getLevel().getCamera().getScale();
		}

		int x = (int) ((this.x + offsetX) * scale);
		int y = (int) ((this.y + offsetY) * scale);
		
		Image[][] matrix = new Image[(int) (width / 50)][(int) (height / 50)];
		
		if (width == 50 && height == 50) {
			matrix[0][0] = groundSmall.getImage();
		} else if (width == 50) {
			matrix[0][0] = groundSmallUpCorner.getImage();
			matrix[0][matrix[0].length - 1] = groundSmallDownCorner.getImage();
			for (int i = 1; i < matrix[0].length - 1; i++) {
				matrix[0][i] = groundSmallSide.getImage();
			}
		} else if (height == 50) {
			matrix[0][0] = groundSmallSideCorner.getImage().getFlippedCopy(true, false);
			matrix[matrix.length - 1][0] = groundSmallSideCorner.getImage();
			for (int i = 1; i < matrix.length - 1; i++) {
				matrix[i][0] = groundSmallUp.getImage();
			}
		} else {
			// Corners
			matrix[0][0] = groundUpCorner.getImage().getFlippedCopy(true, false);
			matrix[matrix.length - 1][0] = groundUpCorner.getImage();
			matrix[0][matrix[0].length - 1] = groundDownCorner.getImage().getFlippedCopy(true, false);
			matrix[matrix.length - 1][matrix[0].length - 1] = groundDownCorner.getImage();
			
			// Up/Down
			for (int i = 1; i < matrix.length - 1; i++) {
				matrix[i][0] = groundUp.getImage();
				matrix[i][matrix[i].length - 1] = groundDown.getImage();
			}
			
			// Sides
			for (int i = 1; i < matrix[0].length - 1; i++) {
				matrix[0][i] = groundSide.getImage().getFlippedCopy(true, false);
				matrix[matrix.length - 1][i] = groundSide.getImage();
			}
			
			// Center
			for (int i = 1; i < matrix.length - 1; i++) {
				for (int j = 1; j < matrix[i].length - 1; j++) {
					matrix[i][j] = groundCenter.getImage();
				}
			}
		}
		
		g.scale(1f / scale, 1f / scale);
		GraphicsUtil.drawImageMatrix(matrix, x, y, scale);
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
}
