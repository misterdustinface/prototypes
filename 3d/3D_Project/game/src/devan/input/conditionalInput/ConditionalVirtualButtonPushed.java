package devan.input.conditionalInput;

import devan.input.button.VirtualButtonPressedListener;

public class ConditionalVirtualButtonPushed implements VirtualButtonPressedListener{
	Conditional conditional;
	VirtualButtonPressedListener listener;
	
	public ConditionalVirtualButtonPushed(Conditional conditional, VirtualButtonPressedListener listener) {
		this.conditional = conditional;
		this.listener = listener;
	}

	@Override
	public void keyPressed() {		
		if(conditional.isConditionMet()) {
			listener.keyPressed();
		}
	}
}