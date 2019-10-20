package neon.physics;

import java.util.ArrayList;

import neon.entity.Entity;
import neon.entity.PhysicalEntity;

public class PhysicsEngine {

	private float gravity = 0.01f;
	private float maxYVelocity = 1f;

	public void applyPhysics(ArrayList<Entity> playField, int delta) {
		// Physics
		for (int i = 0; i < playField.size(); i++) {
			if (playField.get(i) instanceof PhysicalEntity) {
				PhysicalEntity e = (PhysicalEntity) playField.get(i);
				if (e.getCollision().isMovable()) {
					float vel = e.getPhysics().getYVelocity() + gravity * delta;
					if (vel > maxYVelocity) {
						vel = maxYVelocity;
					}
					e.getPhysics().setYVelocity(vel);
					e.setX(e.getX() + e.getPhysics().getXVelocity() * delta);
					e.setY(e.getY() + e.getPhysics().getYVelocity() * delta);
				}
			}
		}

		// Collision
		for (int i = 0; i < playField.size(); i++) {
			if (playField.get(i) instanceof PhysicalEntity) {
				PhysicalEntity e = (PhysicalEntity) playField.get(i);
				if (e.getCollision().isMovable()) {
					for (int j = 0; j < playField.size(); j++) {
						if (j != i && playField.get(j) instanceof PhysicalEntity) {
							PhysicalEntity pe = (PhysicalEntity) playField.get(j);
							CollisionDirection cd = e.isColliding(pe);
							
							// TODO: Add a reaction to movable objects when something hits them.
							if (cd == CollisionDirection.DOWN) {
								e.setY(pe.getY() - e.getCollision().getHitbox().getHeight());
								e.getPhysics().setYVelocity(0f);
							} else if (cd == CollisionDirection.UP) {
								e.setY(pe.getY() + pe.getCollision().getHitbox().getHeight());
								e.getPhysics().setYVelocity(0f);
							} else if (cd == CollisionDirection.RIGHT) {
								e.setX(pe.getX() - e.getCollision().getHitbox().getWidth());
								e.getPhysics().setXVelocity(0f);
							} else if (cd == CollisionDirection.LEFT) {
								e.setX(pe.getX() + pe.getCollision().getHitbox().getWidth());
								e.getPhysics().setXVelocity(0f);
							}
						}
					}
				}
			}
		}
	}
}
