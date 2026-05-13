/*
================================================================
ExchangeRateDAO.java
================================================================
Script Purpose:
    This script is the main script that talks to the database
    directly. So mainly handles SQL queries and stuff.
Methods:
    saveSingleRate          - this handles saving the rates to the database.
                            Useful when storing the data from csv to db
    getRate                 - gets a specific rate from the db. Useful when querying
                            data dun sa database. Bali siya ung kumukuha ng conversion rate
                            ng isang currency.
    isEmpty                 - checks if the database is empty.
    getLatestDate           - gets the latest date ofc. Useful for ui purposes.
    getAvailableCurrencies  - as it is. Make it distinct (avoids duplicates)
    getAvailableDates       - as it is. Useful for UI for dates (to make it distinct)
    getNearestDate          - gets the nearest date to the chosen date
                            useful if ung pinili ng user na date doesn't have a data
                            (e.g. holiday, weekend, typically wala talagang change sa currency ex dito)
Notes:
    DAO means Data Access Object
================================================================
*/

package com.currency.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDAO {

    public static void saveSingleRate(String currencyCode, double rate, String date) {
        String sql = "INSERT OR IGNORE INTO exchange_rates (currency_code, rate, date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, currencyCode);
            stmt.setDouble(2, rate);
            stmt.setString(3, date);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving rate: " + e.getMessage());
        }
    }

    public static double getRate(String currencyCode, String date) {
        String sql = """
                SELECT rate FROM exchange_rates
                WHERE
                    currency_code = ? AND
                    date = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, currencyCode);
            stmt.setString(2, date);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("rate");
            }

        } catch (SQLException e) {
            System.out.println("Error getting rate: " + e.getMessage());
        }

        return -1;
    }
    public static boolean isEmpty() {
        String sql = "SELECT COUNT(*) FROM exchange_rates";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking DB: " + e.getMessage());
        }
        return true;
    }

    public static String getLatestDate() {
        String sql = "SELECT MAX(date) FROM exchange_rates";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println("Error getting latest date: " + e.getMessage());
        }
        return null;
    }

    public static List<String> getAvailableCurrencies() {
        String sql = "SELECT DISTINCT currency_code FROM exchange_rates ORDER BY currency_code";
        List<String> currencies = new ArrayList<>();
        currencies.add("PHP");
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                currencies.add(rs.getString("currency_code"));
            }
        } catch (SQLException e) {
            System.out.println("Error getting currencies: " + e.getMessage());
        }
        return currencies;
    }

    public static List<String> getAvailableDates() {
        String sql = "SELECT DISTINCT date FROM exchange_rates ORDER BY date DESC";
        List<String> dates = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dates.add(rs.getString("date"));
            }
        } catch (SQLException e) {
            System.out.println("Error getting dates: " + e.getMessage());
        }
        return dates;
    }

    public static String getNearestDate(String date) {
        String sql = """
                SELECT date FROM exchange_rates
                WHERE date <= ?
                ORDER BY date DESC
                LIMIT 1
                """;
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, date);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("date");
            }
        } catch (SQLException e) {
            System.out.println("Error getting nearest date: " + e.getMessage());
        }
        return null;
    }
}