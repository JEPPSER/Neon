package neon.controller;

import org.newdawn.slick.Input;

import neon.entity.controllable.Player;
import neon.physics.Physics;

public class PlayerController implements Controller {
	
	private Player player;
	
	public PlayerController(Player player) {
		this.player = player;
	}

	@Override
	public void control(Input input) {
		Physics ph = this.player.getPhysics();
		if (input.isKeyDown(Input.KEY_A)) {
			ph.setXVelocity(-0.4f);
		}
		if (input.isKeyDown(Input.KEY_D)) {
			ph.setXVelocity(0.4f);
		}
		if (!input.isKeyDown(Input.KEY_D) && !input.isKeyDown(Input.KEY_A)) {
			ph.setXVelocity(0);
		}
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			ph.setYVelocity(-2f);
		}
	}
}
