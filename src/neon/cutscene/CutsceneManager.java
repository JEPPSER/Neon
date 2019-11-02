package neon.cutscene;

import java.util.ArrayList;

import neon.camera.Camera;

public class CutsceneManager {
	
	private ArrayList<Cutscene> cutscenes;
	
	public CutsceneManager() {
		cutscenes = new ArrayList<Cutscene>();
	}
	
	public ArrayList<Cutscene> getCutscenes() {
		return cutscenes;
	}
	
	public void updateCutScenes(Camera camera) {
		for (int i = 0; i < cutscenes.size(); i++) {
			if (cutscenes.get(i).isRunning()) {
				cutscenes.get(i).updateCutscene(camera);
			}
		}
	}
	
	public boolean isCutsceneRunnging() {
		for (int i = 0; i < cutscenes.size(); i++) {
			if (cutscenes.get(i).isRunning()) {
				return true;
			}
		}
		return false;
	}
}
