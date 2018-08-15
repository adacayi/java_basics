package com.sanver.basics.lambda;

import java.util.function.Consumer;

public class ConsumerInterfaceSample {

	public static void main(String[] args) {
		Consumer<String> greeter = name -> System.out.println("Selamunaleykum " + name);
		greeter.accept("Ahmet");
		Consumer<Integer> c = x -> System.out.println(x);
		c = c.andThen(x -> System.out.println("Finished"));
		c.accept(12);
	}

}
