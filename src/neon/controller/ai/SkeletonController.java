package neon.controller.ai;

import java.util.Random;

import neon.entity.ai.enemy.Skeleton;
import neon.entity.collectable.Heart;
import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.level.LevelManager;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.time.TimeInfo;

public class SkeletonController implements AIController {
	
	private Skeleton skeleton;
	private Physics ph;
	private Animator anim;
	
	private CollisionDirection damageDirection;

	private float speed = 0.3f;
	private float dmg = 3.0f;
	private int timer = 0;
	private boolean mirrored = false;
	private boolean isDead = false;
	
	private final int INVULNERABLE_TIME = 1000;
	private int dirTime;
	private Random rand;
	private int dmgTimer = 0;
	private boolean isInvulnerable = false;
	
	public SkeletonController(Skeleton skeleton) {
		this.skeleton = skeleton;
		this.ph = skeleton.getPhysics();
		this.anim = skeleton.getGraphics().getAnimator();
		damageDirection = CollisionDirection.NONE;
		ph.setXVelocity(speed);
		anim.setState("moving");
		rand = new Random();
		dirTime = rand.nextInt(2000);
	}

	@Override
	public void control(Player player) {
		if (!isDead) {
			if (!isInvulnerable) {
				backAndForth(player);
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
	
	private void backAndForth(Player player) {
		timer += TimeInfo.getDelta();
		
		if (timer > dirTime) {
			dirTime = rand.nextInt(2000);
			timer = 0;
			if (mirrored) {
				mirrored = false;
				ph.setXVelocity(speed);
				skeleton.setMirrored(mirrored);
			} else {
				mirrored = true;
				ph.setXVelocity(-speed);
				skeleton.setMirrored(mirrored);
			}
		}
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {
		if (!isInvulnerable) {
			damageDirection = cd;
			skeleton.setHealth(skeleton.getHealth() - damage);
			dmgTimer = 0;
			isInvulnerable = true;
		}
	}

	public void hurtPlayer(Player player) {
		if (!isInvulnerable) {
			player.takeDamage(dmg);
		}
	}

	@Override
	public void death() {
		isDead = true;
		ph.setXVelocity(0);
		Heart heart = new Heart(skeleton.getX(), skeleton.getY() - 50);
		LevelManager.addEntity(heart);
		heart.getPhysics().setYVelocity(-1.5f);
		if (damageDirection == CollisionDirection.RIGHT) {
			heart.getPhysics().setXVelocity(-0.2f);
		} else if (damageDirection == CollisionDirection.LEFT) {
			heart.getPhysics().setXVelocity(0.2f);
		}
	}

	@Override
	public boolean isInvulnerable() {
		return isInvulnerable;
	}
}
