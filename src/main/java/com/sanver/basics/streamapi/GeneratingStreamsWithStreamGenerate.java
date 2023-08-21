package com.sanver.basics.streamapi;

import static com.sanver.basics.utils.Utils.sleep;

import java.time.LocalDateTime;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class GeneratingStreamsWithStreamGenerate {

  public static void main(String[] args) {
    Supplier<LocalDateTime> supplier = () -> {
      sleep(1000);
      return LocalDateTime.now();
    };
    Stream<LocalDateTime> stream = Stream.generate(supplier).limit(5);

    stream.forEach(System.out::println);
  }
}
