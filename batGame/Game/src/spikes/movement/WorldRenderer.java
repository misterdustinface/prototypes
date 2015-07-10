package spikes.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {
	World world;
	OrthographicCamera cam;
	SpriteBatch batch;
	ShapeRenderer shapeRender;
	Texture ground;
	Texture city;
	Texture bird;
	
	float w = 480;
	float h = 320;
	
	public WorldRenderer(World world) {
		this.world = world;
		Texture.setEnforcePotImages(false);
		ground = new Texture("movementSpike/ground.png");
		city = new Texture("movementSpike/city.png");
		bird = new Texture("movementSpike/bird.png");

		cam = new OrthographicCamera(w, h);
		batch = new SpriteBatch();
		shapeRender = new ShapeRenderer();
		shapeRender.setColor(Color.GREEN);
	}
	
	float next = w * 3;
	int cycleCount;
	
	public void render() {
		Gdx.gl.glClearColor(0, 194, 206, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		cam.position.set(world.getCenter().x - 100, world.getCenter().y, cam.position.z);
		cam.update();
		
		//Render any textures
		batch.begin();
		cycleCount = ((int)world.getCenter().x) / (int)w;
		
		batch.draw(ground, cycleCount * w - w/2 - 100, 0, w , Globals.WORLD_Floor);
		batch.draw(ground, (cycleCount + 1) * w - w/2 - 100, 0, w , 40);
		batch.draw(city, cycleCount * w - w/2 - 100, Globals.WORLD_Floor, w , 140);
		batch.draw(city, (cycleCount + 1) * w - w/2 - 100, Globals.WORLD_Floor, w , 140);
		
		batch.draw(bird, world.bat.bounds.x, world.bat.bounds.y, world.bat.bounds.width, world.bat.bounds.height);
		batch.end();
		
		batch.setProjectionMatrix(cam.combined);
		shapeRender.setProjectionMatrix(cam.combined);

		shapeRender.begin(ShapeType.Filled);
		//Render bounds
		for(Rectangle r : world.bounds) {
			shapeRender.rect(r.x, r.y, r.width, r.height);
		}
		
		//Render Bat
		Rectangle r = world.bat.getBounds();
		//shapeRender.rect(r.x, r.y, r.width, r.height);
		
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
