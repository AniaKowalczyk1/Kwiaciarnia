package flowerShop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;


public class UserPage extends JFrame{
    private static JPanel productPanel;
    private static JFrame frame;
 
    private static DefaultListModel<String> cartModel;
    private static JList<String> cartList;
    private static JLabel totalLabel;
    private static double totalAmount = 0.0;
    public UserPage() {
        // Ustawienia głównego okna
        setTitle("Kwiaciarnia");

        // Ustawienie pełnego rozmiaru ekranu
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ustawienie pełnego rozmiaru ekranu
        setLocationRelativeTo(null); // Ustawienie okna na środku ekranu

        // Ustawienie głównego panelu
        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));  // Brak marginesów
        contentPane.setBackground(new Color(245, 245, 245));

        // Panel przycisków na górze
        createButtons(contentPane);
        
        // Panel produktów
        productPanel = new JPanel();
        productPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // Siatka 3 kolumny, dynamiczna liczba wierszy
        productPanel.setBackground(new Color(245, 245, 245));

        // Dodanie JScrollPane
        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));

        // Panel koszyka i akcji
        JPanel cartPanel = createCartPanel();

        // Ustawienie układu z GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 50, 10, 50); // Mniejsze odstępy między komponentami
        gbc.weightx = 1.0; // Maksymalne wykorzystanie przestrzeni poziomej
        gbc.weighty = 1.0; // Maksymalne wykorzystanie przestrzeni pionowej
        gbc.fill = GridBagConstraints.BOTH; // Rozciąganie w obu kierunkach

        contentPane.add(scrollPane, gbc); // Dodanie JScrollPane do głównego panelu

        gbc.gridx = 1; // Drugi panel (koszyk)
        gbc.weightx = 0.15; // Zwiększamy elastyczność komponentu
        contentPane.add(cartPanel, gbc); // Dodanie panelu koszyka do głównego panelu

        // Wczytanie produktów
        loadProductsFromFile();
        setContentPane(contentPane);
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
            button.setMinimumSize(new Dimension(0, 60)); // Ustawienie minimalnej wysokości
            // Dodanie przycisku do buttonPanel
            buttonPanel.add(button);
        }

        // Dodanie panelu przycisków do contentPane
        contentPane.add(buttonPanel, gbc);
    }

    private static JPanel createCartPanel() {
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout());

        // Nagłówek koszyka
        JLabel cartHeader = new JLabel("Koszyk", SwingConstants.CENTER);
        cartHeader.setFont(new Font("Arial", Font.BOLD, 16));
        cartPanel.add(cartHeader, BorderLayout.NORTH);

        // Lista produktów w koszyku
        cartModel = new DefaultListModel<>();
        cartList = new JList<>(cartModel);
        JScrollPane cartScroll = new JScrollPane(cartList);
        cartPanel.add(cartScroll, BorderLayout.CENTER);

        // Panel podsumowania
        JPanel summaryPanel = new JPanel(new GridLayout(0, 1));
        totalLabel = new JLabel("Suma: 0.00 zł");

        JButton removeButton = new JButton("Usuń produkt");
        removeButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeButton.setBackground(new Color(70, 130, 180));
        removeButton.setForeground(Color.WHITE);
        removeButton.setFocusPainted(false);
        removeButton.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));

        JButton payButton = new JButton("Zapłać");
        payButton.setFont(new Font("Arial", Font.BOLD, 14));
        payButton.setBackground(new Color(70, 130, 180));
        payButton.setForeground(Color.WHITE);
        payButton.setFocusPainted(false);
        payButton.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));

        // Dodanie komponentów do panelu z odstępami
        summaryPanel.add(totalLabel);
        summaryPanel.add(Box.createVerticalStrut(5)); // Mniejszy odstęp
        summaryPanel.add(removeButton);
        summaryPanel.add(Box.createVerticalStrut(5)); // Mniejszy odstęp
        summaryPanel.add(payButton);
        cartPanel.add(summaryPanel, BorderLayout.SOUTH);

        // Obsługa usuwania produktu
        removeButton.addActionListener(e -> removeSelectedProduct());

        // Obsługa płatności
        payButton.addActionListener(e -> processPayment());

        return cartPanel;
    }

    private static void createCutFlowerPanel(String type, String kind, String variety, String color, String price, String available, String imagePath) {
        // Panel produktu
        JPanel singleProductPanel = new JPanel();
        singleProductPanel.setLayout(new BorderLayout());
        singleProductPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Dodanie obrazka
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(95, 95, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(imageIcon);
        singleProductPanel.add(imageLabel, BorderLayout.NORTH);

        // Tworzenie czcionki o stałym rozmiarze
        Font font = new Font("Arial", Font.PLAIN, 10);  // Możesz zmienić rozmiar czcionki, np. na 14, 16, itp.

        // Informacje o produkcie w elastycznym układzie
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));  // Użycie BoxLayout do automatycznego dopasowywania
        JLabel typeLabel = new JLabel("Typ: " + type);
        typeLabel.setFont(font);
        infoPanel.add(typeLabel);

        JLabel kindLabel = new JLabel("Rodzaj: " + kind);
        kindLabel.setFont(font);
        infoPanel.add(kindLabel);

        JLabel varietyLabel = new JLabel("Odmiana: " + variety);
        varietyLabel.setFont(font);
        infoPanel.add(varietyLabel);

        JLabel colorLabel = new JLabel("Kolor: " + color);
        colorLabel.setFont(font);
        infoPanel.add(colorLabel);

        JLabel priceLabel = new JLabel("Cena: " + price + " zł");
        priceLabel.setFont(font);
        infoPanel.add(priceLabel);

        JLabel availableLabel = new JLabel("Dostępnych: " + available);
        availableLabel.setFont(font);
        infoPanel.add(availableLabel);

        singleProductPanel.add(infoPanel, BorderLayout.CENTER);

        // Pole ilości i przycisk Dodaj
        JPanel actionPanel = new JPanel();
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.parseInt(available), 1));
        JButton addButton = new JButton("Dodaj");
        actionPanel.add(quantitySpinner);
        actionPanel.add(addButton);
        singleProductPanel.add(actionPanel, BorderLayout.SOUTH);

        // Obsługa dodawania do koszyka
        addButton.addActionListener(e -> addToCart(type, kind, variety, color, price, quantitySpinner));

        singleProductPanel.setMaximumSize(new Dimension(220, 250)); // Stały rozmiar
        singleProductPanel.setPreferredSize(new Dimension(220, 250));
        singleProductPanel.setMinimumSize(new Dimension(220, 250));
        productPanel.add(singleProductPanel); // Dodanie panelu produktu bez scrolla
    }



    private static void createPottedFlowerPanel(String type, String kind, String size, String color, String price, String available, String imagePath) {
        // Panel produktu
        JPanel singleProductPanel = new JPanel();
        singleProductPanel.setLayout(new BorderLayout());
        singleProductPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Dodanie obrazka
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(95, 95, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(imageIcon);
        singleProductPanel.add(imageLabel, BorderLayout.NORTH);

        // Tworzenie czcionki o stałym rozmiarze
        Font font = new Font("Arial", Font.PLAIN, 10);  // Możesz zmienić rozmiar czcionki

        // Informacje o produkcie w elastycznym układzie
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));  // Użycie BoxLayout do automatycznego dopasowywania
        JLabel typeLabel = new JLabel("Typ: " + type);
        typeLabel.setFont(font);
        infoPanel.add(typeLabel);

        JLabel kindLabel = new JLabel("Rodzaj: " + kind);
        kindLabel.setFont(font);
        infoPanel.add(kindLabel);

        JLabel sizeLabel = new JLabel("Rozmiar: " + size);
        sizeLabel.setFont(font);
        infoPanel.add(sizeLabel);

        JLabel colorLabel = new JLabel("Kolor: " + color);
        colorLabel.setFont(font);
        infoPanel.add(colorLabel);

        JLabel priceLabel = new JLabel("Cena: " + price + " zł");
        priceLabel.setFont(font);
        infoPanel.add(priceLabel);

        JLabel availableLabel = new JLabel("Dostępnych: " + available);
        availableLabel.setFont(font);
        infoPanel.add(availableLabel);

        singleProductPanel.add(infoPanel, BorderLayout.CENTER);

        // Pole ilości i przycisk Dodaj
        JPanel actionPanel = new JPanel();
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.parseInt(available), 1));
        JButton addButton = new JButton("Dodaj");
        actionPanel.add(quantitySpinner);
        actionPanel.add(addButton);
        singleProductPanel.add(actionPanel, BorderLayout.SOUTH);

        // Obsługa dodawania do koszyka
        addButton.addActionListener(e -> addToCart(type, kind, size, color, price, quantitySpinner));

        singleProductPanel.setMaximumSize(new Dimension(220, 250)); // Stały rozmiar
        singleProductPanel.setPreferredSize(new Dimension(220, 250));
        singleProductPanel.setMinimumSize(new Dimension(220, 250));
        // Dodanie panelu produktu do głównego panelu
        productPanel.add(singleProductPanel);
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

 
    private static void loadProductsFromFile() {
        try {
            productPanel.removeAll(); // Czyścimy panel
            String filePath = "C:\\Users\\AnnaK\\eclipse-workspace\\FlowerShopProject\\kwiaty_ciete_baza.txt";

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String index = line.trim();
                String type = reader.readLine().trim();
                String kind = reader.readLine().trim();
                String sizeOrVariety = reader.readLine().trim(); // Dla kwiatów ciętych będzie to odmiana, dla doniczkowych rozmiar
                String color = reader.readLine().trim();
                String quantityBought = reader.readLine().trim();
                String purchasePrice = reader.readLine().trim();
                String purchaseMonth = reader.readLine().trim();
                String salePrice = reader.readLine().trim();
                String available = reader.readLine().trim();
                String imagePath = reader.readLine().trim();

                createCutFlowerPanel(type, kind, sizeOrVariety, color, salePrice, available, imagePath);
                
            }

            reader.close();
            
            filePath = "C:\\Users\\AnnaK\\eclipse-workspace\\FlowerShopProject\\kwiaty_doniczkowe_baza.txt";

            reader = new BufferedReader(new FileReader(filePath));
      

            while ((line = reader.readLine()) != null) {
                String index = line.trim();
                String type = reader.readLine().trim();
                String kind = reader.readLine().trim();
                String sizeOrVariety = reader.readLine().trim(); // Dla kwiatów ciętych będzie to odmiana, dla doniczkowych rozmiar
                String color = reader.readLine().trim();
                String quantityBought = reader.readLine().trim();
                String purchasePrice = reader.readLine().trim();
                String purchaseMonth = reader.readLine().trim();
                String salePrice = reader.readLine().trim();
                String available = reader.readLine().trim();
                String imagePath = reader.readLine().trim();

                createPottedFlowerPanel(type, kind, sizeOrVariety, color, salePrice, available, imagePath);
                
            }

            reader.close();
            
            productPanel.revalidate();
            productPanel.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Błąd wczytywania pliku: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

   
    private static void addToCart(String type, String kind, String variety, String color, String price, JSpinner spinner) {
        int quantity = (int) spinner.getValue();

        // Sprawdzamy, czy ilość nie przekracza dostępnej ilości
        int availableQuantity = Integer.parseInt(spinner.getModel().getValue().toString()); // Dostępna ilość z pliku
        if (quantity > availableQuantity) {
            JOptionPane.showMessageDialog(frame, "Nie możesz dodać więcej sztuk niż dostępnych w magazynie.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (quantity > 0) {
            try {
                // Zamiana przecinka na kropkę i usunięcie białych znaków
                double parsedPrice = Double.parseDouble(price.replace(",", ".").trim());

                // Tworzymy nowy wpis do koszyka
                String newCartEntry = String.format("%s %s %s (%s) x%d - %.2f zł", type, kind, variety, color, quantity, parsedPrice * quantity);

                // Sprawdzamy, czy produkt już znajduje się w koszyku
                boolean productExists = false;
                for (int i = 0; i < cartModel.getSize(); i++) {
                    String existingItem = cartModel.get(i);
                    if (existingItem.contains(type) && existingItem.contains(kind) && existingItem.contains(variety) && existingItem.contains(color)) {
                        productExists = true;
                        break;
                    }
                }

                if (productExists) {
                    JOptionPane.showMessageDialog(frame, "Ten produkt już znajduje się w koszyku.", "Błąd", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Dodanie nowego produktu do koszyka
                    cartModel.addElement(newCartEntry);
                    totalAmount += parsedPrice * quantity;
                    totalLabel.setText(String.format("Suma: %.2f zł", totalAmount));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Nieprawidłowy format ceny: " + price, "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private static void removeSelectedProduct() {
        int selectedIndex = cartList.getSelectedIndex();
        if (selectedIndex != -1) {
            String item = cartModel.get(selectedIndex);

            // Wyrażenie regularne do wyodrębnienia ceny przed "zł"
            String regex = "(\\d+(?:,\\d{1,2})?)\\s*zł";
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
            java.util.regex.Matcher matcher = pattern.matcher(item);

            if (matcher.find()) {
                String pricePart = matcher.group(1);  // Pierwsza grupa to cena

                try {
                    // Zamiana przecinka na kropkę, aby móc przetworzyć cenę
                    double price = Double.parseDouble(pricePart.replace(",", "."));

                    // Aktualizacja całkowitej kwoty
                    totalAmount -= price;
                    cartModel.remove(selectedIndex);  // Usunięcie produktu z koszyka
                    totalLabel.setText(String.format("Suma: %.2f zł", totalAmount));  // Zaktualizowanie etykiety z sumą
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Nieprawidłowy format ceny: " + pricePart, "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Nie udało się wyodrębnić ceny z wybranego produktu.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void processPayment() {
        // Sprawdzanie, czy koszyk jest pusty
        if (cartModel.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Twój koszyk jest pusty. Dodaj produkty do koszyka przed dokonaniem płatności.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return; // Zatrzymanie dalszego działania, jeśli koszyk jest pusty
        }

        // Utworzenie okna dialogowego do wprowadzenia danych
        JDialog paymentDialog = new JDialog(frame, "Formularz płatności", true); // true - modalne okno
        paymentDialog.setLayout(new GridLayout(7, 4, 10, 10)); // Zwiększenie liczby wierszy i kolumn (7 wierszy, 4 kolumny)

        // Pola tekstowe do wprowadzenia danych
        JLabel nameLabel = new JLabel("Imię i Nazwisko:");
        JTextField nameField = new JTextField();

        JLabel phoneLabel = new JLabel("Nr telefonu:");
        JTextField phoneField = new JTextField();

        JLabel addressLabel = new JLabel("Adres dostawy:");
        JTextField addressField = new JTextField();

        JLabel blikLabel = new JLabel("Kod BLIK:");
        JTextField blikField = new JTextField();

        // Przycisk do zatwierdzenia formularza
        JButton submitButton = new JButton("Zatwierdź");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        

        // Dodanie komponentów do okna dialogowego
        paymentDialog.add(new JLabel()); // Pusty komponent na początek
        paymentDialog.add(new JLabel()); // Pusty komponent na początek
        paymentDialog.add(new JLabel()); // Pusty komponent na początek
        paymentDialog.add(new JLabel()); // Pusty komponent na początek

        // Dodanie pól formularza
        paymentDialog.add(new JLabel()); // Pusty komponent po lewej stronie
        paymentDialog.add(nameLabel);
        paymentDialog.add(nameField);
        paymentDialog.add(new JLabel()); // Pusty komponent po prawej stronie

        paymentDialog.add(new JLabel()); // Pusty komponent po lewej stronie
        paymentDialog.add(phoneLabel);
        paymentDialog.add(phoneField);
        paymentDialog.add(new JLabel()); // Pusty komponent po prawej stronie

        paymentDialog.add(new JLabel()); // Pusty komponent po lewej stronie
        paymentDialog.add(addressLabel);
        paymentDialog.add(addressField);
        paymentDialog.add(new JLabel()); // Pusty komponent po prawej stronie

        paymentDialog.add(new JLabel()); // Pusty komponent po lewej stronie
        paymentDialog.add(blikLabel);
        paymentDialog.add(blikField);
        paymentDialog.add(new JLabel()); // Pusty komponent po prawej stronie

        paymentDialog.add(new JLabel()); // Pusty komponent na dole
        paymentDialog.add(new JLabel()); // Pusty komponent na dole
        paymentDialog.add(submitButton);
        paymentDialog.add(new JLabel()); // Pusty komponent na dole

        // Obsługa przycisku "Zatwierdź"
        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();
            String blik = blikField.getText();

            // Walidacja danych (prosta)
            if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || blik.isEmpty()) {
                JOptionPane.showMessageDialog(paymentDialog, "Wszystkie pola muszą być wypełnione!", "Błąd", JOptionPane.ERROR_MESSAGE);
            } else {
                // Zapis do pliku sprzedane_baza.txt
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("sprzedane_baza.txt", true))) {
                    // Pobranie daty zakupu
                    String currentDate = java.time.LocalDate.now().toString();

                    // Pobranie listy zakupionych artykułów z koszyka
                    StringBuilder purchasedItems = new StringBuilder();
                    for (int i = 0; i < cartModel.getSize(); i++) {
                        purchasedItems.append(cartModel.get(i)).append("\n");
                    }

                    // Zapis do pliku
                    writer.write(currentDate + "\n");
                    writer.write(name + "\n");
                    writer.write(phone + "\n");
                    writer.write(address + "\n");
                    writer.write(purchasedItems.toString());
                    writer.write(totalAmount + "\n" + "koniec \n");
                    
                    JOptionPane.showMessageDialog(paymentDialog, "Płatność zrealizowana pomyślnie!\nDziękujemy za zakupy.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    updateProductAvailability();

                    // Odświeżenie produktów po zakupie
                    loadProductsFromFile();

                    // Zamknięcie okna dialogowego po dokonaniu płatności
                    paymentDialog.dispose();

                    // Resetowanie koszyka
                    cartModel.clear();
                    totalAmount = 0;
                    totalLabel.setText("Suma: 0.00 zł");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(paymentDialog, "Błąd zapisu do pliku: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ustawienie rozmiaru okna dialogowego i wyśrodkowanie na ekranie
        paymentDialog.setSize(450, 350); // Zwiększenie rozmiaru okna
        paymentDialog.setLocationRelativeTo(frame); // Ustawienie na środku ekranu
        paymentDialog.setVisible(true); // Pokazanie okna dialogowego
    }






    private static void updateProductAvailability() {
        // Ścieżki do plików z kwiatami
        String cutFlowersFilePath = "C:\\Users\\AnnaK\\eclipse-workspace\\FlowerShopProject\\kwiaty_ciete_baza.txt";
        String pottedFlowersFilePath = "C:\\Users\\AnnaK\\eclipse-workspace\\FlowerShopProject\\kwiaty_doniczkowe_baza.txt";

        // Przetwarzamy oba pliki
        List<String> updatedCutFlowers = new ArrayList<>();
        processFileForProductUpdate(cutFlowersFilePath, updatedCutFlowers);
        writeUpdatedLinesToFile(cutFlowersFilePath, updatedCutFlowers);
        
        List<String> updatedPottedFlowers = new ArrayList<>();
        processFileForProductUpdate(pottedFlowersFilePath, updatedPottedFlowers);
        writeUpdatedLinesToFile(pottedFlowersFilePath, updatedPottedFlowers);
    }

    private static void processFileForProductUpdate(String filePath, List<String> updatedLines) {
        List<String> lines = new ArrayList<>();
        
        // Odczytujemy plik
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());  // Dodajemy każdą linię do listy i usuwamy zbędne białe znaki
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Wystąpił błąd podczas odczytu pliku: " + e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Iterujemy przez produkty w pliku (po 10 liniach na produkt)
        for (int i = 0; i < lines.size(); i += 11) {
            String id = lines.get(i); // 1 - id
            String type = lines.get(i + 1); // 2 - typ
            String kind = lines.get(i + 2); // 3 - odmiana
            String variety = lines.get(i + 3); // 4 - rodzaj
            String color = lines.get(i + 4); // 5 - kolor
            String quantity = lines.get(i + 5); // 6 - ilość
            String purchasePrice = lines.get(i + 6); // 7 - cena zakupu
            String purchaseDate = lines.get(i + 7); // 8 - data zakupu
            String salePrice = lines.get(i + 8); // 9 - cena sprzedaży
            String availableQuantity = lines.get(i + 9); // 10 - ilość dostępnych
            String imagePath = lines.get(i + 10); // ścieżka do obrazka

            // Sprawdzamy, czy produkt znajduje się w koszyku
            for (int j = 0; j < cartModel.getSize(); j++) {
                String cartItem = cartModel.get(j);
                if (cartItem.contains(type) && cartItem.contains(kind) && cartItem.contains(variety) && cartItem.contains(color)) {
                    // Wyciągamy ilość kupionych sztuk z koszyka
                    int boughtQuantity = Integer.parseInt(cartItem.split(" x")[1].split(" -")[0].trim());

                    // Zmniejszamy ilość dostępnych sztuk
                    int newAvailableQuantity = Integer.parseInt(availableQuantity) - boughtQuantity;
                    
                    // Aktualizujemy ilość dostępną w pliku
                    availableQuantity = String.valueOf(newAvailableQuantity);
                }
            }

            // Tworzymy nową wersję linii z zaktualizowaną ilością dostępną
            updatedLines.add(id);
            updatedLines.add(type);
            updatedLines.add(kind);
            updatedLines.add(variety);
            updatedLines.add(color);
            updatedLines.add(quantity);
            updatedLines.add(purchasePrice);
            updatedLines.add(purchaseDate);
            updatedLines.add(salePrice);
            updatedLines.add(availableQuantity); // Zaktualizowana ilość dostępnych
            updatedLines.add(imagePath);
        }
    }

    private static void writeUpdatedLinesToFile(String filePath, List<String> updatedLines) {
        // Zapisujemy zmiany do pliku
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String lineToWrite : updatedLines) {
                writer.write(lineToWrite);
                writer.newLine(); // Dodajemy nową linię po każdej części danych
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Wystąpił błąd podczas zapisywania pliku: " + e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }


    private static void watchFileForChanges(String filePath) {
        try {
            Path path = Paths.get(filePath).getParent();
            WatchService watchService = FileSystems.getDefault().newWatchService();
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            Thread watcherThread = new Thread(() -> {
                try {
                    while (true) {
                        WatchKey key = watchService.take();
                        for (WatchEvent<?> event : key.pollEvents()) {
                            if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                                Path changed = (Path) event.context();
                                if (changed.toString().equals(Paths.get(filePath).getFileName().toString())) {
                                    SwingUtilities.invokeLater(() -> loadProductsFromFile());
                                }
                            }
                        }
                        key.reset();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Błąd monitorowania pliku: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            });

            watcherThread.setDaemon(true);
            watcherThread.start();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Błąd inicjalizacji monitorowania pliku: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
}
