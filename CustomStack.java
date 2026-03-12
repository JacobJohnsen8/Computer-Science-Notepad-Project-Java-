
// CustomStack is a generic stack implementation with a fixed capacity.
class CustomStack<T> {
    private Object[] elements; // Array to store stack elements.
    private int top;           // Index of the top element in the stack.
    private int capacity;      // Maximum number of elements the stack can hold.

    // Constructor to initialize the stack with a given capacity.
    public CustomStack(int capacity) {
        this.capacity = capacity;              // Set the stack's maximum capacity.
        this.elements = new Object[capacity]; // Create an array to hold the stack elements.
        this.top = -1;                        // Initialize the stack as empty (top is -1).
    }

    // Pushes an item onto the stack.
    public void push(T item) {
        if (isFull()) {
            // If the stack is full, shift all elements to the left by 1 to make space.
            System.arraycopy(elements, 1, elements, 0, capacity - 1);
            elements[top] = item; // Overwrite the last element with the new item.
        } else {
            elements[++top] = item; // Increment top and insert the item at the new top position.
        }
    }

    // Removes and returns the top item from the stack.
    public T pop() {
        if (isEmpty()) {
            // Throw an exception if the stack is empty to prevent underflow.
            throw new IllegalStateException("Stack is empty");
        }
        // Return the top element and decrement the top index.
        return (T) elements[top--];
    }

    // Returns the top item from the stack without removing it.
    public T peek() {
        if (isEmpty()) {
            // Throw an exception if the stack is empty to ensure safe access.
            throw new IllegalStateException("Stack is empty");
        }
        // Return the element at the top index.
        return (T) elements[top];
    }

    // Checks if the stack is empty.
    public boolean isEmpty() {
        return top == -1; // The stack is empty if the top index is -1.
    }

    // Checks if the stack is full.
    public boolean isFull() {
        return top == capacity - 1; // The stack is full if the top index is at the last position.
    }

    // Clears the stack by resetting the top index.
    public void clear() {
        top = -1; // Reset the top index to -1, effectively making the stack empty.
    }
}