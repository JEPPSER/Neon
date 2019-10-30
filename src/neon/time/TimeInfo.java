package neon.time;

public class TimeInfo {
	
	private static int delta;
	
	public static void setDelta(int delta) {
		TimeInfo.delta = delta;
	}
	
	public static int getDelta() {
		return delta;
	}
}
