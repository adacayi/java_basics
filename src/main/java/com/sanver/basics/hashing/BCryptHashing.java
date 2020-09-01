package com.sanver.basics.hashing;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class BCryptHashing {
    private static BCryptPasswordEncoder encoder;

    private static BCryptPasswordEncoder getEncoder() {
        if (encoder == null) {
            synchronized (BCryptHashing.class) {
                if (encoder == null) {
                    encoder = new BCryptPasswordEncoder();
                }
            }
        }
        return encoder;
    }

    public static String hash(String message) {
        return getEncoder().encode(message); // Salting is automatically done here.
    }

    public static void main(String... args) {
        // BCrypt is recommended for hashing passwords, since it is slower to implement, hence more prone to hacking.
        // Spring uses it. For data integrity purposes MD5, SHA512 etc. can be used since they are fast.
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter message to be hashed: ");
            String message = scanner.nextLine();
            var hashed = hash(message);
            System.out.printf("Hashed form: %s\n", hashed);
            System.out.print("Enter text to compare: ");
            String other = scanner.nextLine();

            if (getEncoder().matches(other, hashed)) { // Comparing raw text to a previously hashed text
                System.out.println("They are the same");
            } else {
                System.out.println("They are different");
            }
        }
    }
}
