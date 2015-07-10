package devan.input.VGesture;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

import devan.lua.GdxGestureListener;

public class VirtualGestureInput implements GdxGestureListener {
	
	private LinkedList<TouchUpListener>   touchUpListeners;
	private LinkedList<TouchDownListener> touchDownListeners;
	private LinkedList<TapListener>       tapListeners;
	private LinkedList<LongPressListener> longPressListeners;
	private LinkedList<FlingListener>     flingListeners;
	private LinkedList<PanListener>       panListeners;
	private LinkedList<PanStopListener>   panStopListeners;
	private LinkedList<ZoomListener>      zoomListeners;
	private LinkedList<PinchListener>     pinchListeners;

	public VirtualGestureInput() {
		touchUpListeners   = new LinkedList<TouchUpListener>();
		touchDownListeners = new LinkedList<TouchDownListener>();	
		tapListeners       = new LinkedList<TapListener>();
		longPressListeners = new LinkedList<LongPressListener>();
		flingListeners     = new LinkedList<FlingListener>();
		panListeners       = new LinkedList<PanListener>();
		panStopListeners   = new LinkedList<PanStopListener>();
		zoomListeners      = new LinkedList<ZoomListener>();
		pinchListeners     = new LinkedList<PinchListener>();
	}
	
	public void addTouchUpListener(TouchUpListener l) 	    {touchUpListeners.add(l);}
	public void addTouchDownListener(TouchDownListener l)   {touchDownListeners.add(l);}
	public void addTapListener(TapListener l) 			    {tapListeners.add(l);}
	public void addLongPressListener(LongPressListener l)   {longPressListeners.add(l);}
	public void addFlingListener(FlingListener l) 		    {flingListeners.add(l);}
	public void addPanListener(PanListener l) 			    {panListeners.add(l);}
	public void addPanStopListener(PanStopListener l) 	    {panStopListeners.add(l);}	
	public void addZoomListener(ZoomListener l) 		    {zoomListeners.add(l);}	
	public void addPinchListener(PinchListener l) 		    {pinchListeners.add(l);}

	public void clearTouchUpListener(TouchUpListener l) 	{touchUpListeners.clear();}
	public void clearTouchDownListener(TouchDownListener l) {touchDownListeners.clear();}
	public void clearTapListener(TapListener l) 			{tapListeners.clear();}
	public void clearLongPressListener(LongPressListener l) {longPressListeners.clear();}
	public void clearFlingListener(FlingListener l) 		{flingListeners.clear();}
	public void clearPanListener(PanListener l) 			{panListeners.clear();}
	public void clearPanStopListener(PanStopListener l) 	{panStopListeners.clear();}	
	public void clearZoomListener(ZoomListener l) 		    {zoomListeners.clear();}	
	public void clearPinchListener(PinchListener l) 		{pinchListeners.clear();}
	
	public boolean touchUp(int x, int y, int pointer, int button) {
		for(TouchUpListener l : touchUpListeners) {
			l.touchUp(x, y, pointer, button);
		}
		return false;
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		for(TouchDownListener l : touchDownListeners) {
			l.touchDown(x, y, pointer, button);
		}
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		for(TapListener l : tapListeners) {
			l.tap(x, y, count, button);
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		for(LongPressListener l : longPressListeners) {
			l.longPress(x, y);
		}
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		for(FlingListener l : flingListeners) {
			l.fling(velocityX, velocityY, button);
		}
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		for(PanListener l : panListeners) {
			l.pan(x, y, deltaX, deltaY);
		}
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		for(PanStopListener l : panStopListeners) {
			l.panStop(x, y, pointer, button);
		}
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		for(ZoomListener l : zoomListeners) {
			l.zoom(initialDistance, distance);
		}
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		for(PinchListener l : pinchListeners) {
			l.pinch(initialPointer1, initialPointer2, pointer1, pointer2);
		}
		return false;
	}
}
