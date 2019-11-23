package neon.controller.ai;

import neon.entity.ai.enemy.Serpent;
import neon.entity.controllable.Player;
import neon.physics.CollisionDirection;

public class SerpentController implements AIController {
	
	private Serpent serpent;
	
	public SerpentController(Serpent serpent) {
		this.serpent = serpent;
	}

	@Override
	public void control(Player player) {
		
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {

	}

	@Override
	public void death() {

	}

	@Override
	public boolean isInvulnerable() {
		return false;
	}

}
