package spikes.graphicsGenerator;


import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import com.badlogic.gdx.ApplicationListener;
import com.retro.bat.BatGame;

public class GraphicsGeneratorSpike extends BatGame implements ApplicationListener{
	
	@Override
	public void create() {
		super.create();
		
		gameWorld 	= new GraphicsSpikeWorld();
		renderer 	= new GraphicsSpikeWorldRenderer((GraphicsSpikeWorld)gameWorld);

		lua.set("world", CoerceJavaToLua.coerce(gameWorld));
		lua.loadFile("spikes/graphicsGenerator/Input.lua");
	}


}
