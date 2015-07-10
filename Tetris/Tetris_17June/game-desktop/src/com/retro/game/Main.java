package com.retro.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.retroactivegames.game.Game;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Tetris";
		cfg.width  = 400;
		cfg.height = (int)(400*2.2f);
		
		new LwjglApplication(new Game(), cfg);
	}
}