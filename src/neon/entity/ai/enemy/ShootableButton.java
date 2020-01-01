package neon.entity.ai.enemy;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.Entity;
import neon.entity.PhysicalEntity;
import neon.entity.terrain.movable.MovableTerrain;
import neon.level.LevelManager;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.physics.Collision;

public class ShootableButton extends Enemy {
	
	private CollisionDirection orientation;
	private String activateName;
	private boolean resetWhenDone;
	
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
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		
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
		g.setColor(Color.red);
		g.drawRect(x + offsetX, y + offsetY, width, height);
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
