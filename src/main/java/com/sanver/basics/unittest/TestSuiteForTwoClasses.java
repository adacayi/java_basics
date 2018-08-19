package com.sanver.basics.unittest;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;


// For Eclipse right click this class in Package Explorer and select Run As -> Run Configurations.
// If there is no configuration for this class, on left side of the window right click JUnit and select new.
// Select the configuration for this class and
// select test runner as JUnit 4.
@RunWith(JUnitPlatform.class)
//@SelectPackages("com.sanver.basics.unittest") This can be used to run all tests in a package.
@SelectClasses({DivisibleBy5Test.class,DivisibleBy15Test.class})
public class TestSuiteForTwoClasses {

}
