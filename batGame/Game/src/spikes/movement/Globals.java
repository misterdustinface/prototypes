package spikes.movement;

import lua.LuaScriptManager;

import org.luaj.vm2.LuaValue;

public class Globals {
	static LuaScriptManager lua = new LuaScriptManager();

	static float BAT_initX;
	static float BAT_initY;
	static float BAT_width;
	static float BAT_height;
	static float BAT_Velocity_X;
	static float BAT_Velocity_Flap;
	static float BAT_Velocity_Glide;
	static float BAT_Gravity_Flap;
	static float BAT_Gravity_Glide;
	static float BAT_Gravity_Dive;
	static float BAT_TIME_FLAP;
	
	static float MAX_X_VELOCITY;
	
	static float WORLD_Floor;
	static float WORLD_Ceiling;
	static float WORLD_SpawnTime;
	static float WORLD_Bounds_Width;
	static float WORLD_Bounds_Gap;
	static float WORLD_Bounds_LowerSpawn;
	static float WORLD_Bounds_UpperSpawn;

	static void init() {

		lua.loadFile("spikes/movement/Globals.lua");
		LuaValue globals = lua.get("Globals");
		
		BAT_initX          = globals.get("BAT_initX").tofloat();
		BAT_initY          = globals.get("BAT_initY").tofloat();
		BAT_width          = globals.get("BAT_width").tofloat();
		BAT_height         = globals.get("BAT_height").tofloat();
		BAT_Velocity_X     = globals.get("BAT_Velocity_X").tofloat();
		BAT_Velocity_Flap  = globals.get("BAT_Velocity_Flap").tofloat();
		BAT_Velocity_Glide = globals.get("BAT_Velocity_Glide").tofloat();
		BAT_Gravity_Flap   = globals.get("BAT_Gravity_Flap").tofloat();
		BAT_Gravity_Glide  = globals.get("BAT_Gravity_Glide").tofloat();
		BAT_Gravity_Dive   = globals.get("BAT_Gravity_Dive").tofloat();
		BAT_TIME_FLAP      = globals.get("BAT_TIME_FLAP").tofloat();
		
		//MAX_X_VELOCITY = globals.get("MAX_X_VELOCITY").tofloat();
		
		WORLD_Floor = globals.get("WORLD_Floor").tofloat();
		WORLD_Ceiling = globals.get("WORLD_Ceiling").tofloat();
		WORLD_SpawnTime = globals.get("WORLD_SpawnTime").tofloat();
		WORLD_Bounds_Width = globals.get("WORLD_Bounds_Width").tofloat();
		WORLD_Bounds_Gap = globals.get("WORLD_Bounds_Gap").tofloat();
		WORLD_Bounds_LowerSpawn = globals.get("WORLD_Bounds_LowerSpawn").tofloat();
		WORLD_Bounds_UpperSpawn = globals.get("WORLD_Bounds_UpperSpawn").tofloat();
	}
}