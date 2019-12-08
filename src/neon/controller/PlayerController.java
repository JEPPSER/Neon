package neon.controller;

import java.util.ArrayList;

import org.newdawn.slick.Input;

import neon.combat.Combat;
import neon.entity.Entity;
import neon.entity.area.Trigger;
import neon.entity.controllable.Player;
import neon.entity.terrain.movable.MovableTerrain;
import neon.graphics.animation.Animator;
import neon.level.LevelManager;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.settings.InputSettings;
import neon.state.State;
import neon.state.StateManager;
import neon.time.TimeInfo;

public class PlayerController implements Controller {

	private StateManager sm;
	private Player player;
	private Physics ph;
	private Animator animator;
	private Combat combat;

	private float runningSpeed = 0.7f;
	private float xAcc = 0.008f;
	private int direction = 0; // 0 = right, 1 = left
	private CollisionDirection glideDirection;

	private final int INVULNERABLE_TIME = 1000;
	private final int COOLDOWN_TIME = 500;
	private int cooldown = 500;
	private int dmgTimer = 0;
	private boolean isInvulnerable = false;

	private int deathTimer = 0;
	private final int DEATH_TIME = 1000;

	private int dashTime = 0;
	private final int DASH_DURATION = 150;
	private boolean canDash = true;
	private boolean airJump = false;

	public PlayerController(Player player) {
		this.player = player;
		this.animator = player.getGraphics().getAnimator();
		this.ph = player.getPhysics();
		this.combat = player.getCombat();
		this.glideDirection = CollisionDirection.NONE;
		initStateManager();
	}

	@Override
	public void control(Input input) {
		if (sm.getCurrentState().equals("portal")) {
			updatePortal();
			updateAnimationState();
			return;
		}

		if (player.getHealth() <= 0) {
			death();
			updateAnimationState();
			input.clearKeyPressedRecord();
			input.clearControlPressedRecord();
			return;
		}

		if (sm.getCurrentState().equals("spawn")) {
			spawn();
			updateAnimationState();
			input.clearKeyPressedRecord();
			input.clearControlPressedRecord();
			return;
		}
		
		if (input.isKeyDown(InputSettings.getKeyboardBinds().get("left")) || isButtonDown(input, "left")) {
			left();
		}
		if (input.isKeyDown(InputSettings.getKeyboardBinds().get("right")) || isButtonDown(input, "right")) {
			right();
		}
		if (!input.isKeyDown(InputSettings.getKeyboardBinds().get("left")) && !input.isKeyDown(InputSettings.getKeyboardBinds().get("right"))
				&& !isButtonDown(input, "left") && !isButtonDown(input, "right")) {
			stop();
		}
		if (input.isKeyPressed(InputSettings.getKeyboardBinds().get("jump")) || isButtonPressed(input, "jump")) {
			jump();
		}
		if (input.isKeyPressed(InputSettings.getKeyboardBinds().get("dash")) || isButtonPressed(input, "dash")) {
			dash();
		}
		if (input.isKeyPressed(InputSettings.getKeyboardBinds().get("punch")) || isButtonPressed(input, "punch")) {
			punch();
		}

		updateAnimationState();
		updateActions();
		updateInvulnerability();
		updateCombat();
	}
	
	public void activateAirJump() {
		this.airJump = true;
		this.canDash = true;
		this.dashTime = 0;
	}
	
	public void setState(String state) {
		sm.activateState(state);
	}

