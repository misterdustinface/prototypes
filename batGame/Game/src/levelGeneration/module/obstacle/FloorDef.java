package levelGeneration.module.obstacle;

import collisionDetection.Buffer;

public class FloorDef extends ObstacleDef {

	public FloorDef(ObstacleGenerator generator, int height, int width, int startingX) {
		super(generator, height, width, startingX);
	}

	@Override
	public void generate(Buffer upperBuffer, Buffer lowerBuffer) {		
		Buffer generated = generator.generate(getChunk(lowerBuffer), height);
		
		lowerBuffer.overwrite(startingX, generated);
	}
}
