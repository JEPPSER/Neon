package neon.controller.ai;

import neon.entity.ai.Spider;
import neon.entity.controllable.Player;
import neon.graphics.animation.Animator;
import neon.physics.Physics;
import neon.time.TimeInfo;

public class SpiderController implements AIController {

	private Spider spider;
	private Physics ph;
	private Animator anim;

	private float speed = 0.2f;
	private float dmg = 1.0f;
	private int timer = 0;
	private boolean mirrored = false;

	public SpiderController(Spider spider) {
		this.spider = spider;
		this.ph = spider.getPhysics();
		this.anim = spider.getGraphics().getAnimator();
		ph.setXVelocity(speed);
		anim.setState("moving");
	}

	@Override
	public void control(Player player) {
		backAndForth(player);
	}
	
	private void backAndForth(Player player) {
		timer += TimeInfo.getDelta();

		if (timer > 3000) {
			timer = 0;
			if (mirrored) {
				mirrored = false;
				ph.setXVelocity(speed);
				spider.setMirrored(mirrored);
			} else {
				mirrored = true;
				ph.setXVelocity(-speed);
				spider.setMirrored(mirrored);
			}
		}
	}

	private void followPlayer(Player player) {
		if (player.getX() < spider.getX() - 2) {
			if (!anim.getState().equals("moving")) {
				anim.setState("moving");
			}
			ph.setXVelocity(-speed);
			spider.setMirrored(true);
		} else if (player.getX() > spider.getX() + 2) {
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

	public void hurtPlayer(Player player) {
		player.takeDamage(dmg);
	}
}
