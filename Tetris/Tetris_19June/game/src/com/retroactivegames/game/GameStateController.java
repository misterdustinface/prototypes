package com.retroactivegames.game;

import com.badlogic.gdx.Input.Keys;

import devan.input.VirtualInput;
import devan.input.button.VirtualButtonPressedListener;

final public class GameStateController {
	
	private GameStateController(){}
	
	public static void setControls(VirtualInput vInput){
		vInput.mapVKey("Pause", Keys.P);
		vInput.addKeyPressed("Pause", new VirtualButtonPressedListener(){
			@Override
			public void keyPressed() {
				GameState.togglePause();
			}
		});
	}
	
}
