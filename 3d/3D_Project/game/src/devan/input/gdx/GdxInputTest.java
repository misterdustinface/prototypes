package devan.input.gdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

import devan.input.VirtualInput;
import devan.input.VGesture.TapListener;
import devan.input.button.VirtualButtonPressedListener;
import devan.input.conditionalInput.Conditional;
import devan.input.conditionalInput.ConditionalInputDef;

public class GdxInputTest implements ApplicationListener {
		
	InputDriver inputDriver;
	VirtualInput vInput;
	
	boolean state = false;
	
	@Override
	public void create() {		
		inputDriver = new InputDriver();
		vInput = new VirtualInput();
		vInput.initDriver(inputDriver);

		vInput.mapVKey("ctrl", Keys.CONTROL_LEFT);
		vInput.mapVKey("alt", Keys.ALT_LEFT);
		vInput.mapVKey("a", Keys.A);
		
		Conditional inState = new Conditional() {
			@Override
			public boolean isConditionMet() {
				return state;
			}
		};
		
		Conditional outOfState = new Conditional() {
			@Override
			public boolean isConditionMet() {
				return !state;
			}
		};
		
		ConditionalInputDef nometa = new ConditionalInputDef();
		nometa.vKeysUp.add("ctrl");
		nometa.vKeysUp.add("alt");
		nometa.otherConditions.add(outOfState);
		
		ConditionalInputDef meta1 = new ConditionalInputDef();
		meta1.vKeysDown.add("ctrl");
		meta1.vKeysUp.add("alt");
		meta1.otherConditions.add(outOfState);
		
		ConditionalInputDef meta2 = new ConditionalInputDef();
		meta2.vKeysUp.add("ctrl");
		meta2.vKeysDown.add("alt");
		meta2.otherConditions.add(outOfState);
		
		ConditionalInputDef meta3 = new ConditionalInputDef();
		meta3.vKeysDown.add("ctrl");
		meta3.vKeysDown.add("alt");
		meta3.otherConditions.add(outOfState);
		
		ConditionalInputDef stateCondition = new ConditionalInputDef();
		stateCondition.otherConditions.add(inState);
		
		vInput.addConditionalKeyPressed("a", nometa, new VirtualButtonPressedListener() {
			@Override
			public void keyPressed() {
				Gdx.app.log("key a pressed", "no meta");
			}
		});

		vInput.addConditionalKeyPressed("a", meta1, new VirtualButtonPressedListener() {
			@Override
			public void keyPressed() {
				Gdx.app.log("key a pressed", "meta1");
			}
		});
		
		vInput.addConditionalKeyPressed("a", meta2, new VirtualButtonPressedListener() {
			@Override
			public void keyPressed() {
				Gdx.app.log("key a pressed", "meta2");
			}
		});
		
		vInput.addConditionalKeyPressed("a", meta3, new VirtualButtonPressedListener() {
			@Override
			public void keyPressed() {
				Gdx.app.log("key a pressed", "meta3");
				Gdx.app.log("Toggle state to", "" + !state);
				state = !state;
			}
		});
		
		vInput.addConditionalTap(nometa, new TapListener() {
			@Override
			public boolean tap(float x, float y, int count, int button) {
				Gdx.app.log("tap", "no meta");
				return false;
			}
		});
		vInput.addConditionalTap(meta1, new TapListener() {
			@Override
			public boolean tap(float x, float y, int count, int button) {
				Gdx.app.log("tap", "meta1");
				return false;
			}
		});
		vInput.addConditionalTap(meta2, new TapListener() {
			@Override
			public boolean tap(float x, float y, int count, int button) {
				Gdx.app.log("tap", "meta2");
				return false;
			}
		});
		vInput.addConditionalTap(meta3, new TapListener() {
			@Override
			public boolean tap(float x, float y, int count, int button) {
				Gdx.app.log("tap", "meta3");
				return false;
			}
		});
		vInput.addConditionalTap(stateCondition, new TapListener() {
			@Override
			public boolean tap(float x, float y, int count, int button) {
				Gdx.app.log("tap", "special state");
				Gdx.app.log("going back to normal state", "");
				state = !state;
				return false;
			}
		});
		
		Gdx.input.setInputProcessor(inputDriver.getInputProcessor());
	}

	@Override
	public void dispose() {

	}
	
	@Override
	public void render() {				
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
