import java.util.Arrays;

class CustomList<T> {
    private Object[] elements; // Array to store the elements of the list.
    private int size;          // Tracks the number of elements currently in the list.

    // Constructor to initialize the list with a default capacity of 10.
    public CustomList() {
        elements = new Object[10]; // Create an array with an initial capacity of 10.
        size = 0;                  // Initialize the size to 0 as the list starts empty.
    }

    // Adds an element to the list.
    public void add(T element) {
        if (size == elements.length) {
            resize(); // Double the capacity if the array is full.
        }
        elements[size++] = element; // Add the element and increment the size.
    }

    // Retrieves an element at the specified index.
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            // Throw an exception if the index is out of bounds to ensure safe access.
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        return (T) elements[index]; // Cast the element to the generic type T and return it.
    }

    // Checks if the list is empty.
    public boolean isEmpty() {
        return size == 0; // The list is empty if the size is 0.
    }

    // Converts the list to an array and returns it.
    public Object[] toArray() {
        return Arrays.copyOf(elements, size); // Create a new array containing only the valid elements.
    }

    // Resizes the internal array to double its current capacity.
    private void resize() {
        elements = Arrays.copyOf(elements, elements.length * 2); // Copy elements to a larger array.
    }

    // Checks if the list contains the specified element.
    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            // Compare the element with each element in the list.
            if (elements[i].equals(element)) {
                return true; // Return true if the element is found.
            }
        }
        return false; // Return false if the element is not found.
    }
}