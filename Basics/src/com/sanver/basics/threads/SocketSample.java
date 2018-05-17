package com.sanver.basics.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketSample {

	class Reader extends Thread {
		private BufferedReader reader;

		public Reader(BufferedReader reader) {
			this.reader = reader;
		}

		public void run() {
			String line;
			try {
				while (true) {
					line = reader.readLine();
					System.out.println(line);
				}
			} catch (IOException e) {
				System.out.println("Connection lost with the server.");
			}
		}
	}

	class Writer extends Thread {
		private BufferedReader reader;
		private OutputStream writer;

		public Writer(BufferedReader reader, OutputStream writer) {
			this.reader = reader;
			this.writer = writer;
		}
		
		public void run() {
			String line;
			try {
				while (true) {
					line = reader.readLine();

					if (line == null || line.equals("Exit"))
						break;

					writer.write((line + "\n").getBytes());
				}
			} catch (IOException e) {
				System.out.println("Connection lost with the server.");
			}
		}
	}

	public static void main(String[] args) {
		// First run the com.sanver.basics.threads.ServerSocketSampleWithMultipleClients
		// for a server.
		// You can run com.sanver.basics.threads.ServerSocketSampleWithMultipleClients
		// for a server that can handle multiple clients simultaneously.

		String host = "localhost";
		int port = 3000;
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		SocketSample clientSample = new SocketSample();

		try (Socket client = new Socket(host, port);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(client.getInputStream(), StandardCharsets.ISO_8859_1));
				OutputStream writer = client.getOutputStream();) {// BufferedOutputStream does not work
			System.out.print("Enter your name: ");
			String name = consoleReader.readLine();
			writer.write((name + "\n").getBytes());
			System.out.println(name + " connected to server " + client);
			Reader readerThread = clientSample.new Reader(reader);
			Writer writerThread = clientSample.new Writer(consoleReader, writer);
			readerThread.start();
			writerThread.start();
			readerThread.join();
		} catch (ConnectException e) {
			System.out.println("Cannot connect to server.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
}
