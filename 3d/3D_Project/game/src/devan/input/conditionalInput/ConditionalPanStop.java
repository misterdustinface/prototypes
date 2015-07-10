package devan.input.conditionalInput;

import devan.input.VGesture.PanStopListener;

public class ConditionalPanStop implements PanStopListener{
	private Conditional conditional;
	private PanStopListener listener;
	
	public ConditionalPanStop(Conditional conditional, PanStopListener listener) {
		this.conditional = conditional;
		this.listener = listener;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		if(conditional.isConditionMet()) {
			listener.panStop(x, y, pointer, button);
		}
		return false;
	}
}
