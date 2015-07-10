//package devan.core.world;
//
//import java.util.HashMap;
//import java.util.Set;
//
//import devan.geometry.Interval;
//import devan.geometry.Point;
//
//public class PointHeightMap {
//	
//	private HashMap<Integer, Interval> heightMap;
//	
//	public PointHeightMap(){
//		heightMap = new HashMap<Integer, Interval>();
//	}
//	public PointHeightMap(PointHeightMap other){
//		heightMap = other.heightMap;
//	}
//	
//	public void add(Point point, Interval height){
//		heightMap.put(point.hashCode(), height);
//	}
//	public void remove(Point point){
//		heightMap.remove(point.hashCode());
//	}
//	public void clear(){
//		heightMap.clear();
//	}
//	
//	public void raiseFloor(Set<Point> POINTS, double amount){
//		for(Point point : POINTS){
//			heightMap.get(point).raiseFloor(amount);
//		}
//	}
//	public void lowerFloor(Set<Point> POINTS, double amount){
//		for(Point point : POINTS){
//			heightMap.get(point).lowerFloor(amount);
//		}
//	}
//	public void raiseCeiling(Set<Point> POINTS, double amount){
//		for(Point point : POINTS){
//			heightMap.get(point).raiseCeiling(amount);
//		}
//	}
//	public void lowerCeiling(Set<Point> POINTS, double amount){
//		for(Point point : POINTS){
//			heightMap.get(point).lowerCeiling(amount);
//		}
//	}
//	
//	public double getCeiling(Point point){
//		return heightMap.get(point.hashCode()).getCeiling();
//	}
//	public double getFloor(Point point){
//		return heightMap.get(point.hashCode()).getFloor();
//	}
////	public Interval getHeightInterval(Point point){
////		return heightMap.get(point);
////	}
//}
