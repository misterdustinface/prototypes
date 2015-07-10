package devan.input.button;

import java.util.HashMap;

public class RawButtonInput {
	private HashMap<Integer, RawButtonPressedListener> keysDown;
	private HashMap<Integer, RawButtonReleasedListener> keysUp;
	
	public static boolean DEBUG_KEY_INPUT = true;
	
	RawButtonInput() {
		keysDown  = new HashMap<Integer, RawButtonPressedListener>();
		keysUp    = new HashMap<Integer, RawButtonReleasedListener>();
	}
	
	boolean keyDown(int keyCode) {
		if(keysDown.containsKey(keyCode)) {
			return keysDown.get(keyCode).performPressedKeyAction(keyCode);
		} else {
			nullKeyAction(keyCode, "Key Down");
			return false;
		}
	}
	
	boolean keyUp(int keyCode) {
		if(keysUp.containsKey(keyCode)) {
			return keysUp.get(keyCode).performReleasedKeyAction(keyCode);
		} else {
			nullKeyAction(keyCode, "Key Up");
			return false;
		}
	}
	
	void setKeyDownAction(int keyCode, RawButtonPressedListener action) {
		if(keysDown.containsKey(keyCode)) {
			keysDown.remove(keyCode);
		}
		
		keysDown.put(keyCode, action);
	}
	
	void setKeyUpAction(int keyCode, RawButtonReleasedListener action) {
		if(keysUp.containsKey(keyCode)) {
			keysUp.remove(keyCode);
		}
		
		keysUp.put(keyCode, action);
	}
	
	private void nullKeyAction(int keyCode, String type) {
		if(DEBUG_KEY_INPUT) {
			System.out.println(type + " " + keyCode + " no action");
		}
	}
}
