package neon.entity.weapon;

import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;

public abstract class Weapon {
	
	protected Animator animator;
	
	public abstract void attack(Player player);
	
	public Animator getAnimator() {
		return animator;
	}
	
	public void setAnimator(Animator animator) {
		this.animator = animator;
	}
}
