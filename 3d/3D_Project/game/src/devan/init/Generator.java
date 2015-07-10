package devan.init;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;

import devan.core.GameObject;
import devan.core.Wall;
import devan.core.actors.Actor;
import devan.geometry.LineSegment;
import devan.geometry.Point;
import devan.geometry.Vector;

/**
 * For active testing.
 * @author dustin
 */
final public class Generator {
	
	private static Random rand = new Random();
	
	private Generator(){}
	
//	public static StaticWorld createStaticWorld(final int NUMBER_OF_WALLS,
//												final float minX, final float minY,
//												final int width, final int height,
//												final Color[] colors,
//												final Interval WALL_HEIGHT){
//		
//		ArrayList<Wall> walls 		= Walls(NUMBER_OF_WALLS, minX, minY, width, height, colors);
//		PointHeightMap 	heightMap 	= HeightMap(walls, WALL_HEIGHT);
//		return new StaticWorld(walls, heightMap, new Stage());
//	}
//	
//	public static PointHeightMap HeightMap(	final ArrayList<Wall> WALLS,
//											final Interval WALL_HEIGHT){
//		PointHeightMap heightMap	= new PointHeightMap();
//		
//		for(Wall wall : WALLS){
//			heightMap.add(wall.a(), WALL_HEIGHT);
//			heightMap.add(wall.b(), WALL_HEIGHT);
//		}
//		
//		return heightMap;
//	}
	
	/**
	 * GENERATES AT LEAST 3 WALLS
	 * @param NUMBER_OF_WALLS
	 * @param minX
	 * @param minY
	 * @param width
	 * @param height
	 * @param wallColor
	 * @return
	 */
	public static ArrayList<Wall> Walls(int NUMBER_OF_WALLS,
										final float minX, final float minY,
										final int width, final int height,
										final Color wallColor){
		
		Wall.DEBUG_COLOR = wallColor;
		ArrayList<Wall> walls = new ArrayList<Wall>();
		
		Point START = new Point(minX + rand.nextInt(width), minY + rand.nextInt(height));
		Point B = START;
		
		if(NUMBER_OF_WALLS < 3){ NUMBER_OF_WALLS = 3; }
		
		for(int i = 0; i < NUMBER_OF_WALLS - 1; ++i){
			Point A = new Point(minX + rand.nextInt(width), minY + rand.nextInt(height));
			Wall W = new Wall(new LineSegment(A,B));
			walls.add(W);
			B = A;
		}
		
		Wall W = new Wall(new LineSegment(B, START));
		walls.add(W);

		return walls;
	}
	
	public static ArrayList<Actor> Actors(	final int NUMBER_OF_ACTORS,
											final float minX, final float minY,
											final int width, final int height, 
											final float radius){
		
		ArrayList<Actor> actors = new ArrayList<Actor>();
		
		for(int i = 0; i < NUMBER_OF_ACTORS; ++i){
			actors.add(new Actor(	new Vector(new Point(minX + rand.nextInt(width), minY + rand.nextInt(height)),0,0), radius));
		}
		
		return actors;
	}
	
	public static ArrayList<GameObject> GameObjects(final int NUMBER_OF_GAME_OBJECTS,
													final float minX, final float minY,
													final int width, final int height, 
													final float radius){

		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		
		for(int i = 0; i < NUMBER_OF_GAME_OBJECTS; ++i){
			objects.add(new GameObject(	new Vector(new Point(minX + rand.nextInt(width), minY + rand.nextInt(height)),0,0), radius));
		}
		
		return objects;
	}
	
	
}
