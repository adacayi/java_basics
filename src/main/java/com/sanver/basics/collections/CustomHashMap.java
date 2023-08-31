package com.sanver.basics.collections;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class is to represent a simplified HashMap implement. HashMap stores elements with same hashcode in a linked list (Node<K,V>s where the next field points to next element.
 * After the linked list size exceeds a certain number, i.e. TREEIFY_THRESHOLD = 8, the linked list is converted into a binary tree.
 * This implementation doesn't do that for simplicity and also resize is simplified.
 * There is a test class CustomHashMapTest in the test package that tests if this hash map methods work properly
 *
 * @param <K> Key
 * @param <V> Value
 */
public class CustomHashMap<K, V> {
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static final double DEFAULT_LOAD_FACTOR = 0.75;
    public static final int MAXIMUM_CAPACITY = 1 << 30;
    Node<K, V>[] table;
    int size, threshold;

    static int hash(Object key) {
        int h;
        return key == null ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    void resize() {
        int oldCapacity;
        if (table == null || (oldCapacity = table.length) == 0) {
            @SuppressWarnings({"unchecked"})
            var newTable = (Node<K, V>[]) new Node[DEFAULT_INITIAL_CAPACITY];
            table = newTable;
            threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
        } else {
            if (oldCapacity >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return;
            }
            @SuppressWarnings({"unchecked"})
            var newTable = (Node<K, V>[]) new Node[table.length << 1];
            var oldTable = table;
            table = newTable;
            size = 0;
            threshold <<= 1;

            if (threshold > MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
            }

            moveToNewTable(oldTable);
        }
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return table == null ? 0 : table.length;
    }

    void moveToNewTable(Node<K, V>[] oldTable) {
        if (table == null) {
            return;
        }

        int oldLength = oldTable.length;
        Node<K, V> oldNode;

        for (int i = 0; i < oldLength; i++) {
            oldNode = oldTable[i];
            oldTable[i] = null;

            while (oldNode != null) {
                put(oldNode.key, oldNode.value);
                oldNode = oldNode.next;
            }
        }
    }

    public V put(K key, V value) {
        Node<K, V> currentNode;
        V oldValue = null;
        int hash, capacity;
        if (table == null || (capacity = table.length) == 0) {
            resize();
            capacity = table.length;
        }
        int index = (hash = hash(key)) & (capacity - 1);

        if (table[index] == null) {
            table[index] = new Node<>(key, value, hash, null);
        } else {
            currentNode = table[index];
            while (true) {
                if (currentNode.hash == hash && Objects.equals(key, currentNode.key)) {
                    oldValue = currentNode.value;
                    currentNode.value = value;
                    break;
                }
                if (currentNode.next == null) {
                    currentNode.next = new Node<>(key, value, hash, null);
                    break;
                }

                currentNode = currentNode.next;
            }
        }

        if (++size > threshold) {
            resize();
        }

        return oldValue;
    }

    public V get(K key) {
        Node<K, V> result = findNode(key);
        return result != null ? result.value : null;
    }

    public V remove(K key) {
        if (table == null || table.length == 0) {
            return null;
        }

        var hash = hash(key);
        var index = hash & (table.length - 1);
        var currentNode = table[index];

        if (currentNode == null) {
            return null;
        }

        V result = null;

        if (currentNode.hash == hash && Objects.equals(currentNode.key, key)) {
            table[index] = currentNode.next;
            return currentNode.value;
        }

        var previousNode = currentNode;
        currentNode = currentNode.next;

        for (; currentNode != null; currentNode = currentNode.next, previousNode = previousNode.next) {
            if (currentNode.hash == hash && Objects.equals(currentNode.key, key)) {
                previousNode.next = currentNode.next;
                result = currentNode.value;
                break;
            }
        }

        return result;
    }

    private Node<K, V> findNode(K key) {
        Node<K, V> currentNode, result = null;
        int hash, index;

        if (table == null || table.length == 0) {
            return null;
        }

        hash = hash(key);
        index = hash & (table.length - 1);
        currentNode = table[index];

        for (; currentNode != null; currentNode = currentNode.next) {
            if (currentNode.hash == hash && Objects.equals(key, currentNode.key)) {
                result = currentNode;
                break;
            }
        }

        return result;
    }

    /**
     * This is a simplified implementation of keySet in HashMap.
     * This only returns the keys of the hash map as an immutable set.
     * In HashMap implementation there is an inner class KeySet with custom method remove, which actually changes the underlying array of the map as well.
     * Also it will reflect the current key set all the time.
     *
     * @return Returns the current key set as an immutable set.
     */
    public Set<K> keySet() {
        if (table == null) {
            return Set.of();
        }

        var keySet = new HashSet<K>();

        for (var node : table) {
            for (; node != null; node = node.next) {
                keySet.add(node.key);
            }
        }

        return Set.of((K[]) keySet.toArray(Object[]::new));
    }

    /**
     * This is a simplified implementation of entrySet in HashMap.
     * This only returns the entries as an immutable set and does not support adding and removing of elements from that set.
     * In HashMap implementation there is an inner class EntrySet with custom methods add and remove, which actually change the HashMap as well.
     * Also, it will reflect the current entry set all the time.
     *
     * @return Returns the current entry set as an immutable set.
     */
    public Set<Node<K, V>> entrySet() {
        if (table == null) {
            return Set.of();
        }

        var entrySet = new HashSet<Node<K, V>>();

        for (var node : table) {
            for (; node != null; node = node.next) {
                entrySet.add(node);
            }
        }

        return Set.of((Node<K, V>[]) entrySet.toArray(Node[]::new));
    }

    static class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, int hash, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            return this.value = value;
        }
    }
}
