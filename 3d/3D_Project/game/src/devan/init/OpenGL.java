package devan.init;

import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GLCommon;

/**
 * Not as useful as I initially thought.
 * @author dustin
 */
final public class OpenGL {

	private OpenGL(){}
	
//	public static void initTopDown2D(GLCommon gl){
//		initialize(gl);
//	}
//	
//	public static void initPlatforming2D(GLCommon gl){
//		initialize(gl);
//	}
//	
//	public static void init3D(GLCommon gl){
//		initialize(gl);
//	}
	
	public static void initialize(GLCommon gl){
		gl.glEnable(GL11.GL_POINT_SMOOTH);
		gl.glEnable(GL11.GL_LINE_SMOOTH);
		gl.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
		gl.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST); 
		gl.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
		gl.glClearColor(0, 0, 0, 1);
	}
	
}
