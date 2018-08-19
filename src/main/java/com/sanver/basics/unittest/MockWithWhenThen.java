package com.sanver.basics.unittest;

import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MockWithWhenThen {

	Statement statement;
	FizzBuzz buzzer = new FizzBuzz();

	@BeforeEach
	public void setUp() {
		statement = Mockito.mock(Statement.class);
	}

	@Test
	void should_ThrowException_When_Minus1IsSentToLogger() throws SQLException {
		String query = "INSERT INTO dbo.logTable (Value) VALUES(-1)";
		Mockito.when(statement.executeUpdate(query)).thenThrow(new SQLException());
		Assertions.assertThrows(SQLException.class, () -> buzzer.fizzBuzzWithLogger(-1, statement));
	}

	@Test
	void should_WorkWithoutException_When_1IsSentToLogger() throws SQLException {
		String query = "INSERT INTO dbo.logTable (Value) VALUES(-1)";
		Mockito.when(statement.executeUpdate(query)).thenThrow(new SQLException());
		buzzer.fizzBuzzWithLogger(1, statement);// If exception is thrown test will fail. Hence calling the method is
												// enough to say that we assert exception is not thrown.
	}

	@Test
	void should_ReturnRowCount1_When_3IsSentToLogger() throws SQLException {
		Mockito.when(statement.executeUpdate(Mockito.anyString())).thenReturn(1);
		buzzer.fizzBuzzWithLogger(3, statement);
	}
}
