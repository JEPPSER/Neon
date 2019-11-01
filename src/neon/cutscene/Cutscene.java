package neon.cutscene;

import neon.camera.Camera;
import neon.camera.CameraScript;
import neon.time.TimeInfo;

public class Cutscene {
	
	private CameraScript cameraScript;
	private boolean isRunning = false;
	private int ellapsedTime = 0;
	private int duration;
	
	public Cutscene(int duration) {
		this.duration = duration;
	}

	public CameraScript getCameraScript() {
		return cameraScript;
	}

	public void setCameraScript(CameraScript cameraScript) {
		this.cameraScript = cameraScript;
	}
	
	public void startCutscene() {
		ellapsedTime = 0;
		isRunning = true;
	}
	
	public void updateCutscene(Camera camera) {
		ellapsedTime += TimeInfo.getDelta();
		cameraScript.updateScript(ellapsedTime, camera);
		if (ellapsedTime > duration) {
			isRunning = false;
		}
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
