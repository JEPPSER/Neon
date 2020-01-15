package neon.entity.ai.enemy;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.combat.Attack;
import neon.combat.AttackAnimation;
import neon.combat.Combat;
import neon.controller.ai.RageStickController;
import neon.entity.PhysicalEntity;
import neon.entity.controllable.Player;
import neon.entity.terrain.TerrainEntity;
import neon.graphics.Point;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class RageStick extends Enemy  {
	
	private ArrayList<CollisionDirection> colDirections;
	
	public RageStick(float x, float y) {
		name = "RageStick";
		health = 30f;
		maxHealth = 30f;
		this.color = new Color(0, 255, 0);
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 300, 600), 1f, 10f, true);
		this.width = collision.getHitbox().getWidth();
		this.height = collision.getHitbox().getHeight();
		initGraphics();
		initCombat();
		this.x = x;
		this.y = y;
		this.ai = new RageStickController(this);
		colDirections = new ArrayList<CollisionDirection>();
	}
	
	public ArrayList<CollisionDirection> getCollisionDirections() {
		return colDirections;
	}
	
	@Override
	public void control(Player player) {
		if (ai != null) {
			ai.control(player);
		}
		colDirections.clear();
	}
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		if (other instanceof TerrainEntity){
			super.handleCollision(other);
			colDirections.add(this.collisionDirection);
		}
	}
	
	private void initGraphics() {
		
	}
	
	private void initCombat() {
		this.combat = new Combat();
		
		AttackAnimation sAnim = new AttackAnimation(new Rectangle(0, 0, 300, 100), 200);
		sAnim.getPath().add(new Point(0, 500));
		Attack stomp = new Attack("stomp", 5f, sAnim);
		combat.addAttack(stomp);
		
		AttackAnimation hAnim = new AttackAnimation(new Rectangle(0, 0, 500, 100), 200);
		hAnim.getPath().add(new Point(200, 500));
		Attack headSlam = new Attack("head_slam", 5f, hAnim);
		combat.addAttack(headSlam);
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
		g.setColor(Color.gray);
		g.drawRect(x + offsetX, y + offsetY, width, height);
		
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
		return getID() + "," + x + "," + y;
	}

	@Override
	public int getID() {
		return 25;
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {
		ai.takeDamage(damage, cd);
	}
}