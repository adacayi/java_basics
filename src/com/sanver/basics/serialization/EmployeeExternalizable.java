package com.sanver.basics.serialization;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class EmployeeExternalizable implements java.io.Externalizable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6536466959199096679L;

	private String firstName;
	private String lastName;
	transient private double salary;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String value) {
		firstName = value;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String value) {
		lastName = value;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double value) {
		salary = value;
	}

	public String toString() {
		return "Name is " + getFirstName() + " " + getLastName() + ".\nSalary is " + getSalary() + ".";
	}

	public EmployeeExternalizable(String firstName, String lastName, double salary) {
		setFirstName(firstName);
		setLastName(lastName);
		setSalary(salary);
	}
/**
 * Serializes only last name and salary 
 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(getLastName());
		out.writeDouble(getSalary());
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		setLastName(in.readUTF());
		setSalary(in.readDouble());
	}
}
