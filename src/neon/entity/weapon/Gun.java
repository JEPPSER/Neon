package neon.entity.weapon;

import neon.entity.controllable.Player;
import neon.entity.projectile.Bullet;
import neon.graphics.Sprite;
import neon.graphics.animation.Animation;
import neon.graphics.animation.Animator;
import neon.io.SpriteLoader;
import neon.level.LevelManager;

public class Gun extends Weapon {

	public Gun() {
		initAnimator();
	}

	@Override
	public void attack(Player player) {
		float ang = 0;
		if (player.isMirrored()) {
			ang = (float) Math.PI;
		}
		Bullet b = new Bullet(player.getX(), player.getY(), ang, player.getName());
		LevelManager.addEntity(b);
	}

	private void initAnimator() {
		// Glide animation
		Sprite glide = SpriteLoader.getSprite("player_idle_1");
		Animation gliding = new Animation(100, true);
		gliding.getSprites().add(glide);

		// Dash animation
		Sprite dash1 = SpriteLoader.getSprite("player_dash_1");
		Sprite dash2 = SpriteLoader.getSprite("player_dash_2");
		Animation dashing = new Animation(100, true);
		dashing.getSprites().add(dash1);
		dashing.getSprites().add(dash2);

		// Jump animation
		Sprite jump1 = SpriteLoader.getSprite("player_idle_1");
		// Sprite jump2 = SpriteLoader.getSprite("player_jump_2");
		// Sprite jump3 = SpriteLoader.getSprite("player_jump_3");
		Animation jumping = new Animation(80, false);
		jumping.getSprites().add(jump1);
		// jumping.getSprites().add(jump2);
		// jumping.getSprites().add(jump3);

		// Running animation
		Sprite run1 = SpriteLoader.getSprite("player_run_11");
		Sprite run2 = SpriteLoader.getSprite("player_run_22");
		Sprite run3 = SpriteLoader.getSprite("player_run_33");
		Sprite run4 = SpriteLoader.getSprite("player_run_44");
		Sprite run5 = SpriteLoader.getSprite("player_run_55");
		Animation running = new Animation(50, true);
		running.getSprites().add(run1);
		running.getSprites().add(run2);
		running.getSprites().add(run3);
		running.getSprites().add(run4);
		running.getSprites().add(run5);

		// Idle animation
		Sprite idle1 = SpriteLoader.getSprite("player_idle_1");
		Sprite idle2 = SpriteLoader.getSprite("player_idle_2");
		Sprite idle3 = SpriteLoader.getSprite("player_idle_3");
		Sprite idle4 = SpriteLoader.getSprite("player_idle_4");
		Sprite idle5 = SpriteLoader.getSprite("player_idle_5");
		Animation idle = new Animation(150, true);
		idle.getSprites().add(idle1);
		idle.getSprites().add(idle2);
		idle.getSprites().add(idle3);
		idle.getSprites().add(idle4);
		idle.getSprites().add(idle5);
		idle.getSprites().add(idle4);
		idle.getSprites().add(idle3);
		idle.getSprites().add(idle2);

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
		spawn.getSprites().add(idle1);

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
