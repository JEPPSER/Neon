package neon.time;

public class TimeInfo {
	
	private static int delta;
	private static boolean isPaused;
	
	public static void setDelta(int delta) {
		TimeInfo.delta = delta;
	}
	
	public static int getDelta() {
		return delta;
	}
	
	public static void setPaused(boolean isPaused) {
		TimeInfo.isPaused = isPaused;
	}
	
	public static boolean isPaused() {
		return isPaused;
	}
}
