package spikes.movement;

import java.util.ArrayList;

import levelGeneration.BoundGenerator;
import levelGeneration.StalacGenerator;
import levelGeneration.module.ModuleBounds;
import levelGeneration.module.ModuleBoundsCreator;
import levelGeneration.module.ModuleCreator;
import levelGeneration.module.obstacle.ObstacleGenerator;
import collisionDetection.Buffer;
import collisionDetection.DynamicObject;
import collisionDetection.handeler.Handeler;

import com.badlogic.gdx.math.Vector2;

public class WorldBounds {
	collisionDetection.Bat bat;
	Buffer caveLower, caveUpper;
	
	ArrayList<DynamicObject> objects;
	
	BoundGenerator ceilingGenerator = new BoundGenerator(300,280);
	BoundGenerator floorGenerator 	= new BoundGenerator(40, 20);
	
	Handeler collisionHandeler;
	
	public WorldBounds() {
		init();
	}
	
	ModuleCreator moduleCreator;
	
	ModuleBounds moduleBounds;

	
	public void init() {
		caveLower = new Buffer();
		caveUpper = new Buffer();
		collisionHandeler = new Handeler();
	//	collisionDetection.Constants.init();
		Globals.init();
		bat = new collisionDetection.Bat();
		objects = new ArrayList<DynamicObject>();
		
		ceilingGenerator.forceHeight(160);
		floorGenerator.forceHeight(160);
		
		caveUpper.append(ceilingGenerator.getLine(500));
		caveLower.append(floorGenerator.getLine(500));
		bat.setLowerBounds(150);
		bat.setXVelocity(1);
		DynamicObject test = new DynamicObject(10, 10);
		test.setLowerBounds(100);
		test.setXVelocity(0);
		test.setYVelocity(0);
		test.setGravity(0);
		test.setxOffset(200);
		objects.add(test);
		
		ObstacleGenerator generator = new StalacGenerator();
		//Testing non random creation
//		moduleCreator = new ModuleCreator();
//		moduleCreator.addCellingObject(generator, -150, 30);
//		moduleCreator.addSpaceUpper(20);
//		moduleCreator.addSpaceLower(10);
//		moduleCreator.addFloorObject(generator, 200, 30);
//		moduleCreator.addPair(generator, generator, 200, -150, 30);
//		moduleCreator.addSpaceBoth(50);
//		for(int i = 0; i < 100; ++i) {
//			moduleCreator.generateModule().genereate(floorGenerator, ceilingGenerator, caveUpper, caveLower);
//		}
		
		
		//Now with randomness give ability to define a pattern that will vary within bounds
		ModuleBoundsCreator boundsCreator = new ModuleBoundsCreator();
		
		int maxHeigth = 100;
		int minHeight = 50;
		int minWidth = 0;
		int maxWidth = 30;
		
		boundsCreator.addCellingObject(generator, -maxHeigth, -minHeight, minWidth, maxWidth, 75);
		boundsCreator.addFloorObject(generator, minHeight, maxHeigth, minWidth, maxWidth, 50);
		boundsCreator.addFloorObject(generator, minHeight, maxHeigth, minWidth, maxWidth, 0);
		boundsCreator.addPair(generator, generator, -maxHeigth, -minHeight, minHeight, maxHeigth, minWidth, maxWidth, 30);

		moduleBounds = boundsCreator.generateModuleBounds();
		
		moduleBounds.genereateModule().genereate(floorGenerator, ceilingGenerator, caveUpper, caveLower);
 	}
	
	public void update() {
		
		
		
//		caveUpper.append(ceilingGenerator.getLine(bat.getXVeloctiy() * 2));
//		caveLower.append(floorGenerator.getLine(bat.getXVeloctiy() * 2));
		
		if(caveLower.size() < 500) {
			moduleBounds.genereateModule().genereate(floorGenerator, ceilingGenerator, caveUpper, caveLower);
		}

		collisionDetection.Updater.updateAll(bat, objects, caveUpper, caveLower, collisionHandeler);
	}
	
	public Vector2 getCenter() {
		return new Vector2(240, Globals.WORLD_Ceiling/2);
	}
	
	public void flap() {
		bat.flap();
	}
	
	public void glide() {
		bat.glide();
	}
	
	public void dive() {
		bat.dive();
	}
}
