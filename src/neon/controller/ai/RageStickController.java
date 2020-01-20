package neon.controller.ai;

import neon.entity.ai.enemy.RageStick;
import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.physics.CollisionDirection;
import neon.time.TimeInfo;

public class RageStickController implements AIController {
	
	private RageStick rageStick;
	
	private int timer = 0;
	private final int COOLDOWN = 2000;
	private int dmgTimer = 0;
	private boolean isInvulnerable;
	private final int INVULNERABLE_TIME = 1000;
	
	private boolean jumping = false;
	
	public RageStickController(RageStick rageStick) {
		this.rageStick = rageStick;
	}

	@Override
	public void control(Player player) {
		timer += TimeInfo.getDelta();
		
		if (player.getX() + player.getWidth() / 2 > rageStick.getX() + rageStick.getWidth() / 2) {
			rageStick.setMirrored(false);
		} else {
			rageStick.setMirrored(true);
		}
		
		if (timer > COOLDOWN) {
			timer = 0;
			int attack = (int) (Math.round(Math.random()));
			if (attack == 1) {
				slam(player);
			} else {
				jump(player);
			}
		}
		
		if (jumping && rageStick.getPhysics().getYVelocity() == 0 && rageStick.getCollisionDirections().contains(CollisionDirection.DOWN)) {
			jumping = false;
			rageStick.getPhysics().setXVelocity(0);
			rageStick.getCombat().startAttack("stomp");
		}
		
		rageStick.getCombat().updateAttacks();
		updateInvulnerability();
		updateAnimationState();
	}
	
	private void updateAnimationState() {
		Animator anim = rageStick.getGraphics().getAnimator();
		if (!rageStick.getCombat().isAttacking() && !anim.getState().equals("idle")) {
			anim.setState("idle");
		}
	}
	
	private void slam(Player player) {
		rageStick.getCombat().startAttack("head_slam");
		rageStick.getGraphics().getAnimator().setState("head_slam");
	}
	
	private void jump(Player player) {
		float distance = (player.getX() + player.getWidth() / 2f) - (rageStick.getX() + rageStick.getWidth() / 2f);
		rageStick.getPhysics().setYVelocity(-2.5f);
		rageStick.getPhysics().setXVelocity(distance * 0.002f);
		jumping = true;
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
	public void takeDamage(float damage, CollisionDirection cd) {
		if (!isInvulnerable) {
			rageStick.setHealth(rageStick.getHealth() - damage);
			dmgTimer = 0;
			isInvulnerable = true;
		}
	}

	@Override
	public void death() {
	}

	@Override
	public boolean isInvulnerable() {
		return isInvulnerable;
	}
}
