package com.sanver.basics.encryption;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

// Documentation http://tutorials.jenkov.com/java-cryptography/index.html
public class JavaCryptographyArchitecture {
    public static final int KEY_LENGTH_FOR_256_BIT_KEY = 32;
    public static final String AES = "AES";

    private static Cipher cipherEncode;
    private static Cipher cipherDecode;
    private final SecretKey key;

    public JavaCryptographyArchitecture() throws NoSuchAlgorithmException {
        key = KeyGenerator.getInstance(AES).generateKey();
    }

    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException {
        var sut = new JavaCryptographyArchitecture();
        var message = "Selamunaleykum";
        var encrypted = sut.encrypt(message);
        var decrypted = sut.decrypt(encrypted);
        System.out.printf("Original message: %s%nEncrypted message: %s%nDecrypted message: %s", message, encrypted, decrypted);
    }

    public String encrypt(final String message) throws
            BadPaddingException,
            IllegalBlockSizeException,
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException {
        var cipherInstance = getCipherEncode();
        var encodedBytes = cipherInstance.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encodedBytes);
    }

    public String decrypt(String message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        var cipherInstance = getCipherDecode();
        var decodedBytes = cipherInstance.doFinal(Base64.getDecoder().decode(message));
        return new String(decodedBytes);
    }

    // region singleton for encode
    // Double lock singleton pattern to not instantiate an instance of Cipher each time to encode
    private Cipher getCipherEncode() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        if (cipherEncode != null) {
            return cipherEncode;
        }

        synchronized (JavaCryptographyArchitecture.class) {
            if (cipherEncode != null) {
                return cipherEncode;
            }

            cipherEncode = Cipher.getInstance(AES);
            cipherEncode.init(Cipher.ENCRYPT_MODE, key);
        }

        return cipherEncode;
    }
    // endregion

    // region singleton for decode
    // Double lock singleton pattern to not instantiate an instance of Cipher each time to decode
    private Cipher getCipherDecode() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        if (cipherDecode != null) {
            return cipherDecode;
        }

        synchronized (JavaCryptographyArchitecture.class) {
            if (cipherDecode != null) {
                return cipherDecode;
            }

            cipherDecode = Cipher.getInstance(AES);
            cipherDecode.init(Cipher.DECRYPT_MODE, key);
        }

        return cipherDecode;
    }
    // endregion
}
