package neon.entity.ai.enemy;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.Entity;
import neon.entity.PhysicalEntity;
import neon.entity.controllable.Player;
import neon.entity.terrain.movable.MovableTerrain;
import neon.io.SpriteLoader;
import neon.level.LevelManager;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.time.TimeInfo;
import neon.physics.Collision;

public class ShootableButton extends Enemy {
	
	private CollisionDirection orientation;
	private String activateName;
	private boolean resetWhenDone;
	private Image img1;
	private Image img2;
	private boolean isPushed = false;
	private final int PUSHED_TIME = 1000;
	private int pushedTimer = 0;
	
	public ShootableButton(CollisionDirection orientation, String activateName, boolean resetWhenDone) {
		this.name = "ShootableButton";
		this.orientation = orientation;
		this.activateName = activateName;
		this.resetWhenDone = resetWhenDone;
		this.maxHealth = 100;
		this.health = 100;
		if (orientation == CollisionDirection.UP || orientation == CollisionDirection.DOWN) {
			width = 100;
			height = 50;
		} else {
			width = 50;
			height = 100;
		}
		collision = new Collision(new Rectangle(0, 0, width, height), 1f, 10f, false);
		physics = new Physics(0f, 0f);
		img1 = SpriteLoader.getSprite("button_1").getImage();
		img2 = SpriteLoader.getSprite("button_2").getImage();
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		if (other instanceof Player) {
			if (isPushed) {
				pushedTimer += TimeInfo.getDelta();
				if (pushedTimer > PUSHED_TIME) {
					pushedTimer = 0;
					isPushed = false;
				}
			}
		}
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
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		Image tempImg = img1;
		if (isPushed) {
			tempImg = img2;
		}
		
		float difX = 0;
		float difY = 0;
		if (orientation == CollisionDirection.UP) {
			tempImg.setRotation(90);
			difX = 25;
			difY = -25;
		} else if (orientation == CollisionDirection.DOWN) {
			tempImg.setRotation(-90);
			difY = -25;
			difX = 25;
		} else if (orientation == CollisionDirection.RIGHT) {
			tempImg.setRotation(180);
		} else if (orientation == CollisionDirection.LEFT) {
			tempImg.setRotation(0);
		}
		g.drawImage(tempImg, x + offsetX + difX, y + offsetY + difY);
	}

	@Override
	public int getID() {
		return 21;
	}
	
	@Override
	public String toString() {
		String str = getID() + "," + x + "," + y + "," + orientation.toString() + "," + activateName + "," + resetWhenDone;
		return str;
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {
		isPushed = true;
		ArrayList<Entity> objects = LevelManager.getLevel().getObjects();
		for (Entity e : objects) {
			if (e instanceof MovableTerrain && e.getName().equals(activateName)) {
				((MovableTerrain) e).activate();
				if (resetWhenDone) {
					((MovableTerrain) e).resetWhenDone();
				}
			}
		}
	}
}
