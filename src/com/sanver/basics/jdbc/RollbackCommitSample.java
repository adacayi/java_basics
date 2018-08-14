package com.sanver.basics.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class RollbackCommitSample {

	public static void main(String[] args) {
		String databaseName = "jdbcsample";
		int id = 0;
		try (Connection conn = DriverManager.getConnection(
				"jdbc:sqlserver://DESKTOP-QLKEH1D;databaseName=" + databaseName + ";integratedSecurity=true;", "", "");
				Statement stm = conn.createStatement();) {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn.setAutoCommit(false);
			stm.executeUpdate("INSERT INTO Employee (Name) VALUES('Sadi')");
			Savepoint savepoint = conn.setSavepoint("First Insert");
			stm.executeUpdate("INSERT INTO Employee (Name) VALUES('Ahmet')");
			conn.rollback(savepoint);
			conn.commit();
			try (ResultSet result = stm
					.executeQuery("SELECT TOP 1 EmployeeId,Name FROM Employee e ORDER BY EmployeeId DESC")) {
				while (result.next()) {
					id = result.getInt(1);
					System.out.println(result.getString(2));
				}
			}
			stm.executeUpdate("DELETE Employee WHERE EmployeeId=" + id);
			conn.commit();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
