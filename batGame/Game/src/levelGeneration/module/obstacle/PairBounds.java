package levelGeneration.module.obstacle;

public class PairBounds extends ObstacleBounds {

	private ObstacleGenerator upperGenerator;
	private int minHeightUpper, maxHeightUpper;
	
	
	public PairBounds(ObstacleGenerator upperGenerator, ObstacleGenerator lowerGenerator,
			int minHeightLower, int maxHeightLower, int minHeightUpper, int maxHeightUpper, int minWidth, int maxWidth, int spaceFromPrevious) {
		super(lowerGenerator, minHeightLower, maxHeightLower, minWidth, maxWidth, spaceFromPrevious);
		
		this.upperGenerator = upperGenerator;
		this.minHeightUpper = minHeightUpper;
		this.maxHeightUpper = maxHeightUpper;
	}

	@Override
	public ObstacleDef generateDef(int previousEnd) {
		int heightUpper = generateRandomBetween(minHeightUpper, maxHeightUpper);
		int heightLower = generateRandomBetween(minHeight, maxHeight);
		int width  = generateRandomBetween(minWidth, maxWidth);
		
		return new PairDef(generator, upperGenerator, heightLower, heightUpper, width, previousEnd + spaceFromPrevious);
	}
}
