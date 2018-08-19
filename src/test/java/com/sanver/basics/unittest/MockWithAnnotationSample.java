package com.sanver.basics.unittest;

import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(Lifecycle.PER_CLASS)
class MockWithAnnotationSample {

    FizzBuzz buzzer;

    @Mock
    Statement statement;

    @BeforeAll
    public void initAll() {
        MockitoAnnotations.initMocks(this);
        buzzer = new FizzBuzz();
    }

    @AfterAll
    public void tearDownAll() {
        statement = null;
    }

    @Test
    void fizzBuzzWithLoggerDivisibleBy3() throws SQLException {
        buzzer.fizzBuzzWithLogger(3, statement);
        Mockito.verify(statement, Mockito.times(1)).
                executeUpdate("INSERT INTO dbo.logTable (Value) VALUES(3)");
    }
}
