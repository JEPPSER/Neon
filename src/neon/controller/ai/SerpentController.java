package neon.controller.ai;

import neon.combat.Combat;
import neon.entity.ai.enemy.Serpent;
import neon.entity.collectable.Portal;
import neon.entity.controllable.Player;
import neon.entity.projectile.Bullet;
import neon.entity.projectile.SerpentVenom;
import neon.graphics.Point;
import neon.level.LevelManager;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.time.TimeInfo;

public class SerpentController implements AIController {
	
	private Serpent serpent;
	private Physics ph;
	private Combat combat;
	
	private int time = 0;
	private final int COOLDOWN = 2500;
	private final int RAPID_FIRE_DELTA = 500;
	private final int RAPID_FIRE_SHOTS = 15;
	private int shotsLeft = 10;
	private boolean isFiring = false;
	
	private final int INVULNERABLE_TIME = 1500;
	private int dmgTimer = 0;
	private boolean isInvulnerable = false;
	
	public SerpentController(Serpent serpent) {
		this.serpent = serpent;
		this.ph = serpent.getPhysics();
		this.combat = serpent.getCombat();
	}

	@Override
	public void control(Player player) {
		time += TimeInfo.getDelta();
		
		if (!combat.isAttacking() && !isFiring) {
			if (player.getX() + player.getWidth() / 2 < serpent.getX() + serpent.getWidth() / 2) {
				serpent.setMirrored(true);
			} else {
				serpent.setMirrored(false);
			}
		}
		
		if (!isFiring) {
			if (Math.round(Math.random()) == 0) {
				bite(player);
			} else {
				rapidFire(player);
			}
		} else {
			rapidFire(player);
		}
		
		combat.updateAttacks();
		updateInvulnerability();
	}
	
	private void updateInvulnerability() {
		if (isInvulnerable) {
			dmgTimer += TimeInfo.getDelta();
			if (dmgTimer > INVULNERABLE_TIME) {
				isInvulnerable = false;
				dmgTimer = 0;
			}
		}
	}
	
	private void bite(Player player) {
		if (time >= COOLDOWN) {
			combat.startAttack("bite");
			time = 0;
		}
	}
	
	private void rapidFire(Player player) {
		if (time >= COOLDOWN) {
			isFiring = true;
			if (shotsLeft > 0 && time >= COOLDOWN + RAPID_FIRE_DELTA) {
				Point p1 = new Point(serpent.getX(), serpent.getY());
				Point p2 = new Point(player.getX(), player.getY());
				float angle = (float) (p1.angleTo(p2) + (Math.random() / 2 - 0.25f));
				SerpentVenom b = new SerpentVenom(p1.getX(), p1.getY(), angle, serpent.getName());
				LevelManager.addEntity(b);
				shotsLeft--;
				time = COOLDOWN;
			} else if (shotsLeft <= 0) {
				isFiring = false;
				time = 0;
				shotsLeft = RAPID_FIRE_SHOTS;
			}
		}
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {
		if (!isInvulnerable) {
			serpent.setHealth(serpent.getHealth() - damage);
			dmgTimer = 0;
			isInvulnerable = true;
		}
	}

	@Override
	public void death() {
		Portal portal = new Portal(serpent.getX(), serpent.getY());
		LevelManager.addEntity(portal);
	}

	@Override
	public boolean isInvulnerable() {
		return isInvulnerable;
	}

}
