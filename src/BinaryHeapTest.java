import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Uber binary heap test
 * @author oschreib
 *
 */
public class BinaryHeapTest {
	
	BinaryHeap heap;
	BinaryHeap empty;
	HeapElement[] initialArray;
	int[] keys = {7, 1, 4, 9, 25, 1, 19, 87};
	
	@Before
	public void setup() {
		initialArray = new HeapElement[keys.length];
		 
		for (int i = 0; i < initialArray.length; i++) {
			initialArray[i] = new HeapElement (keys[i], "data");
		}
		this.heap = BinaryHeap.buildHeap (initialArray);
		empty = new BinaryHeap(0);
	}
	
	@After
	public void teardown() {
		this.heap = null;
		this.empty = null;
	}
	
	/**
	 * Makes sure the given heap is valid, using delete max.
	 * @param binHeap the heap to test
	 * @param size the expected size of heap
	 */
	public void validateHeap (BinaryHeap binHeap, int size) {
		HeapElement last =  (size > 0) ? binHeap.deleteMax() : null;
		
		for (int i = 1; i < size; i++) {
			HeapElement current = binHeap.deleteMax();
			if (current.getKey() > last.getKey()) {
				fail("Invalid heap, current element " + current.getKey() 
						+ " is larger than last element " + last.getKey() );
			}
			last = current;
		}
		
		// Try to delete max one last time, when heap suppose to be empty
		try {
			binHeap.deleteMax();
			fail("Heap is not empty as expected");
		} catch (Exception e) {
			// Do noting, expected failure
		}
	}
	
	@Test
	public void buildHeapTest() {
		// Just validate that this.heap is sorted
		validateHeap(heap, keys.length);
	}
	
	@Test
	public void insertTest() {
		BinaryHeap other = new BinaryHeap(9);
		for (int i = 1; i < 10; i ++) {
			other.insert(new HeapElement(i, "RHT"));
		}
		
		validateHeap(other, 9);
	}
	
	@Test
	public void deleteTest() {
		heap.delete(2);
		validateHeap(heap, keys.length - 1);
	}
	
	@Test
	public void deletAllTest() {
		for (int i = 1; i <= keys.length; i++) {
			heap.delete(1);
		}
		
		// Should be empty
		validateHeap(heap, 0);
	}
	
	@Test (expected = HeapException.class)
	public void deletEmptyTest() {
		empty.delete(5);
	}
	
	@Test (expected = HeapException.class)
	public void insertOverflowTest() {
		try {
			heap.insert(new HeapElement(2, "data"));
			fail("Insert should throw HeapException when full");
		} catch (HeapException e) {
			// This is expected
		} catch (Exception e) {
			fail("Insert should throw HeapException when full");
		}
	}
	
	@Test
	public void deleteMaxTest() {
		// No need, as validate heap already does that
	}
	
	@Test 
	public void findMaxTest() {
		assertEquals(87, heap.findMax().getKey());
	}
	
	@Test (expected = HeapException.class)
	public void findMaxOnEmopyHeapTest() {
		empty.findMax();
	}
	
	@Test
	public void removeThirdMaxTest() {
		assertEquals(19, heap.removeKthMax(3).getKey());
		validateHeap(heap, keys.length -3);
	}
	
	@Test
	public void removeEightMaxTest() {
		// Remove All elements
		assertEquals(1, heap.removeKthMax(8).getKey());
		
		// Make sure heap is empty
		validateHeap(heap, 0);
	}
	
	@Test(expected = HeapException.class)
	public void removeKthMaxEmptyHeapTest() {
		this.empty.removeKthMax(1);
	}
	
	@Test
	public void increaseKeyTest() {
		// Increase forth element by 4
		heap.increaseKey(2, 4);
		validateHeap(heap, keys.length);
	}
	
	@Test(expected = HeapException.class)
	public void increaseEmptyKey() {
		// Increase zero element by 100
		heap.increaseKey(0, 100);
	}
	
	@Test
	public void decreaseKeyTest() {
		// Decrease fifth key by 100
		heap.decreaseKey(5, 100);
		validateHeap(heap, keys.length);
	}
	
	@Test(expected = HeapException.class)
	public void decreaseEmptyKey() {
		// Increase forth element by 4
		heap.decreaseKey(0, 4);
	}
	
	
	@Test 
	public void heapSortTest() {
		HeapElement[] sortedArray = BinaryHeap.heapSort (initialArray);
		HeapElement last = sortedArray[0];
		
		for (int i = 1; i < sortedArray.length; i++) {
			HeapElement current = sortedArray[i];
			if (current.getKey() < last.getKey()) {
				fail("Heap is not sorted");
			}
			last = current;
		}
	}
	
	@Test
	public void toStringTest() {
		// Build a small heap, as we can't expect a specific structure
		BinaryHeap other = new BinaryHeap(3);
		other.insert(new HeapElement(4, "data"));
		other.insert(new HeapElement(1, "data"));
		other.insert(new HeapElement(1, "data"));
		assertEquals("4, 1, 1", other.toString());
		
		// Test an empty heap
		assertEquals("", empty.toString());
	}
}
