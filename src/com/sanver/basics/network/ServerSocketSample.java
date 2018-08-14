package com.sanver.basics.network;

import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerSocketSample {

	public static void main(String[] args) {
		int port = 3000;
		String line;
		byte[] termination = "Kabuum\n".getBytes();// Adding \n here is crucial for the readLine method in client to
													// read this message
		String name = "";
		Socket clientInfo = null;

		try (ServerSocket server = new ServerSocket(port)) {
			System.out.println("Server started at " + server);

			while (true) {
				try (Socket client = server.accept();
						BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
						OutputStream writer = client.getOutputStream();) {// BufferedOutputStream does not work
					clientInfo = client;
					name = reader.readLine();
					System.out.println("Connected to client " + name + " " + client);

					while (true) {
						line = reader.readLine();
						System.out.println("Received Data: " + line);

						if (line != null) {
							writer.write(("Server response \n").getBytes());
							writer.write(("\t" + line + "\n").getBytes());// Adding \n here is crucial for the readLine
																			// method in client to read this message
							writer.write(termination);
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
