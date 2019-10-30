package neon.entity.collectable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.controllable.Player;
import neon.graphics.EntityGraphics;
import neon.physics.Collision;
import neon.physics.Physics;

public class Heart extends CollectableEntity {
	
	private float health = 1.0f;

	public Heart(float x, float y) {
		name = "Heart";
		initGraphics();
		this.physics = new Physics(0f, 0f);
		this.collision = new Collision(new Rectangle(0, 0, 50, 50), 1.0f, 10f, true);
		this.x = x;
		this.y = y;
		this.height = collision.getHitbox().getHeight();
		this.width = collision.getHitbox().getWidth();
	}

	@Override
	public void collect(Player player) {
		isCollected = true;
		player.setHealth(player.getHealth() + health);
		if (player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
	}

	@Override
	public float getWidth() {
		return this.width;
	}

	@Override
	public void setWidth(float width) {

	}

	@Override
	public float getHeight() {
		return this.height;
	}

	@Override
	public void setHeight(float height) {

	}

	@Override
	public void setSize(float width, float height) {

	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.red);
		g.drawRect(x + offsetX, y + offsetY, this.width, this.height);
	}

	private void initGraphics() {
		this.graphics = new EntityGraphics(width);
	}

	@Override
	public int getID() {
		return 0;
	}
}
