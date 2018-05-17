package com.sanver.basics.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

public class ServerSocketSampleWithMultipleClients {
	private ServerSocket server;
	private Vector<OutputStream> clients = new Vector<>();

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	public void addClient(OutputStream client) {
		clients.add(client);
	}

	public void removeClient(OutputStream client) {
		clients.remove(client);
	}

	public String toString() {
		return server.toString();
	}

	public void accept() throws IOException {
		Socket client = server.accept();
		new Echo(client).start();
	}

	class Echo extends Thread {
		private Socket client;
		private String name = "";

		public Echo(Socket client) {
			this.client = client;
		}

		public void sendMessage(String message, OutputStream writer) throws IOException {
			for (OutputStream item : clients) {

				if (item == writer)
					continue;

				item.write((message + "\n").getBytes());
			}
		}

		public void run() {
			String line;
			OutputStream clientWriter = null;

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
					OutputStream writer = client.getOutputStream();) {// BufferedOutputStream does not work
				clientWriter = writer;
				name = reader.readLine();
				addClient(writer);
				System.out.println("Connected to client " + name + " " + client);
				sendMessage(name + " joined the conversation", writer);

				while (true) {
					line = reader.readLine();
					System.out.println("Received data from " + name + ": " + line);

					if (line != null)
						sendMessage(name + ": " + line, writer);
				}
			} catch (SocketException se) {
			} catch (IOException e) {
			} finally {
				removeClient(clientWriter);
				System.out.println("Connection closed with the client " + name + " " + client);
				try {
					sendMessage(name + " left the conversation", clientWriter);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		// Run com.sanver.basics.threads.SocketSample for clients

		int port = 3000;
		ServerSocketSampleWithMultipleClients server = new ServerSocketSampleWithMultipleClients();

		try {
			server.setServer(new ServerSocket(port));
			System.out.println("Server started at " + server);

			while (true) {
				server.accept();
			}
		} catch(BindException e) {
			System.out.println("Server address is already in use.");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
