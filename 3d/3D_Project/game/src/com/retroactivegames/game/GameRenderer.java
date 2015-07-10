package com.retroactivegames.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Disposable;

import devan.core.Camera;
import devan.core.world.WorldRenderer;
import devan.drawing.GraphicsData;

/**
 * @author dustin
 */
public class GameRenderer implements Disposable{

	private WorldRenderer 	renderer;
	private GraphicsData 	rendererData;
	private Camera 			playerCamera;
	
	public GameRenderer(WorldRenderer WR, GraphicsData GD, Camera C){
		playerCamera = C;
		renderer = WR;
		rendererData = GD;
	}
	
	public void render(){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.renderTopDownView(rendererData);
		renderer.renderTopDownViewFromPerspectiveDEBUG(playerCamera, rendererData, Color.GREEN);
		
		renderer.render1stPerson(playerCamera, rendererData);
		renderer.render1stPersonDEBUG(playerCamera, rendererData, Color.ORANGE);
		
		renderer.renderEye(rendererData);
	}
	
	@Override
	public void dispose() {
		rendererData.dispose();
	}
}
