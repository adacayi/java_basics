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
        // BCrypt is recommended for hashing, since it is slower to implement than SHA512, hence more prone to
        // hacking. Spring uses it.
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter message to be hashed: ");
            String message = scanner.nextLine();
            var hashed = hash(message);
            System.out.printf("Hashed form: %s", hashed);
            System.out.println("Enter text to compare: ");
            String other = scanner.nextLine();

            if (getEncoder().matches(other, hashed)) { // Comparing raw text to a previously hashed text
                System.out.println("They are the same");
            } else {
                System.out.println("They are different");
            }
        }
    }
}
