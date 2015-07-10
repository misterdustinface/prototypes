package levelGeneration.module;

import java.util.LinkedList;

import collisionDetection.Buffer;
import levelGeneration.BoundGenerator;
import levelGeneration.module.obstacle.ObstacleDef;

public class ModuleDef {
	private int width;
	private LinkedList<ObstacleDef> staticObstacles;
	
	public ModuleDef(LinkedList<ObstacleDef> staticObstacles, int width) {
		this.staticObstacles = staticObstacles;
		this.width = width;
	}
	
	public void genereate(BoundGenerator lowerBoundsGenerator, BoundGenerator upperBoundsGenerator, Buffer upperBuffer, Buffer lowerBuffer) {
		Buffer generatedUpper = upperBoundsGenerator.getLine(width);
		Buffer generatedLower = lowerBoundsGenerator.getLine(width);
		
		for(ObstacleDef def : staticObstacles) {
			def.generate(generatedUpper, generatedLower);
		}
		
		upperBuffer.append(generatedUpper);
		lowerBuffer.append(generatedLower);
	}
}