package neon.entity.ai.enemy;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.combat.Combat;
import neon.controller.ai.SerpentController;
import neon.graphics.EntityGraphics;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class Serpent extends Enemy {
	
	public Serpent(float x, float y) {
		name = "Serpent";
		health = 20f;
		maxHealth = 20f;
		this.color = new Color(0, 100, 0);
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 300, 300), 1f, 10f, true);
		this.width = collision.getHitbox().getWidth();
		this.height = collision.getHitbox().getHeight();
		initGraphics();
		this.x = x;
		this.y = y;
		this.ai = new SerpentController(this);
		this.combat = new Combat();
	}
	
	private void initGraphics() {
		this.graphics = new EntityGraphics(width);
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
		g.setColor(this.color);
		g.drawRect(offsetX + x, offsetY + y, width, height);
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {
			
	}
}
