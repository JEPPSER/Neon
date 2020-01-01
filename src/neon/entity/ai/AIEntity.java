package neon.entity.ai;

import neon.controller.ai.AIController;
import neon.entity.PhysicalEntity;
import neon.entity.controllable.Player;

public abstract class AIEntity extends PhysicalEntity {

	protected AIController ai;
	
	public void control(Player player) {
		if (ai != null) {
			ai.control(player);
		}
	}
}
