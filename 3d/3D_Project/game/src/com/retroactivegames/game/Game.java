package com.retroactivegames.game;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import devan.core.Camera;
import devan.core.actors.Player;
import devan.core.actors.Stage;
import devan.core.world.DynamicWorld;
import devan.core.world.StaticWorld;
import devan.core.world.WorldRenderer;
import devan.drawing.GraphicsData;
import devan.geometry.Point;
import devan.geometry.Vector;
import devan.init.Generator;
import devan.init.OpenGL;
import devan.rendering.SectorVision2D;

public class Game implements ApplicationListener {
			
	private GameRenderer 		gameRenderer;
	//private PlayerController 	controller;
	private WorldUpdateThread 	updater;
	//private Thread 				updateThread;
	
	private final Color WALL_COLOR 				= Color.LIGHT_GRAY;
	private final int 	NUMBER_OF_WALLS 		= 4;
	private final int 	NUMBER_OF_ACTORS 		= 2;
	private final int 	NUMBER_OF_GAME_OBJECTS 	= 2;
	private final float ACTOR_RADIUS 			= 8;
	private final float GAME_OBJECT_RADIUS 		= 2;
	private final float PLAYER_Z_POS 			= 0;
	private final float PLAYER_CAMERA_Z_OFFSET	= 1;
	
	//private final Interval WALL_HEIGHT 	= new Interval(1,0);
	
	@Override
	public void create() {		

		DynamicWorld  world 	= new DynamicWorld(new StaticWorld(Generator.Walls(NUMBER_OF_WALLS, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), WALL_COLOR), new Stage()));
		//DynamicWorld  world 	= new DynamicWorld(Generator.createStaticWorld(NUMBER_OF_WALLS, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), WALL_COLORS, WALL_HEIGHT));
		WorldRenderer renderer 	= new WorldRenderer(world, new SectorVision2D(500));
		//WorldRenderer renderer 	= new WorldRenderer(world, new RayCaster2D(90, 500));
		
		world.cast.add(Generator.Actors(NUMBER_OF_ACTORS, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), ACTOR_RADIUS));
		world.staticWorld.STAGE.add(Generator.GameObjects(NUMBER_OF_GAME_OBJECTS, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), GAME_OBJECT_RADIUS));
		
		Player player 		= new Player(new Vector(new Point(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, PLAYER_Z_POS), 0, 0), ACTOR_RADIUS);
		Camera playerCamera = new Camera(player);
		playerCamera.zOffset = PLAYER_CAMERA_Z_OFFSET;
		world.cast.add(player);
		
		updater			= new WorldUpdateThread(120, world);
		//controller = new PlayerController(player);
		gameRenderer 	= new GameRenderer(renderer, new GraphicsData(), playerCamera);
		
		//Camera actorCam = new Camera(world.cast.actors.get(0));
		//gameRenderer 	= new GameRenderer(renderer, new GraphicsData(), actorCam);
		
		OpenGL.initialize(Gdx.gl);
		
		new PlayerController(player);
		
		//updateThread = new Thread(updater);
		//updateThread.start();
	}

	@Override
	public void dispose() {
		gameRenderer.dispose();
	}

	
	@Override
	public void render() {
		gameRenderer.render();
		
		// Temporary
		updater.world.update();
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
