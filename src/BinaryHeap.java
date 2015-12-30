import java.util.Random;

public class BinaryHeap {
	
	HeapElement[] elementsArray;
	int size;
	
	/**
	 * Construct a Binary Heap with a given capacity
	 * @param capacity - the max size of the binary heap. must be bigger than 0
	 * @throws HeapException - If capacity is smaller than 1
	 */
	public BinaryHeap (int capacity)  {
		
		// Throw exception in case capacity's to small
		if (capacity < 0) {
			throw new HeapException("Capacity must be bigger than 0");
		}
		// Initialize size to 0 and the array to its capacity + 1 since the array
		// starts at [1] (and not [0])
		this.size = 0;
		capacity ++;
		this.elementsArray = new HeapElement[capacity];
	}
	
	/**
	 * Inserts a given element into the binary heap at the right place using
	 * the percolate up system.
	 * @param val - the element to be added
	 * @throws HeapException if heap is full
	 */
	public void insert (HeapElement val) {
		
		// Make sure there's available place in the binary heap
		if (size + 1 >= elementsArray.length) {
			throw new HeapException("The heap if full");
		}
		
		// Increase size of heap
		size++;
		
		// add the heap at the end of array, and percolate it up
		elementsArray[size] = val;
		percUp(size);
	}
	
	/**
	 * Finds the max element
	 * 
	 * @return the maximum element
	 * @throws HeapException
	 */
	public HeapElement findMax() {
		
		// If the binary heap is empty
		if (size == 0) {
			throw new HeapException("Heap is empty");
		}
		return elementsArray[1];
	}
	
	/**
	 * Deletes the max element and arrange the heap using percolate down system
	 * 
	 * @return the maximum element that was deleted
	 * @throws HeapException if heap is empty
	 */
	public HeapElement deleteMax() {
		
		// If the binary heap is empty
		if (size == 0) {
			throw new HeapException("Heap is empty");
		}
		
		// Store the max heap to be deleted
		HeapElement deletedMax = new HeapElement(elementsArray[1].key, elementsArray[1].data);
		
		// Replace the maximum element with the last one
		elementsArray[1] = elementsArray[size];
		
		// Reduce size and percolate down 
		size--;
		percDown(1);	
		
		return deletedMax;
	}
	
	/**
	 * removes the Kth max elements and return the Kth element
	 * @param k - number of max elements to be removed
	 * @return the Kth max element
	 * @throws HeapException - if K > size
	 */
	public HeapElement removeKthMax (int k) {
		
		// make sure "k" is in range
		if (k > size) {
			throw new HeapException("You cannot remove more elements that the existing ammount");
		} else if (k < 1) {
			throw new HeapException("You cannot remove non positive number of elements");
		}
		
		// Save the max element
		HeapElement max = elementsArray[1];
		
		// A loop running k times and delete the max every time
		for (int i = 0; i < k; i++) {
			max = deleteMax();
		}
		return max;
	}
	
	/**
	 * Increase a key by a given delta and percolate it up if needed.
	 * @param index - of element to be increased
	 * @param delta - to be added to key
	 * @throws HeapException - if index is invalid or delta isn't positive
	 */
	public void increaseKey (int index, int delta) {
		
		// Make sure index is valid
		if (index < 1 || index > size) {
			throw new HeapException("Invalid index");
		
		// Make sure delta is positive
		} else if (delta < 1) {
			throw new HeapException("Delta must be a positve nubmer");
		}
		elementsArray[index].key += delta;
		percUp(index);
	}
	
	/**
	 * Decrease a key by a given delta and percolate it down if needed.
	 * @param index - of element to be decreased
	 * @param delta - to be subtracted from key
	 * @throws HeapException - if index is invalid or delta isn't positive
	 */
	public void decreaseKey (int index, int delta) {
		
		// Make sure index is valid
		if (index < 1 || index > size) {
			throw new HeapException("Invalid index");
		
		// Make sure delta is positive
		} else if (delta < 1) {
			throw new HeapException("Delta must be a positve nubmer");
		}
		elementsArray[index].key -= delta;
		percDown(index);
	}
	
	/**
	 * Deletes the key at the given index from the heap.
	 * @param index
	 * @throws HeapException 
	 */
	public void delete (int index) {
		
		// First, increase the key to the maximum value
		increaseKey(index, elementsArray[1].key + 1);
		
		// Second, percolate it up
		percUp(index);
		
		// Finally, delete it
		deleteMax();
	}
	
