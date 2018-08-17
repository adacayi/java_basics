package com.sanver.basics.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class ServerStartForRmiServerSample {
	static final int PORT = 1099;
	static final String SERVER_NAME = "EchoServer";
	static final String ADDRESS = "rmi://localhost:" + PORT + "/" + SERVER_NAME;

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(PORT);// If another process has already created the registry we can use
												// LocateRegistry.getRegistry(), if we will use the
												// default RMI PORT 1099. Otherwise we can call this method with the
												// PORT number of the registered RMI registry.
			RmiServerSample server = new RmiServerSample();
			Naming.rebind(ADDRESS, server);
			System.out.println("Server started at PORT " + PORT);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
