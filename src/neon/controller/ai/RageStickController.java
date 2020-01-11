package neon.controller.ai;

import neon.entity.ai.enemy.RageStick;
import neon.entity.controllable.Player;
import neon.physics.CollisionDirection;
import neon.time.TimeInfo;

public class RageStickController implements AIController {
	
	private RageStick rageStick;
	
	private int timer = 0;
	private final int COOLDOWN = 3000;
	
	private boolean jumping = false;
	
	public RageStickController(RageStick rageStick) {
		this.rageStick = rageStick;
	}

	@Override
	public void control(Player player) {
		timer += TimeInfo.getDelta();
		if (timer > 3000) {
			timer = 0;
			jump(player);
		}
		
		if (jumping && rageStick.getPhysics().getYVelocity() == 0 && rageStick.getCollisionDirections().contains(CollisionDirection.DOWN)) {
			jumping = false;
			rageStick.getCombat().startAttack("stomp");
		}
		
		rageStick.getCombat().updateAttacks();
	}
	
	private void jump(Player player) {
		rageStick.getPhysics().setYVelocity(-1.3f);
		jumping = true;
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {
	}

	@Override
	public void death() {
	}

	@Override
	public boolean isInvulnerable() {
		return false;
	}
}
