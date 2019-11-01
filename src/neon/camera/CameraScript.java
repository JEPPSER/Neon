package neon.camera;

import java.util.ArrayList;

import neon.graphics.Point;

public class CameraScript {
	
	private ArrayList<Float> focalPoints;
	
	public CameraScript() {
		focalPoints = new ArrayList<Float>();
	}
	
	public void addFocalPoint(float time, float x, float y, float zoom) {
		focalPoints.add(time);
		focalPoints.add(x);
		focalPoints.add(y);
		focalPoints.add(zoom);
	}
	
	public ArrayList<Float> getFocalPoints() {
		return focalPoints;
	}
	
	public void updateScript(int ellapsedTime, Camera camera) {
		for (int i = 0; i < focalPoints.size(); i+=4) {
			if (ellapsedTime > focalPoints.get(i)) {
				float x = focalPoints.get(i + 1);
				float y = focalPoints.get(i + 2);
				float zoom = focalPoints.get(i + 3);
				camera.setFocalPoint(new Point(x, y));
				camera.zoom(zoom);
			}
		}
	}
}
