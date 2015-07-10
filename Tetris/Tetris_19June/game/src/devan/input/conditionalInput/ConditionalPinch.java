package devan.input.conditionalInput;

import com.badlogic.gdx.math.Vector2;

import devan.input.VGesture.PinchListener;

public class ConditionalPinch implements PinchListener{
	private Conditional conditional;
	private PinchListener listener;
	
	public ConditionalPinch(Conditional conditional, PinchListener listener) {
		this.conditional = conditional;
		this.listener = listener;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		if(conditional.isConditionMet()) {
			listener.pinch(initialPointer1, initialPointer2, pointer1, pointer2);
		}
		return false;
	}
}
