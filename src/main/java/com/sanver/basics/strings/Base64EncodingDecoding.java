package com.sanver.basics.strings;

import java.util.Arrays;
import java.util.Base64;

/**
 * Demonstrates Base64 encoding and decoding in Java.
 * <p>
 * Base64 encoding is useful for converting binary data, such as images or files, into an ASCII string format.
 * This enables binary data to be transferred over text-based protocols such as HTTP and email.
 * </p>
 *
 * <h2>How Base64 Encoding Works:</h2>
 * <p><b>Encoding:</b> Converts binary data into a sequence of 64 ASCII characters by splitting the data into 6-bit groups and mapping each group
 * to a character from a set of 64 characters (A-Z, a-z, 0-9, +, and /).
 * </p>
 *
 * <p><b>Padding:</b> The encoded string is padded with <code>=</code> characters to ensure the output length is a multiple of 4 characters.
 * Binary data is processed in chunks of 3 bytes (24 bits), which divides evenly into 4 groups of 6 bits, resulting in 4 Base64 characters.
 * However, when the input data length isn’t a multiple of 3, the encoded output needs padding with <code>=</code> characters to reach a multiple of 4.
 * </p>
 *
 * <h3>Padding Scenarios:</h3>
 * <ol>
 *   <li>When the input length is a multiple of 3 bytes: Each 3 bytes is 24 bits, and will be represented by a group of 4 6 bits, hence no padding is needed.</li>
 *   <li>When the input length is 1 byte more than a multiple of 3: The remaining one byte is 8 bits and will be represented by 2 6 bits, hence two <code>=</code> characters are added to make it a multiple of 4.</li>
 *   <li>When the input length is 2 bytes more than a multiple of 3: The remaining two bytes is 16 bits and will be represented by 3 6 bits, hence one <code>=</code> character is added.</li>
 * </ol>
 *
 * <p><b>Decoding:</b> The encoded Base64 string can be decoded back into the original binary format by reversing the encoding process.</p>
 *
 * <p>Java provides the <code>Base64</code> class in <code>java.util</code> for encoding and decoding.</p>
 *
 * <h2>Common use cases for Base64 encoding:</h2>
 * <p>
 * Base64 encoding is widely used to represent binary data as text, enabling it to be transmitted across text-based systems.
 * </p>
 * <ol>
 *   <li><b>Email (MIME Encoding):</b> Encodes attachments as ASCII text for transmission over text-based email protocols.</li>
 *   <li><b>Data Serialization and Storage in Databases:</b> Encodes binary objects (e.g., images, audio) for storage in text-only fields.</li>
 *   <li><b>Web APIs and JSON Data Interchange:</b> Encodes binary data in JSON payloads for safe transmission over APIs.</li>
 *   <li><b>HTML and CSS Embedding (Data URIs):</b> Encodes small images or resources for inline embedding in HTML or CSS.</li>
 *   <li><b>Configuration Files:</b> Encodes binary content like cryptographic keys or certificates in text-based config files.</li>
 *   <li><b>Cryptography (Encoding Keys and Certificates):</b> Encodes cryptographic keys and certificates for secure storage and transfer.</li>
 *   <li><b>Command-Line and Shell Scripts:</b> Encodes binary data for compatibility with text-based scripting tools.</li>
 *   <li><b>XML Documents:</b> Embeds binary data within XML structures, such as multimedia or cryptographic data.</li>
 *   <li><b>File Embedding in Source Code:</b> Embeds small binary files in source code by converting them to text format.</li>
 *   <li><b>Data Serialization and Deserialization:</b> Encodes binary data in text-only serialization formats like Protocol Buffers.</li>
 *   <li><b>Networking Protocols (Text-Based Protocols):</b> Encodes binary data for transfer over protocols like HTTP and SMTP.</li>
 * </ol>
 */
public class Base64EncodingDecoding {
    public static void main(String[] args) {
        var originalMessage = "Original message: ";
        var encodedMessage = "Encoded  message: ";
        var decodedMessage = "Decoded  message: ";

        {
            System.out.printf("First example%n%n");

            byte[] message = {0, -128, 127, 1, 126, -1, -125}; // 7 bytes, so there will be 2 groups of 3 bytes, which will be represented by 2 groups of 4 6 bits and the remaining one byte (8 bits) will be represented by 2 6 bits and 2 padding with =
            String encoded = Base64.getEncoder().encodeToString(message);
            byte[] decoded = Base64.getDecoder().decode(encoded);
            System.out.println(originalMessage + Arrays.toString(message));
            System.out.println(encodedMessage + encoded);
            System.out.println(decodedMessage + Arrays.toString(decoded));
        }

        {
            System.out.printf("%nSecond example%n%n");

            byte[] message = {0, -128, 127, 1, 126, -1, -125};
            byte[] encoded = Base64.getEncoder().encode(message);
            byte[] decoded = Base64.getDecoder().decode(encoded);
            System.out.println(originalMessage + Arrays.toString(message));
            System.out.println(encodedMessage + Arrays.toString(encoded));
            System.out.println(decodedMessage + Arrays.toString(decoded));
        }

        System.out.printf("%nThird example%n%n");

        var message = "Hi there..";
        System.out.println(originalMessage + message);
        var encoded = Base64.getEncoder().encodeToString(message.getBytes());
        var decoded = Base64.getDecoder().decode(encoded);
        var decodedString = new String(decoded);
        System.out.println(encodedMessage + encoded);
        System.out.println(decodedMessage + decodedString);
    }
}
