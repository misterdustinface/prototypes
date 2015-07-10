package devan.input.button;

import java.util.HashSet;
import java.util.LinkedList;

class VirtualButtonKey implements RawButtonPressedListener, RawButtonReleasedListener {
	private HashSet<Integer> keysPressed;
	private LinkedList<VirtualButtonReleasedListener> vKeysReleasedListener;
	private LinkedList<VirtualButtonPressedListener> vKeysPressedListener;
	
	VirtualButtonKey() {
		keysPressed = new HashSet<Integer>();
		vKeysReleasedListener = new LinkedList<VirtualButtonReleasedListener>();
		vKeysPressedListener = new LinkedList<VirtualButtonPressedListener>();
	}
		
	public boolean isPressed() {
		return keysPressed.size() > 0;
	}

	public boolean isRelased() {
		return keysPressed.size() <= 0;
	}
	
	@Override
	public boolean performPressedKeyAction(int keyCode) {	
		for(VirtualButtonPressedListener vKeyPressedListener : vKeysPressedListener) {
			vKeyPressedListener.keyPressed();
		}
		
		keysPressed.add(keyCode);
		
		return true;
	}

	@Override
	public boolean performReleasedKeyAction(int keyCode) {
		for(VirtualButtonReleasedListener vKeyRelasedListener: vKeysReleasedListener) {
			vKeyRelasedListener.keyReleased();
		}		
		
		keysPressed.remove(keyCode);
		return true;
	}
	
	public void addKeyPressedListener(VirtualButtonPressedListener listener) {
		vKeysPressedListener.add(listener);
	}
	
	public void addKeyRelasedListener(VirtualButtonReleasedListener listener) {
		vKeysReleasedListener.add(listener);
	}
}
