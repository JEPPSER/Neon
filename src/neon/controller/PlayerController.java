package neon.controller;

import org.newdawn.slick.Input;

import neon.entity.controllable.Player;

public class PlayerController implements Controller {
	
	private Player player;
	
	public PlayerController(Player player) {
		this.player = player;
	}

	@Override
	public void control(Input input, int delta) {
		if (input.isKeyDown(Input.KEY_W)) {
			this.player.setY(this.player.getY() - 0.4f);
		}
		if (input.isKeyDown(Input.KEY_A)) {
			this.player.setX(this.player.getX() - 0.4f);
		}
		if (input.isKeyDown(Input.KEY_S)) {
			this.player.setY(this.player.getY() + 0.4f);
		}
		if (input.isKeyDown(Input.KEY_D)) {
			this.player.setX(this.player.getX() + 0.4f);
		}
	}
}
