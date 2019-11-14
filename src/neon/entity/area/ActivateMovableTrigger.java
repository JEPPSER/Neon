package neon.entity.area;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import neon.entity.Entity;
import neon.entity.terrain.movable.MovableTerrain;
import neon.level.LevelManager;

public class ActivateMovableTrigger extends Trigger {
	
	private String activateName;
	private boolean isTriggered;
	
	public ActivateMovableTrigger(String activateName) {
		this.activateName = activateName;
		this.name = "ActivateMovableTrigger";
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {

	}

	@Override
	public int getID() {
		return 12;
	}

	@Override
	public void triggered() {
		if (!isTriggered) {
			isTriggered = true;
			ArrayList<Entity> objects = LevelManager.getLevel().getObjects();
			for (int i = 0; i < objects.size(); i++) {
				if (objects.get(i) instanceof MovableTerrain && objects.get(i).getName().equals(activateName)) {
					((MovableTerrain) objects.get(i)).activate();
				}
			}
		}
	}

	@Override
	public void setTrigger(String text, float textX, float textY) {

	}

	@Override
	public void unTriggered() {

	}
}
