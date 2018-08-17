package com.sanver.basics.network;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketSample {

    public static void main(String[] args) {
        // First run the com.sanver.basics.network.ServerSocketSample for a server.
        // You can run com.sanver.basics.threads.ServerSocketSampleWithMultipleClients
        // for a server that can handle multiple clients simultaneously.

        String host = "localhost";
        String termination = "Kabuum";
        int port = 3000;
        String line;
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        try (Socket server = new Socket(host, port);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(server.getInputStream(), StandardCharsets.ISO_8859_1));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()))) {// BufferedOutputStream does not work
            System.out.print("Enter your name: ");
            String name = consoleReader.readLine();
            writer.write(String.format("%s\n", name));
            writer.flush();// Flush is necessary for BufferedWriter to send what is written immediately.
            System.out.println(name + " connected to server " + server);

            while (true) {
                System.out.print(name + ": ");
                line = consoleReader.readLine();
                writer.write(String.format("%s\n", line));// Adding \n here is crucial, else readLine method in server
                // would wait for a new line character
                writer.flush();// Flush is necessary for BufferedWriter to send what is written immediately.
                while (true) {
                    line = reader.readLine();

                    if (line.equals(termination)) // We have to know if server output is terminated by checking the
                        // special termination string. Else reader.readLine method will wait
                        // for a server response indefinitely.
                        break;

                    System.out.println(line);
                }
            }
        } catch (ConnectException e) {
            System.out.println("Cannot connect to server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