	private boolean isButtonPressed(Input input, String action) {
		int button = InputSettings.getControllerBinds().get(action);
		for (int i = 0; i < input.getControllerCount(); i++) {
			if (input.isControlPressed(button, i)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isButtonDown(Input input, String action) {
		int button = InputSettings.getControllerBinds().get(action);
		for (int i = 0; i < input.getControllerCount(); i++) {
			if (button >= 4 && input.isButtonPressed(button - 4, i)) {
				return true;
			} else if (button < 4) {
				if (action.equals("left")) {
					if (input.isControllerLeft(i)) {
						return true;
					}
				} else if (action.equals("right")) {
					if (input.isControllerRight(i)) {
						return true;
					}
				} else if (action.equals("up")) {
					if (input.isControllerUp(i)) {
						return true;
					}
				} else if (action.equals("down")) {
					if (input.isControllerDown(i)) {
						return true;
					}
				}
			}
		}
		return false;
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
			if (player.getHealth() < 0) {
				player.setHealth(0);
			}
			dmgTimer = 0;
			isInvulnerable = true;
		}
	}

	private void death() {
		deathTimer += TimeInfo.getDelta();
		if (!sm.getCurrentState().equals("death")) {
			sm.activateState("death");
			player.getCollision().setMovable(false);
			if (combat.isAttacking()) {
				combat.getCurrentAttack().cancelAttack();
			}
		}
		if (deathTimer >= DEATH_TIME) {
			deathTimer = 0;
			resetLevel();
		}
	}
	
	public void resetLevel() {
		if (LevelManager.getCheckpoint() != null) {
			LevelManager.resetFromCheckpoint();
		} else {
			sm.activateState("spawn");
			isInvulnerable = false;
			player.setHealth(player.getMaxHealth());
			player.setX(LevelManager.getSpawnPoint().getX());
			player.setY(LevelManager.getSpawnPoint().getY());
		}
		ArrayList<Entity> objects = LevelManager.getLevel().getObjects();
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i) instanceof MovableTerrain) {
				((MovableTerrain) objects.get(i)).reset();
			} else if (objects.get(i) instanceof Trigger) {
				((Trigger) objects.get(i)).reset();
			}
		}
	}

	private void spawn() {
		deathTimer += TimeInfo.getDelta();
		player.getCollision().setMovable(false);
		if (deathTimer >= DEATH_TIME) {
			deathTimer = 0;
			sm.activateState("idle");
			player.getCollision().setMovable(true);
		}
	}

	public void portal() {
		sm.activateState("portal");
		player.getCollision().setMovable(false);
	}

	private void updatePortal() {
		deathTimer += TimeInfo.getDelta();
		if (deathTimer >= DEATH_TIME) {
			deathTimer = 0;
			sm.activateState("idle");
			player.getCollision().setMovable(true);
			LevelManager.nextLevel();
		}
	}

	private void updateCombat() {
		combat.updateAttacks();
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

	private void updateActions() {
		if (combat.isAttacking()) {
			return;
		}
		
		if (cooldown <= COOLDOWN_TIME) {
			cooldown += TimeInfo.getDelta();
		}

		if (player.getCollisionDirection() == CollisionDirection.DOWN || ph.getYVelocity() == 0) { // Detect idle or running
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

	private void punch() {
		if (sm.canActivateState("punching") && !combat.isAttacking() && cooldown > COOLDOWN_TIME) {
			if (direction == 0) {
				ph.setXVelocity(0.5f);
			} else if (direction == 1) {
				ph.setXVelocity(-0.5f);
			}
			combat.startAttack("punch");
			sm.activateState("punching");
			cooldown = 0;
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
		if (!sm.getCurrentState().equals("gliding") && sm.canActivateState("jumping") ||
				!sm.getCurrentState().equals("gliding") && airJump) {
			sm.activateState("jumping");
			ph.setYVelocity(-2f);
			airJump = false;
			canDash = true;
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
		if (combat.isAttacking()) {
			ph.setXVelocity(0);
			return;
		}
		if (sm.canActivateState("running")) {
			sm.activateState("running");
		}
		if (!sm.getCurrentState().equals("dashing")) {
			float vel = ph.getXVelocity() + xAcc * TimeInfo.getDelta();
			if (sm.getCurrentState().equals("jumping")) {
				if (ph.getXVelocity() >= runningSpeed) {
					vel = ph.getXVelocity();
				}
			} else if (vel > runningSpeed) {
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
		if (combat.isAttacking()) {
			ph.setXVelocity(0);
			return;
		}
		if (sm.canActivateState("running")) {
			sm.activateState("running");
		}
		if (!sm.getCurrentState().equals("dashing")) {
			float vel = ph.getXVelocity() - xAcc * TimeInfo.getDelta();
			if (sm.getCurrentState().equals("jumping")) {
				if (ph.getXVelocity() <= -runningSpeed) {
					vel = ph.getXVelocity();
				}
			} else if (vel * -1 > runningSpeed) {
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
		State idle = new State("idle", true);
		idle.getToStates().add("running");
		idle.getToStates().add("dashing");
		idle.getToStates().add("jumping");
		idle.getToStates().add("punching");
		idle.getToStates().add("death");
		idle.getToStates().add("portal");

		// Running
		State running = new State("running", true);
		running.getToStates().add("jumping");
		running.getToStates().add("dashing");
		running.getToStates().add("idle");
		running.getToStates().add("punching");
		running.getToStates().add("death");
		running.getToStates().add("portal");

		// Jumping
		State jumping = new State("jumping", true);
		jumping.getToStates().add("idle");
		jumping.getToStates().add("gliding");
		jumping.getToStates().add("dashing");
		jumping.getToStates().add("punching");
		jumping.getToStates().add("death");
		jumping.getToStates().add("portal");

		// Dashing
		State dashing = new State("dashing", false);
		dashing.getToStates().add("idle");
		dashing.getToStates().add("gliding");
		dashing.getToStates().add("punching");
		dashing.getToStates().add("death");
		dashing.getToStates().add("portal");

		// Gliding
		State gliding = new State("gliding", true);
		gliding.getToStates().add("jumping");
		gliding.getToStates().add("idle");
		gliding.getToStates().add("gliding");
		gliding.getToStates().add("death");
		gliding.getToStates().add("portal");

		// Punching
		State punching = new State("punching", false);
		punching.getToStates().add("idle");
		punching.getToStates().add("jumping");
		punching.getToStates().add("running");
		punching.getToStates().add("death");
		punching.getToStates().add("portal");

		// Death
		State death = new State("death", false);

		// Spawn
		State spawn = new State("spawn", false);
		spawn.getToStates().add("idle");

		// Portal
		State portal = new State("portal", false);

		sm.addState(idle);
		sm.addState(running);
		sm.addState(jumping);
		sm.addState(dashing);
		sm.addState(gliding);
		sm.addState(punching);
		sm.addState(death);
		sm.addState(spawn);
		sm.addState(portal);
		sm.activateState("spawn");
	}

	public boolean isInvulnerable() {
		return isInvulnerable;
	}
}
