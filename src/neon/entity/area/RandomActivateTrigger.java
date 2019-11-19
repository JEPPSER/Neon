package neon.entity.area;

import java.util.ArrayList;
import java.util.Random;

import neon.entity.Entity;
import neon.entity.terrain.movable.MovableTerrain;
import neon.level.LevelManager;
import neon.time.TimeInfo;

public class RandomActivateTrigger extends ActivateMovableTrigger {
	
	private int timeBetweenActivate;
	private Random rand;
	private int time = 0;
	private ArrayList<MovableTerrain> objects;

	public RandomActivateTrigger(String activateName, int timeBetweenActivate) {
		super(activateName, false);
		name = "RandomActivateTrigger";
		objects = new ArrayList<MovableTerrain>();
		rand = new Random();
		this.timeBetweenActivate = timeBetweenActivate;
	}
	
	@Override
	public int getID() {
		return 15;
	}
	
	@Override
	public void triggered() {
		if (!isTriggered) {
			isTriggered = true;
			time = 0;
			ArrayList<Entity> list = LevelManager.getLevel().getObjects();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof MovableTerrain && list.get(i).getName().equals(activateName)) {
					objects.add((MovableTerrain) list.get(i));
				}
			}
		}
		if (objects.size() > 0) {
			time += TimeInfo.getDelta();
			if (time > timeBetweenActivate) {
				time = 0;
				int index = rand.nextInt(objects.size());
				objects.get(index).activate();
				objects.remove(index);
			}
		}
	}
}
