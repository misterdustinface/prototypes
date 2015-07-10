package com.retroactivegames.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import devan.input.button.VirtualButtonPressedListener;

public class DebugController extends TetrominoeController{
	private TetrisWorldRenderer renderer;
	
	public DebugController(TetrisWorld WORLD, TetrisWorldRenderer RENDERER){
		super(WORLD);
		renderer = RENDERER;
		setPlayerControls();
	}
	
	protected void setPlayerControls(){
		super.setPlayerControls();

		vInput.mapVKey("increase tile spacing", Keys.NUM_2);
		vInput.mapVKey("decrease tile spacing", Keys.NUM_1);
		vInput.mapVKey("zero tile spacing", 	Keys.NUM_0);

		vInput.mapVKey("shuffle colors", 		Keys.NUM_3);
		
		vInput.addKeyPressed("decrease tile spacing", new VirtualButtonPressedListener(){
			@Override
			public void keyPressed() {
				--BlockGridRenderer.blockOffset;
			}
		});
		
		vInput.addKeyPressed("increase tile spacing", new VirtualButtonPressedListener(){
			@Override
			public void keyPressed() {
				++BlockGridRenderer.blockOffset;
			}
		});
		
		vInput.addKeyPressed("zero tile spacing", new VirtualButtonPressedListener(){
			@Override
			public void keyPressed() {
				BlockGridRenderer.blockOffset = 0;
			}
		});
		
		vInput.addKeyPressed("shuffle colors", new VirtualButtonPressedListener(){
			@Override
			public void keyPressed() {
				renderer.shuffleColors();
			}
		});
		
		Gdx.input.setInputProcessor(inputDriver.getInputProcessor());
	}
}
