package spikes.movement;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class World {
	Bat bat;
	ArrayList<Rectangle> bounds;
	
	public World() {
		init();
	}
	
	public void init() {
		Globals.init();
		bat = new Bat(Globals.BAT_initX, Globals.BAT_initY, Globals.BAT_width, Globals.BAT_height);
		bounds = new ArrayList<Rectangle>();
 	}
	
	public void update() {
		bat.update();
		
		for(Rectangle r : bounds) {
			if(bat.isCollidingWith(r)){
				System.out.println("HIT");
				init();
			}
		}
		
		if(bat.isBellowLine(Globals.WORLD_Floor)) {
			System.out.println("HIT");
			init();
		} else if (bat.isAboveLine(Globals.WORLD_Ceiling)) {
			System.out.println("HIT");
			init();
		}
		
		//spawnIfTime();
	}
	
	
	int spawnTime = 0;
	private void spawnIfTime() {
		++spawnTime;
		if(spawnTime >= Globals.WORLD_SpawnTime)
		{
			float lower = (float)(Math.random() * (Globals.WORLD_Bounds_UpperSpawn - Globals.WORLD_Bounds_Gap - Globals.WORLD_Bounds_LowerSpawn)) + Globals.WORLD_Bounds_LowerSpawn;
			float upper = lower + Globals.WORLD_Bounds_Gap;
			bounds.add(new Rectangle(bat.getX() + 480, Globals.WORLD_Floor, Globals.WORLD_Bounds_Width, lower));
			bounds.add(new Rectangle(bat.getX() + 480, upper, Globals.WORLD_Bounds_Width, Globals.WORLD_Ceiling - upper));
			spawnTime = 0;
		}
	}
	
	public Vector2 getCenter() {
		return new Vector2(bat.getX() + 240, Globals.WORLD_Ceiling/2 + 10);
	}
	
	public void flap() {
		bat.flap();
	}
	
	public void glide() {
		bat.glide();
	}
	
	public void dive() {
		bat.dive();
	}
	
	public void speedBoost(float boost) {
		bat.speedBoost(boost);
	}
}
