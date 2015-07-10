package devan.input.button;

import java.util.HashMap;

public class VirtualButtonInput {
	private RawButtonInput rawKeyInput;
	private HashMap<String, VirtualButtonKey> vKeys;
	
	public VirtualButtonInput() {
		rawKeyInput = new RawButtonInput();
		vKeys = new HashMap<String, VirtualButtonKey>();
	}
	
	public void mapVKey(final String vKeyName, int keyCode) {
		addIfNotThere(vKeyName);
		final VirtualButtonKey vKey = vKeys.get(vKeyName);
		
		rawKeyInput.setKeyDownAction(keyCode, new RawButtonPressedListener() {
			@Override
			public boolean performPressedKeyAction(int keyCode) {
				return vKey.performPressedKeyAction(keyCode);
			}
		});
		
		rawKeyInput.setKeyUpAction(keyCode, new RawButtonReleasedListener() {
			@Override
			public boolean performReleasedKeyAction(int keyCode) {
				return vKey.performReleasedKeyAction(keyCode);
			}
		});
	}
	
	public boolean isPressed(String vKeyName) {
		if(vKeys.containsKey(vKeyName)) {
			return vKeys.get(vKeyName).isPressed();
		} else {
			return false;
		}
	}
	
	public boolean isRelased(String vKeyName) {
		if(vKeys.containsKey(vKeyName)) {
			return vKeys.get(vKeyName).isRelased();
		} else {
			return false;
		}
	}
	
	public void addKeyPressedListener(String vKeyName, VirtualButtonPressedListener listener) {
		addIfNotThere(vKeyName);
		vKeys.get(vKeyName).addKeyPressedListener(listener);
	}
	
	public void addKeyReleasedListener(String vKeyName, VirtualButtonReleasedListener listener) {
		addIfNotThere(vKeyName);
		vKeys.get(vKeyName).addKeyRelasedListener(listener);
	}
	
	public boolean rawKeyDown(int keyCode) {
		return rawKeyInput.keyDown(keyCode);
	}
	
	public boolean rawKeyUp(int keyCode) {
		return rawKeyInput.keyUp(keyCode);
	}
	
	private void addIfNotThere(String vKeyName) {
		if(!vKeys.containsKey(vKeyName)) {
			vKeys.put(vKeyName, new VirtualButtonKey());
		}
	}
}
