package com.sanver.basics.unittest;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

// This class is coded to show console output testing separately. The naming convention should be ClassNameToBeTestedTest. In our case it
// should have been FizzBuzzTest.
class FizzBuzzConsoleTest {
    private static FizzBuzz buzzer;
    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalError = System.err;

    @BeforeAll
    public static void setUp() {
        buzzer = new FizzBuzz();
    }

    @AfterAll
    public static void tearDown() {
        buzzer = null;
    }

    @BeforeEach
    public void setUpStreams() {
        outStream = new ByteArrayOutputStream();
        errStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        System.setErr(new PrintStream(errStream));
    }

    @AfterEach
    public void tearDownStreams() {
        try {
            outStream.close();
            errStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.setOut(originalOut);
            System.setErr(originalError);
        }
    }

    @Test
    void should_ReturnFizz_When_6() {
        buzzer.fizzBuzzConsole(2);
        String expected = "\r\n"; // This is for new line
        String actual = outStream.toString();
        Assertions.assertEquals("fizz", buzzer.fizzBuzz(6),
                "Numbers divisible by 3 must return fizz if they are not also divisible by 5");
    }

    @Test
    void should_ThrowException_When_Negative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> buzzer.fizzBuzz(-1),
                "Negative numbers should throw an exception");
    }
}
