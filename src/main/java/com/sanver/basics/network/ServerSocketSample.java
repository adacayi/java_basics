package com.sanver.basics.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerSocketSample {

    public static void main(String[] args) {
        int port = 3000;
        String line;
        String termination = "Kabuum\n";// Adding \n here is crucial for the readLine method in client to
        // read this message
        String name = "";
        Socket clientInfo = null;

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server started at " + server);

            while (true) {
                try (Socket client = server.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {
                    clientInfo = client;
                    name = reader.readLine();
                    System.out.printf("Connected to client %s %s\n", name, client);

                    while (true) {
                        line = reader.readLine();
                        System.out.printf("Received Data: %s\n", line);

                        if (line != null) {
                            writer.write("Server response \n");
                            writer.write(String.format("\t%s\n", line));// Adding \n here is crucial for the readLine
                            // method in client to read this message
                            writer.write(termination);
                            writer.flush();// Flush is necessary for BufferedWriter to send what is written immediately.
                        }
                    }
                } catch (SocketException se) {
                    System.out.println("Connection closed with the client " + name + " " + clientInfo);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
