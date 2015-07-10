package com.retro.bat;

import java.io.IOException;
import java.io.Writer;

import hud.Hud;
import lua.LuaScriptManager;

import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import rendering.WorldRenderer;
import world.World;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.sun.java_cup.internal.runtime.Scanner;

import debug.PipeStandardOut;

public class BatGame implements ApplicationListener {
	
	protected World gameWorld;
	protected WorldRenderer renderer;
	protected LuaScriptManager lua;
	private InputMultiplexer inputs;
		
	public void addInput(InputProcessor processor) {
		inputs.addProcessor(processor);
	}
	
	@Override
	public void create() {		
		lua = new LuaScriptManager();
		
		lua.addConstant("world", CoerceJavaToLua.coerce(world.Constants.class));
		lua.addConstant("hud", CoerceJavaToLua.coerce(hud.Constants.class));
		lua.addConstant("collisionDetection", CoerceJavaToLua.coerce(collisionDetection.Constants.class));
		
		inputs    = new InputMultiplexer();
		gameWorld = new World();
		renderer  = new WorldRenderer(gameWorld);
		
		lua.set("world", CoerceJavaToLua.coerce(gameWorld));
		lua.set("hud", CoerceJavaToLua.coerce(new Hud()));
		Hud.init(lua);
		
		inputs.addProcessor(Hud.getInputProcessor());
		Gdx.input.setInputProcessor(inputs);
		
		PipeStandardOut.setFileWriter(Gdx.files.local("lastRun.txt").write(false));
	}
	
	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		gameWorld.update();
		renderer.render();
		
		Hud.update();
		Hud.render();
	}

	@Override
	public void dispose() {
		renderer.dispose();
		Hud.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		Hud.resize(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
