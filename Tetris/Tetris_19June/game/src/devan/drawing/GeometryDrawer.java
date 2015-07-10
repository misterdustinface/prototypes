package devan.drawing;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

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
	
//	public static void drawPolygon(GraphicsData g, Polygon p, Color color){
//		g.shapeRenderer.begin(ShapeType.Filled);
//		g.shapeRenderer.setColor(color);
//		g.shapeRenderer.polygon(p.getVertices());
//		g.shapeRenderer.end();
//	}
	
	public static void drawRectangleWithoutTranslation(GraphicsData g, int x, int y, int w, int h, Color color){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.rect(x, y, w, h);
		g.shapeRenderer.end();
	}
	
	public static void drawRectangle(GraphicsData g, int x, int y, int w, int h, Color color){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.rect(g.transX(x), g.transY(y), g.transWidth(w), g.transHeight(h));
		g.shapeRenderer.end();
	}
	public static void drawFilledRectangle(GraphicsData g, int x, int y, int w, int h, Color color){
		g.shapeRenderer.begin(ShapeType.Filled);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.rect(g.transX(x), g.transY(y), g.transWidth(w), g.transHeight(h));
		g.shapeRenderer.end();
	}
	
	public static void drawLine(GraphicsData g, LineSegment l, Color color){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.line	(	g.transX(l.getCopyOfPointA().x), 
									g.transY(l.getCopyOfPointA().y), 
									g.transX(l.getCopyOfPointB().x), 
									g.transY(l.getCopyOfPointB().y));
		g.shapeRenderer.end();
	}
	
	public static void drawCircle(GraphicsData g, Circle c, Color color){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.circle(g.transX(c.getX()), g.transY(c.getY()), g.transWidth(c.getRadius()));
		g.shapeRenderer.end();
		//g.begin(ShapeType.Filled);
		//g.circle(c.x()-c.radius(), c.y()-c.radius(), c.radius());
	}
	public static void drawPoint(GraphicsData g, Point p, Color color, float radius){
		g.shapeRenderer.begin(ShapeType.Filled);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.circle(g.transX(p.x), g.transY(p.y), g.transWidth(radius));
		g.shapeRenderer.end();
//		g.circle(p.x-((GraphicData.pointSize)>>1), 
//				 p.y-((GraphicData.pointSize)>>1), 
//				GraphicData.pointSize);
	}
	
	public static void drawRay(GraphicsData g, Ray r, Color color, float length){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.line(g.transX(r.getX()), g.transY(r.getY()), 
							g.transX(r.getX() + length*(float)Math.cos(r.getTheta())), 
							g.transY(r.getY() + length*(float)Math.sin(r.getTheta())));
		g.shapeRenderer.end();
	}
	public static void drawRay(GraphicsData g, Point p, double theta, Color color, float length){
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.line(g.transX(p.x), g.transY(p.y), 
							g.transX(p.x + length*(float)Math.cos(theta)), 
							g.transY(p.y + length*(float)Math.sin(theta)));
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
		g.shapeRenderer.arc(g.transX(s.getX()), g.transY(s.getY()), 
				g.transWidth((float)s.getRadius()), (float)Math.toDegrees(s.getLeftAngleRelativeToSectorOrientation()), (float)Math.toDegrees(s.getSectorLengthInRadians()));
		g.shapeRenderer.end();
	}

}
