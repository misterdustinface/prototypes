package spikes.movement;


import lua.LuaScriptManager;

import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import com.badlogic.gdx.ApplicationListener;

public class BoundsMovementSpike implements ApplicationListener {

	WorldBounds world;
	WorldBoundsRenderer renderer;
	LuaScriptManager lua;
	
	@Override
	public void create() {
		Globals.init();
		
		world = new WorldBounds();
		renderer = new WorldBoundsRenderer(world);
		lua = Globals.lua;
		
		lua.set("world", CoerceJavaToLua.coerce(world));
		lua.loadFile("spikes/movement/Input.lua");
	}

	@Override
	public void resize(int width, int height) {		
	}

	@Override
	public void render() {
		world.update();
		renderer.render();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {	

	}

	public void reload() {
		renderer.dispose();
		create();
	}
	
	@Override
	public void dispose() {	
		renderer.dispose();
	}

}
