package com.sanver.basics.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ConnectingToSqlServerSample {

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
		try (Scanner scanner = new Scanner(System.in);
				Connection conn = DriverManager.getConnection(
						"jdbc:sqlserver://DESKTOP-QLKEH1D;databaseName=" + databaseName + ";integratedSecurity=true;",
						"", "");
				Statement stm = conn.createStatement();) {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String statement = "";
			int i, columnCount, rowCount;
			ResultSetMetaData resultMeta;
			System.out.println("Connected to database: " + databaseName);

			while (true) {
				System.out.print("> ");
				statement = scanner.nextLine();
				System.out.println();
				try {
					if (statement.trim().substring(0, 6).equalsIgnoreCase("select")) {
						try (ResultSet result = stm.executeQuery(statement)) {
							resultMeta = result.getMetaData();
							columnCount = resultMeta.getColumnCount();

							for (i = 1; i <= columnCount; i++)// Be careful to start the index from 1
								System.out.printf("%-15s", resultMeta.getColumnName(i));
							System.out.println();

							while (result.next()) {
								for (i = 1; i <= columnCount; i++) // Be careful to start the index from 1
									System.out.printf("%-15s", result.getObject(i));

								System.out.println();
							}
						}
					} else {
						rowCount = stm.executeUpdate(statement);
						System.out.println(rowCount + " rows effected.");
					}
				} catch (SQLException e) {
					System.out.println("Error " + e.getMessage() + "\nCode: " + e.getErrorCode());
				}
				System.out.println();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
