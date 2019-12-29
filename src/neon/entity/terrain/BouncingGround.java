package neon.entity.terrain;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import neon.entity.PhysicalEntity;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class BouncingGround extends Ground {
	
	private float bounceFactor = 3f;
	
	public BouncingGround() {
		this.name = "BouncingGround";
		this.physics = new Physics(0f, 0f);
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.magenta);
		g.drawRect(x + offsetX, y + offsetY, width, height);
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		CollisionDirection cd = this.collisionDirection;
		PhysicalEntity pe = this.collidingEntity;
		if (other == pe && !(pe instanceof TerrainEntity)) {
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
