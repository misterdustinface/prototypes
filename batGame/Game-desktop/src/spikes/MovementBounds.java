package spikes;

import spikes.movement.BoundsMovementSpike;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class MovementBounds {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "spikes";
		cfg.useGL20 = true;
		cfg.width = 480 * 2;
		cfg.height = 320 * 2;
		
		new LwjglApplication(new BoundsMovementSpike(), cfg);
	}
}