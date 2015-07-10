package levelGeneration.module.obstacle;

import collisionDetection.Buffer;

public class PairDef extends ObstacleDef {
	private int heightUpper;
	private ObstacleGenerator upperGenerator;
	
	public PairDef(ObstacleGenerator lowerGenerator, ObstacleGenerator upperGenerator, int heightLower, int heightUpper, int width, int startingX) {
		super(lowerGenerator, heightLower, width, startingX);
		this.upperGenerator = upperGenerator;
		this.heightUpper = heightUpper;
	}

	@Override
	public void generate(Buffer upperBuffer, Buffer lowerBuffer) {		
		Buffer generatedUpper = upperGenerator.generate(getChunk(upperBuffer), heightUpper);
		Buffer generatedLower = generator.generate(getChunk(lowerBuffer), height);

		upperBuffer.overwrite(startingX, generatedUpper);
		lowerBuffer.overwrite(startingX, generatedLower);
	}
}
