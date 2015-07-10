package levelGeneration.module.obstacle;

import collisionDetection.Buffer;

public class CellingDef extends ObstacleDef {
	public CellingDef(ObstacleGenerator generator, int height, int width, int startingX) {
		super(generator, height, width, startingX);
	}

	@Override
	public void generate(Buffer upperBuffer, Buffer lowerBuffer) {		
		Buffer generated = generator.generate(getChunk(upperBuffer), height);
		
		upperBuffer.overwrite(startingX, generated);
	}
}
