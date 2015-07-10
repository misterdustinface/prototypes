//package collisionDetection;
//
//import java.util.TreeMap;
//
//
//public class BoundsBuffer extends Buffer implements Bounded{
//	
//	// HEIGHT, INDEX
//	private TreeMap<Integer, Integer> distribution;
//	
//	public BoundsBuffer(){
//		super();
//		distribution = new TreeMap<Integer, Integer>();// HEIGHT, INDEX
//	}
//	
//	@Override
//	public int getHighest(){
//    	//return getHighestInRange(0, super.getSize()-1);
//		return distribution.lastKey();
//    }
//	@Override
//    public int getLowest(){
//    	//return getLowestInRange(0, super.getSize()-1);
//    	return distribution.firstKey();
//    }
//    /**
//     * Gets the highest bound contained by this structure in the given range of indexes
//     * @param start
//     * @param end
//     * @return highest
//     */
//    public int getHighestInRange(int start, int end){
//    	// TODO use treemap?
//    	int highest = super.get(start);
//    	for(int i = start; i < end; i++)
//	    	if(super.get(i) > highest)
//	    		highest = super.get(i);
//    	return highest;
//    }
//    /**
//     * Gets the lowest bound contained by this structure in the given range of indexes
//     * @param start
//     * @param end
//     * @return lowest
//     */
//    public int getLowestInRange(int start, int end){
//    	// TODO use treemap?
//    	int lowest = super.get(start);
//    	for(int i = start; i < end; i++)
//	    	if(super.get(i) < lowest)
//	    		lowest = super.get(i);
//    	return lowest;
//    }
//	
//    ////////////////////////////////////////////////////////////////////////
//    @Override
//	public void addLast(Integer value) {
//		super.addLast(value);
//		distribution.put(value, super.getSize()-1); // HEIGHT, INDEX
//	}
//
//	@Override
//	public int removeFirst() {
//		distribution.remove(super.get(0)); // HEIGHT
//		return super.removeFirst();
//	}
//	
//	@Override
//	public void setValue(int index, Integer value) {
//		super.setValue(index, value);
//		distribution.put(value, index); // HEIGHT, INDEX
//	}
//	
////	@Override
////	public int streamFrom(Buffer bufferToStream) {
////		Integer value = removeFirst();
////		addLast(bufferToStream.removeFirst());
////		return value;
////	}
//	
////	@Override
////	public void readFrom(int amountToRead, Buffer bufferToStream) {
////		for(int i = 0; i < amountToRead; ++i) {
////			addLast(bufferToStream.removeFirst());
////		}
////	}
//	
//	@Override
//	//TODO
//	public void insert(int startIndex, Buffer elementToInsert) {
//		super.insert(startIndex, elementToInsert);
//		//distribution.put(key, value)
////		elements.addAll(startIndex, elementToInsert.elements);
//	}
//	
////	@Override
////	//TODO
////	public void overwrite(int startIndex, Buffer bufferToRead) {
////		int index = startIndex;
////		while(!bufferToRead.isEmpty()) {
////			setValue(index, bufferToRead.removeFirst());
////			++index;
////		}
////	}
//	
//	@Override
//	public void append(Buffer bufferToAppend) {
//		for(int element : bufferToAppend){
//			addLast(element);
//		}
//	}
//	
//	@Override
//    public void clear() {
//    	super.clear();
//    	distribution.clear();
//	}	
//}