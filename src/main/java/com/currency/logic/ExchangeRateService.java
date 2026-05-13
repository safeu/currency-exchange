/*
================================================================
ExchangeRateService.java
================================================================
Script Purpose:
    A helper script whose main purpose is to import the csv file
    to the database.
Notes:
    It calls the ExchangeRateDAO.saveSingleRate method, and that
    method handles the one by one input of the values to the database.
    While this one handles the loop to iterate to every line in that
    csv.
================================================================
*/

package com.currency.logic;

import com.currency.db.ExchangeRateDAO;
import java.io.*;

public class ExchangeRateService {

    public static void loadFromCSV(String filePath) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String currency = parts[0].trim();
                    double rate = Double.parseDouble(parts[1].trim());
                    String date = parts[2].trim();
                    ExchangeRateDAO.saveSingleRate(currency, rate, date);
                }
            }
        }
        System.out.println("CSV loaded successfully.");
    }
}