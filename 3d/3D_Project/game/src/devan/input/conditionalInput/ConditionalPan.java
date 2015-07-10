package devan.input.conditionalInput;

import devan.input.VGesture.PanListener;

public class ConditionalPan implements PanListener{
	private Conditional conditional;
	private PanListener listener;
	
	public ConditionalPan(Conditional conditional, PanListener listener) {
		this.conditional = conditional;
		this.listener = listener;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if(conditional.isConditionMet()) {
			listener.pan(x, y, deltaX, deltaY);
		}
		return false;
	}
}
