package neon.entity.weapon;

import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.physics.CollisionDirection;

public abstract class Weapon {
	
	protected Animator animator;
	
	public abstract void attack(Player player, CollisionDirection aimDirection);
	
	public Animator getAnimator() {
		return animator;
	}
	
	public void setAnimator(Animator animator) {
		this.animator = animator;
	}
}