	/**
	 * Accepts a list of elements, and creates a new heap containing those elements
	 * @param elements - an array of HeapElements to be put in a binary heap
	 * @return 
	 * @throws HeapException - If elements.length < 0
	 */
	public static BinaryHeap buildHeap (HeapElement elements[]) {
		BinaryHeap heap;
		heap = new BinaryHeap(elements.length);
		
		// Copy the given "element" array to the object's "elementArray"
		System.arraycopy(elements, 0, heap.elementsArray, 1, elements.length);
		
		// Initialize size
		heap.size = elements.length;
		
		// sort the elements starting in the middle of the heap and go upwards 
		for (int i = (heap.size / 2) ; i > 0; i--) {
			
			// Percolate down each element
			heap.percDown(i);
		}
		return heap;
		
	}
		
	/**
	 * Creates a heap from the input array, the data is then sorted using Heap sort
	 * 
	 * @return the internal HeapElement array
	 * @throws HeapException - if inArray is empty
	 * 
	 */
	public static HeapElement[] heapSort (HeapElement inArray[]) {
		
		// Creates a heap from the input array
		BinaryHeap binary = buildHeap(inArray);
		
		// New array to store result
		HeapElement result[] = new HeapElement[inArray.length];
		
		// for every element sort it back into the original array using the deleteMax function
		for (int i = binary.size - 1; i >= 0; i--) {
			result[i] = binary.deleteMax();
		}
		return result;
		
	}
	
