package neon.camera;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import neon.entity.controllable.ControllableEntity;
import neon.level.Level;

public class Camera {
	
	private ControllableEntity focusedEntity;
	private GameContainer gc;
	
	public Camera(ControllableEntity focusedEntity, GameContainer gc) {
		this.focusedEntity = focusedEntity;
		this.gc = gc;
	}
	
	public void setFocusedEntity(ControllableEntity focusedEntity) {
		this.focusedEntity = focusedEntity;
	}
	
	public ControllableEntity getFocusedEntity() {
		return this.focusedEntity;
	}
	
	public void renderPlayField(Level level, Graphics g) {
		float offsetX = focusedEntity.getX() * -1 + gc.getWidth() / 2;
		float offsetY = focusedEntity.getY() * -1 + gc.getHeight() / 2;
		g.setColor(Color.green);
		g.drawRect(offsetX, offsetY, level.getWidth(), level.getHeight());
		for (int i = 0; i < level.getObjects().size(); i++) {
			level.getObjects().get(i).render(g, offsetX, offsetY);
		}
	}
}
