package neon.entity;

import neon.physics.Collision;
import neon.physics.Physics;

public abstract class PhysicalEntity implements Entity {
	
	protected Collision collision;
	protected Physics physics;
	
	public Collision getCollision() {
		return collision;
	}
	
	public Physics getPhysics() {
		return physics;
	}
}
