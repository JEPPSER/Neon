package neon.settings;

import java.util.HashMap;

import org.newdawn.slick.Input;

public class InputSettings {
	
	private static HashMap<String, Integer> controllerBinds;
	private static HashMap<String, Integer> keyboardBinds;

	public static void init() {
		controllerBinds = new HashMap<String, Integer>();
		keyboardBinds = new HashMap<String, Integer>();
		
		controllerBinds.put("jump", 4);
		controllerBinds.put("dash", 8);
		controllerBinds.put("punch", 9);
		controllerBinds.put("left", 0);
		controllerBinds.put("right", 1);
		
		keyboardBinds.put("jump", Input.KEY_SPACE);
		keyboardBinds.put("dash", Input.KEY_LSHIFT);
		keyboardBinds.put("punch", Input.KEY_J);
		keyboardBinds.put("left", Input.KEY_A);
		keyboardBinds.put("right", Input.KEY_D);
		keyboardBinds.put("up", Input.KEY_W);
		keyboardBinds.put("down", Input.KEY_S);
		keyboardBinds.put("action", Input.KEY_J);
	}
	
	public static HashMap<String, Integer> getControllerBinds() {
		return controllerBinds;
	}

	public static HashMap<String, Integer> getKeyboardBinds() {
		return keyboardBinds;
	}
}
