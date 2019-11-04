package neon.controller.ai;

import neon.entity.controllable.Player;
import neon.physics.CollisionDirection;

public interface AIController {
	public void control(Player player);
	public void takeDamage(float damage, CollisionDirection cd);
	public void death();
	public boolean isInvulnerable();
}
