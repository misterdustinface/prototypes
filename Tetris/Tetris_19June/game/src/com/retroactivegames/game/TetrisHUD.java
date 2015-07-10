package com.retroactivegames.game;

import com.badlogic.gdx.graphics.Color;

import devan.drawing.GraphicsData;

public class TetrisHUD {

	private TetrisWorld 		world;
	private BlockGridRenderer 	nextBlockRenderer;
	
	final private String NEXT_BLOCK_DISPLAY;
	final private String HELD_BLOCK_DISPLAY;
	
	public static String RESET_GAME_MESSAGE = "PRESS F1 TO RESET";
	
	public TetrisHUD(TetrisWorld WORLD, String NEXT_BLOCK_DISPLAY, String HELD_BLOCK_DISPLAY){
		world = WORLD;
		nextBlockRenderer = new BlockGridRenderer();
		this.NEXT_BLOCK_DISPLAY = NEXT_BLOCK_DISPLAY;
		this.HELD_BLOCK_DISPLAY = HELD_BLOCK_DISPLAY;
	}
	
	public void render(GraphicsData g){
		
		if(GameState.showFPS){			
			g.drawText("FPS: " + com.badlogic.gdx.Gdx.graphics.getFramesPerSecond(), 0, g.getDisplay().getCenterY(), Color.WHITE);
		}
		
		if(world.hasLost()){
			g.drawCenteredText(RESET_GAME_MESSAGE, g.getDisplay().getCenterX(), g.getDisplay().getCenterY(), Color.WHITE);
		}else{
			g.setTargetDisplay(NEXT_BLOCK_DISPLAY);
//			g.drawDisplayBounds(Color.BLUE);
			//g.drawText(NEXT_BLOCK_DISPLAY, 0, g.getDisplay().getHeight(), Color.WHITE);
			nextBlockRenderer.renderGrid(g, TetrominoeFactory.peek().currentGrid());	
			
			if(world.hasReservedTetrominoe()){
				g.setTargetDisplay(HELD_BLOCK_DISPLAY);
//				g.drawDisplayBounds(Color.RED);
				//g.drawText(HELD_BLOCK_DISPLAY, 0, g.getDisplay().getHeight(), Color.WHITE);
				nextBlockRenderer.renderGrid(g, world.getReservedTetrominoe().currentGrid());
			}
			g.resetTargetDisplayToMainDisplay();
		}
		
		g.drawText("LEVEL: " + (world.getDifficulty()), 0, 0, Color.WHITE);
		g.drawText("SCORE: " + (world.getNumberOfClearedRows() * 100), 	g.getDisplay().getCenterX(), 0, Color.WHITE);
		
//		g.drawDisplayBounds(Color.GREEN);
	}
	

}
