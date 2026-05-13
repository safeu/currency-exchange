# Currency Exchange + VAT System

A project for Computer Fundamentals and Programming course. A Java Swing desktop app for currency exchange with Philippine VAT computation.
Built using BSP-referenced exchange rates.

## Requirements
- Java JDK 17 or higher
- Apache Maven 3.6 or higher

## How to Run

1. Clone the repository
2. Place your `bsp_rates.csv` inside the `data/` folder or use the one here
3. Run the following command:

    mvn compile exec:java "-Dexec.mainClass=com.currency.Main"

The app will automatically:
- Create the SQLite database on first run
- Import rates from `bsp_rates.csv`
- Launch the UI

## CSV Format

The `data/bsp_rates.csv` file must follow this format:

    currency_code,rate,date
    USD,60.69,2026-05-08
    EUR,71.22,2026-05-08

- `rate` is always the PHP equivalent of 1 unit of that currency (conversion rate to PHP)
- `date` must be in `YYYY-MM-DD` format
- Skip any N/A currencies

## Project Structure

    src/main/java/com/currency/
    ├── Main.java                  # Entry point
    ├── db/
    │   ├── DatabaseConnection.java
    │   ├── DatabaseInitializer.java
    │   └── ExchangeRateDAO.java
    ├── logic/
    │   ├── CurrencyConverter.java
    │   ├── ExchangeRateService.java
    │   └── VATCalculator.java
    └── ui/
        ├── AppConstants.java
        └── MainWindow.java

    Data is in root folder (data/)

## Data Source

Exchange rates are based on BSP (Bangko Sentral ng Pilipinas) reference rates.
VAT computed at 12% as per Philippine tax law.