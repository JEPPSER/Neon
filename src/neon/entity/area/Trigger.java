package neon.entity.area;

import org.newdawn.slick.geom.Rectangle;

import neon.entity.PhysicalEntity;
import neon.graphics.EntityGraphics;
import neon.physics.Collision;
import neon.physics.Physics;

public abstract class Trigger extends PhysicalEntity {
	
	protected String text;
	protected float textX;
	protected float textY;
	protected float scale = 1.0f;
	protected boolean isTriggered;

	public Trigger() {
		this.name = "Trigger";
		this.physics = new Physics(0f, 0f);
		this.layer = 3;
	}
	
	public abstract void triggered();
	
	public abstract void setTrigger(String text, float textX, float textY);
	
	public abstract void unTriggered();
	
	@Override
	public void handleCollision(PhysicalEntity other) {
		
	}
	
	public void reset() {
		isTriggered = false;
	}
	
	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getTextX() {
		return textX;
	}

	public void setTextX(float textX) {
		this.textX = textX;
	}

	public float getTextY() {
		return textY;
	}

	public void setTextY(float textY) {
		this.textY = textY;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public void setWidth(float width) {
		this.width = width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void setHeight(float height) {
		this.height = height;
	}

	@Override
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
		this.collision = new Collision(new Rectangle(0, 0, this.width, this.height), 1.0f, 10f, false);
		this.collision.getHitbox().setHeight(height);
		this.collision.getHitbox().setWidth(width);
		initGraphics();
	}

	private void initGraphics() {
		this.graphics = new EntityGraphics(this.width);
	}
}
