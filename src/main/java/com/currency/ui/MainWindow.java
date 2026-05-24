/*
================================================================
MainWindow.java
================================================================
Script Purpose:
    Majority if not everything that relates to UI/GUI is here.
    So siya ung nagccreate nung pinakamain window na nakikita natin
Notes:
    FlatDarkLaf was used to make it more modern and clean looking,
    compared to simple java swing GUI.
================================================================
*/

package com.currency.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.currency.db.ExchangeRateDAO;
import com.currency.logic.CurrencyConverter;
import com.currency.logic.VATCalculator;
import static com.currency.ui.AppConstants.ACCENT;
import static com.currency.ui.AppConstants.ACCENT_HOVER;
import static com.currency.ui.AppConstants.BG_CARD;
import static com.currency.ui.AppConstants.BG_DARK;
import static com.currency.ui.AppConstants.BG_INPUT;
import static com.currency.ui.AppConstants.BORDER_COLOR;
import static com.currency.ui.AppConstants.CURRENCY_NAMES;
import static com.currency.ui.AppConstants.SUCCESS;
import static com.currency.ui.AppConstants.TEXT_MUTED;
import static com.currency.ui.AppConstants.TEXT_PRIMARY;
import static com.currency.ui.AppConstants.WARNING;
import static com.currency.ui.AppConstants.WINDOW_HEIGHT;
import static com.currency.ui.AppConstants.WINDOW_WIDTH;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

public class MainWindow extends JFrame {

    private JTextField amountField;
    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private DatePicker datePicker;
    private JRadioButton vatInclusive;
    private JRadioButton vatExclusive;
    private JLabel rateLabel;
    private JLabel baseAmountLabel;
    private JLabel vatAmountLabel;
    private JLabel totalLabel;
    private JLabel convertedLabel;
    private JLabel dateNoticeLabel;
    private JPanel vatSection;


    public MainWindow() {
        //For the title of the GUI App (ung nasa top left), includes the size of it
        setTitle("Currency Exchange + VAT");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BG_DARK);

        // creating of main panel
        final JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(BG_DARK);
        main.setBorder(new EmptyBorder(16, 24, 16, 24));

        // The title label, different from earlier setTitle, eto ung nakikita sa main app
        final JLabel title = new JLabel("Currency Exchange + VAT");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        main.add(title);

        // subtitle for it
        final JLabel subtitle = new JLabel("BSP-referenced rates · 12% VAT computation");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        main.add(subtitle);
        main.add(Box.createVerticalStrut(12));

        // Input card
        final JPanel inputCard = makeCard();
        inputCard.setLayout(new BoxLayout(inputCard, BoxLayout.Y_AXIS));

        // Amount + From + To in tighter layout
        inputCard.add(makeFieldLabel("Amount"));
        amountField = makeTextField();
        inputCard.add(amountField);
        inputCard.add(Box.createVerticalStrut(8));

        final List<String> currencies = ExchangeRateDAO.getAvailableCurrencies();
        final String[] displayNames = currencies.stream()
            .map(c -> CURRENCY_NAMES.getOrDefault(c, c))
            .toArray(String[]::new);

