package neon.entity.ai.enemy;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.controller.ai.GorillaController;
import neon.entity.PhysicalEntity;
import neon.entity.terrain.TerrainEntity;
import neon.graphics.EntityGraphics;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class Gorilla extends Enemy {
	
	public Gorilla(float x, float y) {
		name = "Gorilla";
		health = 10f;
		maxHealth = 10f;
		initGraphics();
		this.color = Color.lightGray;
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 200, 200), 1.0f, 10f, true);
		this.width = collision.getHitbox().getWidth();
		this.height = collision.getHitbox().getHeight();
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
		g.drawRect(x + offsetX, y + offsetY, width, height);
		drawHealthBar(g, x + offsetX + (width - 100) / 2, y + offsetY - 40);
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {
		ai.takeDamage(damage, cd);
	}
	
	private void initGraphics() {
		this.graphics = new EntityGraphics(this.width);
		//Animator anim = new Animator();
		//graphics.setAnimator(anim);
	}
}