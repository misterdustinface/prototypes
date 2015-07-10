package devan.input;

import devan.input.VGesture.VirtualGestureInput;
import devan.input.button.VirtualButtonInput;

public abstract class InputDriver {
	protected VirtualButtonInput vKeys;
	protected VirtualGestureInput gestureInput;
	
	void setVKeys(VirtualButtonInput vKeys) {
		this.vKeys = vKeys;
	}
	
	void setGestureInput(VirtualGestureInput gestureInput) {
		this.gestureInput = gestureInput;
	}
}
