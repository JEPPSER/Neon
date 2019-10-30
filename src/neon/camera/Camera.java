package neon.camera;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import neon.entity.Entity;
import neon.entity.PhysicalEntity;
import neon.graphics.Point;
import neon.level.Level;

public class Camera {

	private PhysicalEntity focusedEntity;
	private GameContainer gc;
	private float offsetX;
	private float offsetY;
	private Point focalPoint;
	private float yScrollValue = 0.2f;
	private float scale = 1.0f;

	public Camera(PhysicalEntity focusedEntity, GameContainer gc) {
		this.focusedEntity = focusedEntity;
		offsetX = focusedEntity.getX() * -1 + gc.getWidth() / 2;
		offsetY = focusedEntity.getY() * -1 + gc.getHeight() / 2;
		this.gc = gc;
	}

	public void setFocusedEntity(PhysicalEntity focusedEntity) {
		this.focusedEntity = focusedEntity;
	}

	public PhysicalEntity getFocusedEntity() {
		return this.focusedEntity;
	}

	public void renderPlayField(Level level, Graphics g) {
		g.scale(scale, scale);
		
		float focalX;
		float focalY;
		if (focalPoint == null) {
			focalX = focusedEntity.getX();
			focalY = focusedEntity.getY();
		} else {
			focalX = focalPoint.getX();
			focalY = focalPoint.getY();
		}
		
		offsetX = focalX * -1 + gc.getWidth() / (2f * scale) - focusedEntity.getWidth() / 2;
		float y = offsetY + focalY;
		float height = focusedEntity.getHeight();

		if (focalPoint == null) {
			// Scroll screen vertically
			if (y > gc.getHeight() - height - gc.getHeight() * yScrollValue) {
				offsetY = -focalY + gc.getHeight() - height - gc.getHeight() * yScrollValue;
			} else if (y < gc.getHeight() * yScrollValue) {
				offsetY = -focalY + gc.getHeight() * yScrollValue;
			}
		} else {
			offsetY = focalY * -1 + gc.getHeight() / (2f * scale);
		}

		for (int i = 0; i < level.getObjects().size(); i++) {
			if (isInView(level.getObjects().get(i))) {
				level.getObjects().get(i).render(g, offsetX, offsetY);
			}
		}
	}
	
	public void zoom(float scale) {
		this.scale = scale;
	}
	
	public void setFocalPoint(Point focalPoint) {
		this.focalPoint = focalPoint;
	}

	private boolean isInView(Entity entity) {
		if (entity.getX() + offsetX + entity.getWidth() > 0 && entity.getX() + offsetX < gc.getWidth()
				&& entity.getY() + offsetY + entity.getHeight() > 0 && entity.getY() + offsetY < gc.getHeight()) {
			return true;
		}
		return false;
	}
}
