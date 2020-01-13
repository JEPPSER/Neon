package neon.entity.terrain;

import java.util.ArrayList;

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
	
	private Image[][] matrix;
	
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
		
		matrix = new Image[(int) (width / 50)][(int) (height / 50)];
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
	}
	
	public void adjustImageMatrix(ArrayList<Ground> grounds) {
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[x].length; y++) {
				if (hasRightNeighbor(x, y, grounds)) {
					if (matrix[x][y] == groundUpCorner.getImage()) {
						matrix[x][y] = groundUp.getImage();
					} else if (matrix[x][y] == groundSide.getImage()) {
						matrix[x][y] = groundCenter.getImage();
					} else if (matrix[x][y] == groundDownCorner.getImage()) {
						matrix[x][y] = groundDown.getImage();
					} else if (matrix[x][y] == groundSmallSide.getImage()) {
						matrix[x][y] = groundSide.getImage().getFlippedCopy(true, false);
					} else if (matrix[x][y] == groundSmallUpCorner.getImage()) {
						matrix[x][y] = groundUpCorner.getImage().getFlippedCopy(true, false);
					} else if (matrix[x][y] == groundSmallDownCorner.getImage()) {
						matrix[x][y] = groundDownCorner.getImage().getFlippedCopy(true, false);
					} else if (matrix[x][y] == groundSmallSideCorner.getImage()) {
						matrix[x][y] = groundSmallUp.getImage();
					} else if (matrix[x][y] == groundSmall.getImage()) {
						matrix[x][y] = groundSmallSideCorner.getImage().getFlippedCopy(true, false);
					}
				}
				
				if (hasLeftNeighbor(x, y, grounds)) {
					if (GraphicsUtil.isSameImage(matrix[x][y], groundUpCorner.getImage().getFlippedCopy(true, false))) {
						matrix[x][y] = groundUp.getImage();
					} else if (GraphicsUtil.isSameImage(matrix[x][y], groundSide.getImage().getFlippedCopy(true, false))) {
						matrix[x][y] = groundCenter.getImage();
					} else if (GraphicsUtil.isSameImage(matrix[x][y], groundDownCorner.getImage().getFlippedCopy(true, false))) {
						matrix[x][y] = groundDown.getImage();
					} else if (matrix[x][y] == groundSmallSide.getImage()) {
						matrix[x][y] = groundSide.getImage();
					} else if (matrix[x][y] == groundSmallUpCorner.getImage()) {
						matrix[x][y] = groundUpCorner.getImage();
					} else if (matrix[x][y] == groundSmallDownCorner.getImage()) {
						matrix[x][y] = groundDownCorner.getImage();
					} else if (GraphicsUtil.isSameImage(matrix[x][y], groundSmallSideCorner.getImage().getFlippedCopy(true, false))) {
						matrix[x][y] = groundSmallUp.getImage();
					} else if (matrix[x][y] == groundSmall.getImage()) {
						matrix[x][y] = groundSmallSideCorner.getImage();
					}
				}
				
				if (hasBottomNeighbor(x, y, grounds)) {
					if (matrix[x][y] == groundDown.getImage()) {
						matrix[x][y] = groundCenter.getImage();
					} else if (matrix[x][y] == groundDownCorner.getImage()) {
						matrix[x][y] = groundSide.getImage();
					} else if (GraphicsUtil.isSameImage(matrix[x][y], groundDownCorner.getImage().getFlippedCopy(true, false))) {
						matrix[x][y] = groundSide.getImage().getFlippedCopy(true, false);
					} else if (matrix[x][y] == groundSmallDownCorner.getImage()) {
						matrix[x][y] = groundSmallSide.getImage();
					} else if (matrix[x][y] == groundSmallSideCorner.getImage()) {
						matrix[x][y] = groundUpCorner.getImage();
					} else if (GraphicsUtil.isSameImage(matrix[x][y], groundSmallSideCorner.getImage().getFlippedCopy(true, false))) {
						matrix[x][y] = groundUpCorner.getImage().getFlippedCopy(true, false);
					} else if (matrix[x][y] == groundSmallUp.getImage()) {
						matrix[x][y] = groundUp.getImage();
					} else if (matrix[x][y] == groundSmall.getImage()) {
						matrix[x][y] = groundSmallUpCorner.getImage();
					}
				}
				
				if (hasTopNeighbor(x, y, grounds)) {
					if (matrix[x][y] == groundUp.getImage()) {
						matrix[x][y] = groundCenter.getImage();
					} else if (matrix[x][y] == groundUpCorner.getImage()) {
						matrix[x][y] = groundSide.getImage();
					} else if (GraphicsUtil.isSameImage(matrix[x][y], groundUpCorner.getImage().getFlippedCopy(true, false))) {
						matrix[x][y] = groundSide.getImage().getFlippedCopy(true, false);
					} else if (matrix[x][y] == groundSmallUpCorner.getImage()) {
						matrix[x][y] = groundSmallSide.getImage();
					} else if (matrix[x][y] == groundSmallSideCorner.getImage()) {
						matrix[x][y] = groundDownCorner.getImage();
					} else if (GraphicsUtil.isSameImage(matrix[x][y], groundSmallSideCorner.getImage().getFlippedCopy(true, false))) {
						matrix[x][y] = groundDownCorner.getImage().getFlippedCopy(true, false);
					} else if (matrix[x][y] == groundSmallUp.getImage()) {
						matrix[x][y] = groundDown.getImage();
					} else if (matrix[x][y] == groundSmall.getImage()) {
						matrix[x][y] = groundSmallDownCorner.getImage();
					}
				}
			}
		}
	}
	
	private boolean hasNeighbor(int x, int y, int targetX, int targetY, ArrayList<Ground> grounds) {
		float realX = this.x + 50 * x;
		float realY = this.y + 50 * y;
		for (Ground e : grounds) {
			if (e != this && e.getName().equals(this.name)) {
				for (int i = 0; i < e.getMatrix().length; i++) {
					for (int j = 0; j < e.getMatrix()[i].length; j++) {
						if (e.getX() + 50 * i == realX + targetX && e.getY() + 50 * j == realY + targetY) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean hasTopNeighbor(int x, int y, ArrayList<Ground> grounds) {
		return hasNeighbor(x, y, 0, -50, grounds);
	}
	
	private boolean hasBottomNeighbor(int x, int y, ArrayList<Ground> grounds) {
		return hasNeighbor(x, y, 0, 50, grounds);
	}
	
	private boolean hasRightNeighbor(int x, int y, ArrayList<Ground> grounds) {
		return hasNeighbor(x, y, 50, 0, grounds);
	}
	
	private boolean hasLeftNeighbor(int x, int y, ArrayList<Ground> grounds) {
		return hasNeighbor(x, y, -50, 0, grounds);
	}
	
	public Image[][] getMatrix() {
		return matrix;
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
		
		g.scale(1f / scale, 1f / scale);
		GraphicsUtil.drawImageMatrix(matrix, x, y, scale);
		g.scale(scale, scale);
	}

	@Override
	public int getID() {
		return 0;
	}
	
	@Override
	public String toString() {
		String str = getID() + "," + x + "," + y + "," + getWidth() + "," + getHeight() + "," + name;
		return str;
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
