package flowerShop;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class UserBlog extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String obrazekSciezka = null;  
    private String fileName = "blog.txt";
    private JTextArea textArea;
    private JTextArea wpisField;
    private ImageIcon iconN = new ImageIcon("C:\\Users\\AnnaK\\eclipse-workspace\\FlowerShopProject\\image.png");
    private JLabel lblNewLabel = new JLabel(); 
    private int selectedPostIndex = -1; 
    private List<Wpis> wpisy = new ArrayList<>();

    public UserBlog() {
        // Ustawienia głównego okna
        setTitle("Kwiaciarnia");

        // Ustawienie pełnego rozmiaru ekranu
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ustawienie pełnego rozmiaru ekranu
        setLocationRelativeTo(null); // Ustawienie okna na środku ekranu


        // Ustawienie głównego panelu
        contentPane = new JPanel(new GridBagLayout());
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));  // Brak marginesów
        contentPane.setBackground(new Color(245, 245, 245));

        // Panel przycisków na górze
        createButtons(contentPane);

        // Panel na marginesy
        JPanel leftMarginPanel = new JPanel();
        leftMarginPanel.setPreferredSize(new Dimension(10, 0)); // Margines po lewej stronie
        leftMarginPanel.setBackground(new Color(245, 245, 245));  // Kolor tła pasujący do głównego panelu

        JPanel rightMarginPanel = new JPanel();
        rightMarginPanel.setPreferredSize(new Dimension(10, 0)); // Margines po prawej stronie
        rightMarginPanel.setBackground(new Color(245, 245, 245));  // Kolor tła pasujący do głównego panelu

        // Panel wpisów z BoxLayout
        JPanel wpisyPanel = new JPanel();
        wpisyPanel.setLayout(new BoxLayout(wpisyPanel, BoxLayout.Y_AXIS)); // Używamy BoxLayout z orientacją pionową
        wpisyPanel.setBackground(new Color(245, 245, 245));

        JScrollPane scrollPane = new JScrollPane(wpisyPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Wczytanie wpisów
        wyswietlWpisy(wpisyPanel);

     
        scrollPane.setViewportView(wpisyPanel);

        // Dodanie paneli marginesów oraz scrollPane do głównego panelu
        GridBagConstraints gbcLeftMargin = new GridBagConstraints();
        gbcLeftMargin.gridx = 0;
        gbcLeftMargin.gridy = 1;
        gbcLeftMargin.weightx = 0.1;  // Panel marginesu nie rozciąga się w poziomie
        gbcLeftMargin.weighty = 1.0;  // Panel marginesu wypełnia całą wysokość
        gbcLeftMargin.fill = GridBagConstraints.VERTICAL;
        contentPane.add(leftMarginPanel, gbcLeftMargin);  // Dodanie lewego marginesu

        GridBagConstraints gbcScroll = new GridBagConstraints();
        gbcScroll.gridx = 1;  // Wstawiamy scrollPane w środkową kolumnę
        gbcScroll.gridy = 1;
        gbcScroll.weightx = 0.8;  // ScrollPane rozciąga się w poziomie
        gbcScroll.weighty = 1.0;  // ScrollPane rozciąga się w pionie
        gbcScroll.fill = GridBagConstraints.BOTH;  // Rozciąganie w obu kierunkach
        contentPane.add(scrollPane, gbcScroll);

        GridBagConstraints gbcRightMargin = new GridBagConstraints();
        gbcRightMargin.gridx = 2;
        gbcRightMargin.gridy = 1;
        gbcRightMargin.weightx = 0.1;  // Panel marginesu nie rozciąga się w poziomie
        gbcRightMargin.weighty = 1.0;  // Panel marginesu wypełnia całą wysokość
        gbcRightMargin.fill = GridBagConstraints.VERTICAL;
        contentPane.add(rightMarginPanel, gbcRightMargin);  // Dodanie prawego marginesu

        // Dodanie całego contentPane do JScrollPane, aby cała strona była przewijalna
        JScrollPane mainScrollPane = new JScrollPane(contentPane);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setContentPane(mainScrollPane);  // Ustawienie głównego kontenera z przewijaniem
    }


    private void wyswietlWpisy(JPanel wpisyPanel) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            StringBuilder wpis = new StringBuilder();
            String sciezkaObrazka = null;
            boolean isReadingWpis = false;

            while ((line = reader.readLine()) != null) {
                if (line.equals("start")) {
                    isReadingWpis = true;
                    wpis.setLength(0);
                    sciezkaObrazka = null;
                } else if (line.equals("koniec")) {
                    isReadingWpis = false;

                    // Pobieramy ścieżkę do obrazka
                    sciezkaObrazka = reader.readLine();

                    // Tworzymy obiekt Wpis i dodajemy go do listy
                    Wpis nowyWpis = new Wpis(wpis.toString(), sciezkaObrazka);
                    wpisy.add(nowyWpis);

                    // Tworzymy panel dla danego wpisu
                    JPanel wpisPanel = new JPanel();
                    wpisPanel.setLayout(new GridBagLayout());
                    wpisPanel.setBackground(new Color(255, 255, 255));
                    wpisPanel.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));

                    // Panel dla obrazka i przycisku (po lewej stronie)
                    JPanel leftPanel = new JPanel();
                    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
                    leftPanel.setBackground(new Color(255, 255, 255));

                    // Dodajemy obrazek, jeśli istnieje
                    if (sciezkaObrazka != null && !sciezkaObrazka.isEmpty()) {
                        ImageIcon imageIcon = new ImageIcon(sciezkaObrazka);
                        Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        JLabel obrazekLabel = new JLabel(new ImageIcon(image));

                        leftPanel.add(obrazekLabel);
                    }

                    // Dodajemy odstęp 5px między obrazkiem a przyciskiem
                    leftPanel.add(Box.createVerticalStrut(5));

                    // Dodajemy przycisk "Zobacz szczegóły"
                    JButton viewButton = new JButton("Zobacz szczegóły");
                    viewButton.setFont(new Font("Arial", Font.BOLD, 14));
                    viewButton.setBackground(new Color(70, 130, 180));
                    viewButton.setForeground(Color.WHITE);
                    viewButton.setFocusPainted(false);
                    viewButton.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
                    viewButton.addActionListener(e -> openWpisWindow(nowyWpis));
                    viewButton.setPreferredSize(new Dimension(200, viewButton.getPreferredSize().height));
                    viewButton.setMaximumSize(new Dimension(200, viewButton.getPreferredSize().height));

                    leftPanel.add(viewButton);
                
                    
                    // Tworzymy JTextArea z tekstem
                    JTextArea wpisText = new JTextArea(wpis.toString());
                    wpisText.setEditable(false);
                    wpisText.setLineWrap(true);
                    wpisText.setWrapStyleWord(true);
                    wpisText.setFont(new Font("Arial", Font.PLAIN, 14));

                    // Ustawienia dla JTextArea
                    wpisText.setPreferredSize(null); // Usuwamy stałą preferowaną wielkość

                    // Ustawiamy JScrollPane wokół JTextArea
                    JScrollPane wpisScrollPane = new JScrollPane(wpisText);
                    wpisScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    wpisScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                    // Usuń stałą preferowaną wielkość, aby JScrollPane dostosował się do wysokości zawartości
                    wpisScrollPane.setPreferredSize(new Dimension(400, 200)); // Możesz dostosować szerokość, ale wysokość jest zależna od treści
                    GridBagConstraints gbcLeft = new GridBagConstraints();
                    gbcLeft.gridx = 0;
                    gbcLeft.gridy = 0;
                    gbcLeft.insets = new Insets(10, 10, 10, 10);
                    wpisPanel.add(leftPanel, gbcLeft);

                    GridBagConstraints gbcRight = new GridBagConstraints();
                    gbcRight.gridx = 1;
                    gbcRight.gridy = 0;
                    gbcRight.insets = new Insets(10, 10, 10, 10);
                    gbcRight.weightx = 1.0;
                    gbcRight.weighty = 1.0; // Ważne: dodaj weighty, aby JScrollPane rozciągał się w pionie
                    gbcRight.fill = GridBagConstraints.BOTH; // Zapewnia, że JScrollPane zajmuje całą dostępną przestrzeń
                    wpisPanel.add(wpisScrollPane, gbcRight); // Dodanie przewijanego panelu z tekstem

                    // Dodajemy wpisPanel do głównego panelu
                    wpisyPanel.add(wpisPanel);
                    wpisyPanel.add(Box.createVerticalStrut(20)); // Dodatkowy odstęp między wpisami
                } else if (isReadingWpis) {
                    wpis.append(line).append("\n");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Błąd przy odczycie wpisów!", "Błąd", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


    // Metoda otwierająca okno z pełnym widokiem wpisu
    private void openWpisWindow(Wpis wpis) {
        WpisWindow wpisWindow = new WpisWindow(wpis.tresc, wpis.obrazekSciezka);
        wpisWindow.setVisible(true);
    }

    private class Wpis {
        String tresc;
        String obrazekSciezka;

        public Wpis(String tresc, String obrazekSciezka) {
            this.tresc = tresc;
            this.obrazekSciezka = obrazekSciezka;
        }
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

    private void createButtons(JPanel contentPane) {
        // Zdefiniowanie przycisków
        String[] buttonLabels = {"Sklep", "Blog", "Wyloguj"};
        ActionListener[] buttonActions = {
                e -> openPage(new UserPage()),   // Akcja dla przycisku "Sklep"
                e -> openPage(new UserBlog()),   // Akcja dla przycisku "Blog"
                e -> logOut()                    // Akcja dla przycisku "Wyloguj"
        };

        // Panel na przyciski
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3)); // Siatka 1 wiersz, 3 kolumny
        buttonPanel.setBackground(new Color(245, 245, 245)); // Tło panelu

        // Ustawienie constraints dla panelu przycisków
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;  // Rozciąganie w poziomie
        gbc.gridwidth = 3; // Panel zajmuje całą szerokość
        gbc.insets = new Insets(0, 0, 40, 0); // Zwiększony odstęp od dołu

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
            button.addActionListener(buttonActions[i]);
            button.setPreferredSize(new Dimension(getWidth() / 3, 60)); // Rozciąganie przycisków na całą szerokość okna

            // Dodanie przycisku do buttonPanel
            buttonPanel.add(button);
        }

        // Dodanie panelu przycisków do contentPane
        contentPane.add(buttonPanel, gbc);
    }
}
