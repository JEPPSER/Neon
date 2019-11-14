package neon.controller.ai;

import java.util.Random;

import neon.entity.ai.enemy.Gunman;
import neon.entity.collectable.Heart;
import neon.entity.controllable.Player;
import neon.entity.projectile.Bullet;
import neon.graphics.Point;
import neon.graphics.animation.Animator;
import neon.level.LevelManager;
import neon.physics.CollisionDirection;
import neon.time.TimeInfo;

public class GunmanController implements AIController {
	
	private Gunman gunman;
	private Animator anim;
	
	private final int COOLDOWN_TIME = 2000;
	private int cooldown = 0;
	
	private final int AIM_TIME = 1000;
	private int aim = 0;
	private boolean isAiming = false;
	private float aimAngle;
	private int numberOfShots = 1;
	private Random rand;
	
	private boolean isMoving = false;
	private float movingSpeed = 0.2f;
	private float range = 500;
	
	private final int INVULNERABLE_TIME = 1000;
	private int dmgTimer = 0;
	private boolean isInvulnerable = false;
	private boolean isDead = false;
	
	public GunmanController(Gunman gunman) {
		this.gunman = gunman;
		rand = new Random();
		anim = gunman.getGraphics().getAnimator();
	}

	@Override
	public void control(Player player) {
		updateAnimations();
		if (isDead) {
			return;
		}
		move(player);
		takeAim(player);
		shoot();
		updateInvulnerability();
	}
	
	private void updateAnimations() {
		if (isDead) {
			if (!anim.getState().equals("death")) {
				anim.setState("death");
			}
		} else if (isMoving && !anim.getState().equals("moving")) {
			anim.setState("moving");
		} else if (!isMoving && !isAiming && !anim.getState().equals("idle")) {
			anim.setState("idle");
		} else if (isAiming && !anim.getState().equals("aiming")) {
			anim.setState("aiming");
		}
	}
	
	private void move(Player player) {
		if (player.getX() < gunman.getX()) {
			gunman.setMirrored(true);
		} else {
			gunman.setMirrored(false);
		}
		if (Math.abs(player.getX() - gunman.getX()) > range && !isAiming) {
			if (gunman.isMirrored()) {
				gunman.getPhysics().setXVelocity(-movingSpeed);
			} else {
				gunman.getPhysics().setXVelocity(movingSpeed);
			}
			isMoving = true;
		} else {
			gunman.getPhysics().setXVelocity(0);
			isMoving = false;
		}
	}
	
	private void shoot() {
		if (isAiming) {
			aim += TimeInfo.getDelta();
			if (aim > AIM_TIME) {
				if (numberOfShots > 0) {
					aim = AIM_TIME - 200;
					numberOfShots--;
					Bullet b = new Bullet(gunman.getX(), gunman.getY(), aimAngle, gunman.getName());
					LevelManager.addEntity(b);
				} else {
					isAiming = false;
					aim = 0;
					cooldown = 0;
				}
			}
		}
	}
	
	private void takeAim(Player player) {
		cooldown += TimeInfo.getDelta();
		if (cooldown > COOLDOWN_TIME && !isMoving) {
			if (!isAiming) {
				numberOfShots = rand.nextInt(3) + 1;
			}
			isAiming = true;
			Point one = new Point(gunman.getX(), gunman.getY());
			Point two = new Point(player.getX(), player.getY());
			aimAngle = one.angleTo(two);
		}
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {
		if (!isInvulnerable) {
			gunman.setHealth(gunman.getHealth() - damage);
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
			}
		}
	}

	@Override
	public void death() {
		isDead = true;
		gunman.getPhysics().setXVelocity(0);
	}

	@Override
	public boolean isInvulnerable() {
		return isInvulnerable;
	}
	
	public boolean isAiming() {
		return isAiming;
	}
	
	public float getAimAngle() {
		return aimAngle;
	}
}
