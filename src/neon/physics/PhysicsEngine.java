package neon.physics;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

import neon.entity.Entity;
import neon.entity.PhysicalEntity;

public class PhysicsEngine {

	public void applyPhysics(ArrayList<Entity> playField) {
		
		// Placeholder physics and collision!!
		
		// Physics
		for (int i = 0; i < playField.size(); i++) {
			if (playField.get(i) instanceof PhysicalEntity) {
				PhysicalEntity e = (PhysicalEntity) playField.get(i);
				e.setX(e.getX() + e.getPhysics().getXVelocity());
				e.setY(e.getY() + e.getPhysics().getYVelocity());
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
							Rectangle eR = e.getCollision().getHitbox();
							eR = new Rectangle(eR.getX() + e.getX(), eR.getY() + e.getY(), eR.getWidth(),
									eR.getHeight());
							Rectangle peR = pe.getCollision().getHitbox();
							peR = new Rectangle(peR.getX() + pe.getX(), peR.getY() + pe.getY(), peR.getWidth(),
									peR.getHeight());
							if (eR.intersects(peR)) {
								e.setY(e.getY() - 1);
							}
						}
					}
				}
			}
		}
	}
}
