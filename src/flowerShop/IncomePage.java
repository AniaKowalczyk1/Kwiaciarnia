package flowerShop;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class IncomePage extends JFrame {

    private static Map<String, Double> monthlyExpenses = new HashMap<>();
    private static Map<String, Double> monthlyIncome = new HashMap<>();
    private JPanel contentPane;

    public IncomePage() {
        setTitle("Kwiaciarnia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBackground(new Color(245, 245, 245)); // Jasnoszare tło
        setContentPane(contentPane);

        createButtons(); // Tworzymy przyciski za pomocą nowoczesnej funkcji
     // Panel z tabelą
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        GridBagConstraints gbcTable = new GridBagConstraints();
        gbcTable.gridx = 1; // Tabela zaczyna się od drugiego przycisku (kolumna 1)
        gbcTable.gridy = 2; // Tabela poniżej przycisków
        gbcTable.gridwidth = 3; // Tabela zajmuje kolumny od 1 do 3
        gbcTable.insets = new Insets(10, 10, 30, 10); // Zwiększony odstęp od dołu
        gbcTable.fill = GridBagConstraints.BOTH;
        gbcTable.weightx = 1;
        gbcTable.weighty = 1;
        contentPane.add(scrollPane, gbcTable);

        // Panel z wykresem
        JPanel chartPanel = createChartPanel();
        GridBagConstraints gbcChart = new GridBagConstraints();
        gbcChart.gridx = 1; // Wykres zaczyna się od drugiego przycisku (kolumna 1)
        gbcChart.gridy = 1; // Wykres poniżej tabeli
        gbcChart.gridwidth = 3; // Wykres zajmuje kolumny od 1 do 3
        gbcChart.insets = new Insets(10, 10, 10, 10); // Zwiększony odstęp od góry (w razie potrzeby)
        gbcChart.fill = GridBagConstraints.BOTH;
        gbcChart.weightx = 1;
        gbcChart.weighty = 1;
        contentPane.add(chartPanel, gbcChart);



        // Obliczanie danych i rysowanie wykresu
        try {
            String expensesFilePath = "kwiaty_ciete_baza.txt";
            String salesFilePath = "sprzedane_baza.txt";

            monthlyExpenses = calculateMonthlyExpenses(expensesFilePath);
            monthlyIncome = calculateMonthlyIncome(salesFilePath);

            Object[][] tableData = mergeData(monthlyExpenses, monthlyIncome);
            String[] columnNames = {"Data (Rok-Miesiąc)", "Wydatki", "Przychód", "Dochód"};

            table.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));
            chartPanel.repaint();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(contentPane, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);
    }

    

    private void openPage(JFrame page) {
        page.setVisible(true);
        dispose();
    }

    private void logOut() {
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
        dispose();
    }

    
    private void createButtons() {
        String[] buttonLabels = {"Magazyn", "Sprzedane", "Blog", "Dochód", "Wyloguj"};
        ActionListener[] buttonActions = {
                e -> openPage(new MainPage()),
                e -> openPage(new SoldPage()),
                e -> openPage(new BlogPage()),
                e -> openPage(new IncomePage()),
                e -> logOut()
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0; // Przyciski są w pierwszym wierszu
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 50, 0); // Odstęp między przyciskami a tabelą
        gbc.weighty = 0; 
        gbc.weightx = 1; 
        gbc.gridwidth = 1;

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
            button.addActionListener(buttonActions[i]);
            button.setPreferredSize(new Dimension(button.getPreferredSize().width, 60));

            gbc.gridx = i;
            contentPane.add(button, gbc);
        }
    }

    private JPanel createChartPanel() {
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (monthlyExpenses.isEmpty() || monthlyIncome.isEmpty()) {
                    return;
                }

                Graphics2D g2d = (Graphics2D) g;
                int padding = 50; // Odstęp od krawędzi
                int labelPadding = 20; // Odstęp dla podpisów osi
                int chartWidth = getWidth() - 2 * padding;
                int chartHeight = getHeight() - 2 * padding - labelPadding;

                // Przygotowanie miesięcy i lat w odpowiedniej kolejności
                List<String> monthsList = new ArrayList<>();
                for (String month : monthlyExpenses.keySet()) {
                    monthsList.add(month);
                }
                for (String month : monthlyIncome.keySet()) {
                    if (!monthsList.contains(month)) {
                        monthsList.add(month);
                    }
                }

                // Sortowanie miesięcy w porządku rosnącym (Styczeń, Luty, ..., Grudzień)
                Collections.sort(monthsList, new Comparator<String>() {
                    @Override
                    public int compare(String month1, String month2) {
                        int monthIndex1 = getMonthIndex(month1);
                        int monthIndex2 = getMonthIndex(month2);
                        return Integer.compare(monthIndex1, monthIndex2);
                    }

                    private int getMonthIndex(String month) {
                        switch (month) {
                            case "Styczeń": return 1;
                            case "Luty": return 2;
                            case "Marzec": return 3;
                            case "Kwiecień": return 4;
                            case "Maj": return 5;
                            case "Czerwiec": return 6;
                            case "Lipiec": return 7;
                            case "Sierpień": return 8;
                            case "Wrzesień": return 9;
                            case "Październik": return 10;
                            case "Listopad": return 11;
                            case "Grudzień": return 12;
                            default: return 0;
                        }
                    }
                });

                // Obliczenie maksymalnej wartości na osi Y
                double maxValue = Math.max(
                        Math.abs(monthlyExpenses.values().stream().mapToDouble(Double::doubleValue).max().orElse(1)),
                        Math.abs(monthlyIncome.values().stream().mapToDouble(Double::doubleValue).max().orElse(1))
                );

                // Rysowanie osi Y
                g2d.setColor(Color.BLACK);
                g2d.drawLine(padding, getHeight() - padding - labelPadding, padding, padding); // Oś Y

                // Rysowanie osi X w połowie wysokości wykresu
                int axisXPosition = getHeight() / 2;
                g2d.drawLine(padding, axisXPosition, getWidth() - padding, axisXPosition); // Oś X

                // Podpis osi Y
                g2d.drawString("Wartość (zł)", padding - 40, padding - 10);

                int barWidth = (chartWidth - (monthsList.size() + 1) * 10) / (3 * monthsList.size()); // Proporcjonalna szerokość słupków
                int barSpacing = (chartWidth - (barWidth * 3 * monthsList.size())) / (monthsList.size() + 1); // Odstęp między zestawami słupków

                int x = padding + barSpacing; // Początek rysowania słupków

                // Rysowanie słupków
                for (String month : monthsList) {
                    // Oblicz wysokości słupków
                    int expenseHeight = (int) ((monthlyExpenses.getOrDefault(month, 0.0) / maxValue) * (chartHeight - 90)); // Zmniejszamy wysokość słupków o margines
                    int incomeHeight = (int) ((monthlyIncome.getOrDefault(month, 0.0) / maxValue) * (chartHeight - 90)); // Zmniejszamy wysokość słupków o margines
                    int profitHeight = (int) (((monthlyIncome.getOrDefault(month, 0.0) - monthlyExpenses.getOrDefault(month, 0.0)) / maxValue) * (chartHeight - 90)); // Zmniejszamy wysokość słupków o margines

                    // Rysowanie słupków dla wydatków (negatywne wartości poniżej osi X, pozytywne powyżej)
                    g2d.setColor(Color.DARK_GRAY);
                    if (monthlyExpenses.getOrDefault(month, 0.0) < 0) {
                        g2d.fillRect(x, axisXPosition, barWidth, Math.min(-expenseHeight, chartHeight));  // Słupki ujemne rysowane w dół
                    } else {
                        g2d.fillRect(x, axisXPosition - expenseHeight, barWidth, Math.min(expenseHeight, chartHeight)); // Słupki dodatnie powyżej osi X
                    }

                    // Rysowanie słupków dla przychodów (negatywne wartości poniżej osi X, pozytywne powyżej)
                    g2d.setColor(Color.GRAY);
                    if (monthlyIncome.getOrDefault(month, 0.0) < 0) {
                        g2d.fillRect(x + barWidth + 5, axisXPosition, barWidth, Math.min(-incomeHeight, chartHeight));  // Słupki ujemne rysowane w dół
                    } else {
                        g2d.fillRect(x + barWidth + 5, axisXPosition - incomeHeight, barWidth, Math.min(incomeHeight, chartHeight)); // Słupki dodatnie powyżej osi X
                    }

                    // Rysowanie słupków dla dochodu (różnica między przychodami a wydatkami)
                    g2d.setColor(Color.LIGHT_GRAY);
                    if ((monthlyIncome.getOrDefault(month, 0.0) - monthlyExpenses.getOrDefault(month, 0.0)) < 0) {
                        g2d.fillRect(x + 2 * (barWidth + 5), axisXPosition, barWidth, Math.min(-profitHeight, chartHeight));  // Słupki ujemne rysowane w dół
                    } else {
                        g2d.fillRect(x + 2 * (barWidth + 5), axisXPosition - profitHeight, barWidth, Math.min(profitHeight, chartHeight)); // Słupki dodatnie powyżej osi X
                    }

                    // Rysowanie nazw miesięcy
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(month, x + barWidth / 2, getHeight() - padding);

                    // Rysowanie wartości nad słupkami
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(String.format("%.2f", monthlyExpenses.getOrDefault(month, 0.0)), x + barWidth / 2, axisXPosition - expenseHeight - 15);
                    g2d.drawString(String.format("%.2f", monthlyIncome.getOrDefault(month, 0.0)), x + barWidth + 5 + barWidth / 2, axisXPosition - incomeHeight - 15);
                    g2d.drawString(String.format("%.2f", monthlyIncome.getOrDefault(month, 0.0) - monthlyExpenses.getOrDefault(month, 0.0)), x + 2 * (barWidth + 5) + barWidth / 2, axisXPosition - profitHeight - 15);

                    x += 3 * barWidth + 2 * barSpacing; // Przesunięcie na kolejny zestaw słupków
                }

              
            }
        };

        chartPanel.setBackground(Color.WHITE);
        chartPanel.setPreferredSize(new Dimension(800, 300));
        return chartPanel;
    }





    private static Map<String, Double> calculateMonthlyExpenses(String filePath) throws IOException {
        Map<String, Double> monthlyExpenses = new HashMap<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty() || Character.isDigit(line.charAt(0))) {
                    continue; // Skip ID or empty lines
                }

                String[] lines = new String[10];
                for (int i = 0; i < 10 && line != null; i++) {
                    lines[i] = line;
                    line = reader.readLine();
                }

                double purchasePrice = Double.parseDouble(lines[5]);
                int purchasedQuantity = Integer.parseInt(lines[4]);
                String purchaseDate = lines[6];

                LocalDate date = LocalDate.parse(purchaseDate, dateFormatter);
                String month = date.getMonth().toString() + " " + date.getYear();

                double totalCost = purchasePrice * purchasedQuantity;
                monthlyExpenses.merge(month, totalCost, Double::sum);
            }
        }

        return monthlyExpenses;
    }

    private static Map<String, Double> calculateMonthlyIncome(String filePath) throws IOException {
        Map<String, Double> monthlyIncome = new HashMap<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentDate = "";
            double totalIncome = 0.0;
            double lastSaleAmount = 0.0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Jeśli linia zawiera datę (yyyy-mm-dd), zapisujemy datę transakcji
                if (line.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    currentDate = line;
                }

                // Jeśli linia zawiera łączną kwotę sprzedaży (liczbę), zapisujemy ją jako ostatnią kwotę przed "koniec"
                else if (line.matches("[0-9]+(\\.[0-9]{1,2})?")) {
                    // Zamieniamy przecinek na kropkę w przypadku kwot z przecinkiem
                    String cleanedAmount = line.replace(",", ".");
                    lastSaleAmount = Double.parseDouble(cleanedAmount);
                }

                // Gdy napotkamy słowo "koniec", sumujemy dochód z ostatniej transakcji
                else if (line.equals("koniec") && !currentDate.isEmpty()) {
                    // Jeżeli mamy datę i ostatnią kwotę, dodajemy dochód do odpowiedniego miesiąca
                    if (lastSaleAmount > 0) {
                        // Parsujemy datę do miesiąca i roku
                        LocalDate date = LocalDate.parse(currentDate, dateFormatter);
                        String month = date.getMonth().toString() + " " + date.getYear();

                        // Sumujemy dochód dla miesiąca
                        monthlyIncome.merge(month, lastSaleAmount, Double::sum);
                    }
                    // Resetujemy zmienne do następnej transakcji
                    lastSaleAmount = 0.0;
                }
            }
        }

        return monthlyIncome;
    }
    

    private static Object[][] mergeData(Map<String, Double> monthlyExpenses, Map<String, Double> monthlyIncome) {
        // Tworzymy nowy, modyfikowalny zestaw z kluczy obu map
        Set<String> allMonths = new HashSet<>(monthlyExpenses.keySet());
        allMonths.addAll(monthlyIncome.keySet());  // Dodajemy klucze z drugiej mapy
        
        Object[][] data = new Object[allMonths.size()][4];
        int index = 0;
        
        for (String month : allMonths) {
            Double expense = monthlyExpenses.getOrDefault(month, 0.0);
            Double income = monthlyIncome.getOrDefault(month, 0.0);
            Double profit = income - expense;
            data[index][0] = month;
            data[index][1] = String.format("%.2f", expense);
            data[index][2] = String.format("%.2f", income);
            data[index][3] = String.format("%.2f", profit);
            index++;
        }
        
        return data;
    }
}
