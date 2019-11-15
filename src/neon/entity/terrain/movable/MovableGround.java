package neon.entity.terrain.movable;

import java.util.ArrayList;

import neon.entity.PhysicalEntity;
import neon.entity.controllable.ControllableEntity;
import neon.entity.terrain.Ground;
import neon.entity.terrain.TerrainEntity;
import neon.graphics.Point;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.time.TimeInfo;

public class MovableGround extends Ground implements MovableTerrain {
	
	private boolean active;
	private ArrayList<Point> path;
	private int currentPoint = 0;
	private int direction = 1;
	private boolean looping;
	private boolean standing;
	private PhysicalEntity standingEntity;
	private float speed;
	
	public MovableGround(String name, boolean active, boolean looping, float speed, ArrayList<Point> path) {
		this.name = name;
		this.active = active;
		this.path = path;
		this.looping = looping;
		this.speed = speed;
		this.physics = new Physics(0f, 0f);
		x = path.get(0).getX();
		y = path.get(0).getY();
	}
	
	@Override
	public void reset() {
		if (!looping) {
			active = false;
			x = path.get(0).getX();
			y = path.get(0).getY();
			currentPoint = 0;
			direction = 1;
		}
	}

	@Override
	public void activate() {
		this.active = true;
	}
	
	@Override
	public void deactivate() {
		this.active = false;
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		super.handleCollision(other);
		if (this.collisionDirection == CollisionDirection.UP && (collidingEntity instanceof ControllableEntity)) {
			standing = true;
			standingEntity = collidingEntity;
		}
	}

	@Override
	public void updateMovement() {
		if (active && !(currentPoint == path.size() - 1 && !looping)) {
			Point target = path.get(currentPoint + direction);

			float angle = path.get(currentPoint).angleTo(target);
			float difX = (float) Math.cos(angle) * speed * TimeInfo.getDelta();
			float difY = (float) Math.sin(angle) * speed * TimeInfo.getDelta();
			x += difX;
			y += difY;
			
			if (standing) {
				standingEntity.setX(standingEntity.getX() + difX);
				standingEntity.setY(standingEntity.getY() + difY);
			}
			
			if (target.distanceTo(new Point(x, y)) < speed * TimeInfo.getDelta()) {
				currentPoint = currentPoint + direction;
				if (currentPoint == 0) {
					direction = 1;
				} else if (currentPoint == path.size() - 1) {
					direction = -1;
				}
				x = target.getX();
				y = target.getY();
			}
		}
		standing = false;
		standingEntity = null;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public boolean isLooping() {
		return looping;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public ArrayList<Point> getPath() {
		return path;
	}
	
	@Override
	public int getID() {
		return 8;
	}
}
