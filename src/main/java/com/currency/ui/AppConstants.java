/*
================================================================
AppConstants.java
================================================================
Script Purpose:
    Stores all hardcoded values used across the UI.
    Colors, currency names, etc. are defined here so they're
    easy to find and change without digging through MainWindow.
Notes:
    Same concept as a config/settings file in Python.
================================================================
*/

package com.currency.ui;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

public class AppConstants {

    // window constants for mainwindow
    public static final int WINDOW_WIDTH  = 520;
    public static final int WINDOW_HEIGHT = 680;

    // Colors
    public static final Color BG_DARK      = new Color(18, 18, 24);
    public static final Color BG_CARD      = new Color(28, 28, 38);
    public static final Color BG_INPUT     = new Color(38, 38, 52);
    public static final Color ACCENT       = new Color(99, 102, 241);
    public static final Color ACCENT_HOVER = new Color(79, 82, 221);
    public static final Color TEXT_PRIMARY = new Color(240, 240, 255);
    public static final Color TEXT_MUTED   = new Color(140, 140, 170);
    public static final Color BORDER_COLOR = new Color(55, 55, 75);
    public static final Color SUCCESS      = new Color(52, 211, 153);
    public static final Color WARNING      = new Color(251, 191, 36);

    // Currency names for better UI/UX
    public static final Map<String, String> CURRENCY_NAMES = new LinkedHashMap<>();
    static {
        CURRENCY_NAMES.put("PHP", "PHP — Philippine Peso");
        CURRENCY_NAMES.put("AED", "AED — UAE Dirham");
        CURRENCY_NAMES.put("ARS", "ARS — Argentine Peso");
        CURRENCY_NAMES.put("AUD", "AUD — Australian Dollar");
        CURRENCY_NAMES.put("BHD", "BHD — Bahraini Dinar");
        CURRENCY_NAMES.put("BND", "BND — Brunei Dollar");
        CURRENCY_NAMES.put("BRL", "BRL — Brazilian Real");
        CURRENCY_NAMES.put("CAD", "CAD — Canadian Dollar");
        CURRENCY_NAMES.put("CHF", "CHF — Swiss Franc");
        CURRENCY_NAMES.put("CNY", "CNY — Chinese Renminbi");
        CURRENCY_NAMES.put("DKK", "DKK — Danish Krone");
        CURRENCY_NAMES.put("EUR", "EUR — Euro");
        CURRENCY_NAMES.put("GBP", "GBP — British Pound");
        CURRENCY_NAMES.put("HKD", "HKD — Hong Kong Dollar");
        CURRENCY_NAMES.put("IDR", "IDR — Indonesian Rupiah");
        CURRENCY_NAMES.put("INR", "INR — Indian Rupee");
        CURRENCY_NAMES.put("JPY", "JPY — Japanese Yen");
        CURRENCY_NAMES.put("KRW", "KRW — South Korean Won");
        CURRENCY_NAMES.put("MXN", "MXN — Mexican Peso");
        CURRENCY_NAMES.put("MYR", "MYR — Malaysian Ringgit");
        CURRENCY_NAMES.put("NOK", "NOK — Norwegian Krone");
        CURRENCY_NAMES.put("NZD", "NZD — New Zealand Dollar");
        CURRENCY_NAMES.put("PKR", "PKR — Pakistani Rupee");
        CURRENCY_NAMES.put("SAR", "SAR — Saudi Riyal");
        CURRENCY_NAMES.put("SEK", "SEK — Swedish Krona");
        CURRENCY_NAMES.put("SGD", "SGD — Singapore Dollar");
        CURRENCY_NAMES.put("SYP", "SYP — Syrian Pound");
        CURRENCY_NAMES.put("THB", "THB — Thai Baht");
        CURRENCY_NAMES.put("TWD", "TWD — Taiwan Dollar");
        CURRENCY_NAMES.put("USD", "USD — US Dollar");
        CURRENCY_NAMES.put("VES", "VES — Venezuelan Bolivar");
        CURRENCY_NAMES.put("ZAR", "ZAR — South African Rand");
    }
}