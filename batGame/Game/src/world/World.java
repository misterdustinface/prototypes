package world;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

import levelGeneration.BoundGenerator;
import levelGeneration.ModuleSelector;
import levelGeneration.StalacGenerator;
import levelGeneration.module.ModuleBoundsCreator;
import levelGeneration.module.obstacle.ObstacleGenerator;
import collisionDetection.Bat;
import collisionDetection.Buffer;
import collisionDetection.DynamicObject;
import collisionDetection.Updater;
import collisionDetection.handeler.BoundsCollisionListener;
import collisionDetection.handeler.Handeler;

public class World {
	public Bat 						bat;
	public Buffer 					caveLower, caveUpper;
	public BoundGenerator 			ceilingGenerator, floorGenerator;
	public ArrayList<DynamicObject> objects;
	public Handeler collisionHandeler;
	
	private ModuleSelector moduleSector;
	
	public World() {
		init();
	}
	
	public void init() {
		ceilingGenerator 	= new BoundGenerator(Constants.CEILING_GENERATOR_MAX, Constants.CEILING_GENERATOR_MIN);
		floorGenerator 		= new BoundGenerator(Constants.FLOOR_GENERATOR_MAX, Constants.FLOOR_GENERATOR_MIN);
		
		collisionHandeler = new Handeler();
		
		moduleSector = new ModuleSelector();
		
		caveLower = new Buffer();
		caveUpper = new Buffer();
		
		bat 	= new Bat();

		objects = new ArrayList<DynamicObject>();

		ceilingGenerator.forceHeight(Constants.CAVE_START_MINPOINT);
		floorGenerator.forceHeight(Constants.CAVE_START_MINPOINT);
		
		caveUpper.append(ceilingGenerator.getLine(Constants.MIN_BUFFER_SIZE));
		caveLower.append(floorGenerator.getLine(Constants.MIN_BUFFER_SIZE));
		
		bat.setxOffset(Constants.BAT_X_OFFSET);
		bat.setLowerBounds(Constants.BAT_SPAWN_Y);
		bat.setXVelocity(Constants.BAT_SPAWN_X_VELOCITY);
		
		testing();			//Todo delete this
	}
	
	public void update() {
		if(caveLower.size() < Constants.MIN_BUFFER_SIZE) {
			moduleSector.generateNextModule(floorGenerator, ceilingGenerator, caveUpper, caveLower);
		}
		
		Updater.updateAll(bat, objects, caveUpper, caveLower, collisionHandeler);
	}
	
	
	private void testing() {
		collisionHandeler.addBatWithCeilingListener(new BoundsCollisionListener() {
			
			@Override
			public void collisionOccured(DynamicObject object, int ceiling) {
				Gdx.app.log("Collision", "hit celing");

			}
		});
		
		ModuleBoundsCreator test = new ModuleBoundsCreator();
		ObstacleGenerator generator = new StalacGenerator();
		
		int maxHeigth = 100;
		int minHeight = 50;
		int minWidth = 20;
		int maxWidth = 30;
		
		test.addCellingObject(generator, -maxHeigth - 400, -minHeight - 400, minWidth, maxWidth, 75);
		test.addFloorObject(generator, minHeight, maxHeigth, minWidth, maxWidth, 50);
		test.addFloorObject(generator, minHeight, maxHeigth, minWidth, maxWidth, 0);
		test.addPair(generator, generator, -maxHeigth, -minHeight, minHeight, maxHeigth, minWidth, maxWidth, 30);
				
		moduleSector.addModule(test.generateModuleBounds());;
		
	}
}
