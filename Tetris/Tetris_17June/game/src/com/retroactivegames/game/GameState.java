package com.retroactivegames.game;

public class GameState {
	public static boolean isPaused = false;
	public static void togglePause(){ isPaused = !isPaused; }
	
	public static boolean showFPS = false;
	public static void toggleShowFPS(){ showFPS = !showFPS; }
}
