package neon.entity.area;

import org.newdawn.slick.Graphics;

import neon.camera.Camera;
import neon.level.LevelManager;
import neon.time.TimeInfo;

public class CameraChangeTrigger extends Trigger {

	private float panX;
	private float panY;
	private final int PAN_TIME = 500;
	private int timer = 0;

	public CameraChangeTrigger(float panX, float panY) {
		this.panX = panX;
		this.panY = panY;
		this.name = "CameraChangeTrigger";
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
	}

	@Override
	public int getID() {
		return 20;
	}

	@Override
	public String toString() {
		String str = getID() + "," + x + "," + y + "," + width + "," + height + "," + panX + "," + panY;
		return str;
	}

	@Override
	public void triggered() {
		if (timer <= PAN_TIME) {
			timer += TimeInfo.getDelta();
			float percent = (float) timer / (float) PAN_TIME;
			Camera camera = LevelManager.getLevel().getCamera();
			camera.setPan(-panX * percent, -panY * percent);
		}
	}

	@Override
	public void setTrigger(String text, float textX, float textY) {
	}

	@Override
	public void unTriggered() {
		if (timer > 0) {
			timer -= TimeInfo.getDelta();
			float percent = (float) timer / (float) PAN_TIME;
			Camera camera = LevelManager.getLevel().getCamera();
			camera.setPan(-panX * percent, -panY * percent);
		}
	}
}
