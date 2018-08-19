package com.sanver.basics.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class StringTokenizerSample {

    public static void main(String[] args) {
        System.out.print("Enter parameters separated with comma: ");
        Scanner scanner = new Scanner(System.in);
        String delimiter = ",";
        String line = scanner.nextLine();
        StringTokenizer tokenizer = new StringTokenizer(line, delimiter);
        System.out.println("Parameters you entered are: ");
        while (tokenizer.hasMoreTokens()) {
            System.out.println(tokenizer.nextToken());
        }
    }
}
