package com.sanver.basics.network;

import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
				OutputStream writer = server.getOutputStream();) {// BufferedOutputStream does not work
			System.out.print("Enter your name: ");
			String name = consoleReader.readLine();
			writer.write((name + "\n").getBytes());
			System.out.println(name + " connected to server " + server);

			while (true) {
				System.out.print(name + ": ");
				line = consoleReader.readLine();
				writer.write((line + "\n").getBytes());// Adding \n here is crucial, else readLine method in server
														// would wait for a new line character
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
