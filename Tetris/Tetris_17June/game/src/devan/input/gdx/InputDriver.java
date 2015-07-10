package devan.input.gdx;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import devan.input.button.*;
import devan.lua.GdxGestureListener;

public class InputDriver extends devan.input.InputDriver{

	private GestureDetector gestureDetector;
	private InputMultiplexer gdxInput;

	public InputDriver() {
		gestureDetector = new GestureDetector(mouseInput);
		
		gdxInput = new InputMultiplexer();
		gdxInput.addProcessor(keyInput);
		gdxInput.addProcessor(gestureDetector);
	}
	
	public InputProcessor getInputProcessor() {
		return gdxInput;
	}
	
	public void mapVKey(String vKeyName, int keyCode) {
		vKeys.mapVKey(vKeyName, keyCode);
	}
	
	public void addVKeyPressedListener(String vKeyName, VirtualButtonPressedListener listener) {
		vKeys.addKeyPressedListener(vKeyName, listener);
	}
	
	public void addVKeyReleasedListener(String vKeyName, VirtualButtonReleasedListener listener) {
		vKeys.addKeyReleasedListener(vKeyName, listener);
	}
	
	public boolean isPressed(String vKey) {
		return vKeys.isPressed(vKey);
	}
	
	InputProcessor keyInput = new InputAdapter() {
		@Override
		public boolean keyDown(int keycode) {
			vKeys.rawKeyDown(keycode);
			return false;
		}
	
		@Override
		public boolean keyUp(int keycode) {
			vKeys.rawKeyUp(keycode);
			return false;
		}
		
		@Override
		public boolean touchUp(int x, int y, int pointer, int button) {
			gestureInput.touchUp(x, y, pointer, button);
			return false;
		}
	};
	
	GdxGestureListener mouseInput = new GdxGestureListener() {
		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			gestureInput.touchDown(x, y, pointer, button);
			return false;
		}

		@Override
		public boolean tap(float x, float y, int count, int button) {
			gestureInput.tap(x, y, count, button);
			return false;
		}

		@Override
		public boolean longPress(float x, float y) {
			gestureInput.longPress(x, y);
			return false;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			gestureInput.fling(velocityX, velocityY, button);
			return false;
		}

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			gestureInput.pan(x, y, deltaX, deltaY);
			return false;
		}

		@Override
		public boolean panStop(float x, float y, int pointer, int button) {
			gestureInput.panStop(x, y, pointer, button);
			return false;
		}

		@Override
		public boolean zoom(float initialDistance, float distance) {
			gestureInput.zoom(initialDistance, distance);
			return false;
		}

		@Override
		public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
				Vector2 pointer1, Vector2 pointer2) {
			gestureInput.pinch(initialPointer1, initialPointer2, pointer1, pointer2);
			return false;
		}
	};
}
