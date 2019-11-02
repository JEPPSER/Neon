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
		for (int i = 0; i < focalPoints.size() - 4; i+=4) {
			if (ellapsedTime > focalPoints.get(i)) {
				float nextX = focalPoints.get(i + 5);
				float nextY = focalPoints.get(i + 6);
				float nextZoom = focalPoints.get(i + 7);
				float nextTime = focalPoints.get(i + 4);
				float x = focalPoints.get(i + 1);
				float y = focalPoints.get(i + 2);
				float zoom = focalPoints.get(i + 3);
				float difX = nextX - x;
				float difY = nextY - y;
				float difZoom = nextZoom - zoom;
				float difTime = nextTime - focalPoints.get(i);
				float percent = (ellapsedTime - focalPoints.get(i)) / difTime;
				camera.setFocalPoint(new Point(x + difX * percent, y + difY * percent));
				camera.zoom(zoom + difZoom * percent);
			}
		}
	}
}
