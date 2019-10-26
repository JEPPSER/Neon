package neon.controller;

import org.newdawn.slick.Color;
import org.newdawn.slick.Input;

import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.state.State;
import neon.state.StateManager;
import neon.time.TimeInfo;

public class PlayerController implements Controller {

	private StateManager sm;
	private Player player;
	private Physics ph;
	private Animator animator;
	
	private float runningSpeed = 0.7f;
	private float xAcc = 0.008f;
	private int direction = 0; // 0 = right, 1 = left
	private CollisionDirection glideDirection;
	
	private final int INVULNERABLE_TIME = 1000;
	private int dmgTimer = 0;
	private boolean isInvulnerable = false;

	private int dashTime = 0;
	private final int DASH_DURATION = 150;
	private boolean canDash = true;

	public PlayerController(Player player) {
		this.player = player;
		this.animator = player.getGraphics().getAnimator();
		this.ph = player.getPhysics();
		this.glideDirection = CollisionDirection.NONE;
		initStateManager();
	}

	public void glide(CollisionDirection cd) {
		if (ph.getYVelocity() > 0 && sm.canActivateState("gliding")) {
			sm.activateState("gliding");
			ph.setYVelocity(0.4f);
			glideDirection = cd;
		}
	}
	
	public void takeDamage(float damage) {
		if (!isInvulnerable) {
			player.setHealth(player.getHealth() - damage);
			dmgTimer = 0;
			isInvulnerable = true;
			player.getGraphics().setColor(Color.red);
		}
	}

	@Override
	public void control(Input input) {
		if (input.isKeyDown(Input.KEY_A)) {
			left();
		}
		if (input.isKeyDown(Input.KEY_D)) {
			right();
		}
		if (!input.isKeyDown(Input.KEY_D) && !input.isKeyDown(Input.KEY_A)) {
			stop();
		}
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			jump();
		}
		if (input.isKeyPressed(Input.KEY_LSHIFT)) {
			dash();
		}

		updateAnimationState();
		updateActions();
		updateInvulnerability();
	}
	
	private void updateInvulnerability() {
		if (isInvulnerable) {
			dmgTimer += TimeInfo.getDelta();
			if (dmgTimer > INVULNERABLE_TIME) {
				isInvulnerable = false;
				dmgTimer = 0;
				player.getGraphics().setColor(player.getColor());
			}
		}
	}

	private void updateActions() {
		if (ph.getYVelocity() == 0) { // Detect idle or running
			if (ph.getXVelocity() == 0) {
				sm.activateState("idle");
			} else if (!sm.getCurrentState().equals("dashing")) {
				sm.activateState("running");
			}
			canDash = true;
		} else if (!sm.getCurrentState().equals("dashing")) {
			sm.activateState("jumping");
		} else {
			canDash = false;
		}

		// Detect dashing stopped
		if (sm.getCurrentState().equals("dashing")) {
			if (dashTime > DASH_DURATION) {
				sm.activateState("running");
				dashTime = 0;
				if (direction == 0) {
					ph.setXVelocity(runningSpeed);
				} else if (direction == 1) {
					ph.setXVelocity(-runningSpeed);
				}
			} else {
				dashTime += TimeInfo.getDelta();
			}
		}
	}

	private void updateAnimationState() {
		if (!animator.getState().equals(sm.getCurrentState())) {
			animator.setState(sm.getCurrentState());
		}
	}

	private void dash() {
		if (sm.canActivateState("dashing") && canDash) {
			sm.activateState("dashing");
			dashTime = 0;
			if (direction == 0) {
				ph.setXVelocity(2f);
			} else if (direction == 1) {
				ph.setXVelocity(-2f);
			}
		}
	}

	private void jump() {
		if (!sm.getCurrentState().equals("gliding") && sm.canActivateState("jumping")) {
			sm.activateState("jumping");
			ph.setYVelocity(-2f);
		} else if (sm.canActivateState("jumping")) {
			sm.activateState("jumping");
			ph.setYVelocity(-2f);
			if (glideDirection == CollisionDirection.RIGHT) {
				ph.setXVelocity(-1.5f);
			} else if (glideDirection == CollisionDirection.LEFT) {
				ph.setXVelocity(1.5f);
			}
		}
	}

	private void stop() {
		if (!sm.getCurrentState().equals("dashing")) {
			if (ph.getXVelocity() < 0) {
				float vel = ph.getXVelocity() + xAcc * TimeInfo.getDelta();
				if (vel > 0) {
					vel = 0;
				}
				ph.setXVelocity(vel);
			} else if (ph.getXVelocity() > 0) {
				float vel = ph.getXVelocity() - xAcc * TimeInfo.getDelta();
				if (vel < 0) {
					vel = 0;
				}
				ph.setXVelocity(vel);
			}
		}
	}

	private void right() {
		if (sm.canActivateState("running")) {
			sm.activateState("running");
		}
		if (!sm.getCurrentState().equals("dashing")) {
			float vel = ph.getXVelocity() + xAcc * TimeInfo.getDelta();
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

	private void left() {
		if (sm.canActivateState("running")) {
			sm.activateState("running");
		}
		if (!sm.getCurrentState().equals("dashing")) {
			float vel = ph.getXVelocity() - xAcc * TimeInfo.getDelta();
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

	private void initStateManager() {
		this.sm = new StateManager();

		// Idle
		State idle = new State("idle");
		idle.getToStates().add("running");
		idle.getToStates().add("dashing");
		idle.getToStates().add("jumping");
		//idle.getToStates().add("idle");

		// Running
		State running = new State("running");
		running.getToStates().add("jumping");
		running.getToStates().add("dashing");
		running.getToStates().add("idle");
		//running.getToStates().add("running");

		// Jumping
		State jumping = new State("jumping");
		jumping.getToStates().add("idle");
		//jumping.getToStates().add("running");
		jumping.getToStates().add("gliding");
		jumping.getToStates().add("dashing");

		// Dashing
		State dashing = new State("dashing");
		dashing.getToStates().add("idle");
		//dashing.getToStates().add("running");
		dashing.getToStates().add("gliding");

		// Gliding
		State gliding = new State("gliding");
		gliding.getToStates().add("jumping");
		//gliding.getToStates().add("running");
		gliding.getToStates().add("idle");
		gliding.getToStates().add("gliding");

		sm.addState(idle);
		sm.addState(running);
		sm.addState(jumping);
		sm.addState(dashing);
		sm.addState(gliding);
		sm.setCurrentState("idle");
	}
}
