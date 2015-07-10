package levelGeneration.module.obstacle;

import collisionDetection.Buffer;

public abstract class ObstacleDef {
	protected ObstacleGenerator generator;
	protected int height;
	protected int width;
	protected int startingX;
	
	public ObstacleDef(ObstacleGenerator generator, int height, int width, int startingX) {
		this.generator = generator;
		this.height = height;
		this.width = width;
		this. startingX = startingX;
	}
	
	protected Buffer getChunk(Buffer buffer) {
		return buffer.subBuffer(startingX, startingX + width);
	}
	
	public abstract void generate (Buffer upperBuffer, Buffer lowerBuffer);
	public int getWidth() {
		return width;
	}
	
	public int getStartingX() {
		return startingX;
	}	
}
