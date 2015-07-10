package com.retroactivegames.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import devan.input.button.VirtualButtonPressedListener;
import devan.input.button.VirtualButtonReleasedListener;

public class DebugController extends TetrisWorldController{
	private TetrisWorldRenderer renderer;
	
	public DebugController(TetrisWorld WORLD, TetrisWorldRenderer RENDERER){
		super(WORLD);
		renderer = RENDERER;
		setControls();
	}
	
	protected void setControls(){
		super.setControls();

		vInput.mapVKey("increase tile spacing", Keys.NUM_2);
		vInput.mapVKey("decrease tile spacing", Keys.NUM_1);
		vInput.mapVKey("zero tile spacing", 	Keys.NUM_0);

		vInput.mapVKey("shuffle colors", 		Keys.NUM_3);
		
		vInput.mapVKey("Show FPS", Keys.F);
		
		vInput.addKeyPressed("Show FPS", new VirtualButtonPressedListener(){
			@Override
			public void keyPressed() {
				GameState.showFPS = true;
			}
		});
		vInput.addKeyReleased("Show FPS", new VirtualButtonReleasedListener(){
			@Override
			public void keyReleased() {
				GameState.showFPS = false;
			}
		});
		
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
