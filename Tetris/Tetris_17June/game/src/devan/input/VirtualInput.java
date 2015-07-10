package devan.input;


import devan.input.VGesture.*;
import devan.input.button.VirtualButtonInput;
import devan.input.button.VirtualButtonPressedListener;
import devan.input.button.VirtualButtonReleasedListener;
import devan.input.conditionalInput.*;

public class VirtualInput {
	private VirtualButtonInput vKeys;
	private VirtualGestureInput gestureInput;
	
	public VirtualInput() {
		vKeys = new VirtualButtonInput();
		gestureInput = new VirtualGestureInput();
	}
	
	public void initDriver(InputDriver driver) {
		driver.setVKeys(vKeys);
		driver.setGestureInput(gestureInput);
	}
	
	public void mapVKey(String vKeyName, int keyCode) {
		vKeys.mapVKey(vKeyName, keyCode);
	}
	
	public boolean isKeyPressed(String vKeyName) {
		return vKeys.isPressed(vKeyName);
	}
	
	public void addConditionalKeyPressed(String triggerKey, ConditionalInputDef condition, VirtualButtonPressedListener listener) {
		vKeys.addKeyPressedListener(triggerKey, new ConditionalVirtualButtonPushed(new ConditionalInput(condition, vKeys), listener));
	}
	
	public void addConditionalKeyReleased(String triggerKey, ConditionalInputDef condition, VirtualButtonReleasedListener listener) {
		vKeys.addKeyReleasedListener(triggerKey, new ConditionalVirtualButtonReleased(new ConditionalInput(condition, vKeys), listener));
	}
	
	public void addConditionalTouchUp(ConditionalInputDef condition, TouchUpListener listener) {
		gestureInput.addTouchUpListener(new ConditionalTouchUp(new ConditionalInput(condition, vKeys), listener));
	}
	
	public void addConditionalTouchDown(ConditionalInputDef condition, TouchDownListener listener) {
		gestureInput.addTouchDownListener(new ConditionalTouchDown(new ConditionalInput(condition, vKeys), listener));
	}
	
	public void addConditionalTap(ConditionalInputDef condition, TapListener listener) {
		gestureInput.addTapListener(new ConditionalTap(new ConditionalInput(condition, vKeys), listener));
	}
	
	public void addConditionalLongPress(ConditionalInputDef condition, LongPressListener listener) {
		gestureInput.addLongPressListener(new ConditionalLongPress(new ConditionalInput(condition, vKeys), listener));
	}
	
	public void addConditionalFling(ConditionalInputDef condition, FlingListener listener) {
		gestureInput.addFlingListener(new ConditionalFling(new ConditionalInput(condition, vKeys), listener));
	}
	
	public void addConditionalPan(ConditionalInputDef condition, PanListener listener) {
		gestureInput.addPanListener(new ConditionalPan(new ConditionalInput(condition, vKeys), listener));
	}
	
	public void addConditionalPanStop(ConditionalInputDef condition, PanStopListener listener) {
		gestureInput.addPanStopListener(new ConditionalPanStop(new ConditionalInput(condition, vKeys), listener));
	}
	
	public void addConditionalZoom(ConditionalInputDef condition, ZoomListener listener) {
		gestureInput.addZoomListener(new ConditionalZoom(new ConditionalInput(condition, vKeys), listener));
	}
	
	public void addConditionalPinch(ConditionalInputDef condition, PinchListener listener) {
		gestureInput.addPinchListener(new ConditionalPinch(new ConditionalInput(condition, vKeys), listener));
	}
	
	public void addKeyPressed(String key, VirtualButtonPressedListener listener) {
		vKeys.addKeyPressedListener(key, listener);
	}
	
	public void addKeyReleased(String key, VirtualButtonReleasedListener listener) {
		vKeys.addKeyReleasedListener(key, listener);
	}	
	
	public void addTouchDown(TouchDownListener listener) {
		gestureInput.addTouchDownListener(listener);
	}
	
	public void addTouchUp(TouchUpListener listener) {
		gestureInput.addTouchUpListener(listener);
	}
	
	public void addTap(TapListener listener) {
		gestureInput.addTapListener(listener);
	}
	
	public void addLongPress(LongPressListener listener) {
		gestureInput.addLongPressListener(listener);
	}
	
	public void addFling(FlingListener listener) {
		gestureInput.addFlingListener(listener);
	}
	
	public void addPan(PanListener listener) {
		gestureInput.addPanListener(listener);
	}
	
	public void addPanStop(PanStopListener listener) {
		gestureInput.addPanStopListener(listener);
	}
	
	public void addZoom(ZoomListener listener) {
		gestureInput.addZoomListener(listener);
	}
	
	public void addPinch(PinchListener listener) {
		gestureInput.addPinchListener(listener);
	}
}
