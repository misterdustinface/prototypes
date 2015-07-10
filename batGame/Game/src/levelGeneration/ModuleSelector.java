package levelGeneration;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

import collisionDetection.Buffer;
import levelGeneration.module.ModuleBounds;
import levelGeneration.module.ModuleDef;

public class ModuleSelector {
	private ArrayList<ModuleBounds> modules;
	
	public ModuleSelector() {
		modules = new ArrayList<ModuleBounds>();
	}
	
	public void generateNextModule(BoundGenerator lowerBoundsGenerator, BoundGenerator upperBoundsGenerator, Buffer upperBuffer, Buffer lowerBuffer) {
		ModuleDef module = getNextModule();
		
		module.genereate(lowerBoundsGenerator, upperBoundsGenerator, upperBuffer, lowerBuffer);
		Gdx.app.log("ModuleSelector", "Generated");
	}

	public void addModule(ModuleBounds module) {
		modules.add(module);
	}
	
	private ModuleDef getNextModule() {
		return modules.get(0).genereateModule();
	}
}
