package spikes.graphicsGenerator;

import lua.LuaScriptManager;

import org.luaj.vm2.LuaValue;

public class Globals {
	static LuaScriptManager lua = new LuaScriptManager();
	
	static float WORLD_Floor;
	static float WORLD_Ceiling;
	static float WORLD_Bounds_Width;
	static float WORLD_Bounds_Gap;
	
	static float CURSOR_SPAWN_X;
	static float CURSOR_SPAWN_Y;
	static float CURSOR_Move_Speed;
	
	static void init() {

		lua.loadFile("spikes/graphicsGenerator/Globals.lua");
		LuaValue globals = lua.get("Globals");
		
		WORLD_Floor = globals.get("WORLD_Floor").tofloat();
		WORLD_Ceiling = globals.get("WORLD_Ceiling").tofloat();
		WORLD_Bounds_Width = globals.get("WORLD_Bounds_Width").tofloat();
		WORLD_Bounds_Gap = globals.get("WORLD_Bounds_Gap").tofloat();
		
		CURSOR_SPAWN_X = globals.get("CURSOR_SPAWN_X").tofloat();
		CURSOR_SPAWN_Y = globals.get("CURSOR_SPAWN_Y").tofloat();
		CURSOR_Move_Speed = globals.get("CURSOR_Move_Speed").tofloat();
	}
}