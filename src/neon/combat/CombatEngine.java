package neon.combat;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

import neon.entity.Entity;
import neon.entity.ai.Enemy;
import neon.entity.controllable.Player;
import neon.physics.CollisionDirection;

public class CombatEngine {
	
	public void updateCombat(ArrayList<Entity> objects, Player p) {
		if (p.getCombat().isAttacking()) {
			Attack attack = p.getCombat().getCurrentAttack();
			Rectangle attackHitBox = attack.getHitBox(p);
			
			for (int i = 0; i < objects.size(); i++) {
				if (objects.get(i) instanceof Enemy) {
					Enemy e = (Enemy) objects.get(i);
					Rectangle tempR = e.getCollision().getHitbox();
					Rectangle enemyHitBox = new Rectangle(tempR.getX() + e.getX(), tempR.getY() + e.getY(), tempR.getWidth(), tempR.getHeight());
					if (enemyHitBox.intersects(attackHitBox)) {
						if (e.getX() < p.getX()) {
							e.takeDamage(attack.getDamage(), CollisionDirection.RIGHT);
						} else {
							e.takeDamage(attack.getDamage(), CollisionDirection.LEFT);
						}
						if (e.getHealth() <= 0) {
							e.death();
						}
					}
				}
			}
		}
		
		// Checks for dead enemies that can be despawned
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i) instanceof Enemy) {
				Enemy e = (Enemy) objects.get(i);
				if (e.isDead()) {
					if (e.canDespawn()) {
						objects.remove(e);
					}
				}
			}
		}
	}
}
