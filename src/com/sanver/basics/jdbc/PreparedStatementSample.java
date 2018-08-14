package com.sanver.basics.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PreparedStatementSample {

	public static void main(String[] args) {
		// First download Ms Sql Server JDBC Drivers from
		// https://www.microsoft.com/en-us/download/details.aspx?id=54670.
		// Extract them to a folder, preferably C:\Program Files\Microsoft JDBC Driver
		// 4.1 For SQL Server. Find the jar file sqljdbc41.jar and x64
		// authentication dll file sqljdbc_auth.dll and copy them to your project
		// folder. Then select the project in Eclipse Package Explorer, right click
		// it and select properties->java build path->libraries-> add external jars and
		// select the jar file. Create a database in SQL Server Express (local db seems
		// to be not supported by JDBC)
		// Right click databases and select new database, give it a name,jdbcsample,
		// and click add. Open Sql Server Configuration Manager, go to Sql Server
		// Network Configuration and enable TCP/IP

		String databaseName = "jdbcsample";

		List<Integer> idList = new ArrayList<Integer>();
		try (Connection conn = DriverManager.getConnection(
				"jdbc:sqlserver://DESKTOP-QLKEH1D;databaseName=" + databaseName + ";integratedSecurity=true;", "", "");
				Statement stm = conn.createStatement();
				PreparedStatement preparedStatement = conn
						.prepareStatement("SELECT * FROM Employee e WITH(NOLOCK) WHERE e.EmployeeId=?");) {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("Connected to database: " + databaseName + "\n");

			try (ResultSet result = stm.executeQuery("SELECT EmployeeId FROM Employee e WITH(NOLOCK)")) {
				while (result.next())
					idList.add(result.getInt(1));
			}
			idList.forEach(id -> {
				ResultSetMetaData resultMeta;
				int i, columnCount;
				try {
					preparedStatement.setInt(1, id);

					try (ResultSet result = preparedStatement.executeQuery()) {
						resultMeta = result.getMetaData();
						columnCount = resultMeta.getColumnCount();

						while (result.next()) {
							for (i = 1; i <= columnCount; i++) // Be careful to start the index from 1
								System.out.printf("%-15s", result.getObject(i));

							System.out.println();
						}
					}
				} catch (SQLException e) {
					System.out.println("Error " + e.getMessage() + "\nCode: " + e.getErrorCode());
				}

			});

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}