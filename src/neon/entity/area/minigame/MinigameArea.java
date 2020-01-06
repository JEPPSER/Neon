package neon.entity.area.minigame;

import org.newdawn.slick.Input;

import neon.entity.area.Trigger;
import neon.entity.controllable.Player;
import neon.level.LevelManager;

public abstract class MinigameArea extends Trigger {
	
	protected boolean isRunning;

	@Override
	public void triggered() {
		if (!isTriggered) {
			Player player = LevelManager.getLevel().getPlayer();
			player.setMinigame(this);
			player.getPhysics().setXVelocity(0);
			player.getPhysics().setYVelocity(0);
			start();
			isTriggered = true;
		}
	}

	@Override
	public void setTrigger(String text, float textX, float textY) {

	}

	@Override
	public void unTriggered() {
		if (isTriggered) {
			LevelManager.getLevel().getPlayer().setMinigame(null);
			end();
			isTriggered = false;
		}
	}
	
	public abstract void start();
	public abstract void end();
	public abstract void update(Input input, Player player);
}
