package levelGeneration.module;

import java.util.LinkedList;

import levelGeneration.module.obstacle.CellingBounds;
import levelGeneration.module.obstacle.CellingDef;
import levelGeneration.module.obstacle.FloorBounds;
import levelGeneration.module.obstacle.FloorDef;
import levelGeneration.module.obstacle.ObstacleBounds;
import levelGeneration.module.obstacle.ObstacleDef;
import levelGeneration.module.obstacle.ObstacleGenerator;
import levelGeneration.module.obstacle.PairBounds;
import levelGeneration.module.obstacle.PairDef;

public class ModuleBoundsCreator {
	int lowerWidth;
	int upperWidth;
	LinkedList<ObstacleBounds> staticObstacles;
	
	public ModuleBoundsCreator() {
		clear();
	}
	
	public void addFloorObject(ObstacleGenerator generator, int minHeight, int maxHeight, int minWidth, int maxWidth, int spaceFromPrevious) {
		staticObstacles.add(new FloorBounds(generator, minHeight, maxHeight, minWidth, maxWidth, spaceFromPrevious));
	}
	
	public void addCellingObject(ObstacleGenerator generator, int minHeight, int maxHeight, int minWidth, int maxWidth, int spaceFromPrevious) {
		staticObstacles.add(new CellingBounds(generator, minHeight, maxHeight, minWidth, maxWidth, spaceFromPrevious));
	}
	
	public void addPair(ObstacleGenerator lowerGenerator, ObstacleGenerator upperGenerator, 
			int minHeightUpper, int maxHeightUpper, int minHeightLower, int maxHeightLower,
			int minWidth, int maxWidth, int spaceFromPrevious) {
		
		staticObstacles.add(new PairBounds(lowerGenerator, upperGenerator,
				minHeightLower,  maxHeightLower, minHeightUpper,  maxHeightUpper,  minWidth,  maxWidth, spaceFromPrevious));
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
	
	public ModuleBounds generateModuleBounds() {
		
		return new ModuleBounds(staticObstacles);
	}
	
	public void clear() {
		staticObstacles = new LinkedList<ObstacleBounds>();
		upperWidth = 0;
		lowerWidth = 0;
	}
}
