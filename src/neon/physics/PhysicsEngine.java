package neon.physics;

import java.util.ArrayList;

import neon.entity.Entity;
import neon.entity.PhysicalEntity;
import neon.entity.collectable.Portal;
import neon.entity.projectile.ProjectileEntity;
import neon.entity.terrain.movable.MovableTerrain;
import neon.time.TimeInfo;

public class PhysicsEngine {

	private float gravity = 0.01f;
	private float maxYVelocity = 1f;

	public void applyPhysics(ArrayList<Entity> playField) {
		// Physics
		for (int i = 0; i < playField.size(); i++) {
			if (playField.get(i) instanceof PhysicalEntity) {
				PhysicalEntity e = (PhysicalEntity) playField.get(i);
				if (e.getCollision().isMovable() && !(e instanceof Portal)) {
					float tempGravity = gravity;
					if (e instanceof ProjectileEntity) {
						tempGravity *= ((ProjectileEntity) e).getWeight();
					}
					float vel = e.getPhysics().getYVelocity() + tempGravity * TimeInfo.getDelta();
					if (vel > maxYVelocity) {
						vel = maxYVelocity;
					}
					e.getPhysics().setYVelocity(vel);
					e.setX(e.getX() + e.getPhysics().getXVelocity() * TimeInfo.getDelta());
					e.setY(e.getY() + e.getPhysics().getYVelocity() * TimeInfo.getDelta());
				}
				if (e instanceof MovableTerrain) {
					((MovableTerrain) e).updateMovement();
				}
			}
		}

		// Collision
		for (int i = 0; i < playField.size(); i++) {
			if (playField.get(i) instanceof PhysicalEntity) {
				PhysicalEntity e = (PhysicalEntity) playField.get(i);
				for (int j = 0; j < playField.size(); j++) {
					if (j != i && playField.get(j) instanceof PhysicalEntity) {
						PhysicalEntity pe = (PhysicalEntity) playField.get(j);
						e.checkCollision(pe);
						e.handleCollision(pe);
					}
				}
			}
		}
	}
}
