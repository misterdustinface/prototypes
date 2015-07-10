package levelGeneration.module;

import java.util.LinkedList;

import levelGeneration.module.obstacle.CellingBounds;
import levelGeneration.module.obstacle.FloorBounds;
import levelGeneration.module.obstacle.ObstacleBounds;
import levelGeneration.module.obstacle.ObstacleDef;

public class ModuleBounds {
	private LinkedList<ObstacleBounds> bounds;
	
	public ModuleBounds(LinkedList<ObstacleBounds> bounds) {
		this.bounds = bounds;
	}
	
	public ModuleDef genereateModule() {
		int xLower = 0;
		int xUpper = 0;
		
		LinkedList<ObstacleDef> defs = new LinkedList<ObstacleDef>();
		
		for(ObstacleBounds bound : bounds) {
			if(bound instanceof CellingBounds) {
				defs.addLast(bound.generateDef(xUpper));
				xUpper = defs.getLast().getWidth() + defs.getLast().getStartingX();
			} else if(bound instanceof FloorBounds) {
				defs.addLast(bound.generateDef(xLower));
				xLower = defs.getLast().getWidth() + defs.getLast().getStartingX();
			} else {
				if(xLower > xUpper) {
					xUpper = xLower;
				} else {
					xLower = xUpper;
				}
				
				defs.addLast(bound.generateDef(xLower));
				xLower = defs.getLast().getWidth() + defs.getLast().getStartingX();
				xUpper = defs.getLast().getWidth() + defs.getLast().getStartingX();
			}
		}
		
		if(xLower > xUpper) {
			xUpper = xLower;
		} else {
			xLower = xUpper;
		}
		
		return new ModuleDef(defs, xLower);
	}
}
