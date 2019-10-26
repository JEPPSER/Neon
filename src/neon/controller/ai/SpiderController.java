package neon.controller.ai;

import neon.entity.ai.Spider;
import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.physics.Physics;

public class SpiderController implements AIController {
	
	private Spider spider;
	private Physics ph;
	private Animator anim;
	
	private float speed = 0.2f;
	private float dmg = 1.0f;
	
	public SpiderController(Spider spider) {
		this.spider = spider;
		this.ph = spider.getPhysics();
		this.anim = spider.getGraphics().getAnimator();
	}

	@Override
	public void control(Player player) {
		if (player.getX() < spider.getX() - 2) {
			if (!anim.getState().equals("moving")) {
				anim.setState("moving");
			}
			ph.setXVelocity(-speed);
			spider.setMirrored(true);
		} else if (player.getX() > spider.getX() + 2){
			if (!anim.getState().equals("moving")) {
				anim.setState("moving");
			}
			ph.setXVelocity(speed);
			spider.setMirrored(false);
		} else {
			if (!anim.getState().equals("idle")) {
				anim.setState("idle");
			}
			ph.setXVelocity(0);
		}
	}
	
	public void hurtPlayer() {
	}
}
