package spikes.movement;

import collisionDetection.DynamicObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class WorldBoundsRenderer {
	WorldBounds world;
	OrthographicCamera cam;
	SpriteBatch batch;
	ShapeRenderer shapeRender;
	Texture ground;
	Texture city;
	Texture bird;
	
	float w = 480;
	float h = 320;
	
	public WorldBoundsRenderer(WorldBounds world) {
		this.world = world;
		Texture.setEnforcePotImages(false);
		ground = new Texture("spikes/movement/ground.png");
		city = new Texture("spikes/movement/city.png");
		bird = new Texture("spikes/movement/bird.png");

		cam = new OrthographicCamera(w, h);
		batch = new SpriteBatch();
		shapeRender = new ShapeRenderer();
		shapeRender.setColor(Color.RED);
	}
	
	float next = w * 3;
	int cycleCount;
	
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		cam.position.set(world.getCenter().x, world.getCenter().y, cam.position.z);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		//Render any textures
//		batch.begin();
//		cycleCount = ((int)world.getCenter().x) / (int)w;
//		
//		batch.draw(ground, cycleCount * w - w/2 - 100, 0, w , Globals.WORLD_Floor);
//		batch.draw(ground, (cycleCount + 1) * w - w/2 - 100, 0, w , 40);
//		batch.draw(city, cycleCount * w - w/2 - 100, Globals.WORLD_Floor, w , 140);
//		batch.draw(city, (cycleCount + 1) * w - w/2 - 100, Globals.WORLD_Floor, w , 140);
//		
//		batch.draw(bird, world.bat.bounds.x, world.bat.bounds.y, world.bat.bounds.width, world.bat.bounds.height);
//		batch.end();
		
		shapeRender.setProjectionMatrix(cam.combined);
		shapeRender.setColor(Color.RED);

		shapeRender.begin(ShapeType.Filled);
		//Render bounds
		int i = 0;
		for(int lower : world.caveLower){
			
			shapeRender.rect(i, 0, 1, lower);
			++i;
		}
		
		i = 0;

		for(int upper : world.caveUpper){
			shapeRender.rect(i, upper, 1, h - upper);
			++i;
		}
		
		//Render Bat
		
		shapeRender.setColor(Color.BLUE);

    	shapeRender.rect(world.bat.getxOffset(), world.bat.getLowerBound(), world.bat.getWidth(), world.bat.getHeight());
		
		shapeRender.setColor(Color.GREEN);

		for(DynamicObject object : world.objects) {
	    	shapeRender.rect(object.getxOffset(), object.getLowerBound(), object.getWidth(), object.getHeight());
		}
    	
		shapeRender.end();
		
		//Render ceiling and floor
		shapeRender.begin(ShapeType.Line);
		shapeRender.line(world.getCenter().x - w, Globals.WORLD_Ceiling, world.getCenter().x + 2 * w, Globals.WORLD_Ceiling);
		shapeRender.end();
	}
	
	public void dispose() {
		batch.dispose();
		shapeRender.dispose();
		city.dispose();
		bird.dispose();
		ground.dispose();
	}
}
