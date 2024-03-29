package com.sanver.basics.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MySqlSample {

    public static void main(String[] args) {
        // To be able to connect to the database, we need to have a JDBC driver.
        // In this case, we are using the MySQL JDBC driver.
        // The driver is added to the pom.xml file, so we don't need to add it manually.
        // The driver is added to the classpath when the project is built.
        // Table structure and functionality to be achieved:
        /*
            -- Create table Delivery
            CREATE TABLE Delivery (
                customer_id INT,
                order_date DATE,
                customer_pref_delivery_date DATE
            );

            -- Insert sample data into Delivery table
            INSERT INTO Delivery (customer_id, order_date, customer_pref_delivery_date) VALUES
            (1, '2024-01-01', '2024-01-01'),
            (1, '2024-01-02', '2024-01-01'),
            (2, '2024-01-05', '2024-01-06'),
            (2, '2024-01-06', '2024-01-06'),
            (3, '2024-01-10', '2024-01-15');

            -- SQL Query to test
            WITH first_order_immediate_scheduled AS (
                SELECT t.*,
                       RANK() OVER (PARTITION BY t.customer_id ORDER BY t.order_date) AS first_order,
                       CASE WHEN order_date = customer_pref_delivery_date THEN 1 ELSE 0 END AS immediate
                FROM Delivery t
            )
            SELECT ROUND(AVG(immediate), 4) * 100 AS immediate_percentage
            FROM first_order_immediate_scheduled t
            WHERE first_order = 1;
         */

        // Database connection details
        var url = "jdbc:mysql://localhost:3306/your_database";
        var username = "your_username";
        var password = "your_password";

        // SQL query to fetch the necessary data (simplified for conceptual clarity)
        var query = "SELECT customer_id, order_date, customer_pref_delivery_date FROM Delivery ORDER BY customer_id, order_date";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Temporary storage to calculate the metric
            Map<String, Boolean> firstOrderImmediate = new HashMap<>();
            Map<String, Date> previousOrders = new HashMap<>();

            while (resultSet.next()) {
                var customerId = resultSet.getString("customer_id");
                var orderDate = resultSet.getDate("order_date");
                var prefDeliveryDate = resultSet.getDate("customer_pref_delivery_date");

                // Check if it's the first order
                if (!previousOrders.containsKey(customerId)) {
                    previousOrders.put(customerId, orderDate);
                    // Check if the order was immediate i.e. order date equals preferred delivery date
                    firstOrderImmediate.put(customerId, orderDate.equals(prefDeliveryDate));
                }
            }

            // Calculate the percentage of immediate first orders to the total number of first orders
            double immediateCount = firstOrderImmediate.values().stream().filter(val -> val).count();
            double immediatePercentage = (immediateCount / firstOrderImmediate.size()) * 100;

            System.out.println("Immediate first order percentage: " + immediatePercentage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
