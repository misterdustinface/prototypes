package devan.animation;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/**
 * THE HEAD HAUNCHO OF THE SPRITE KINGDOM
 * @author dustin
 */
public class SpriteMap implements Disposable{

	private HashMap<String, Sprite> sprites;
	
	public Texture tempWallTexture;
	public Texture tempGameObjectTexture;
	public Texture tempActorTexture;
	
	
	public SpriteMap(){
		sprites 	= new HashMap<String, Sprite>();
		
		tempWallTexture 		= new Texture(Gdx.files.internal("data/vine2.png"));
		tempGameObjectTexture 	= new Texture(Gdx.files.internal("data/shell.png"));
		tempActorTexture 		= new Texture(Gdx.files.internal("data/fritz1.png"));
	}
	
	public void addSprite(String SPRITE_ID, Sprite SPRITE){
		sprites.put(SPRITE_ID, SPRITE);
	}
	
	public void removeSprite(String SPRITE_ID){
		sprites.remove(SPRITE_ID);
	}
	
	public int getNumberOfSprites(){ return sprites.size(); }

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
}
