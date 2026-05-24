/*
================================================================
DatabaseInitializer.java
================================================================
Script Purpose:
    This script initializes the database. So pagkacreate ng connection
    with the sqlite database, magsstart ung program to create a
    new table for that database (IF the table does not exist)
Notes:
    - Only 1 table was created "exchange_rates"
================================================================
*/

package com.currency.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS exchange_rates (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    currency_code TEXT NOT NULL,
                    rate FLOAT NOT NULL,
                    date TEXT NOT NULL
                )
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Database initialized.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}