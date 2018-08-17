package com.sanver.basics.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiClientSample {

	public static void main(String[] args) {
		try {
			EchoInterface stub = (EchoInterface) Naming.lookup(ServerStartForRmiServerSample.ADDRESS);
			System.out.println(stub.helloWorld());
			System.out.println(stub.echo("Ve aleykumselam"));
			System.out.println("The time is " + stub.getTime());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

	}

}
