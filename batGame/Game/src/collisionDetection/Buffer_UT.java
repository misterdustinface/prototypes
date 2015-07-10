package collisionDetection;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Buffer_UT {
	private Buffer buffer;
	private int TEST_CAPACITY = 10;
		
	@Before
	public void createBoundsBuffer() {
		buffer = new Buffer();
	}
	
	@Test
	public void whenaddLastd_SizeIncreases() {
		assertEquals(0, buffer.getSize());
		buffer.addLast(1);
		assertEquals(1, buffer.getSize());
	}
	
	@Test
	public void whenBufferIsNotEmptyremoveFirst_SizeDecreases() {
		fillBufferWith(0);
		assertEquals(TEST_CAPACITY, buffer.getSize());
		buffer.removeFirst();
		assertEquals(TEST_CAPACITY - 1, buffer.getSize());
	}
	
	@Test
	public void clearingAFullBuffer_SizeShouldBeZero() {
		fillBufferWith(0);
		buffer.clear();
		assertEquals(0, buffer.getSize());
		assertTrue(buffer.isEmpty());
	}
	
	@Test
	public void whenEqueueX_removeFirstWillReturnX() {
		int x = 2;
		buffer.addLast(x);
		assertEquals(x, buffer.removeFirst());
	}
	
	@Test
	public void whenaddLastdXAndY_removeFirstWillReturnXThenY() {
		int x = 1;
		int y = 2;
		buffer.addLast(x);
		buffer.addLast(y);

		assertEquals(x, buffer.removeFirst());
		assertEquals(y, buffer.removeFirst());
	}
	
	@Test
	public void streamFromOtherBuffer() {
		Buffer buffer2 = new Buffer();
		
		int firstVauleInBuffer = 1;
		int secondValueInBuffer = 2;

		int firstValueInBuffer2 = 3;
		int secondValueInBuffer2 = 4;

		buffer.addLast(firstVauleInBuffer);
		buffer.addLast(secondValueInBuffer);
		buffer2.addLast(firstValueInBuffer2);
		buffer2.addLast(secondValueInBuffer2);
		
		int value = buffer.streamFrom(buffer2);
		
		assertEquals(firstVauleInBuffer, value);
		
		assertEquals(secondValueInBuffer, buffer.get(0));
		assertEquals(firstValueInBuffer2, buffer.get(1));
		
		assertEquals(secondValueInBuffer2, buffer2.get(0));
	}
	
	private void fillBufferWith(int value) {
		for(int i = 0; i < TEST_CAPACITY; ++i) {
			buffer.addLast(value);
		}
	}
}