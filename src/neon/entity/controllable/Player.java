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
	
	public Player(float x, float y) {
		name = "Player";
		initGraphics();
		this.controller = new PlayerController(this);
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 30, 60), 1.0f, 10f, true);
		this.x = x;
		this.y = y;
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
		graphics.render(g, this.x + offsetX, this.y + offsetY, 0, false);
	}
	
	private void initGraphics() {
		// Animation 1
		Sprite s1 = SpriteLoader.getSprite("test2");
		Sprite s2 = SpriteLoader.getSprite("scuffed");
		Animation a1 = new Animation(100, true);
		a1.getSprites().add(s1);
		a1.getSprites().add(s2);
		
		// Animation 2
		Sprite s3 = SpriteLoader.getSprite("test");
		Sprite s4 = SpriteLoader.getSprite("player_idle");
		Animation a2 = new Animation(100, true);
		a2.getSprites().add(s3);
		a2.getSprites().add(s4);
		
		Animator anim = new Animator();
		anim.addAnimation(a1, "test1");
		anim.setState("test1");
		anim.addAnimation(a2, "test2");
		
		this.graphics = new EntityGraphics();
		this.graphics.setAnimator(anim);
		this.graphics.setColor(Color.red);
		this.graphics.setLineWidth(2.0f);
	}
}
