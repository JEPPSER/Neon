package neon.entity.terrain;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.graphics.EntityGraphics;
import neon.physics.Collision;
import neon.physics.CollisionDirection;
import neon.physics.Physics;

public class TreeBranch extends OneWay {
	
	public TreeBranch(float x, float y, float width, boolean mirrored) {
		this.name = "TreeBranch";
		this.physics = new Physics(0f, 0f);
		setSize(width, 25);
		this.x = x;
		this.y = y;
		this.orientation = CollisionDirection.UP;
		this.mirrored = mirrored;
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(temp);
		g.drawRect(offsetX + x, offsetY + y, width, height);
	}
	
	@Override
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
		this.collision = new Collision(new Rectangle(0, 0, width, height), 1, 10, false);
		this.collision.getHitbox().setHeight(height);
		this.collision.getHitbox().setWidth(width);
		initGraphics();
	}
	
	private void initGraphics() {
		this.graphics = new EntityGraphics(width);
	}
	
	@Override
	public String toString() {
		return getID() + "," + x + "," + y + "," + width + "," + mirrored;
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
	public int getID() {
		return 28;
	}
}
