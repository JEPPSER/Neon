package neon.entity.ai.enemy;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.controller.ai.RageStickController;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class RageStick extends Enemy  {
	
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
	}
	
	private void initGraphics() {
		
	}
	
	private void initCombat() {
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return 25;
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {

	}
}
