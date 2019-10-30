package neon.entity.controllable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.combat.Attack;
import neon.combat.AttackAnimation;
import neon.combat.Combat;
import neon.controller.PlayerController;
import neon.entity.PhysicalEntity;
import neon.entity.ai.AIEntity;
import neon.entity.area.Trigger;
import neon.entity.collectable.CollectableEntity;
import neon.graphics.EntityGraphics;
import neon.graphics.Point;
import neon.graphics.Sprite;
import neon.graphics.animation.Animation;
import neon.graphics.animation.Animator;
import neon.io.SpriteLoader;
import neon.physics.Physics;
import neon.physics.Collision;
import neon.physics.CollisionDirection;

public class Player extends ControllableEntity {

	private Combat combat;
	private float health;
	private float maxHealth;
	
	public Player(float x, float y) {
		name = "Player";
		color = Color.yellow;
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 50, 100), 1.0f, 10f, true);
		this.x = x;
		this.y = y;
		this.health = 10f;
		this.maxHealth = 10f;
		initGraphics();
		initCombat();
		this.controller = new PlayerController(this);
	}
	
	public void takeDamage(float damage) {
		((PlayerController) controller).takeDamage(damage);
	}
	
	public void setHealth(float health) {
		this.health = health;
	}
	
	public float getHealth() {
		return health;
	}
	
	public float getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public void drawHealthBar(Graphics g, float x, float y) {
		g.setColor(Color.white);
		g.drawRect(x, y, 100, 20);
		g.fillRect(x, y, (health / maxHealth) * 100, 20);
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		if (((PlayerController) controller).isInvulnerable()) {
			this.graphics.setColor(Color.red);
		} else {
			this.graphics.setColor(this.color);
		}
		graphics.render(g, this.x + offsetX, this.y + offsetY, 0, mirrored);
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
	public void handleCollision(PhysicalEntity other) {
		CollisionDirection cd = this.collisionDirection;
		PhysicalEntity pe = this.collidingEntity;

		if (other instanceof Trigger) {
			if (pe == other) {
				((Trigger) other).triggered();
			} else {
				((Trigger) other).unTriggered();
			}
		} else if (other instanceof AIEntity) {

		} else if (other instanceof CollectableEntity && cd != CollisionDirection.NONE) {
			((CollectableEntity) other).collect(this);
		} else if (cd == CollisionDirection.DOWN) {
			this.setY(pe.getY() - this.getCollision().getHitbox().getHeight());
			physics.setYVelocity(0f);
		} else if (cd == CollisionDirection.UP) {
			this.setY(pe.getY() + pe.getCollision().getHitbox().getHeight());
			physics.setYVelocity(0f);
		} else if (cd == CollisionDirection.RIGHT) {
			this.setX(pe.getX() - this.getCollision().getHitbox().getWidth());
			physics.setXVelocity(0f);
			((PlayerController) controller).glide(cd);
		} else if (cd == CollisionDirection.LEFT) {
			this.setX(pe.getX() + pe.getCollision().getHitbox().getWidth());
			physics.setXVelocity(0f);
			((PlayerController) controller).glide(cd);
		}
	}
	
	public Combat getCombat() {
		return combat;
	}
	
	private void initCombat() {
		combat = new Combat();
		
		// Punch attack
		AttackAnimation pAnim = new AttackAnimation(new Rectangle(0, 0, 40, 80), 300);
		Point endPoint = new Point(60, 10);
		pAnim.getPath().add(new Point(35, 10));
		pAnim.getPath().add(new Point(40, 10));
		pAnim.getPath().add(new Point(45, 10));
		pAnim.getPath().add(new Point(50, 10));
		pAnim.getPath().add(new Point(55, 10));
		pAnim.getPath().add(endPoint);
		pAnim.getPath().add(endPoint);
		pAnim.getPath().add(endPoint);
		pAnim.getPath().add(endPoint);
		pAnim.getPath().add(endPoint);
		pAnim.getPath().add(endPoint);
		pAnim.getPath().add(endPoint);
		pAnim.getPath().add(endPoint);
		Attack punch = new Attack("punch", 2f, pAnim);
		combat.addAttack(punch);
	}

	private void initGraphics() {

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
		Sprite idleSprite = SpriteLoader.getSprite("player_idle");
		Animation idle = new Animation(100, true);
		idle.getSprites().add(idleSprite);
		
		// Punch animation
		Sprite punch1 = SpriteLoader.getSprite("player_punch_1");
		Sprite punch2 = SpriteLoader.getSprite("player_punch_2");
		Animation punch = new Animation(100, false);
		punch.getSprites().add(punch1);
		punch.getSprites().add(punch2);

		Animator anim = new Animator();
		anim.addAnimation(idle, "idle");
		anim.setState("idle");
		anim.addAnimation(running, "running");
		anim.addAnimation(jumping, "jumping");
		anim.addAnimation(dashing, "dashing");
		anim.addAnimation(gliding, "gliding");
		anim.addAnimation(punch, "punching");

		this.graphics = new EntityGraphics(this.getWidth());
		this.graphics.setAnimator(anim);
		this.graphics.setColor(color);
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public float getWidth() {
		return this.collision.getHitbox().getWidth();
	}

	@Override
	public void setWidth(float width) {
	}

	@Override
	public float getHeight() {
		return this.collision.getHitbox().getHeight();
	}

	@Override
	public void setHeight(float height) {
	}

	@Override
	public void setSize(float width, float height) {
	}
}
