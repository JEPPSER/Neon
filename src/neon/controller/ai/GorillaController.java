package neon.controller.ai;

import neon.combat.Combat;
import neon.entity.ai.enemy.Gorilla;
import neon.entity.collectable.Heart;
import neon.entity.collectable.Portal;
import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.level.LevelManager;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.time.TimeInfo;

public class GorillaController implements AIController {

	private Gorilla gorilla;
	private Physics ph;
	private Animator anim;
	private Combat combat;

	private CollisionDirection damageDirection;

	private float speed = 0.2f;
	private int movementTimer = 0;
	private int cooldownTimer = 0;
	private final int COOLDOWN = 2000;
	private final int MOVEMENT_INTERVAL = 2000;
	private final float MINIMUM_DISTANCE = 200;
	private boolean isDead = false;

	private final int INVULNERABLE_TIME = 1000;
	private int dmgTimer = 0;
	private boolean isInvulnerable = false;

	public GorillaController(Gorilla gorilla) {
		this.gorilla = gorilla;
		this.ph = gorilla.getPhysics();
		this.combat = gorilla.getCombat();
		this.anim = gorilla.getGraphics().getAnimator();
		this.damageDirection = CollisionDirection.NONE;
	}

	@Override
	public void control(Player player) {
		if (!isDead) {
			updateMovement(player);
			updateInvulnerability();
			updateCombat();
			updateAnimations();
		}
	}
	
	private void updateAnimations() {
		if (combat.isAttacking()) {
			if (!anim.getState().equals("ground_smash")) {
				anim.setState("ground_smash");
			}
		} else {
			if (ph.getXVelocity() != 0 && !anim.getState().equals("walk")) {
				anim.setState("walk");
			} else if (ph.getXVelocity() == 0 && !anim.getState().equals("idle")) {
				anim.setState("idle");
			}
		}
	}

	private void updateCombat() {
		combat.updateAttacks();
		if (cooldownTimer < COOLDOWN) {
			cooldownTimer += TimeInfo.getDelta();
		}
	}

	private void updateMovement(Player player) {
		movementTimer += TimeInfo.getDelta();
		if (movementTimer > MOVEMENT_INTERVAL * 2) {
			movementTimer = 0;
		}

		float playerX = player.getX() + player.getWidth() / 2;
		float gorX = gorilla.getX() + gorilla.getWidth() / 2;
		float playerY = player.getY() + player.getHeight() / 2;
		float gorY = gorilla.getY() + gorilla.getHeight() / 2;
		if (!combat.isAttacking()) {
			if (gorX > playerX) {
				gorilla.setMirrored(true);
			} else {
				gorilla.setMirrored(false);
			}
		}

		if (Math.abs(gorX - playerX) < MINIMUM_DISTANCE && cooldownTimer >= COOLDOWN
				&& Math.abs(gorY - playerY) < MINIMUM_DISTANCE) {
			combat.startAttack("ground_smash");
			cooldownTimer = 0;
		}

		if (canMove()) {
			if (Math.abs(gorX - playerX) > MINIMUM_DISTANCE) {
				if (gorX > playerX) {
					ph.setXVelocity(-speed);
				} else {
					ph.setXVelocity(speed);
				}
			} else {
				ph.setXVelocity(0);
			}
		} else {
			ph.setXVelocity(0);
		}
	}

	private boolean canMove() {
		return (movementTimer < MOVEMENT_INTERVAL);
	}

	private void updateInvulnerability() {
		if (isInvulnerable) {
			dmgTimer += TimeInfo.getDelta();
			if (dmgTimer > INVULNERABLE_TIME) {
				isInvulnerable = false;
				dmgTimer = 0;
			}
			if (dmgTimer > INVULNERABLE_TIME / 2) {
				ph.setXVelocity(0);
			} else {
				knockBack();
			}
		}
	}

	private void knockBack() {
		if (damageDirection == CollisionDirection.RIGHT) {
			ph.setXVelocity(-0.3f);
		} else if (damageDirection == CollisionDirection.LEFT) {
			ph.setXVelocity(0.3f);
		}
	}

	@Override
	public void death() {
		isDead = true;
		ph.setXVelocity(0);
		Portal portal = new Portal(gorilla.getX() + 75, 800);
		LevelManager.addEntity(portal);
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {
		if (!isInvulnerable) {
			damageDirection = cd;
			gorilla.setHealth(gorilla.getHealth() - damage);
			dmgTimer = 0;
			isInvulnerable = true;
		}
	}

	@Override
	public boolean isInvulnerable() {
		return isInvulnerable;
	}
}
