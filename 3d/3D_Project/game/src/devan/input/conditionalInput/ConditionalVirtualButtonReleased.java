package devan.input.conditionalInput;

import devan.input.button.VirtualButtonReleasedListener;

public class ConditionalVirtualButtonReleased implements VirtualButtonReleasedListener{
	Conditional conditional;
	VirtualButtonReleasedListener listener;
	
	public ConditionalVirtualButtonReleased(Conditional condition, VirtualButtonReleasedListener listener) {
		this.conditional = condition;
		this.listener = listener;
	}

	@Override
	public void keyReleased() {		
		if(conditional.isConditionMet()) {
			listener.keyReleased();
		}
	}
}