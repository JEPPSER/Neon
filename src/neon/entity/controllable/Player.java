package neon.entity.controllable;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import neon.combat.Attack;
import neon.combat.AttackAnimation;
import neon.combat.Combat;
import neon.controller.Controller;
import neon.controller.PlayerController;
import neon.controller.PlayerOverworldController;
import neon.entity.PhysicalEntity;
import neon.entity.ai.AIEntity;
import neon.entity.area.Trigger;
import neon.entity.collectable.CollectableEntity;
import neon.entity.terrain.Spikes;
import neon.entity.terrain.TerrainEntity;
import neon.entity.weapon.Weapon;
import neon.graphics.EntityGraphics;
import neon.graphics.Point;
import neon.graphics.Sprite;
import neon.graphics.animation.Animation;
import neon.graphics.animation.Animator;
import neon.io.SpriteLoader;
import neon.physics.Physics;
import neon.time.TimeInfo;
import neon.physics.Collision;
import neon.physics.CollisionDirection;

public class Player extends ControllableEntity {

	private Weapon weapon;
	private Combat combat;
	private float health;
	private float maxHealth;
	private boolean exitWorld = false;
	private ArrayList<CollisionDirection> colDirections;
	
	private ArrayList<Point> trail;
	private int trailTimer = 0;
	private final int TRAIL_TIME = 15;
	private Image trailImage;
	private boolean showTrail = true;
	private Color trailColor = Color.magenta;
	private ArrayList<String> trailActions;

	public Player(float x, float y, Weapon weapon) {
		name = "Player";
		color = Color.white;
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 50, 100), 1.0f, 10f, true);
		this.x = x;
		this.y = y;
		this.width = 50;
		this.height = 100;
		this.health = 10f;
		this.maxHealth = 10f;
		initGraphics();
		this.weapon = weapon;
		if (weapon != null) {
			this.graphics.setAnimator(weapon.getAnimator());
		}
		initCombat();
		this.controller = new PlayerController(this);
		colDirections = new ArrayList<CollisionDirection>();
		
		trail = new ArrayList<Point>();
		trailImage = SpriteLoader.getSprite("trail").getImage();
		trailActions = new ArrayList<String>();
		trailActions.add("jumping");
		trailActions.add("running");
		trailActions.add("dashing");
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
		this.graphics.setAnimator(weapon.getAnimator());
	}

	public void activateAirJump() {
		((PlayerController) controller).activateAirJump();
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setEnterWorld(boolean enterWorld) {
		((PlayerOverworldController) controller).setEnterWorld(enterWorld);
	}

	public boolean enterWorld() {
		return ((PlayerOverworldController) controller).enterWorld();
	}

	public void setExitWorld(boolean exitWorld) {
		this.exitWorld = exitWorld;
	}

	public boolean exitWorld() {
		return exitWorld;
	}

	public void resetLevel() {
		((PlayerController) controller).resetLevel();
	}

	public void setState(String state) {
		((PlayerController) controller).setState(state);
		;
	}

	public void enterPortal() {
		((PlayerController) controller).portal();
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

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		if (((PlayerController) controller).isInvulnerable()) {
			this.graphics.setColor(new Color(1.0f, 1.0f, 1.0f, 0.4f));
		} else {
			this.graphics.setColor(this.color);
		}
		drawTrail(g, offsetX, offsetY);
		graphics.render(g, this.x + offsetX, this.y + offsetY, 0, mirrored);
	}
	
	private void drawTrail(Graphics g, float offsetX, float offsetY) {
		for (int i = 0; i < trail.size(); i++) {
			Point p = trail.get(i);
			if (p.distanceTo(new Point(x, y)) > 0) {
				float x = p.getX() + offsetX + width / 2f - trailImage.getWidth() / 2;
				float y = p.getY() + offsetY + height / 2f - trailImage.getHeight() / 2;
				Color c = trailColor;
				c.a = 0.03f * i;
				trailImage.draw(x, y, 1f, c);
			}
		}
	}
	
	private void updateTrail() {
		trailTimer += TimeInfo.getDelta();
		if (trailTimer >= TRAIL_TIME) {
			trailTimer = 0;
			if (trailActions.contains(graphics.getAnimator().getState())){
				Point p = new Point(x, y);
				trail.add(p);
				if (trail.size() >= 15) {
					trail.remove(0);
				}
			} else if (trail.size() > 0) {
				trail.remove(0);
			}
		}
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
	public void control(Input input) {
		controller.control(input);
		colDirections.clear();
		if (showTrail) {
			updateTrail();
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

		} else if (other instanceof CollectableEntity) {
			if (cd != CollisionDirection.NONE) {
				((CollectableEntity) other).collect(this);
			}
		} else if (pe == other && other instanceof Spikes) {
			health = 0;
		} else if (pe == other && other instanceof TerrainEntity) {
			if (cd == CollisionDirection.DOWN) {
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
			colDirections.add(cd);
			if (colDirections.contains(CollisionDirection.UP) && colDirections.contains(CollisionDirection.DOWN)) {
				this.health = 0;
			}
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
		Sprite glide1 = SpriteLoader.getSprite("player_glide");
		Animation gliding = new Animation(80, false);
		gliding.getSprites().add(glide1);

		// Dash animation
		Sprite dash1 = SpriteLoader.getSprite("player_dash_1");
		Sprite dash2 = SpriteLoader.getSprite("player_dash_2");
		Animation dashing = new Animation(100, true);
		dashing.getSprites().add(dash1);
		dashing.getSprites().add(dash2);

		// Jump animation
		Sprite jump1 = SpriteLoader.getSprite("player_jump_1");
		Sprite jump2 = SpriteLoader.getSprite("player_jump_2");
		Animation jumping = new Animation(100, false);
		jumping.getSprites().add(jump2);

		// Falling animation
		Animation falling = new Animation(100, true);
		falling.getSprites().add(jump1);

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
		anim.addAnimation(falling, "falling");
		anim.addAnimation(dashing, "dashing");
		anim.addAnimation(gliding, "gliding");
		anim.addAnimation(punch, "punching");
		anim.addAnimation(death, "death");
		anim.addAnimation(spawn, "spawn");
		anim.addAnimation(portal, "portal");

		this.graphics = new EntityGraphics(this.getWidth());
		this.graphics.setAnimator(anim);
		this.graphics.setColor(color);
	}

	@Override
	public int getID() {
		return -1;
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
