package com.sanver.basics.generics;

import java.util.HashMap;
import java.util.Map;

class MyDictionary<K, V> {
	private Map<K, V> map = new HashMap<>();

	public void put(K key, V value) {
		map.put(key, value);
	}

	public V get(K key) {
		return map.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T extends V> T getConverted(K key) {
		return (T) this.get(key);
	}
}

public class TwoGenericTypeSample {

	static class A {
		String name;

		public A(String name) {
			this.name = name;
		}
	}

	static class B extends A {
		public B(String name) {
			super(name);
		}

		public void greet() {
			System.out.println("Selamunaleykum " + name);
		}
	}

	public static void main(String[] args) {
		MyDictionary<Integer, A> dictionary = new MyDictionary<>();
		dictionary.put(1, new B("Ahmet"));
		dictionary.<B>getConverted(1).greet();
	}
}
