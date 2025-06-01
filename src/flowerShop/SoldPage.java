package flowerShop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;

public class SoldPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;

    public SoldPage() {
        setTitle("Kwiaciarnia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout()); // Używamy GridBagLayout
        contentPane.setBackground(new Color(245, 245, 245)); // Jasnoszare tło
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        // Przyciski
        createButtons();

        // Panel z tabelą (oddzielony od przycisków)
        JPanel tablePanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        tablePanel.add(scrollPane, BorderLayout.CENTER);

       
        table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Zmieniamy czcionkę
        table.setRowHeight(30); // Zwiększamy wysokość wiersza dla lepszej czytelności
        table.setSelectionBackground(new Color(102, 204, 255)); // Kolor tła zaznaczenia
        table.setSelectionForeground(Color.BLACK); // Kolor tekstu zaznaczonego wiersza
        scrollPane.setViewportView(table);

        loadDataToTable("sprzedane_baza.txt");
    }

    private void createButtons() {
        // Ustawienie przycisków z nowoczesnym wyglądem
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
        gbc.fill = GridBagConstraints.HORIZONTAL; // Przyciski rozciągają się w poziomie
        gbc.insets = new Insets(0, 0, 50, 0); // Odstęp między przyciskami a tabelą
        gbc.weighty = 0; // Zapewnia, że przyciski nie będą rozciągać się w pionie

        // Ustawienie parametrów dla równej szerokości przycisków
        gbc.weightx = 1; // Zapewnia, że każdy przycisk będzie miał równą wagę (szerokość)
        gbc.gridwidth = 1; // Każdy przycisk zajmuje 1 kolumnę

        // Przypisujemy przyciski równomiernie na 5 kolumn
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(70, 130, 180)); // Kolor tła przycisku
            button.setForeground(Color.WHITE); // Kolor tekstu przycisku
            button.setFocusPainted(false); // Usunięcie konturu przy kliknięciu
            button.setBorder(new LineBorder(new Color(50, 80, 120), 2, true)); // Obramowanie przycisku
            button.addActionListener(buttonActions[i]);
            // Zmiana wysokości przycisków
            button.setPreferredSize(new java.awt.Dimension(button.getPreferredSize().width, 60)); // Zmieniamy wysokość na 60px

            // Rozmieszczamy przyciski równomiernie
            gbc.gridx = i; // Ustawiamy pozycję przycisku w wierszu
            contentPane.add(button, gbc);
        }
    }


    private void loadDataToTable(String fileName) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Data");
        model.addColumn("Imię i Nazwisko");
        model.addColumn("Telefon");
        model.addColumn("Adres");
        model.addColumn("Zakupy");

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            List<String> currentPurchase = new ArrayList<>();
            String date = "", name = "", phone = "", address = "";

            while ((line = br.readLine()) != null) {
                if (line.trim().matches("\\d+\\.\\d{1,2}")) {
                    continue;
                }

                if (line.trim().equalsIgnoreCase("koniec")) {
                    if (!currentPurchase.isEmpty()) {
                        model.addRow(new Object[] {
                            date, name, phone, address, currentPurchase.get(0)
                        });

                        for (int i = 1; i < currentPurchase.size(); i++) {
                            model.addRow(new Object[] {
                                "", "", "", "", currentPurchase.get(i)
                            });
                        }

                        currentPurchase.clear();
                        date = name = phone = address = "";
                    }
                } else {
                    if (date.isEmpty()) {
                        date = line;
                    } else if (name.isEmpty()) {
                        name = line;
                    } else if (phone.isEmpty()) {
                        phone = line;
                    } else if (address.isEmpty()) {
                        address = line;
                    } else {
                        currentPurchase.add(line);
                    }
                }
            }

            if (!currentPurchase.isEmpty()) {
                model.addRow(new Object[] {
                    date, name, phone, address, currentPurchase.get(0)
                });

                for (int i = 1; i < currentPurchase.size(); i++) {
                    model.addRow(new Object[] {
                        "", "", "", "", currentPurchase.get(i)
                    });
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Błąd podczas wczytywania danych: " + e.getMessage(),
                    "Błąd", JOptionPane.ERROR_MESSAGE);
        }

        table.setModel(model);

        // Ustawienie wyrównania tekstu w komórkach
        for (int column = 0; column < table.getColumnCount(); column++) {
            for (int row = 0; row < table.getRowCount(); row++) {
                Object value = table.getValueAt(row, column);
                if (value != null) {
                    table.setValueAt(value.toString().trim(), row, column);
                }
            }
        }

        // Optymalizacja szerokości kolumn
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 0;
            for (int row = 0; row < table.getRowCount(); row++) {
                Object value = table.getValueAt(row, column);
                if (value != null) {
                    width = Math.max(width, value.toString().length());
                }
            }
            table.getColumnModel().getColumn(column).setPreferredWidth(width * 10);
        }

        // Dodanie kolorów wierszy
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                                                                    boolean isSelected, boolean hasFocus,
                                                                    int row, int column) {
                java.awt.Component cell = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (row % 2 == 0) {
                    cell.setBackground(new Color(240, 240, 240)); // Jasnoszary kolor dla parzystych wierszy
                } else {
                    cell.setBackground(Color.WHITE); // Biały kolor dla nieparzystych wierszy
                }

                if (isSelected) {
                    cell.setBackground(new Color(102, 204, 255)); // Kolor zaznaczenia
                }

                return cell;
            }
        });

        // Dodanie scrolla i wyświetlenie tabeli
        JScrollPane scrollPane = new JScrollPane(table);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1; // Tabela zaczyna się od drugiej kolumny
        gbc.gridy = 1;
        gbc.gridwidth = 3; // Tabela zajmuje 3 kolumny
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1; // Tabela rozciąga się na dostępną szerokość
        gbc.weighty = 1; // Umożliwia wypełnienie przestrzeni
        contentPane.add(scrollPane, gbc);
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

   
}
