package neon.controller.ai;

import neon.entity.ai.Spider;
import neon.entity.controllable.Player;
import neon.physics.Physics;

public class SpiderController implements AIController {
	
	private Spider spider;
	private Physics ph;
	
	private float speed = 0.1f;
	private float dmg = 1.0f;
	
	public SpiderController(Spider spider) {
		this.spider = spider;
		this.ph = spider.getPhysics();
	}

	@Override
	public void control(Player player) {
		if (player.getX() < spider.getX()) {
			ph.setXVelocity(-speed);
		} else {
			ph.setXVelocity(speed);
		}
	}
	
	public void hurtPlayer() {
		
	}
}
