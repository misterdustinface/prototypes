package levelGeneration.module.obstacle;

import collisionDetection.Buffer;

public interface ObstacleGenerator {
	public Buffer generate(Buffer subBuffer, final int HEIGHT);
}
