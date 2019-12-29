package neon.entity.ai.enemy;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.combat.Attack;
import neon.combat.AttackAnimation;
import neon.combat.Combat;
import neon.controller.ai.SerpentController;
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

public class Serpent extends Enemy {
	
	public Serpent(float x, float y) {
		name = "Serpent";
		health = 20f;
		maxHealth = 20f;
		this.color = new Color(0, 255, 0);
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 300, 300), 1f, 10f, true);
		this.width = collision.getHitbox().getWidth();
		this.height = collision.getHitbox().getHeight();
		initGraphics();
		initCombat();
		this.x = x;
		this.y = y;
		this.ai = new SerpentController(this);
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		if (other instanceof TerrainEntity){
			super.handleCollision(other);
		}
	}
	
	private void initGraphics() {
		this.graphics = new EntityGraphics(width);
		Animator anim = new Animator();
		graphics.setAnimator(anim);
		
		// Idle animation
		Sprite idle1 = SpriteLoader.getSprite("serpent_idle_1");
		Animation idle = new Animation(100, true);
		idle.getSprites().add(idle1);
		
		anim.addAnimation(idle, "idle");
		anim.setState("idle");
	}
	
	private void initCombat() {
		this.combat = new Combat();
		
		// Bite attack
		AttackAnimation pAnim = new AttackAnimation(new Rectangle(0, 0, 50, 50), 800);
		Point endPoint = new Point(600, 200);
		pAnim.getPath().add(new Point(0, 200));
		pAnim.getPath().add(new Point(0, 200));
		pAnim.getPath().add(new Point(0, 200));
		pAnim.getPath().add(new Point(0, 200));
		pAnim.getPath().add(new Point(0, 200));
		pAnim.getPath().add(new Point(0, 200));
		pAnim.getPath().add(new Point(0, 200));
		pAnim.getPath().add(new Point(0, 200));
		pAnim.getPath().add(new Point(300, 200));
		pAnim.getPath().add(new Point(400, 200));
		pAnim.getPath().add(new Point(500, 200));
		pAnim.getPath().add(new Point(600, 200));
		pAnim.getPath().add(new Point(700, 200));
		pAnim.getPath().add(new Point(800, 200));
		Attack bite = new Attack("bite", 4f, pAnim);
		combat.addAttack(bite);
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

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		if (((SerpentController) ai).isInvulnerable()) {
			this.graphics.setColor(Color.red);
		} else {
			this.graphics.setColor(Color.white);
		}
		
		graphics.render(g, x + offsetX, y + offsetY, 0, mirrored);
		drawAttackHitBox(g, offsetX, offsetY);
		
		if (!isDead) {
			drawHealthBar(g, x + offsetX + (width - 100) / 2, y + offsetY - 40);
		}
	}
	
	private void drawHealthBar(Graphics g, float x, float y) {
		g.setColor(Color.white);
		g.drawRect(x, y, 100, 20);
		g.setColor(Color.red);
		g.fillRect(x + 2, y + 2, (health / maxHealth) * 100 - 4, 20 - 4);
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
	public String toString() {
		String str = getID() + "," + x + "," + y;
		return str;
	}

	@Override
	public int getID() {
		return 18;
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {
		ai.takeDamage(damage, cd);
	}
}
