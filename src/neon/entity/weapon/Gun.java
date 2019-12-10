package neon.entity.weapon;

import neon.entity.controllable.Player;
import neon.graphics.Sprite;
import neon.graphics.animation.Animation;
import neon.graphics.animation.Animator;
import neon.io.SpriteLoader;

public class Gun extends Weapon {

	public Gun() {
		initAnimator();
	}

	@Override
	public void attack(Player player) {

	}

	private void initAnimator() {
		// Glide animation
		Sprite glide = SpriteLoader.getSprite("player_glide");
		Animation gliding = new Animation(100, true);
		gliding.getSprites().add(glide);

		// Dash animation
		Sprite dash = SpriteLoader.getSprite("player_dash");
		Animation dashing = new Animation(100, true);
		dashing.getSprites().add(dash);

		// Jump animation
		Sprite jump1 = SpriteLoader.getSprite("player_jump_1");
		Sprite jump2 = SpriteLoader.getSprite("player_jump_2");
		Sprite jump3 = SpriteLoader.getSprite("player_jump_3");
		Animation jumping = new Animation(80, false);
		jumping.getSprites().add(jump1);
		jumping.getSprites().add(jump2);
		jumping.getSprites().add(jump3);

		// Running animation
		Sprite run1 = SpriteLoader.getSprite("player_run_1");
		Sprite run2 = SpriteLoader.getSprite("player_run_2");
		Sprite run3 = SpriteLoader.getSprite("player_run_3");
		Sprite run4 = SpriteLoader.getSprite("player_run_4");
		Sprite run5 = SpriteLoader.getSprite("player_run_5");
		Animation running = new Animation(50, true);
		running.getSprites().add(run1);
		running.getSprites().add(run2);
		running.getSprites().add(run3);
		running.getSprites().add(run4);
		running.getSprites().add(run5);

		// Idle animation
		Sprite idleSprite = SpriteLoader.getSprite("gunman_idle");
		Animation idle = new Animation(100, true);
		idle.getSprites().add(idleSprite);

		// Punch animation
		Sprite punch1 = SpriteLoader.getSprite("player_punch_1");
		Sprite punch2 = SpriteLoader.getSprite("player_punch_2");
		Animation punch = new Animation(100, false);
		punch.getSprites().add(punch1);
		punch.getSprites().add(punch2);

		// Death animation
		Sprite d1 = SpriteLoader.getSprite("player_death_1");
		Sprite d2 = SpriteLoader.getSprite("player_death_2");
		Sprite d3 = SpriteLoader.getSprite("player_death_3");
		Sprite d4 = SpriteLoader.getSprite("player_death_4");
		Sprite d5 = SpriteLoader.getSprite("player_death_5");
		Animation death = new Animation(100, false);
		death.getSprites().add(d1);
		death.getSprites().add(d2);
		death.getSprites().add(d3);
		death.getSprites().add(d4);
		death.getSprites().add(d5);

		// Spawn animation
		Animation spawn = new Animation(100, false);
		spawn.getSprites().add(d5);
		spawn.getSprites().add(d4);
		spawn.getSprites().add(d3);
		spawn.getSprites().add(d2);
		spawn.getSprites().add(d1);
		spawn.getSprites().add(idleSprite);

		// Portal animation
		Sprite p1 = SpriteLoader.getSprite("player_portal_1");
		Sprite p2 = SpriteLoader.getSprite("player_portal_2");
		Sprite p3 = SpriteLoader.getSprite("player_portal_3");
		Sprite p4 = SpriteLoader.getSprite("player_portal_4");
		Sprite p5 = SpriteLoader.getSprite("player_portal_5");
		Sprite p6 = SpriteLoader.getSprite("player_portal_6");
		p1.setOffsetY(-50);
		p2.setOffsetY(-50);
		p3.setOffsetY(-50);
		p4.setOffsetY(-50);
		p5.setOffsetY(-50);
		p6.setOffsetY(-50);
		Animation portal = new Animation(70, false);
		portal.getSprites().add(p1);
		portal.getSprites().add(p2);
		portal.getSprites().add(p3);
		portal.getSprites().add(p4);
		portal.getSprites().add(p5);
		portal.getSprites().add(p6);
		portal.getSprites().add(d5);

		Animator anim = new Animator();
		anim.addAnimation(idle, "idle");
		anim.setState("idle");
		anim.addAnimation(running, "running");
		anim.addAnimation(jumping, "jumping");
		anim.addAnimation(dashing, "dashing");
		anim.addAnimation(gliding, "gliding");
		anim.addAnimation(punch, "punching");
		anim.addAnimation(death, "death");
		anim.addAnimation(spawn, "spawn");
		anim.addAnimation(portal, "portal");
		
		this.animator = anim;
	}
}
