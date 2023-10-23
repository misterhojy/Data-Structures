package cse214hw4;

public interface OpenAddressTable {
    /**
     * @return the load factor of the table.
     */
    double loadFactor();
    /**
     * The dictionary <b>insert</b> operation.
     * @param value the value to be inserted into the dictionary.
     * @throws java.lang.NullPointerException if the value is <code>null</code>.
     */
    void insert(int value);
    /**
     * The dictionary <b>search</b> operation.
     * @param value the value to search.
     * @return the index associated with the given value, or -1 if the value is
     * not present in the dictionary.
     */
    int find(int value);

    /**
     * The extended hash function, which takes a pair of values (the key and the
     * probe number; where a probe number <code>i</code> indicates the i’th slot
     * in the probe sequence.
     * @param key the key to be stored or searched in the hash table.
     * @param probenumber the number that specifies which element of the probe
     * sequence is to be calculated.
     * @return the slot number (i.e., index or position) to be probed in the table.
     */
    int hash(int key, int probenumber);
}
