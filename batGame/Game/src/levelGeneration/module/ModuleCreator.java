package levelGeneration.module;

import java.util.LinkedList;

import levelGeneration.module.obstacle.CellingDef;
import levelGeneration.module.obstacle.FloorDef;
import levelGeneration.module.obstacle.ObstacleDef;
import levelGeneration.module.obstacle.ObstacleGenerator;
import levelGeneration.module.obstacle.PairDef;

public class ModuleCreator {
	int lowerWidth;
	int upperWidth;
	LinkedList<ObstacleDef> staticObstacles;
	
	public ModuleCreator() {
		clear();
	}
	
	public void addFloorObject(ObstacleGenerator generator, int height, int width) {
		staticObstacles.add(new FloorDef(generator, height, width, lowerWidth));
		lowerWidth += width;
	}
	
	public void addCellingObject(ObstacleGenerator generator, int height, int width) {
		staticObstacles.add(new CellingDef(generator, height, width, lowerWidth));
		upperWidth += width;
	}
	
	public void addPair(ObstacleGenerator lowerGenerator, ObstacleGenerator upperGenerator, int heightLower, int heightUpper, int width) {
		allingUpperAndLower();
		
		staticObstacles.add(new PairDef(lowerGenerator, upperGenerator, heightLower, heightUpper, width, lowerWidth));
		
		upperWidth += width;
		lowerWidth += width;
	}
	
	public void addSpaceLower(int spaceAmount) {
		lowerWidth += spaceAmount;
	}
	
	public void addSpaceUpper(int spaceAmount) {
		upperWidth += spaceAmount;
	}
	
	public void addSpaceBoth(int spaceAmount) {
		upperWidth += spaceAmount;
		lowerWidth += spaceAmount;
	}
	
	public void allingUpperAndLower() {
		if(upperWidth > lowerWidth) {
			lowerWidth = upperWidth;
		} else {
			upperWidth = lowerWidth;
		}
	}
	
	public ModuleDef generateModule() {
		int width = lowerWidth > upperWidth ? lowerWidth : upperWidth;
		
		return new ModuleDef(staticObstacles, width);
	}
	
	public void clear() {
		staticObstacles = new LinkedList<ObstacleDef>();
		upperWidth = 0;
		lowerWidth = 0;
	}
}
