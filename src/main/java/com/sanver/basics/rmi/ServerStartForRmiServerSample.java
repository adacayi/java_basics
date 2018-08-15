package com.sanver.basics.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class ServerStartForRmiServerSample {
	static final int port = 1099;
	static final String serverName = "EchoServer";
	static final String address = "rmi://localhost:" + port + "/" + serverName;

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(port);// If another process has already created the registry we can use
												// LocateRegistry.getRegistry(), if we will use the
												// default RMI port 1099. Otherwise we can call this method with the
												// port number of the registered RMI registry.
			RmiServerSample server = new RmiServerSample();
			Naming.rebind(address, server);
			System.out.println("Server started at port " + port);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
