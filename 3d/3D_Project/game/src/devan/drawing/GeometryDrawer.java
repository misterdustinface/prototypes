package devan.drawing;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;

import devan.geometry.Circle;
import devan.geometry.LineSegment;
import devan.geometry.Point;
import devan.geometry.Ray;
import devan.geometry.Sector;
import devan.geometry.Vector;

/**
 * @author dustin
 */
final public class GeometryDrawer {
	
	private GeometryDrawer(){};
	
	public static void drawPolygon(GraphicsData g, Polygon p, Color color){
		g.shapeRenderer.begin(ShapeType.Filled);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.polygon(p.getVertices());
		g.shapeRenderer.end();
	}
	
	public static void drawLine(GraphicsData g, LineSegment l, Color color){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.line	(	(l.getCopyOfPointA().x), 
									(l.getCopyOfPointA().y), 
									(l.getCopyOfPointB().x), 
									(l.getCopyOfPointB().y));
		g.shapeRenderer.end();
	}
	
	public static void drawCircle(GraphicsData g, Circle c, Color color){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.circle(c.getX(), c.getY(), c.getRadius());
		g.shapeRenderer.end();
		//g.begin(ShapeType.Filled);
		//g.circle(c.x()-c.radius(), c.y()-c.radius(), c.radius());
	}
	public static void drawPoint(GraphicsData g, Point p, Color color, float radius){
		g.shapeRenderer.begin(ShapeType.Filled);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.circle(p.x, p.y, radius);
		g.shapeRenderer.end();
//		g.circle(p.x-((GraphicData.pointSize)>>1), 
//				 p.y-((GraphicData.pointSize)>>1), 
//				GraphicData.pointSize);
	}
	
	public static void drawRay(GraphicsData g, Ray r, Color color, float length){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.line(r.getX(), r.getY(), r.getX() + length*(float)Math.cos(r.getTheta()), r.getY() + length*(float)Math.sin(r.getTheta()));
		g.shapeRenderer.end();
	}
	public static void drawRay(GraphicsData g, Point p, double theta, Color color, float length){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.line(p.x, p.y, p.x + length*(float)Math.cos(theta), p.y + length*(float)Math.sin(theta));
		g.shapeRenderer.end();
	}
	
	public static void drawVector(GraphicsData g, Vector v, Color color){
		drawRay(g, v, color, (float)v.getMagnitude());
	}
	public static void drawVector(GraphicsData g, Vector v, Color color, float length){
		drawRay(g, v, color, length);
	}
	
	public static void drawSector(GraphicsData g, Sector s, Color color){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.arc(s.getX(), s.getY(), (float)s.getRadius(), (float)Math.toDegrees(s.getLeftAngleRelativeToSectorOrientation()), (float)Math.toDegrees(s.getSectorLengthInRadians()));
		g.shapeRenderer.end();
	}

}
