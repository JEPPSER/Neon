package neon.entity.weapon;

import org.newdawn.slick.Image;

import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.physics.CollisionDirection;

public abstract class Weapon {
	
	protected Animator animator;
	protected Image img;
	
	public abstract void attack(Player player, CollisionDirection aimDirection);
	
	public Animator getAnimator() {
		return animator;
	}
	
	public void setAnimator(Animator animator) {
		this.animator = animator;
	}
	
	public Image getImage() {
		return img;
	}
}
