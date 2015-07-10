package devan.input.conditionalInput;

import devan.input.VGesture.TouchDownListener;

public class ConditionalTouchDown implements TouchDownListener{
	Conditional conditional;
	TouchDownListener listener;
	
	public ConditionalTouchDown(Conditional conditional, TouchDownListener listener) {
		this.conditional = conditional;
		this.listener = listener;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if(conditional.isConditionMet()) {
			listener.touchDown(x, y, pointer, button);
		}
		return false;
	}
}
