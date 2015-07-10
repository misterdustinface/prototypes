package devan.drawing;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import devan.geometry.Point;

/**
 * @author dustin
 */
public abstract class CursorDrawer {
	public static void drawCrosshairCursor(GraphicsData g, Point position, float innerEmptyStretch, float outerFilledStretch){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.line(	position.x - outerFilledStretch, position.y, 
				position.x - innerEmptyStretch,  position.y);
		g.shapeRenderer.line(	position.x + outerFilledStretch, position.y, 
				position.x + innerEmptyStretch,  position.y);
		g.shapeRenderer.line(	position.x, position.y - outerFilledStretch, 
				position.x, position.y - innerEmptyStretch);
		g.shapeRenderer.line(	position.x, position.y + outerFilledStretch, 
				position.x, position.y + innerEmptyStretch);
		g.shapeRenderer.end();
	}

	private final static double radianThetaOfCursor = Math.PI/6;
	public static void drawTriangularCrosshairCursor(GraphicsData g, Point position, float innerEmptyStretch, float outerFilledStretch){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.line(	(float)(position.x - outerFilledStretch * Math.cos(radianThetaOfCursor)), (float)(position.y + outerFilledStretch * Math.sin(radianThetaOfCursor)),
				(float)(position.x - innerEmptyStretch * Math.cos(radianThetaOfCursor)),  (float)(position.y + innerEmptyStretch * Math.sin(radianThetaOfCursor)));
		g.shapeRenderer.line(	1+(float)(position.x + outerFilledStretch * Math.cos(radianThetaOfCursor)), (float)(position.y + outerFilledStretch * Math.sin(radianThetaOfCursor)), 
				1+(float)(position.x + innerEmptyStretch * Math.cos(radianThetaOfCursor)),  (float)(position.y + innerEmptyStretch * Math.sin(radianThetaOfCursor)));
		g.shapeRenderer.line(	position.x,	position.y - outerFilledStretch, 
				position.x, position.y - innerEmptyStretch);
		g.shapeRenderer.end();
	}
	
	public static void drawLargeCircleCursor(GraphicsData g, Point position, float outwardStretch){
		drawO(g, position, outwardStretch);
	}
	public static void drawSmallCircleCursor(GraphicsData g, Point position, float outwardStretch){
		drawO(g, position, outwardStretch);
	}
	
	public static void drawLargeXCursor(GraphicsData g, Point position, float outwardStretch){
		drawX(g, position, outwardStretch);
	}
	public static void drawSmallXCursor(GraphicsData g, Point position, float outwardStretch){
		drawX(g, position, outwardStretch);
	}
	
	private static void drawX(GraphicsData g, Point position, float lineDistanceFromCenter){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.line(	position.x - lineDistanceFromCenter, position.y - lineDistanceFromCenter, 
				position.x + lineDistanceFromCenter, position.y + lineDistanceFromCenter);
		g.shapeRenderer.line(	position.x + lineDistanceFromCenter, position.y - lineDistanceFromCenter, 
				position.x - lineDistanceFromCenter, position.y + lineDistanceFromCenter);
		g.shapeRenderer.end();
	}
	private static void drawO(GraphicsData g, Point position, float lineDistanceFromCenter){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.circle(position.x, position.y, lineDistanceFromCenter);
		g.shapeRenderer.end();
//				(int)position.x - lineDistanceFromCenter, 
//					(int)position.y - lineDistanceFromCenter, 
//					lineDistanceFromCenter+lineDistanceFromCenter, 
//					lineDistanceFromCenter+lineDistanceFromCenter);
	}
}
