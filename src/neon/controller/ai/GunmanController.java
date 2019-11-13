package neon.controller.ai;

import java.util.Random;

import neon.entity.ai.enemy.Gunman;
import neon.entity.controllable.Player;
import neon.entity.projectile.Bullet;
import neon.graphics.Point;
import neon.level.LevelManager;
import neon.physics.CollisionDirection;
import neon.time.TimeInfo;

public class GunmanController implements AIController {
	
	private Gunman gunman;
	
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
	
	public GunmanController(Gunman gunman) {
		this.gunman = gunman;
		rand = new Random();
	}

	@Override
	public void control(Player player) {
		move(player);
		takeAim(player);
		shoot();
		updateInvulnerability();
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
		} else {
			gunman.getPhysics().setXVelocity(0);
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
