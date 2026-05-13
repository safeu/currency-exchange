/*
================================================================
VATCalculator.java
================================================================
Script Purpose:
    The math logic of the code FOR VAT calculation.
Methods:
    addVAT          - adds 12% VAT on top of the amount. (amount × 1.12)
    extractVAT      - extracts the VAT from a VAT-inclusive amount. (amount × 0.12/1.12)
    getVATAmount    - returns just the VAT portion. Behavior depends on inclusive/exclusive mode.
    getBaseAmount   - returns the base amount without VAT. 
    getTotal        - returns the final total amount with VAT applied.
Notes:
    Uses fixed rate 12% vat rate in ph, still dont know if this is
    how its done.
================================================================
*/

package com.currency.logic;

public class VATCalculator {

    private static final double VAT_RATE = 0.12;

    public static double addVAT(double amount) {
        return amount * (1 + VAT_RATE);
    }

    public static double extractVAT(double amount) {
        return amount * (VAT_RATE / (1 + VAT_RATE));
    }

    public static double getVATAmount(double amount, boolean inclusive) {
        if (inclusive) {
            return extractVAT(amount);
        } else {
            return amount * VAT_RATE;
        }
    }

    public static double getBaseAmount(double amount, boolean inclusive) {
        if (inclusive) {
            return amount - extractVAT(amount);
        } else {
            return amount;
        }
    }

    public static double getTotal(double amount, boolean inclusive) {
        if (inclusive) {
            return amount;
        } else {
            return addVAT(amount);
        }
    }
}