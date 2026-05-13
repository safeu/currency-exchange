/*
================================================================
CurrencyConverter.java
================================================================
Script Purpose:
    The math logic of the code FOR currency conversion. Handles 
    the conversion like multiply the conversion if its foreign to php 
    or other way around, etc.
    Also handles foreign to foreign by using PHP as the bridge.
Notes:
    N/A
================================================================
*/

package com.currency.logic;

import com.currency.db.ExchangeRateDAO;

public class CurrencyConverter {

    public static double convert(String fromCurrency, String toCurrency, double amount, String date) {
        if (fromCurrency.equals(toCurrency)) return amount;

        if (toCurrency.equals("PHP")) {
            // foreign → PHP ### so multiply need
            double rate = ExchangeRateDAO.getRate(fromCurrency, date);
            return amount * rate;
        }

        if (fromCurrency.equals("PHP")) {
            // PHP → foreign ### so divide need
            double rate = ExchangeRateDAO.getRate(toCurrency, date);
            return amount / rate;
        }

        // foreign → PHP → foreign ### so gagamiting bridge ung PHP to handle conversion between 2 foreign currencies
        double fromRate = ExchangeRateDAO.getRate(fromCurrency, date);
        double toRate = ExchangeRateDAO.getRate(toCurrency, date);
        double inPHP = amount * fromRate;
        return inPHP / toRate;
    }
}