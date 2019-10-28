package neon.camera;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import neon.entity.Entity;
import neon.entity.controllable.ControllableEntity;
import neon.level.Level;

public class Camera {

	private ControllableEntity focusedEntity;
	private GameContainer gc;
	private float offsetX;
	private float offsetY;
	private float yScrollValue = 0.2f;

	public Camera(ControllableEntity focusedEntity, GameContainer gc) {
		this.focusedEntity = focusedEntity;
		offsetX = focusedEntity.getX() * -1 + gc.getWidth() / 2;
		offsetY = focusedEntity.getY() * -1 + gc.getHeight() / 2;
		this.gc = gc;
	}

	public void setFocusedEntity(ControllableEntity focusedEntity) {
		this.focusedEntity = focusedEntity;
	}

	public ControllableEntity getFocusedEntity() {
		return this.focusedEntity;
	}

	public void renderPlayField(Level level, Graphics g) {
		offsetX = focusedEntity.getX() * -1 + gc.getWidth() / 2 - focusedEntity.getWidth() / 2;
		float y = focusedEntity.getY() + offsetY;
		float height = focusedEntity.getHeight();

		// Scroll screen vertically
		if (y > gc.getHeight() - height - gc.getHeight() * yScrollValue) {
			offsetY = -focusedEntity.getY() + gc.getHeight() - height - gc.getHeight() * yScrollValue;
		} else if (y < gc.getHeight() * yScrollValue) {
			offsetY = -focusedEntity.getY() + gc.getHeight() * yScrollValue;
		}

		for (int i = 0; i < level.getObjects().size(); i++) {
			if (isInView(level.getObjects().get(i))) {
				level.getObjects().get(i).render(g, offsetX, offsetY);
			}
		}
	}

	private boolean isInView(Entity entity) {
		if (entity.getX() + offsetX + entity.getWidth() > 0 && entity.getX() + offsetX < gc.getWidth()
				&& entity.getY() + offsetY + entity.getHeight() > 0 && entity.getY() + offsetY < gc.getHeight()) {
			return true;
		}
		return false;
	}
}
