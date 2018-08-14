package com.sanver.basics.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalTime;

public interface EchoInterface extends Remote {
	public String echo(String message) throws RemoteException;

	public String helloWorld() throws RemoteException;

	public LocalTime getTime() throws RemoteException;
}
