package neon.controller;

import org.newdawn.slick.Input;

import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class PlayerController implements Controller {

	private Player player;
	private Physics ph;
	private Animator animator;
	private float runningSpeed = 0.7f;
	private float xAcc = 0.008f;
	private int direction = 0; // 0 = right, 1 = left
	private CollisionDirection glideDirection;

	private int dashTime = 0;
	private final int DASH_DURATION = 150;

	private boolean isJumping = false;
	private boolean isDashing = false;
	private boolean isGrounded = false;
	private boolean isGliding = false;

	private boolean canJump = true;
	private boolean canGlideJump = false;
	private boolean canDash = true;
	private boolean canRun = true;
	private boolean canStop = true;

	public PlayerController(Player player) {
		this.player = player;
		this.animator = player.getGraphics().getAnimator();
		this.ph = player.getPhysics();
		this.glideDirection = CollisionDirection.NONE;
	}

	public void glide(CollisionDirection cd) {
		if (ph.getYVelocity() > 0) {
			ph.setYVelocity(0.4f);
			glideDirection = cd;
			isJumping = false;
			isGliding = true;
		}
	}

	@Override
	public void control(Input input, int delta) {
		animator.updateAnimations(delta);

		if (input.isKeyDown(Input.KEY_A)) {
			left(delta);
		}
		if (input.isKeyDown(Input.KEY_D)) {
			right(delta);
		}
		if (!input.isKeyDown(Input.KEY_D) && !input.isKeyDown(Input.KEY_A)) {
			stop(delta);
		}
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			jump();
		}
		if (input.isKeyPressed(Input.KEY_LSHIFT)) {
			dash();
		}
		
		updateAnimationState();
		updateActions(delta);
	}

	private void updateActions(int delta) {
		if (!isDashing && ph.getYVelocity() == 0) {
			isGrounded = true;
			isJumping = false;
			isGliding = false;
		}
		
		if (isDashing) {
			canGlideJump = false;
			canRun = false;
			canStop = false;
			canDash = false;
			canJump = false;
			if (dashTime > DASH_DURATION) {
				isDashing = false;
				dashTime = 0;
				if (direction == 0) {
					ph.setXVelocity(runningSpeed);
				} else if (direction == 1) {
					ph.setXVelocity(-runningSpeed);
				}
				canRun = true;
				canStop = true;
				canDash = true;
				canJump = true;
				canGlideJump = true;
			} else {
				dashTime += delta;
			}
		}
		
		if (isGrounded) {
			canGlideJump = false;
			canJump = true;
			canRun = true;
			canStop = true;
			canDash = true;
		}
		
		if (isJumping) {
			canGlideJump = false;
			canDash = true;
			canRun = true;
			canStop = true;
			canJump = false;
		}
		
		if (isGliding) {
			canGlideJump = true;
			canJump = false;
			canRun = true;
			canDash = false;
			canStop = true;
			isDashing = false;
			isGliding = false;
			isJumping = true;
		}
	}

	private void updateAnimationState() {
		if (isGrounded) {
			if (Math.abs(ph.getXVelocity()) > 0 && !animator.getState().equals("running")) {
				animator.setState("running");
			} else if (ph.getXVelocity() == 0 && !animator.getState().equals("idle")) {
				animator.setState("idle");
			}
		} else if (isJumping && !animator.getState().equals("jumping")) {
			animator.setState("jumping");
		} else if (isDashing && !animator.getState().equals("dashing")) {
			animator.setState("dashing");
		} else if (isGliding && !animator.getState().equals("gliding")) {
			animator.setState("gliding");
		}
	}

	private void dash() {
		if (canDash) {
			dashTime = 0;
			isJumping = false;
			isGliding = false;
			isGrounded = false;
			isDashing = true;
			if (direction == 0) {
				ph.setXVelocity(2f);
			} else if (direction == 1) {
				ph.setXVelocity(-2f);
			}
		}
	}

	private void jump() {
		if (canJump) {
			isJumping = true;
			isGrounded = false;
			isDashing = false;
			isGliding = false;
			ph.setYVelocity(-2f);
		} else if (canGlideJump) {
			isGliding = false;
			isJumping = true;
			isGrounded = false;
			isDashing = false;
			ph.setYVelocity(-2f);
			if (glideDirection == CollisionDirection.RIGHT) {
				ph.setXVelocity(-1.5f);
			} else if (glideDirection == CollisionDirection.LEFT) {
				ph.setXVelocity(1.5f);
			}
		}
	}

	private void stop(int delta) {
		if (canStop) {
			if (ph.getXVelocity() < 0) {
				float vel = ph.getXVelocity() + xAcc * delta;
				if (vel > 0) {
					vel = 0;
				}
				ph.setXVelocity(vel);
			} else if (ph.getXVelocity() > 0) {
				float vel = ph.getXVelocity() - xAcc * delta;
				if (vel < 0) {
					vel = 0;
				}
				ph.setXVelocity(vel);
			}
		}
	}

	private void right(int delta) {
		if (canRun) {
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
	}

	private void left(int delta) {
		if (canRun) {
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
	}
}
