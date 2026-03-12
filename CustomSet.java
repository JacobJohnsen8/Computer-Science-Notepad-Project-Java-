class CustomSet {//Hash Set
    // Default capacity for the hash table, chosen as a large prime number for better distribution.
    private static final int DEFAULT_CAPACITY = 118550;
    private String[] table; // Array to store the elements of the set.
    private int size;       // Tracks the number of unique elements in the set.

    // Constructor to initialize the hash table with the default capacity.
    public CustomSet() {
        this.table = new String[DEFAULT_CAPACITY]; // Create an array with a fixed size.
        this.size = 0;                             // Initialize the set as empty.
    }

    // Computes the hash value for a given string key.
    private int hash(String key) {
        // Use the key's hashCode and mod it with the table length to determine the index.
        // Math.abs ensures a non-negative index.
        return Math.abs(key.hashCode() % table.length);
    }

    // Adds a string to the set.
    public void add(String word) {
        int index = hash(word); // Compute the initial index for the word.
        while (table[index] != null) { // Handle collisions using linear probing.
            if (table[index].equals(word)) return; // If the word already exists, do nothing.
            index = (index + 1) % table.length; // Move to the next index, wrapping around if necessary.
        }
        table[index] = word; // Place the word in the calculated or probed index.
        size++;              // Increment the size since a new word is added.
    }

    // Checks if a word is present in the set.
    public boolean contains(String word) {
        System.out.println("Checking: " + word); // Log the word being checked (for debugging purposes).
        int index = hash(word); // Compute the initial index for the word.
        while (table[index] != null) { // Continue searching until an empty slot is found.
            if (table[index].equals(word)) return true; // If the word matches, it's in the set.
            index = (index + 1) % table.length; // Move to the next index, wrapping around if necessary.
        }
        return false; // If no match is found, return false.
    }

    // Returns the number of unique elements in the set.
    public int size() {
        return size; // The `size` field keeps track of how many elements have been added.
    }
}