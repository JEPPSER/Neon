package neon.controller.ai;

import neon.entity.ai.enemy.RageStick;
import neon.entity.controllable.Player;
import neon.physics.CollisionDirection;

public class RageStickController implements AIController {
	
	private RageStick rageStick;
	
	public RageStickController(RageStick rageStick) {
		this.rageStick = rageStick;
	}

	@Override
	public void control(Player player) {
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
