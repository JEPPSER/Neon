package neon.entity.terrain;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import neon.entity.PhysicalEntity;
import neon.entity.area.Trigger;
import neon.entity.controllable.Player;
import neon.io.SpriteLoader;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.time.TimeInfo;

public class BouncingGround extends Ground {
	
	private float bounceFactor = 3f;
	private Image img1;
	private Image img2;
	private Image img3;
	private Image img4;
	
	private Image center;
	
	private Image currentImg;
	
	private int animTimer = 0;
	private boolean isBouncing = false;
	private final int ANIM_TIME = 200;
	private CollisionDirection currentDir;
	
	public BouncingGround() {
		this.name = "BouncingGround";
		this.physics = new Physics(0f, 0f);
		layer = 1;
		initGraphics();
	}
	
	private void initGraphics() {
		img1 = SpriteLoader.getSprite("bounce_pad_1").getImage();
		img2 = SpriteLoader.getSprite("bounce_pad_2").getImage();
		img3 = SpriteLoader.getSprite("bounce_pad_3").getImage();
		img4 = SpriteLoader.getSprite("bounce_pad_4").getImage();
		center = SpriteLoader.getSprite("bounce_center").getImage();
		currentImg = img1;
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		for (int i = 0; i < width; i+=50) {
			for (int j = 0; j < height; j+=50) {
				g.drawImage(center, x + offsetX + i, y + offsetY + j);
			}
		}
		
		drawSprings(g, offsetX, offsetY);
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		CollisionDirection cd = this.collisionDirection;
		PhysicalEntity pe = this.collidingEntity;
		
		if (other == pe && !(pe instanceof TerrainEntity) && !(pe instanceof Trigger)) {
			if (cd == CollisionDirection.UP) {
				pe.setY(pe.getY() - bounceFactor);
				pe.getPhysics().setYVelocity(-bounceFactor);
			} else if (cd == CollisionDirection.RIGHT) {
				pe.setX(pe.getX() + bounceFactor);
				pe.getPhysics().setXVelocity(bounceFactor);
			} else if (cd == CollisionDirection.LEFT) {
				pe.setX(pe.getX() - bounceFactor);
				pe.getPhysics().setXVelocity(-bounceFactor);
			} else if (cd == CollisionDirection.DOWN) {
				pe.setY(pe.getY() + bounceFactor);
				pe.getPhysics().setYVelocity(bounceFactor);
			}
			isBouncing = true;
			animTimer = 0;
			currentDir = cd;
		}
		
		if (other instanceof Player && isBouncing) {
			animTimer += TimeInfo.getDelta();
			if (animTimer < 1 * ANIM_TIME / 5) {
				currentImg = img2;
			} else if (animTimer < 2 * ANIM_TIME / 5) {
				currentImg = img3;
			} else if (animTimer < 3 * ANIM_TIME / 5) {
				currentImg = img4;
			} else if (animTimer < 4 * ANIM_TIME / 5) {
				currentImg = img3;
			} else if (animTimer < ANIM_TIME) {
				currentImg = img2;
			} else if (animTimer > ANIM_TIME) {
				animTimer = 0;
				isBouncing = false;
				currentImg = img1;
				currentDir = CollisionDirection.NONE;
			}
		}
	}
	
	private void drawSprings(Graphics g, float offsetX, float offsetY) {
		Image tempImg = img1;
		int realX = Math.round(x + offsetX);
		int realY = Math.round(y + offsetY);
		
		for (int i = 0; i < width; i+=50) {
			if (currentDir == CollisionDirection.UP) {
				tempImg = currentImg;
			}
			tempImg.setRotation(0);
			g.drawImage(tempImg, realX + i, realY - tempImg.getHeight());
			tempImg = img1;
			
			if (currentDir == CollisionDirection.DOWN) {
				tempImg = currentImg;
			}
			tempImg.setRotation(180);
			g.drawImage(tempImg, realX + i, realY + height);
			tempImg = img1;
		}
		
		for (int i = 0; i < height; i+=50) {
			if (currentDir == CollisionDirection.RIGHT) {
				tempImg = currentImg;
			}
			tempImg.setRotation(90);
			float dif = (tempImg.getWidth() - tempImg.getHeight()) / 2;
			g.drawImage(tempImg, realX + width - dif, realY + i + dif);
			tempImg = img1;
			
			if (currentDir == CollisionDirection.LEFT) {
				tempImg = currentImg;
			}
			tempImg.setRotation(-90);
			dif = (tempImg.getWidth() - tempImg.getHeight()) / 2;
			g.drawImage(tempImg, realX - dif - tempImg.getHeight(), realY + i + dif);
			tempImg = img1;
		}
	}
	
	@Override
	public int getID() {
		return 14;
	}
	
	@Override
	public String toString() {
		String str = getID() + "," + x + "," + y + "," + getWidth() + "," + getHeight();
		return str;
	}
}
