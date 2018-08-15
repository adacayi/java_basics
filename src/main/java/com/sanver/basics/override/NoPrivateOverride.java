package com.sanver.basics.override;

class A1 {
	private void write() {
		System.out.println("A");
	}

	public void get() {
		write();
	}
	public void get1() {
		write();
	}
}

class B1 extends A1 {
	private void write() {
		System.out.println("B");
	}

	public void getNew() {
		write();
	}
	public void get1() {
		write();
	}
}

public class NoPrivateOverride {

	public static void main(String[] args) {
		B1 b = new B1();
		b.get();
		b.getNew();
		b.get1();
		((A1)b).get1();
	}

}
