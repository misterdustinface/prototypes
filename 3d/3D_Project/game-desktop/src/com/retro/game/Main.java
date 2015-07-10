package com.retro.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.retroactivegames.game.Game;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "game";
		cfg.width = 480<<1;
		cfg.height = 320<<1;
		
		new LwjglApplication(new Game(), cfg);
	}
}