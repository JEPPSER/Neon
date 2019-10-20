package neon.camera;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import neon.entity.Entity;
import neon.entity.controllable.ControllableEntity;

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
	
	public void renderPlayField(ArrayList<Entity> playField, Graphics g) {
		float offsetX = focusedEntity.getX() * -1 + gc.getWidth() / 2;
		float offsetY = focusedEntity.getY() * -1 + gc.getHeight() / 2;
		for (int i = 0; i < playField.size(); i++) {
			playField.get(i).render(g, offsetX, offsetY);
		}
	}
}
