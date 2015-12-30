
public class HeapElement {
	
	int key;
	Object data;
	
	/**
	 * A Basic constructor for the heap element
	 * @param key
	 * @param data
	 */
	public HeapElement(int key, Object data) {
		
		this.key = key;
		this.data = data;
	}

	/**
	 * Returns the Key
	 * @return key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * Set the key
	 * @param key
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * Return the data
	 * @return data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Set the data
	 * @param data
	 */
	public void setData(Object data) {
		this.data = data;
	}
}