	/**
	 * Prints the elements with a comma separation
	 */
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= size; i++) {
			sb.append(elementsArray[i].key + ", ");
		}
		
		// Make sure it's not an empty heap
		if (sb.length() >= 2) {
			
			// delete the last comma
			sb.delete(sb.length() - 2, sb.length());
		}
		return sb.toString();
		
	}
	
	/**
	 * Percolate up an element
	 * @param index - the index of the element to be percolated
	 * @param val - the element to be percolated
	 */
	public void percUp(int index) {
		
		// Save the element to be percolate
		HeapElement val = new HeapElement(elementsArray[index].key, elementsArray[index].data);
		
		// As long as percolating have'nt reach the top, or to a bigger value
		while (index > 1 && elementsArray[index / 2].key <= val.key) {		

			// Swap elements
			elementsArray[index] = elementsArray[index / 2];
			elementsArray[index / 2] = val;
			
			// Divide index and keep percolating
			index = index / 2; 
		}
	}
	
	/**
	 * Percolate down an element
	 * @param index - the index of the element to be percolated
	 * @param val - the element to be percolated
	 */
	public void percDown (int index) {
		int index1 = index * 2;
		int index2 = index * 2 + 1;
	
		// Store an element for the swap
		HeapElement temp = new HeapElement(elementsArray[index].key, elementsArray[index].data);
		
		// For corner case of heap smaller than 3
		if (size < 3 && elementsArray[index].key < elementsArray[index1].key) {
			elementsArray[index] = elementsArray[index1];
			elementsArray[index1] = temp;
			return;
		}
		
		// while percolating down haven't reached the end of the heap
		while (index2 <= size) {

			// Check which element is smaller and conduct the swap accordingly 
			if (elementsArray[index1].key >= elementsArray[index2].key && 
					(elementsArray[index].key < elementsArray[index1].key)) {
				elementsArray[index] = elementsArray[index1];
				elementsArray[index1] = temp;
				index = index1;
				index1 = index * 2;
				index2 = index * 2 + 1;
			} else if (elementsArray[index2].key > elementsArray[index1].key && 
					(elementsArray[index].key < elementsArray[index2].key)) {
				elementsArray[index] = elementsArray[index2];
				elementsArray[index2] = temp;
				index = index2;
				index1 = index * 2;
				index2 = index * 2 + 1;
			} else {
				break;
			}
		}
	}
	
	/**
	 * Checks if the heap is empty
	 * 
	 * @return true the heap is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	public static void main (String args[]) {
		
		// Basic tests given to us
		System.out.println("********** Basics Tests Given To Us **********" + System.lineSeparator());
		int[] keys = {5, 23, 3, 9, 6, 15, 19};
		try {
			HeapElement[] initialArray = new HeapElement[keys.length];
			for (int i = 0; i < initialArray.length; i++)
				initialArray[i] = new HeapElement (keys[i], "data");
			System.out.print ("Sort array using heapSort(): ");
			HeapElement[] sortedArray = heapSort (initialArray);
			for (HeapElement element: sortedArray)
				System.out.print (element.getKey() + " ");
			System.out.println();
			BinaryHeap heap = BinaryHeap.buildHeap (initialArray);
			System.out.println ("Build heap: " + heap);
			System.out.print ("Delete and print top 3 elements: ");
			System.out.print (heap.deleteMax().getKey() + ", ");
			System.out.print (heap.deleteMax().getKey() + ", ");
			System.out.println (heap.deleteMax().getKey());
			System.out.println ("Heap after deletion: " + heap);
			System.out.println ("Inserting 2 new elements...");
			heap.insert (new HeapElement (20, "Some data"));
			heap.insert (new HeapElement (30, "More data"));
			System.out.println ("Heap after insertion: " + heap);
			System.out.println ("Top data: " + heap.findMax().getData());
			heap.increaseKey (4, 20);
			System.out.println ("After increasing key 5 by 20: " + heap);
			heap.decreaseKey (3, 18);
			System.out.println ("After decreasing key 20 by 18: " + heap);
			heap.delete (2);
			System.out.println ("After deleting 2nd key (25): " + heap);
		} catch (HeapException e) {
			System.out.println (e.getMessage());
		}
		
		// additional tests by me
		System.out.println(System.lineSeparator()+ "********** additional tests by me **********" + System.lineSeparator());
		
		// Check constructor
		try {
			BinaryHeap heap = new BinaryHeap(-1);
			System.out.println("***TEST FAIL***" + System.lineSeparator() + 
					"Should not be able to start binary heap with number lower than 1"  + System.lineSeparator());
		} catch (HeapException e) {
			System.out.println("***TEST PASSED***"  + System.lineSeparator() + 
					"HeapException was thrown when needed"  + System.lineSeparator());
		}
		
		// Check Insert
		BinaryHeap heap = new BinaryHeap(4);
		try {
			
			heap.insert(new HeapElement(3, null));
			heap.insert(new HeapElement(0, null));
			heap.insert(new HeapElement(467, null));
			heap.insert(new HeapElement(-23, null));
		} catch (Exception e) {
			System.out.println("*** TEST FAIL ***" + System.lineSeparator() + 
					"an exception was thrown when it shouldn't had"  + System.lineSeparator());
		}
		try {
			heap.insert(new HeapElement(-23, null));
			System.out.println("*** TEST FAIL ***" + System.lineSeparator() + 
					"Should not be able to insert over heap capacity"  + System.lineSeparator());
		} catch (HeapException e) {
			System.out.println("***TEST PASSED***"  + System.lineSeparator() + 
					"HeapException was thrown when needed"  + System.lineSeparator());
		}
		
		// Check findMax
		BinaryHeap heap2 = new BinaryHeap(0);
		try {
			heap2.findMax();
			System.out.println("*** TEST FAIL ***" + System.lineSeparator() + 
					"Should not be able to find max in empty heap"  + System.lineSeparator());
		} catch (Exception e) {
			System.out.println("***TEST PASSED***"  + System.lineSeparator() + 
					"HeapException was thrown when needed"  + System.lineSeparator());
		}
		try {
			heap2.insert(new HeapElement(23, null));
			heap2.delete(1);
			heap2.findMax();
			System.out.println("*** TEST FAIL ***" + System.lineSeparator() + 
					"Should not be able to find max in empty heap"  + System.lineSeparator());
		} catch (HeapException e) {
			System.out.println("***TEST PASSED***"  + System.lineSeparator() + 
					"HeapException was thrown when needed"  + System.lineSeparator());
		}
		
		// Check remove Kth Max
		BinaryHeap heap3 = new BinaryHeap(5);
		try {
			heap3.insert(new HeapElement(23, null));
			heap3.insert(new HeapElement(24, null));
			heap3.insert(new HeapElement(25, null));
			heap3.insert(new HeapElement(26, null));
			heap3.insert(new HeapElement(27, null));
			heap3.removeKthMax(6);
			System.out.println("*** TEST FAIL ***" + System.lineSeparator() + 
					"Should not be able to delete k max when k is bigger than size"  + System.lineSeparator());
		} catch (Exception e) {
			System.out.println("***TEST PASSED***"  + System.lineSeparator() + 
					"HeapException was thrown when needed"  + System.lineSeparator());
		}
		try {
			heap3.removeKthMax(0);
			System.out.println("*** TEST FAIL ***" + System.lineSeparator() + 
					"Should not be able to delete k max when k is non positive"  + System.lineSeparator());
		} catch (HeapException e) {
			System.out.println("***TEST PASSED***"  + System.lineSeparator() + 
					"HeapException was thrown when needed"  + System.lineSeparator());
		}
		
		// Test Increase Key
		BinaryHeap heap4 = new BinaryHeap(5);
		heap4.insert(new HeapElement(23, null));
		heap4.insert(new HeapElement(24, null));
		heap4.insert(new HeapElement(25, null));
		try {
			heap4.increaseKey(4, 7);
			System.out.println("*** TEST FAIL ***" + System.lineSeparator() + 
					"Should not be able to increase invalid index"  + System.lineSeparator());
		} catch (HeapException e) {
			System.out.println("***TEST PASSED***"  + System.lineSeparator() + 
					"HeapException was thrown when needed"  + System.lineSeparator());
		}
		try {
			heap4.increaseKey(0, 7);
			System.out.println("*** TEST FAIL ***" + System.lineSeparator() + 
					"Should not be able to increase invalid index"  + System.lineSeparator());
		} catch (HeapException e) {
			System.out.println("***TEST PASSED***"  + System.lineSeparator() + 
					"HeapException was thrown when needed"  + System.lineSeparator());
		}
		
		// Test decrease Key
		BinaryHeap heap5 = new BinaryHeap(5);
		heap5.insert(new HeapElement(23, null));
		heap5.insert(new HeapElement(24, null));
		heap5.insert(new HeapElement(25, null));
		try {
			heap5.decreaseKey(4, 7);
			System.out.println("*** TEST FAIL ***" + System.lineSeparator() + 
					"Should not be able to decrease invalid index"  + System.lineSeparator());
		} catch (HeapException e) {
			System.out.println("***TEST PASSED***"  + System.lineSeparator() + 
					"HeapException was thrown when needed"  + System.lineSeparator());
		}
		try {
			heap5.decreaseKey(0, 7);
			System.out.println("*** TEST FAIL ***" + System.lineSeparator() + 
					"Should not be able to decrease invalid index"  + System.lineSeparator());
		} catch (HeapException e) {
			System.out.println("***TEST PASSED***"  + System.lineSeparator() + 
					"HeapException was thrown when needed"  + System.lineSeparator());
		}
		
		// Test delete
		BinaryHeap heap6 = new BinaryHeap(5);
		heap6.insert(new HeapElement(23, null));
		heap6.insert(new HeapElement(24, null));
		heap6.insert(new HeapElement(25, null));
		try {
			heap6.delete(5);
			System.out.println("*** TEST FAIL ***" + System.lineSeparator() + 
					"Should not be able to delete invalid index"  + System.lineSeparator());
		} catch (HeapException e) {
			System.out.println("***TEST PASSED***"  + System.lineSeparator() + 
					"HeapException was thrown when needed"  + System.lineSeparator());
		}
		try {
			heap6.delete(0);
			System.out.println("*** TEST FAIL ***" + System.lineSeparator() + 
					"Should not be able to delete invalid index"  + System.lineSeparator());
		} catch (HeapException e) {
			System.out.println("***TEST PASSED***"  + System.lineSeparator() + 
					"HeapException was thrown when needed"  + System.lineSeparator());
		}
		
		// Test buildheap + heapSort
		HeapElement[] elements2 = new HeapElement[25];
		try {
			for (int i = 0; i < 25; i ++) {
				elements2[i] = new HeapElement((int)(Math.random() * 100), null);
			}
			System.out.print("Array before Sort: ");
			for (HeapElement element: elements2)	
				System.out.print (element.getKey() + ", ");
			System.out.println();
			HeapElement[] sorted = heapSort(elements2);
			System.out.print("Array after Sort: ");
			for (HeapElement element: sorted)
				System.out.print (element.getKey() + ", ");
			boolean isSorted = true;
			for (int i = 0; i < 24; i ++) {
				for (int j = i + 1; j < 25; j++) {
					if (sorted[i].key > sorted[j].key) {
						isSorted = false;
						break;
					}
				}
			}
			if (isSorted) {
				System.out.println();
				System.out.println();
				System.out.println("*** TEST PASSED ***");
				System.out.println("The array is sorted");
			} else {
				System.out.println("*** TEST FAIL ***");
				System.out.println("The array is not sorted");
			}
		} catch (Exception e) {
			System.out.println("***TEST FAIL***"  + System.lineSeparator() + 
					"Exception was thrown when tring to bulid a heap"  + System.lineSeparator());
		}
		
		System.out.println();
		System.out.println();
		System.out.println("*** DONE TESTING ***");
	}

}
