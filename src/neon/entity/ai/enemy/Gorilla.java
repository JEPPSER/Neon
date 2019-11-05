package neon.entity.ai.enemy;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.combat.Attack;
import neon.combat.AttackAnimation;
import neon.combat.Combat;
import neon.controller.ai.GorillaController;
import neon.entity.PhysicalEntity;
import neon.entity.terrain.TerrainEntity;
import neon.graphics.EntityGraphics;
import neon.graphics.Point;
import neon.graphics.Sprite;
import neon.graphics.animation.Animation;
import neon.graphics.animation.Animator;
import neon.io.SpriteLoader;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class Gorilla extends Enemy {
	
	public Gorilla(float x, float y) {
		name = "Gorilla";
		health = 20f;
		maxHealth = 20f;
		this.color = Color.lightGray;
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 200, 200), 1.0f, 10f, true);
		this.width = collision.getHitbox().getWidth();
		this.height = collision.getHitbox().getHeight();
		initGraphics();
		initCombat();
		this.x = x;
		this.y = y;
		this.ai = new GorillaController(this);
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		if (other instanceof TerrainEntity){
			super.handleCollision(other);
		}
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public void setWidth(float width) {
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void setHeight(float height) {
	}

	@Override
	public void setSize(float width, float height) {
	}
	
	private void drawHealthBar(Graphics g, float x, float y) {
		g.setColor(Color.white);
		g.drawRect(x, y, 100, 20);
		g.setColor(Color.red);
		g.fillRect(x + 2, y + 2, (health / maxHealth) * 100 - 4, 20 - 4);
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		if (((GorillaController) ai).isInvulnerable()) {
			this.graphics.setColor(Color.red);
			g.setColor(Color.red);
		} else {
			this.graphics.setColor(this.color);
			g.setColor(this.color);
		}
		//g.drawRect(x + offsetX, y + offsetY, width, height);
		graphics.render(g, x + offsetX, y + offsetY, 0, mirrored);
		drawHealthBar(g, x + offsetX + (width - 100) / 2, y + offsetY - 40);
		//drawAttackHitBox(g, offsetX, offsetY);
	}
	
	private void drawAttackHitBox(Graphics g, float offsetX, float offsetY) {
		Attack attack = combat.getCurrentAttack();
		if (attack != null) {
			Rectangle r = attack.getHitBox(this);
			if (r != null) {
				g.setColor(Color.red);
				g.drawRect(r.getX() + offsetX, r.getY() + offsetY, r.getWidth(), r.getHeight());
			}
		}
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {
		ai.takeDamage(damage, cd);
	}
	
	public Combat getCombat() {
		return combat;
	}
	
	private void initCombat() {
		combat = new Combat();
		
		// Ground smash attack
		AttackAnimation pAnim = new AttackAnimation(new Rectangle(0, 0, 200, 50), 300);
		Point endPoint = new Point(80, 150);
		pAnim.getPath().add(new Point(60, 0));
		pAnim.getPath().add(new Point(60, 0));
		pAnim.getPath().add(new Point(60, 0));
		pAnim.getPath().add(new Point(60, 0));
		pAnim.getPath().add(new Point(70, 30));
		pAnim.getPath().add(new Point(80, 60));
		pAnim.getPath().add(new Point(80, 90));
		pAnim.getPath().add(new Point(80, 120));
		pAnim.getPath().add(endPoint);
		pAnim.getPath().add(endPoint);
		pAnim.getPath().add(endPoint);
		pAnim.getPath().add(endPoint);
		Attack groundSmash = new Attack("ground_smash", 4f, pAnim);
		combat.addAttack(groundSmash);
	}
	
	private void initGraphics() {
		this.graphics = new EntityGraphics(this.width);
		Animator anim = new Animator();
		graphics.setAnimator(anim);
		
		// Idle animation
		Sprite idleSprite = SpriteLoader.getSprite("gorilla_idle");
		Animation idle = new Animation(100, true);
		idle.getSprites().add(idleSprite);
		
		// Walk animation
		Sprite walk1 = SpriteLoader.getSprite("gorilla_walk_1");
		Sprite walk2 = SpriteLoader.getSprite("gorilla_walk_2");
		Animation walk = new Animation(150, true);
		walk.getSprites().add(walk2);
		walk.getSprites().add(walk1);
		walk.getSprites().add(walk2);
		walk.getSprites().add(idleSprite);
		
		// Ground smash animation
		Sprite smash1 = SpriteLoader.getSprite("gorilla_smash_1");
		smash1.setOffsetY(-80);
		Sprite smash2 = SpriteLoader.getSprite("gorilla_smash_2");
		Sprite smash3 = SpriteLoader.getSprite("gorilla_smash_3");
		smash3.setOffsetY(-80);
		Animation groundSmash = new Animation(100, false);
		groundSmash.getSprites().add(smash3);
		groundSmash.getSprites().add(smash1);
		groundSmash.getSprites().add(smash2);
		
		anim.addAnimation(idle, "idle");
		anim.addAnimation(walk, "walk");
		anim.addAnimation(groundSmash, "ground_smash");
		anim.setState("idle");
	}
}