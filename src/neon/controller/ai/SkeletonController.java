package neon.controller.ai;

import neon.entity.ai.enemy.Skeleton;
import neon.entity.controllable.Player;
import neon.physics.CollisionDirection;

public class SkeletonController implements AIController {
	
	private Skeleton skeleton;
	
	public SkeletonController(Skeleton skeleton) {
		this.skeleton = skeleton;
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
