package neon.entity.event;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import neon.entity.Entity;
import neon.entity.terrain.movable.MovableTerrain;
import neon.graphics.EntityGraphics;
import neon.level.LevelManager;

public class ActivateMovableEvent implements Event {

	private String activateName;
	private String conditionName;
	private String conditionType;
	private String name = "ActivateMovableEvent";

	public ActivateMovableEvent(String activateName, String conditionName, String conditionType) {
		this.activateName = activateName;
		this.conditionName = conditionName;
		this.conditionType = conditionType;
	}

	@Override
	public int getID() {
		return 10;
	}
	
	@Override
	public String toString() {
		String str = getID() + "," + activateName + "," + conditionName + "," + conditionType;
		return str;
	}

	@Override
	public boolean meetsCondition() {
		ArrayList<Entity> objects = LevelManager.getLevel().getObjects();
		for (int i = 0; i < objects.size(); i++) {
			if (conditionType.equals("dead")) {
				if (objects.get(i).getName().equals(conditionName)) {
					return false;
				}
			} else if (conditionType.equals("activated")) {
				if (objects.get(i).getName().equals(conditionName) && !((MovableTerrain) objects.get(i)).isActive()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void fireEvent() {
		ArrayList<Entity> objects = LevelManager.getLevel().getObjects();
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i).getName().equals(activateName)) {
				((MovableTerrain) objects.get(i)).activate();
			}
		}
		LevelManager.removeEntity(this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public float getX() {
		return 0;
	}

	@Override
	public void setX(float x) {
	}

	@Override
	public float getY() {
		return 0;
	}

	@Override
	public void setY(float y) {
	}

	@Override
	public float getWidth() {
		return 0;
	}

	@Override
	public void setWidth(float width) {
	}

	@Override
	public float getHeight() {
		return 0;
	}

	@Override
	public void setHeight(float height) {
	}

	@Override
	public void setSize(float width, float height) {
	}

	@Override
	public EntityGraphics getGraphics() {
		return null;
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
	}
}
