class CustomMap<K, V> {
    // Entry class represents a key-value pair in the map.
    public static class Entry<K, V> {
        K key;   // Key of the entry.
        V value; // Value associated with the key.

        // Constructor to initialize a key-value pair.
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public int capacity;      // Maximum number of slots in the hash table.
    private int size;         // Number of key-value pairs in the map.
    public Entry<K, V>[] table; // Array to store entries in the hash table.

    // Constructor to initialize the map with a given capacity.
    @SuppressWarnings("unchecked")
    public CustomMap(int capacity) {
        this.capacity = capacity;             // Set the maximum capacity.
        this.size = 0;                        // Start with zero key-value pairs.
        this.table = new Entry[capacity];     // Create an array for the hash table.
    }

    // Computes the hash value for a given key.
    private int hash(K key) {
        // Use the key's hashCode and mod it with the capacity to get an index.
        // Math.abs ensures the index is non-negative.
        return Math.abs(key.hashCode() % capacity);
    }

    // Adds or updates a key-value pair in the map.
    public void put(K key, V value) {
        int index = hash(key); // Compute the initial index for the key.

        // Handle collisions using linear probing.
        while (table[index] != null) {
            if (table[index].key.equals(key)) {
                // If the key already exists, update its value and return.
                table[index].value = value;
                return;
            }
            // Move to the next index, wrapping around if necessary.
            index = (index + 1) % capacity;
        }

        // Insert the new key-value pair at the computed or probed index.
        table[index] = new Entry<>(key, value);
        size++; // Increment the size of the map.
    }

    // Retrieves the value associated with a given key.
    public V get(K key) {
        int index = hash(key); // Compute the initial index for the key.

        // Handle collisions using linear probing.
        while (table[index] != null) {
            if (table[index].key.equals(key)) {
                // If the key is found, return its associated value.
                return table[index].value;
            }
            // Move to the next index, wrapping around if necessary.
            index = (index + 1) % capacity;
        }

        // If the key is not found, return null.
        return null;
    }

    // Checks if a given key is present in the map.
    public boolean containsKey(K key) {
        return get(key) != null; // Reuse the `get` method to check for the key's presence.
    }
}