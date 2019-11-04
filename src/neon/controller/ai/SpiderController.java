package neon.controller.ai;

import neon.entity.ai.enemy.Spider;
import neon.entity.collectable.Heart;
import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.level.LevelManager;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.time.TimeInfo;

public class SpiderController implements AIController {

	private Spider spider;
	private Physics ph;
	private Animator anim;
	
	private CollisionDirection damageDirection;

	private float speed = 0.2f;
	private float dmg = 3.0f;
	private int timer = 0;
	private boolean mirrored = false;
	private boolean isDead = false;
	
	private final int INVULNERABLE_TIME = 500;
	private int dmgTimer = 0;
	private boolean isInvulnerable = false;

	public SpiderController(Spider spider) {
		this.spider = spider;
		this.ph = spider.getPhysics();
		this.anim = spider.getGraphics().getAnimator();
		damageDirection = CollisionDirection.NONE;
		ph.setXVelocity(speed);
		anim.setState("moving");
	}

	@Override
	public void control(Player player) {
		if (!isDead) {
			if (!isInvulnerable) {
				backAndForth(player);
				//followPlayer(player);
			}
			
			updateInvulnerability();
		}
	}
	
	public void takeDamage(float damage, CollisionDirection cd) {
		if (!isInvulnerable) {
			damageDirection = cd;
			spider.setHealth(spider.getHealth() - damage);
			dmgTimer = 0;
			isInvulnerable = true;
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
	
	private void backAndForth(Player player) {
		timer += TimeInfo.getDelta();

		if (timer > 3000) {
			timer = 0;
			if (mirrored) {
				mirrored = false;
				ph.setXVelocity(speed);
				spider.setMirrored(mirrored);
			} else {
				mirrored = true;
				ph.setXVelocity(-speed);
				spider.setMirrored(mirrored);
			}
		}
	}

	private void followPlayer(Player player) {
		if (player.getX() < spider.getX() - 2) {
			if (!anim.getState().equals("moving")) {
				anim.setState("moving");
			}
			ph.setXVelocity(-speed);
			spider.setMirrored(true);
		} else if (player.getX() > spider.getX() + 2) {
			if (!anim.getState().equals("moving")) {
				anim.setState("moving");
			}
			ph.setXVelocity(speed);
			spider.setMirrored(false);
		} else {
			if (!anim.getState().equals("idle")) {
				anim.setState("idle");
			}
			ph.setXVelocity(0);
		}
	}
	
	public boolean isInvulnerable() {
		return isInvulnerable;
	}

	public void hurtPlayer(Player player) {
		player.takeDamage(dmg);
	}

	@Override
	public void death() {
		isDead = true;
		ph.setXVelocity(0);
		Heart heart = new Heart(spider.getX(), spider.getY() - 50);
		LevelManager.addEntity(heart);
		heart.getPhysics().setYVelocity(-1.5f);
		if (damageDirection == CollisionDirection.RIGHT) {
			heart.getPhysics().setXVelocity(-0.2f);
		} else if (damageDirection == CollisionDirection.LEFT) {
			heart.getPhysics().setXVelocity(0.2f);
		}
	}
}
