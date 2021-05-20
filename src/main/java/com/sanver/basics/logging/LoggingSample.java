package com.sanver.basics.logging;

import org.slf4j.LoggerFactory;

public class LoggingSample {

  public static void main(String[] args) {
    // logback dependencies logback-core and logback-classic are added for logging
    // logback.xml is put to resources for logging configuration
    var logger = LoggerFactory.getLogger(LoggingSample.class);
    logger.info("Something happened");
  }
}
