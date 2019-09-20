package com.sanver.basics.strings;

import java.util.Base64;

public class Base64EncodingDecoding {
    public static void main(String[] args) {
        var message = "Selamunaleykum";
        var messageBytes = message.getBytes();
        var encrypted = Base64.getEncoder().encodeToString(messageBytes);
        var decrypted = Base64.getDecoder().decode(encrypted);
        var decryptedString = new String(decrypted);
        System.out.printf("Message: %s%nEncrypted: %s%nDecrypted String: %s%n",message,encrypted,decryptedString);
    }
}
