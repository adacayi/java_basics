package com.sanver.basics.hashing;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class SHA512HashingWithSalt {
    public static final String SHA_512 = "SHA-512";
    private static MessageDigest messageDigest;

    public synchronized static String hash(String message) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        if (messageDigest == null) {
            try {
                messageDigest = MessageDigest.getInstance(SHA_512);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        messageDigest.update(salt);
        var hashedBytes = messageDigest.digest(message.getBytes(StandardCharsets.UTF_8));
        return String.format("%064x", new BigInteger(1, hashedBytes));
    }

    public static void main(String... args) {
        // BCrypt is recommended for hashing passwords, since it is slower to implement, hence more prone to hacking.
        // Spring uses it. SHA512 is fast to implement, hence best to be used to check data integrity.
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter message to be hashed: ");
            String message = scanner.nextLine();
            System.out.printf("Hashed form: %s", hash(message));
        }
    }
}
