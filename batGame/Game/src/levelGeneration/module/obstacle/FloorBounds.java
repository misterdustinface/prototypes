package levelGeneration.module.obstacle;

public class FloorBounds extends ObstacleBounds {
	public FloorBounds(ObstacleGenerator generator, int minHeight, int maxHeight, int minWidth, int maxWidth, int spaceFromPrevious) {
		super(generator, minHeight, maxHeight, minWidth, maxWidth, spaceFromPrevious);
	}

	@Override
	public ObstacleDef generateDef(int previousEnd) {

		int height = generateRandomBetween(minHeight, maxHeight);
		int width  = generateRandomBetween(minWidth, maxWidth);
		
		return new FloorDef(generator, height, width, previousEnd + spaceFromPrevious);
	}
}
