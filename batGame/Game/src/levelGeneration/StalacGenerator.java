package levelGeneration;

import levelGeneration.module.obstacle.ObstacleGenerator;
import collisionDetection.Buffer;

public class StalacGenerator implements ObstacleGenerator{
	
	/**
	 * O(n/2 + c)
	 * @param subBuffer
	 * @param STALAC_HEIGHT
	 * @return modified subBuffer
	 */
	public Buffer generate(Buffer subBuffer, final int STALAC_HEIGHT){
		
		final int START_HEIGHT 	= subBuffer.get(0);
		final int END_HEIGHT   	= subBuffer.getLast();
//		
		final int STALAC_WIDTH 	= subBuffer.getSize();
		final int CENTER_X 		= STALAC_WIDTH/2;
		
		final float MAGIC_SCALE = 1 + (float) STALAC_WIDTH/ Math.abs(STALAC_HEIGHT);
		
		int height;
		
		for(int i = 0; i < CENTER_X; ++i){
			height = (int)(i/(float)STALAC_WIDTH * MAGIC_SCALE * STALAC_HEIGHT);
			
			subBuffer.setValue(i, 						subBuffer.get(i) 					+ height);
			subBuffer.setValue(STALAC_WIDTH - 1 - i, 	subBuffer.get(STALAC_WIDTH - 1 - i)	+ height);
		}
		
		//subBuffer.setValue(CENTER_X, subBuffer.get(CENTER_X) + STALAC_HEIGHT);
		
		return subBuffer;
	}
	
}
