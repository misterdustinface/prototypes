package levelGeneration.module.obstacle;

public abstract class ObstacleBounds {
	protected ObstacleGenerator generator;
	protected int minHeight;
	protected int maxHeight;
	protected int minWidth;
	protected int maxWidth;
	protected int spaceFromPrevious;
	
	public ObstacleBounds(ObstacleGenerator generator, int minHeight, int maxHeight, int minWidth, int maxWidth, int spaceFromPrevious) {
		this.generator = generator;
		this.maxHeight = maxHeight;
		this.minHeight = minHeight;
		this.minWidth = minWidth;
		this.maxWidth = maxWidth;
		this.spaceFromPrevious = spaceFromPrevious;
	}
	
	protected int generateRandomBetween(int min, int max) {
		return (int)(Math.random() * (max - min) + max);
	}
	
	public abstract ObstacleDef generateDef(int previousEnd);
}