        // From + To side by side
        final JPanel currencyRow = new JPanel(new GridLayout(1, 2, 8, 0));
        currencyRow.setBackground(BG_CARD);
        currencyRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        currencyRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));

        // panel ng From currency
        final JPanel fromPanel = new JPanel();
        fromPanel.setLayout(new BoxLayout(fromPanel, BoxLayout.Y_AXIS));
        fromPanel.setBackground(BG_CARD);
        fromPanel.add(makeFieldLabel("From"));
        fromCurrency = makeComboBox(displayNames);
        fromPanel.add(fromCurrency);

        // panel ng To currency
        final JPanel toPanel = new JPanel();
        toPanel.setLayout(new BoxLayout(toPanel, BoxLayout.Y_AXIS));
        toPanel.setBackground(BG_CARD);
        toPanel.add(makeFieldLabel("To"));
        toCurrency = makeComboBox(displayNames);
        toCurrency.setSelectedItem(CURRENCY_NAMES.getOrDefault("USD", "USD"));
        toPanel.add(toCurrency);

        currencyRow.add(fromPanel);
        currencyRow.add(toPanel);
        inputCard.add(currencyRow);
        inputCard.add(Box.createVerticalStrut(8));

        // Date picker
        inputCard.add(makeFieldLabel("Date"));
        final DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("yyyy-MM-dd");

        // color/ui for date
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundOverallCalendarPanel, BG_CARD);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearNavigationButtons, BG_CARD);
        dateSettings.setColor(DatePickerSettings.DateArea.CalendarBackgroundNormalDates, BG_CARD);
        dateSettings.setColor(DatePickerSettings.DateArea.CalendarBackgroundSelectedDate, ACCENT);
        dateSettings.setColor(DatePickerSettings.DateArea.CalendarBorderSelectedDate, ACCENT);
        dateSettings.setColor(DatePickerSettings.DateArea.CalendarTextNormalDates, TEXT_PRIMARY);
        dateSettings.setColor(DatePickerSettings.DateArea.CalendarTextWeekdays, TEXT_MUTED);
        dateSettings.setColor(DatePickerSettings.DateArea.TextMonthAndYearNavigationButtons, TEXT_PRIMARY);
        dateSettings.setColor(DatePickerSettings.DateArea.TextFieldBackgroundValidDate, BG_INPUT);
        dateSettings.setColor(DatePickerSettings.DateArea.DatePickerTextValidDate, TEXT_PRIMARY);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels, BG_CARD);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundTodayLabel, BG_CARD);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundClearLabel, BG_CARD);
        dateSettings.setColor(DatePickerSettings.DateArea.TextTodayLabel, ACCENT);
        dateSettings.setColor(DatePickerSettings.DateArea.TextClearLabel, TEXT_MUTED);

        datePicker = new DatePicker(dateSettings);
        datePicker.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        datePicker.setAlignmentX(Component.LEFT_ALIGNMENT);

        // picks the latest date (available) as default pick for the date section
        final String latestDate = ExchangeRateDAO.getLatestDate();
        if (latestDate != null) {
            datePicker.setDate(LocalDate.parse(latestDate));
        }

        dateNoticeLabel = new JLabel(" ");
        dateNoticeLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        dateNoticeLabel.setForeground(WARNING);
        dateNoticeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        inputCard.add(datePicker);
        inputCard.add(Box.createVerticalStrut(2));
        inputCard.add(dateNoticeLabel);

        main.add(inputCard);
        main.add(Box.createVerticalStrut(8));

        // VAT card
        final JPanel vatCard = makeCard();
        vatCard.setLayout(new BoxLayout(vatCard, BoxLayout.Y_AXIS));
        vatCard.add(makeFieldLabel("VAT Mode"));
        vatCard.add(Box.createVerticalStrut(4));

        vatExclusive = makeRadioButton("No VAT — show base amount only");
        vatInclusive = makeRadioButton("With VAT — add 12% on top");
        vatExclusive.setSelected(true);
        final ButtonGroup vatGroup = new ButtonGroup();
        vatGroup.add(vatExclusive);
        vatGroup.add(vatInclusive);
        vatCard.add(vatExclusive);
        vatCard.add(vatInclusive);

        main.add(vatCard);
        main.add(Box.createVerticalStrut(8));

        // Convert button
        final JButton convertBtn = new JButton("Convert");
        convertBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        convertBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        convertBtn.setBackground(ACCENT);
        convertBtn.setForeground(Color.WHITE);
        convertBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        convertBtn.setFocusPainted(false);
        convertBtn.setBorderPainted(false);
        convertBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        convertBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(final java.awt.event.MouseEvent e) {
                convertBtn.setBackground(ACCENT_HOVER);
            }
            @Override
            public void mouseExited(final java.awt.event.MouseEvent e) {
                convertBtn.setBackground(ACCENT);
            }
        });
        convertBtn.addActionListener(e -> calculate());
        main.add(convertBtn);
        main.add(Box.createVerticalStrut(8));

        // Results card
        final JPanel resultsCard = makeCard();
        resultsCard.setLayout(new BoxLayout(resultsCard, BoxLayout.Y_AXIS));

        final JPanel rateRow = new JPanel(new BorderLayout());
        rateRow.setBackground(BG_CARD);
        rateRow.add(makeResultLabel("Exchange Rate"), BorderLayout.WEST);
        rateLabel = makeResultValue();
        rateRow.add(rateLabel, BorderLayout.EAST);

        final JPanel convertedRow = new JPanel(new BorderLayout());
        convertedRow.setBackground(BG_CARD);
        convertedLabel = makeResultValue();
        convertedLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        convertedLabel.setForeground(SUCCESS);
        convertedRow.add(makeResultLabel("Converted Amount"), BorderLayout.WEST);
        convertedRow.add(convertedLabel, BorderLayout.EAST);

        resultsCard.add(rateRow);
        resultsCard.add(Box.createVerticalStrut(6));
        resultsCard.add(convertedRow);

        // VAT section
        vatSection = new JPanel(new GridLayout(3, 2, 0, 6));
        vatSection.setBackground(BG_CARD);
        vatSection.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(1, 0, 0, 0, BORDER_COLOR),
            new EmptyBorder(8, 0, 0, 0)
        ));

        vatSection.add(makeResultLabel("Amount (PHP)"));
        baseAmountLabel = makeResultValue();
        vatSection.add(baseAmountLabel);

        vatSection.add(makeResultLabel("VAT (12%)"));
        vatAmountLabel = makeResultValue();
        vatAmountLabel.setForeground(WARNING);
        vatSection.add(vatAmountLabel);

        vatSection.add(makeResultLabel("Total (PHP)"));
        totalLabel = makeResultValue();
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalLabel.setForeground(SUCCESS);
        vatSection.add(totalLabel);

        vatSection.setVisible(false);
        resultsCard.add(Box.createVerticalStrut(6));
        resultsCard.add(vatSection);

        main.add(resultsCard);

        add(main);
    }

    private JPanel makeCard() {
        final JPanel card = new JPanel();
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(1, 1, 1, 1, BORDER_COLOR),
            new EmptyBorder(10, 12, 10, 12)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return card;
    }

    private JLabel makeFieldLabel(final String text) {
        final JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        label.setForeground(TEXT_MUTED);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(0, 0, 2, 0));
        return label;
    }

    private JTextField makeTextField() {
        final JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        field.setBackground(BG_INPUT);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(1, 1, 1, 1, BORDER_COLOR),
            new EmptyBorder(3, 8, 3, 8)
        ));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JComboBox<String> makeComboBox(final String[] items) {
        final JComboBox<String> combo = new JComboBox<>(items);
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        combo.setBackground(BG_INPUT);
        combo.setForeground(TEXT_PRIMARY);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setBorder(new MatteBorder(1, 1, 1, 1, BORDER_COLOR));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return combo;
    }

    private JRadioButton makeRadioButton(final String text) {
        final JRadioButton btn = new JRadioButton(text);
        btn.setBackground(BG_CARD);
        btn.setForeground(TEXT_PRIMARY);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        return btn;
    }

    private JLabel makeResultLabel(final String text) {
        final JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(TEXT_MUTED);
        return label;
    }

    private JLabel makeResultValue() {
        final JLabel label = new JLabel("—");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(TEXT_PRIMARY);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    private String getCodeFromDisplay(final String display) {
        if (display == null) return "PHP";
        return display.contains("—") ? display.split("—")[0].replaceAll("[^A-Z]", "").trim() : display.trim();
    }

    private void calculate() {
        try {
            final double amount = Double.parseDouble(amountField.getText().trim());
            final String from = getCodeFromDisplay((String) fromCurrency.getSelectedItem());
            final String to = getCodeFromDisplay((String) toCurrency.getSelectedItem());

            if (from.equals(to)) {
                JOptionPane.showMessageDialog(this, "From and To currencies cannot be the same.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            final LocalDate picked = datePicker.getDate();
            if (picked == null) {
                JOptionPane.showMessageDialog(this, "Please select a valid date.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            final String selectedDate = picked.toString();
            final String date = ExchangeRateDAO.getNearestDate(selectedDate);

            if (date == null) {
                JOptionPane.showMessageDialog(this, "No data available for or before this date.",
                    "No Data", JOptionPane.WARNING_MESSAGE);
                return;
            }

            dateNoticeLabel.setText(
                !date.equals(selectedDate)
                ? "No data for " + selectedDate + " — showing " + date
                : " "
            );

            final double converted = CurrencyConverter.convert(from, to, amount, date);
            final String rateBase = from.equals("PHP") ? to : from;
            final double rate = ExchangeRateDAO.getRate(rateBase, date);
            rateLabel.setText(String.format("1 %s = ₱ %.4f", rateBase, rate));
            convertedLabel.setText(String.format("%.4f %s", converted, to));

            final boolean phpInvolved = from.equals("PHP") || to.equals("PHP");
            vatSection.setVisible(phpInvolved);

            if (phpInvolved) {
                final double inPHP = CurrencyConverter.convert(from, "PHP", amount, date);
                final double vatAmount = vatInclusive.isSelected() ? VATCalculator.getVATAmount(inPHP) : 0;
                final double total = vatInclusive.isSelected() ? VATCalculator.getTotal(inPHP) : inPHP;

                baseAmountLabel.setText(String.format("₱ %,.2f", inPHP));
                vatAmountLabel.setText(vatInclusive.isSelected() ? String.format("₱ %,.2f", vatAmount) : "N/A");
                totalLabel.setText(String.format("₱ %,.2f", total));
            }

            revalidate();
            repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}