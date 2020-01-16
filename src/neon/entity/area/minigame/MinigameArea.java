package neon.entity.area.minigame;

import org.newdawn.slick.Input;

import neon.entity.area.Trigger;
import neon.entity.controllable.Player;
import neon.graphics.Point;
import neon.level.LevelManager;

public abstract class MinigameArea extends Trigger {
	
	protected boolean isDone = false;

	@Override
	public void triggered() {
		if (!isTriggered && !isDone) {
			Player player = LevelManager.getLevel().getPlayer();
			player.setMinigame(this);
			player.getPhysics().setXVelocity(0);
			player.getPhysics().setYVelocity(0);
			player.setX(x + player.getWidth());
			player.setY(y + 20);
			start();
			isTriggered = true;
			LevelManager.getLevel().getCamera().setFocalPoint(new Point(x + width / 2, y + height / 2));
		}
	}

	@Override
	public void setTrigger(String text, float textX, float textY) {

	}

	@Override
	public void unTriggered() {
		if (isTriggered) {
			Player player = LevelManager.getLevel().getPlayer();
			player.setMinigame(null);
			end();
			isTriggered = false;
			LevelManager.getLevel().getCamera().setFocalPoint(null);
		}
	}
	
	public abstract void start();
	public abstract void end();
	public abstract void update(Input input, Player player);
}
