package devan.input.conditionalInput;

import devan.input.VGesture.TapListener;

public class ConditionalTap implements TapListener{
	private Conditional conditional;
	private TapListener listener;
	
	public ConditionalTap(Conditional conditional, TapListener listener) {
		this.conditional = conditional;
		this.listener = listener;
	}
	
	@Override
	public boolean tap(float x, float y, int count, int button) {
		if(conditional.isConditionMet()) {
			listener.tap(x, y, count, button);
		}
		return false;
	}

}
