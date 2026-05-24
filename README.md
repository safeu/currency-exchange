# Currency Exchange + VAT Computation System

A desktop application built with Java Swing that provides real-time currency exchange
rate conversions with Philippine VAT (Value Added Tax) computation. Exchange rates are
based on BSP (Bangko Sentral ng Pilipinas) reference rates.

A Project for Computer Fundamentals and Programming - EIT 032.1

---

## Overview

This system allows users to:
- Convert between any two supported currencies using historical BSP exchange rates
- Compute 12% Philippine VAT on PHP-involved transactions
- Look up exchange rates for any specific date within the loaded 6-month dataset
- Automatically fall back to the nearest available trading day if a selected date has no data

---

## Requirements

- Java JDK 17 or higher
- Apache Maven 3.6 or higher

To verify your installations:
```bash
java -version
mvn -version
```

---

## Setup and Installation

**1. Clone the repository**
```bash
git clone https://github.com/safeu/currency-exchange.git

```

**2. Add the exchange rate data**

Place your BSP exchange rate CSV file inside the `data/` folder and name it `bsp_rates.csv`. Or use the one here (covers 6 months of historical BSP exchange rates data)

The `data/bsp_rates.csv` file must follow this format:

    currency_code,rate,date
    USD,60.69,2026-05-08
    EUR,71.22,2026-05-08

Rules for the CSV:
- `currency_code` must be a valid 3-letter ISO currency code
- `rate` is always the Philippine Peso equivalent of 1 unit of that currency (as published by BSP)
- `date` must be in `YYYY-MM-DD` format
- Skip any currencies marked N/A in the BSP bulletin
- No duplicate entries for the same currency and date

**3. Run the application**
```bash
mvn compile exec:java "-Dexec.mainClass=com.currency.Main"
```

On first run, the application will automatically:
- Create a local SQLite database (`currency.db`)
- Import all rates from `bsp_rates.csv`
- Launch the main window

Subsequent runs will skip the import and load instantly.

---

## How to Use

**Basic Conversion**
1. Enter the amount you want to convert
2. Select the source currency (From) and target currency (To)
3. Select a date — defaults to the latest available date in the dataset
4. Click Convert

**VAT Options (only shown for PHP-involved transactions)**
- No VAT — displays the raw converted amount in PHP
- With VAT — adds 12% VAT on top of the PHP amount

**Date Selection**
- If you select a date with no available data (weekends, public holidays), the system
  automatically uses the most recent available trading day and displays a notice.

**Cross-Currency Conversion**
- Any non-PHP to non-PHP conversion (e.g. USD to EUR) is supported.
- The system uses PHP as a bridge currency internally: USD → PHP → EUR.
- VAT is not shown for foreign-to-foreign conversions as VAT is a Philippine tax.

---

## Project Structure
CurrencyVATSystem/
├── src/main/java/com/currency/
│   ├── Main.java                        # Entry point — initializes DB, loads CSV, launches UI
│   ├── db/
│   │   ├── DatabaseConnection.java      # Opens and returns SQLite connection
│   │   ├── DatabaseInitializer.java     # Creates exchange_rates table on first run
│   │   └── ExchangeRateDAO.java         # All database queries (save, fetch, lookup)
│   ├── logic/
│   │   ├── CurrencyConverter.java       # Conversion math including PHP bridge logic
│   │   ├── ExchangeRateService.java     # Reads and imports CSV into the database
│   │   └── VATCalculator.java           # VAT math (12% Philippine VAT rate)
│   └── ui/
│       ├── AppConstants.java            # Colors, window size, currency display names
│       └── MainWindow.java              # Main application window and all UI logic
├── data/
│   └── bsp_rates.csv                    # Exchange rate data (not included, see Setup)
├── pom.xml                              # Maven dependencies and build configuration
└── README.md

---

## Dependencies

All dependencies are managed automatically by Maven via `pom.xml`.

| Library | Version | Purpose |
|---|---|---|
| FlatLaf | 3.4.1 | Modern dark theme for Java Swing |
| SQLite JDBC | 3.45.1.0 | Local SQLite database driver |
| LGoodDatePicker | 11.2.1 | Calendar date picker component |
| org.json | 20240303 | JSON parsing library |

---

## Data Source

Exchange rates are sourced from the **Bangko Sentral ng Pilipinas (BSP)** Financial
Markets Reference Exchange Rate Bulletin, published daily on business days.

BSP Website: https://www.bsp.gov.ph

Supported currencies match the BSP bulletin:
AED, ARS, AUD, BHD, BND, BRL, CAD, CHF, CNY, DKK, EUR, GBP, HKD, IDR, INR,
JPY, KRW, MXN, MYR, NOK, NZD, PKR, SAR, SEK, SGD, SYP, THB, TWD, USD, VES, ZAR

---

## VAT Computation

Philippine VAT is fixed at 12% as mandated by the National Internal Revenue Code (NIRC).

VAT is computed on the Philippine Peso equivalent of the transaction:

No VAT:    Converted Amount = amount × exchange rate
With VAT:  VAT Amount       = PHP equivalent × 0.12
Total            = PHP equivalent × 1.12

VAT is only shown when the Philippine Peso (PHP) is one of the selected currencies.
Foreign-to-foreign conversions display the converted amount only.

---

## Known Limitations

- Exchange rates are only available for dates within the loaded CSV dataset
- Weekends and Philippine public holidays have no rate data — the system falls back
  to the most recent available trading day
- BSP publishes rates on business days only, typically once per day
- The dataset covers approximately 6 months of trading days

