package com.sanver.basics.serialization;

public class Employee implements java.io.Serializable {
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

	public Employee(String firstName, String lastName, double salary) {
		setFirstName(firstName);
		setLastName(lastName);
		setSalary(salary);
	}
}
