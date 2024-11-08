package com.sanver.basics.strings;

/**
 * <p>This class demonstrates the usage of the <code>StringBuilder</code> class in Java.
 * <code>StringBuilder</code> is a mutable sequence of characters, designed for efficient string manipulation.
 * Unlike <code>String</code>, which is immutable, <code>StringBuilder</code> allows modifications to its contents.
 * However, unlike <code>StringBuffer</code>, it is not synchronized, making it faster but not thread-safe.
 * </p>
 *
 * <h3>StringBuilder vs. StringBuffer:</h3>
 * <ol>
 *   <li><code>StringBuilder</code> is not synchronized, which makes it faster but not thread-safe.</li>
 *   <li><code>StringBuffer</code> is synchronized (thread-safe) but slightly slower due to the added overhead of synchronization.</li>
 * </ol>
 *
 * <h3>Common Methods of StringBuilder:</h3>
 * <ul>
 *   <li><code>append(String str)</code>: Appends the specified string to this sequence.</li>
 *   <li><code>insert(int offset, String str)</code>: Inserts the specified string at the specified position.</li>
 *   <li><code>replace(int start, int end, String str)</code>: Replaces the characters in a substring of this sequence with characters in the specified <code>String</code>.</li>
 *   <li><code>delete(int start, int end)</code>: Removes the characters in a substring of this sequence.</li>
 *   <li><code>reverse()</code>: Reverses the sequence of characters in this <code>StringBuilder</code>.</li>
 *   <li><code>capacity()</code>: Returns the current capacity of the builder.</li>
 *   <li><code>length()</code>: Returns the length (character count) of this sequence.</li>
 *   <li><code>charAt(int index)</code>: Returns the <code>char</code> value at the specified index.</li>
 *   <li><code>setCharAt(int index, char ch)</code>: Sets the character at the specified index to <code>ch</code>.</li>
 *   <li><code>substring(int start)</code>: Returns a new <code>String</code> that contains a subsequence of characters currently contained in this character sequence.</li>
 *   <li><code>substring(int start, int end)</code>: Returns a new <code>String</code> that contains a subsequence of characters currently contained in this character sequence, starting at the specified start and ending at the specified end.</li>
 * </ul>
 */

public class StringBuilderSample {
	public static void main(String[] args) {
		// Initialize a new StringBuilder with an initial string
		var builder = new StringBuilder("Hello");
		System.out.println("Initial         : " + builder);

		// Demonstrating append method
		builder.append(" World");
		System.out.println("After append    : " + builder);

		// Demonstrating insert method
		builder.insert(5, ",");
		System.out.println("After insert    : " + builder);

		// Demonstrating replace method
		builder.replace(0, 5, "Hi");
		System.out.println("After replace   : " + builder);

		// Demonstrating delete method
		builder.delete(2, 5);
		System.out.println("After delete    : " + builder);

		// Demonstrating reverse method
		builder.reverse();
		System.out.println("After reverse   : " + builder);

		// Demonstrating capacity method
		System.out.println("Builder capacity: " + builder.capacity());

		// Demonstrating length method
		System.out.println("Builder length  : " + builder.length());
	}
}
