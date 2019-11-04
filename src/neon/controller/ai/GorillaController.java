package neon.controller.ai;

import neon.entity.ai.enemy.Gorilla;
import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.time.TimeInfo;

public class GorillaController implements AIController {
	
	private Gorilla gorilla;
	private Physics ph;
	private Animator anim;
	
	private CollisionDirection damageDirection;

	private float speed = 0.2f;
	private int timer = 0;
	private boolean mirrored = false;
	private boolean isDead = false;
	
	private final int INVULNERABLE_TIME = 500;
	private int dmgTimer = 0;
	private boolean isInvulnerable = false;

	public GorillaController(Gorilla gorilla) {
		this.gorilla = gorilla;
		this.ph = gorilla.getPhysics();
		//this.anim = gorilla.getGraphics().getAnimator();
		this.damageDirection = CollisionDirection.NONE;
	}

	@Override
	public void control(Player player) {
		if (!isDead) {
			if (!isInvulnerable) {
				ph.setXVelocity(0f);
			}
			
			updateInvulnerability();
		}
	}
	
	private void updateInvulnerability() {
		if (isInvulnerable) {
			dmgTimer += TimeInfo.getDelta();
			if (dmgTimer > INVULNERABLE_TIME) {
				isInvulnerable = false;
				dmgTimer = 0;
				timer = 3001;
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
