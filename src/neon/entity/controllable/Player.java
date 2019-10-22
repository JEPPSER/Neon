package neon.entity.controllable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.controller.PlayerController;
import neon.graphics.EntityGraphics;
import neon.graphics.Sprite;
import neon.graphics.animation.Animation;
import neon.graphics.animation.Animator;
import neon.io.SpriteLoader;
import neon.physics.Physics;
import neon.physics.Collision;

public class Player extends ControllableEntity {
	
	private EntityGraphics graphics;
	private String name;
	private float x = 300;
	private float y = 100;
	private boolean mirrored = false;
	
	public Player(float x, float y) {
		name = "Player";
		initGraphics();
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 50, 100), 1.0f, 10f, true);
		this.x = x;
		this.y = y;
		this.controller = new PlayerController(this);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return this.y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public EntityGraphics getGraphics() {
		return graphics;
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		graphics.render(g, this.x + offsetX, this.y + offsetY, 0, mirrored);
	}
	
	public void setMirrored(boolean mirrored) {
		this.mirrored = mirrored;
	}
	
	public boolean isMirrored() {
		return this.mirrored;
	}
	
	private void initGraphics() {
		
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
		Sprite idleSprite = SpriteLoader.getSprite("player_idle");
		Animation idle = new Animation(100, true);
		idle.getSprites().add(idleSprite);
		
		Animator anim = new Animator();
		anim.addAnimation(idle, "idle");
		anim.setState("idle");
		anim.addAnimation(running, "running");
		anim.addAnimation(jumping, "jumping");
		anim.addAnimation(dashing, "dashing");
		
		this.graphics = new EntityGraphics();
		this.graphics.setAnimator(anim);
		this.graphics.setColor(Color.cyan);
		this.graphics.setLineWidth(2.0f);
	}
}
