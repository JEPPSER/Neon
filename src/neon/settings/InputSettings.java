package neon.settings;

import java.util.HashMap;

public class InputSettings {
	
	private static HashMap<String, Integer> controllerBinds;
	private static HashMap<String, Integer> keyboardBinds;

	public static void init() {
		controllerBinds = new HashMap<String, Integer>();
		keyboardBinds = new HashMap<String, Integer>();
		
		controllerBinds.put("jump", 4);
		controllerBinds.put("dash", 6);
		controllerBinds.put("punch", 9);
		controllerBinds.put("left", 0);
		controllerBinds.put("right", 1);
	}
	
	public static HashMap<String, Integer> getControllerBinds() {
		return controllerBinds;
	}

	public static HashMap<String, Integer> getKeyboardBinds() {
		return keyboardBinds;
	}
}
