package com.sanver.basics.strings;

/**
 * <p>This class demonstrates the usage of the <code>StringBuffer</code> class in Java.
 * <code>StringBuffer</code> is a thread-safe, mutable sequence of characters.
 * Unlike <code>String</code>, which is immutable, <code>StringBuffer</code> allows modifications
 * to its contents. All methods are synchronized, making it thread-safe.
 * </p>
 *
 * <h3>StringBuffer vs. StringBuilder:</h3>
 * <ol>
 *   <li><code>StringBuffer</code> is synchronized (thread-safe) but slightly slower due to the added overhead of synchronization.</li>
 *   <li><code>StringBuilder</code> is faster but not synchronized, so it's not thread-safe.</li>
 * </ol>
 *
 * <h3>Common Methods of StringBuffer:</h3>
 * <ul>
 *   <li><code>append(String str)</code>: Appends the specified string to this sequence.</li>
 *   <li><code>insert(int offset, String str)</code>: Inserts the specified string at the specified position.</li>
 *   <li><code>replace(int start, int end, String str)</code>: Replaces the characters in a substring of this sequence with characters in the specified <code>String</code>.</li>
 *   <li><code>delete(int start, int end)</code>: Removes the characters in a substring of this sequence.</li>
 *   <li><code>reverse()</code>: Reverses the sequence of characters in this <code>StringBuffer</code>.</li>
 *   <li><code>capacity()</code>: Returns the current capacity of the buffer.</li>
 *   <li><code>length()</code>: Returns the length (character count) of this sequence.</li>
 *   <li><code>charAt(int index)</code>: Returns the <code>char</code> value at the specified index.</li>
 *   <li><code>setCharAt(int index, char ch)</code>: Sets the character at the specified index to <code>ch</code>.</li>
 *   <li><code>substring(int start)</code>: Returns a new <code>String</code> that contains a subsequence of characters currently contained in this character sequence.</li>
 *   <li><code>substring(int start, int end)</code>: Returns a new <code>String</code> that contains a subsequence of characters currently contained in this character sequence, starting at the specified start and ending at the specified end.</li>
 * </ul>
 */
public class StringBufferSample {

	public static void main(String[] args) {
		// Initialize a new StringBuffer with an initial string
		var buffer = new StringBuffer("Hello");
		System.out.println("Initial        : " + buffer);

		// Demonstrating append method
		buffer.append(" World");
		System.out.println("After append   : " + buffer);

		// Demonstrating insert method
		buffer.insert(5, ",");
		System.out.println("After insert   : " + buffer);

		// Demonstrating replace method
		buffer.replace(0, 5, "Hi");
		System.out.println("After replace  : " + buffer);

		// Demonstrating delete method
		buffer.delete(2, 5);
		System.out.println("After delete   : " + buffer);

		// Demonstrating reverse method
		buffer.reverse();
		System.out.println("After reverse  : " + buffer);

		// Demonstrating capacity method
		System.out.println("Buffer capacity: " + buffer.capacity());

		// Demonstrating length method
		System.out.println("Buffer length  : " + buffer.length());
	}
}
