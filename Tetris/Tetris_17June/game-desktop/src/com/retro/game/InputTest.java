package com.retro.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import devan.input.gdx.GdxInputTest;

public class InputTest {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "game";
		cfg.width = 480;
		cfg.height = 320;
		
		new LwjglApplication(new GdxInputTest(), cfg);
	}
}
