/*
================================================================
DatabaseConnection.java
================================================================
Script Purpose:
    This scripts handles the connection between java and the
    chosen database, sqlite.
Notes:
    sqlite ung ginamit since local database lang naman need for
    now so overkill kapag gumamit pa ng SQL database na may server
    like postgres, mysql, etc.
================================================================
*/

package com.currency.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:currency.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}