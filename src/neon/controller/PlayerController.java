package neon.controller;

import org.newdawn.slick.Input;

import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.physics.Physics;

public class PlayerController implements Controller {
	
	private Player player;
	private Animator animator;
	private float runningSpeed = 0.4f;
	private float xAcc = 0.008f;
	private int direction = 0;
	
	public PlayerController(Player player) {
		this.player = player;
		this.animator = player.getGraphics().getAnimator();
	}

	@Override
	public void control(Input input, int delta) {
		animator.updateAnimations(delta);
		Physics ph = this.player.getPhysics();
		if (input.isKeyDown(Input.KEY_A)) {
			float vel = ph.getXVelocity() - xAcc * delta;
			if (vel * -1 > runningSpeed) {
				vel = runningSpeed * -1;
			}
			ph.setXVelocity(vel);
			if (direction == 0) {
				direction = 1;
				player.setMirrored(true);
			}
		}
		if (input.isKeyDown(Input.KEY_D)) {
			float vel = ph.getXVelocity() + xAcc * delta;
			if (vel > runningSpeed) {
				vel = runningSpeed;
			}
			ph.setXVelocity(vel);
			if (direction == 1) {
				direction = 0;
				player.setMirrored(false);
			}
		}
		if (!input.isKeyDown(Input.KEY_D) && !input.isKeyDown(Input.KEY_A)) {
			ph.setXVelocity(0);
		}
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			ph.setYVelocity(-2f);
		}
		
		if (Math.abs(ph.getXVelocity()) > 0 && !animator.getState().equals("running")) {
			animator.setState("running");
		} else if (ph.getXVelocity() == 0 && !animator.getState().equals("idle")) {
			animator.setState("idle");
		}
	}
}
