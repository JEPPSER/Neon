package neon.cutscene;

import java.util.ArrayList;

import neon.camera.Camera;
import neon.camera.CameraScript;

public class CutsceneManager {
	
	private ArrayList<Cutscene> cutscenes;
	
	public CutsceneManager() {
		cutscenes = new ArrayList<Cutscene>();
	}
	
	public void updateCutScenes(Camera camera) {
		for (int i = 0; i < cutscenes.size(); i++) {
			if (cutscenes.get(i).isRunning()) {
				cutscenes.get(i).updateCutscene(camera);
			}
		}
	}
}
