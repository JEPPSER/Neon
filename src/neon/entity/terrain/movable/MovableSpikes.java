package neon.entity.terrain.movable;

import java.util.ArrayList;

import neon.entity.terrain.Spikes;
import neon.graphics.Point;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.time.TimeInfo;

public class MovableSpikes extends Spikes implements MovableTerrain {
	
	private boolean active;
	private ArrayList<Point> path;
	private int currentPoint = 0;
	private int direction = 1;
	private boolean looping;
	private float speed;
	private boolean canReset;
	private boolean originalActive;
	private boolean resetWhenDone = false;

	public MovableSpikes(CollisionDirection cd, String name, boolean active, boolean looping, boolean canReset, float speed, ArrayList<Point> path) {
		super(cd);
		this.name = name;
		this.active = active;
		this.path = path;
		this.looping = looping;
		this.speed = speed;
		this.canReset = canReset;
		this.originalActive = active;
		this.physics = new Physics(0f, 0f);
		x = path.get(0).getX();
		y = path.get(0).getY();
	}
	
	@Override
	public void resetWhenDone() {
		resetWhenDone = true;
	}
	
	@Override
	public void reset() {
		if (canReset) {
			active = originalActive;
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
	public void updateMovement() {
		if (active && !(currentPoint == path.size() - 1 && !looping)) {
			Point target = path.get(currentPoint + direction);

			float angle = path.get(currentPoint).angleTo(target);
			float difX = (float) Math.cos(angle) * speed * TimeInfo.getDelta();
			float difY = (float) Math.sin(angle) * speed * TimeInfo.getDelta();
			x += difX;
			y += difY;
			
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
		} else if (active && resetWhenDone) {
			reset();
		}
	}

	@Override
	public int getID() {
		return 12;
	}
}
