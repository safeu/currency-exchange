/*
================================================================
Main.java
================================================================
Script Purpose:
    The main entry point of the code. Siya ung nagcocombine ng lahat
    for it to work. From initializing db to launching the main window.
Notes:
    FlatDarkLaf was used to make it more modern and clean looking, compared
    to simple java swing GUI.
================================================================
*/

package com.currency;

import javax.swing.SwingUtilities;

import com.currency.db.DatabaseInitializer;
import com.currency.db.ExchangeRateDAO;
import com.currency.logic.ExchangeRateService;
import com.currency.ui.MainWindow;
import com.formdev.flatlaf.FlatDarkLaf;

public class Main {
    public static void main(String[] args) throws Exception {
        DatabaseInitializer.initialize();

        if (ExchangeRateDAO.isEmpty()) {
            System.out.println("Loading rates from CSV...");
            ExchangeRateService.loadFromCSV("data/bsp_rates.csv");
        } else {
            System.out.println("Rates already loaded, skipping.");
        }

        // lambda expression ung -> so kumbaga way siya to give java a code to 
        // run later, without creating a whole nother class just for it.
        FlatDarkLaf.setup();
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}