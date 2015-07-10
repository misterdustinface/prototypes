package devan.geometry;

import static org.junit.Assert.*;

import org.junit.Test;

public class CollisionCheckerTest {

	@Test
	public void testCircleCircleIntersection() {
		Circle A = new Circle(0,0,5);
		Circle B = new Circle(4,4,3);
		assertTrue(CollisionChecker.intersects(A, B));
		assertTrue(CollisionChecker.intersects(B, A));
		Circle C = new Circle(-4,-4,3);
		assertTrue(CollisionChecker.intersects(A, C));
		assertTrue(CollisionChecker.intersects(C, A));
		assertFalse(CollisionChecker.intersects(B, C));
		assertFalse(CollisionChecker.intersects(C, B));
		Circle 	D = new Circle(0,0,2);
		assertTrue(CollisionChecker.intersects(A,D));
		assertTrue(CollisionChecker.intersects(D,A));
		Circle 	E = new Circle(0,0,5);
		assertTrue(CollisionChecker.intersects(A,E));
		assertTrue(CollisionChecker.intersects(E,A));
	}
	
	@Test
	public void testCircleLineIntersection() {
		Circle 	A = new Circle(0,0,5);
		LineSegment   	B = new LineSegment(A.getCenter(), new Point(9,9));
		assertTrue(CollisionChecker.intersects(A, B));
		LineSegment	C = new LineSegment(new Point(-9,-9), A.getCenter());
		assertTrue(CollisionChecker.intersects(A, C));
		LineSegment	D = new LineSegment(new Point(-8,0), new Point(8,0));
		assertTrue(CollisionChecker.intersects(A, D));
		LineSegment	E = new LineSegment(new Point(0,-8), new Point(0,8));
		assertTrue(CollisionChecker.intersects(A, E));
		LineSegment	F = new LineSegment(new Point(-8,16), new Point(8,16));
		assertFalse(CollisionChecker.intersects(A, F));
	}
	
	@Test
	public void testLineLineIntersection() {
		LineSegment	A = new LineSegment(new Point(0,0), new Point(1,0));
		LineSegment	B = new LineSegment(new Point(0,0), new Point(0,1));
		assertTrue(CollisionChecker.intersects(A, B));
		assertTrue(CollisionChecker.intersects(B, A));
		
		LineSegment 	C = new LineSegment(new Point(3,2), new Point(2,3));
		LineSegment 	D = new LineSegment(new Point(4,2), new Point(2,2));
		LineSegment 	G = new LineSegment(new Point(4,1), new Point(2,2));
		assertTrue(CollisionChecker.intersects(C, D));
		assertTrue(CollisionChecker.intersects(D, C));
		D.a.y = 3;
		assertTrue(CollisionChecker.intersects(C, D));
		assertTrue(CollisionChecker.intersects(D, C));
		D.a.y = 4;
		assertTrue(CollisionChecker.intersects(C, D));
		assertTrue(CollisionChecker.intersects(D, C));
		
		assertFalse(CollisionChecker.intersects(C, G));
		assertFalse(CollisionChecker.intersects(G, C));
		
		LineSegment	E = new LineSegment(new Point(-9,-9), new Point(9,9));
		LineSegment	F = new LineSegment(new Point(-8,16), new Point(8,16));
		assertTrue(CollisionChecker.intersects(E, F));
		assertTrue(CollisionChecker.intersects(F, E));
		
		assertTrue(CollisionChecker.intersects(A, E));
		assertTrue(CollisionChecker.intersects(E, A));
		assertTrue(CollisionChecker.intersects(A, F));
		assertTrue(CollisionChecker.intersects(F, A));
	}
	
	@Test
	public void testRayLineIntersection() {
		
		Ray R = new Ray(new Point(0,0),Math.PI/2);
		LineSegment A = new LineSegment(new Point(-1,1), new Point(1,1));
		assertTrue(CollisionChecker.intersects(R, A));
		R.setTheta(-Math.PI/2);
		assertFalse(CollisionChecker.intersects(R, A));
		
		LineSegment B = new LineSegment(new Point(1,-1), new Point(1,1));
		R.setTheta(0);
		assertTrue(CollisionChecker.intersects(R, B));
		R.setTheta(Math.PI);
		assertFalse(CollisionChecker.intersects(R, B));
		
		R.origin.set(2, 2);
		R.setTheta(Math.PI/4);
		LineSegment C = new LineSegment(new Point(2,4), new Point(4,2));
		assertTrue(CollisionChecker.intersects(R, C));
		
		R.origin.set(-2, -2);
		assertTrue(CollisionChecker.intersects(R, C));
		
		LineSegment D = new LineSegment(new Point(-4,-2), new Point(-2,-4));
		R.setTheta(Math.PI*5/4f);
		assertTrue(CollisionChecker.intersects(R, D));
		R.setTheta(-Math.PI*3/4f);
		assertTrue(CollisionChecker.intersects(R, D));
	}
	
	@Test
	public void testRayCircleIntersection() {
		Ray R = new Ray(new Point(0,0),Math.PI/2);
		Circle A = new Circle(new Point(0,100), 1);
		assertTrue(CollisionChecker.intersects(R, A));
		A.setPosition(0, 10000);
		assertTrue(CollisionChecker.intersects(R, A));
		R.setTheta(-Math.PI);
		assertFalse(CollisionChecker.intersects(R, A));
		R.setTheta(-Math.PI/2);
		assertFalse(CollisionChecker.intersects(R, A));
		A.setPosition(0, -10000);
		assertTrue(CollisionChecker.intersects(R, A));
		
		//TODO more
	}
	
	@Test
	public void testVectorLineIntersection() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testVectorCircleIntersection() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testPointOfIntersectionBetweenRayAndLine() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetLineMethodCreatesDesiredLine() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testObservedCircleSubsectionIsDesiredSubsection() {
		fail("Not yet implemented");
	}

}
