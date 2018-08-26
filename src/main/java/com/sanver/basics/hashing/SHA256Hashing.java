package com.sanver.basics.hashing;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class SHA256Hashing {
    public static final String SHA_256 = "SHA-256";
    private static MessageDigest digest;

    public synchronized static String hash(String message) {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance(SHA_256);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        digest.update(message.getBytes());
        return String.format("%064x", new BigInteger(1, digest.digest()));
    }

    public static void main(String... args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter message to be hashed: ");
            String message = scanner.nextLine();
            System.out.printf("Hashed form: %s", hash(message));
        }
    }
}
