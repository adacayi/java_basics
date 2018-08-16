package com.sanver.basics.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetHelper {
    public static void printResultData(ResultSet result) throws SQLException {
        ResultSetMetaData resultMeta = result.getMetaData();
        int i, columnCount = resultMeta.getColumnCount();

        for (i = 1; i <= columnCount; i++)// Be careful to start the index from 1
            System.out.printf("%-15s", resultMeta.getColumnName(i));
        System.out.println();

        while (result.next()) {
            for (i = 1; i <= columnCount; i++) // Be careful to start the index from 1
                System.out.printf("%-15s", result.getObject(i));

            System.out.println();
        }
    }
}
