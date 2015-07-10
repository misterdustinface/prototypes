package collisionDetection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Buffer implements Iterable<Integer>{
	private LinkedList<Integer> elements;
	
	public Buffer() {
		elements = new LinkedList<Integer>();
	}
	
	public Buffer(Buffer bufferToCopy) {
		elements = new LinkedList<Integer>(bufferToCopy.elements);
	}

	public int size() {
		return elements.size();
	}
	
	public void addLast(Integer value) {
		elements.addLast(value);
	}

	public int removeFirst() {
		return elements.removeFirst();
	}
	
	public void setValue(int index, Integer value) {
		elements.set(index, value);
	}
	
	public int streamFrom(Buffer bufferToStream) {
		Integer value = removeFirst();
		addLast(bufferToStream.removeFirst());
		return value;
	}
	
	public void readFrom(int amountToRead, Buffer bufferToStream) {
		for(int i = 0; i < amountToRead; ++i) {
			addLast(bufferToStream.removeFirst());
		}
	}
	
	public void insert(int startIndex, Buffer elementToInsert) {
		elements.addAll(startIndex, elementToInsert.elements);
	}
	
	public void overwrite(int startIndex, Buffer bufferToRead) {
		int index = startIndex;
		while(!bufferToRead.isEmpty()) {
			setValue(index, bufferToRead.removeFirst());
			++index;
		}
	}
	
	public void append(Buffer bufferToAppend) {
		elements.addAll(bufferToAppend.elements);
	}
	
	public Iterator<Integer> iterator() {
		return elements.iterator();
	}
	
	public ListIterator<Integer> iterator(int startingIndex) {
		return elements.listIterator(startingIndex);
	}
	
	public Buffer subBuffer(int startIndex, int endIndex) {
		Buffer buffer = new Buffer();
		buffer.elements.addAll(elements.subList(startIndex, endIndex));
		return buffer;
	}
	
	public int getLast() {
		return elements.getLast();
	}
	
	public String toString() {
		return elements.toString();
	}
	
	public int get(int index) {
		return elements.get(index);
	}

	public int getSize() {
		return elements.size();
	}
	
    public void clear() {
    	elements.clear();
	}	
    
    public boolean isEmpty() {
		return elements.isEmpty();
	}    
}