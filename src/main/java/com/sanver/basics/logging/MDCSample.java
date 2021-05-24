package com.sanver.basics.logging;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class MDCSample {

  public static void main(String[] args) {
    // To output the value in MDC we put %X{userName} in the pattern in the logback.xml
    // https://www.baeldung.com/mdc-in-log4j-2-logback
    var logger = LoggerFactory.getLogger(MDCSample.class);
    MDC.put("userName", "Abdullah");
    logger.info("Something happened");
  }
}
