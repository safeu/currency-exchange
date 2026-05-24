/*
================================================================
VATCalculator.java
================================================================
Script Purpose:
    The math logic of the code FOR VAT calculation.
Methods:
    getVATAmount    - returns the VAT portion of an amount (amount × 0.12)
    getTotal        - returns the total amount with VAT added (amount × 1.12)
Notes:
    Uses fixed 12% VAT rate as per Philippine tax law.
    VAT is applied on the PHP equivalent of the transaction.
================================================================
*/

package com.currency.logic;

public class VATCalculator {

    private static final double VAT_RATE = 0.12;

    public static double getVATAmount(double amount) {
        return amount * VAT_RATE;
    }

    public static double getTotal(double amount) {
        return amount * (1 + VAT_RATE);
    }
}