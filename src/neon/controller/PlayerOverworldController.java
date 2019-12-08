package neon.controller;

import org.newdawn.slick.Input;

import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.level.LevelManager;
import neon.overworld.entity.World;
import neon.settings.InputSettings;
import neon.state.State;
import neon.state.StateManager;
import neon.time.TimeInfo;

public class PlayerOverworldController extends PlayerController {

	private StateManager sm;
	private Player player;
	private Animator animator;
	
	private boolean enterWorld = false;

	private float runningSpeed = 0.5f;
	private int direction = 0; // 0 = right, 1 = left

	private boolean isInvulnerable = false;

	private int deathTimer = 0;
	private final int DEATH_TIME = 1000;

	public PlayerOverworldController(Player player) {
		super(player);
		this.player = player;
		this.animator = player.getGraphics().getAnimator();
		initStateManager();
	}

	@Override
	public void control(Input input) {
		if (sm.getCurrentState().equals("portal")) {
			updatePortal();
			updateAnimationState();
			return;
		}

		if (sm.getCurrentState().equals("spawn")) {
			spawn();
			updateAnimationState();
			input.clearKeyPressedRecord();
			input.clearControlPressedRecord();
			return;
		}

		boolean isIdle = true;
		if (input.isKeyDown(InputSettings.getKeyboardBinds().get("left")) || isButtonDown(input, "left")) {
			isIdle = false;
			left();
		}
		if (input.isKeyDown(InputSettings.getKeyboardBinds().get("right")) || isButtonDown(input, "right")) {
			isIdle = false;
			right();
		}
		if (input.isKeyDown(InputSettings.getKeyboardBinds().get("up"))) {
			isIdle = false;
			up();
		}
		if (input.isKeyDown(InputSettings.getKeyboardBinds().get("down"))) {
			isIdle = false;
			down();
		}
		if (input.isKeyPressed(InputSettings.getKeyboardBinds().get("action"))) {
			if (player.getCollidingEntity() instanceof World) {
				this.enterWorld = true;
				LevelManager.setWorld((World) player.getCollidingEntity());
			}
		}
		if (isIdle) {
			sm.activateState("idle");
		}

		updateAnimationState();
	}
	
	public void setEnterWorld(boolean enterWorld) {
		this.enterWorld = enterWorld;
	}
	
	public boolean enterWorld() {
		return enterWorld;
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

	private void updateAnimationState() {
		if (!animator.getState().equals(sm.getCurrentState())) {
			animator.setState(sm.getCurrentState());
		}
	}

	private void right() {
		if (sm.canActivateState("running")) {
			sm.activateState("running");
		}
		float vel = runningSpeed * TimeInfo.getDelta();
		player.setX(player.getX() + vel);
		if (direction == 1) {
			direction = 0;
			player.setMirrored(false);
		}
	}

	private void left() {
		if (sm.canActivateState("running")) {
			sm.activateState("running");
		}
		float vel = -runningSpeed * TimeInfo.getDelta();
		player.setX(player.getX() + vel);
		if (direction == 0) {
			direction = 1;
			player.setMirrored(true);
		}
	}
	
	private void up() {
		if (sm.canActivateState("running")) {
			sm.activateState("running");
		}
		float vel = -runningSpeed * TimeInfo.getDelta();
		player.setY(player.getY() + vel);
	}
	
	private void down() {
		if (sm.canActivateState("running")) {
			sm.activateState("running");
		}
		float vel = runningSpeed * TimeInfo.getDelta();
		player.setY(player.getY() + vel);
	}

	private void initStateManager() {
		this.sm = new StateManager();

		// Idle
		State idle = new State("idle", true);
		idle.getToStates().add("running");
		idle.getToStates().add("portal");

		// Running
		State running = new State("running", true);
		running.getToStates().add("idle");
		running.getToStates().add("portal");

		// Spawn
		State spawn = new State("spawn", false);
		spawn.getToStates().add("idle");

		// Portal
		State portal = new State("portal", false);

		sm.addState(idle);
		sm.addState(running);
		sm.addState(spawn);
		sm.addState(portal);
		sm.activateState("spawn");
	}

	public boolean isInvulnerable() {
		return isInvulnerable;
	}
}