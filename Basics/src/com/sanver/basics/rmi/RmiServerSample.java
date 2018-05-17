package com.sanver.basics.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;

public class RmiServerSample extends UnicastRemoteObject implements EchoInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8419664212297210087L;

	protected RmiServerSample() throws RemoteException {
		super();
	}

	@Override
	public String echo(String message) {
		return message;
	}

	@Override
	public String helloWorld() {
		return "Selamunaleykum";
	}

	@Override
	public LocalTime getTime() {
		return LocalTime.now();
	}

}
