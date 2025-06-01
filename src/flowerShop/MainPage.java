package flowerShop;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.util.List;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import java.awt.Font;

public class MainPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
   
    private JTextField textField_81;
    private JTextField txtCite1;
    private JTextField textField_101;
    private JTextField textField_111;
    private JTextField textField_121;
    private JTextField textField_131;
    private JTextField textField_141;
    private JTextField textField_151;
    private JTextField textField_8;
    private JTextField txtCite;
    private JTextField textField_10;
    private JTextField textField_11;
    private JTextField textField_12;
    private JTextField textField_13;
    private JTextField textField_14;
    private JTextField textField_15;
    private JTable tableAvailable;
    private JTable tableAvailable1;
    private JTextField textField_17;
    private JTextField textField_16;
    private JLabel obrazekLabel;
    private JLabel obrazekLabel1;
    private JTextField textField_18;
	private ImageIcon iconN = new ImageIcon("C:\\Users\\AnnaK\\eclipse-workspace\\FlowerShopProject\\image.png");
	private JTextField textField_161;
	private JTextField textField_171;
	private JTextField textField_181;
	private JScrollPane scrollPaneCutFlowers;
	private JScrollPane scrollPanePottedFlowers;
	
	 public void setupButtons(JPanel contentPane) {
	        String[] buttonLabels = {"Magazyn", "Sprzedane", "Blog", "Dochód", "Wyloguj"};
	        Runnable[] buttonActions = {
	            () -> {
	                MainPage mainPage = new MainPage();
	                mainPage.setVisible(true);
	                dispose();
	            },
	            () -> {
	                SoldPage soldPage = new SoldPage();
	                soldPage.setVisible(true);
	                dispose();
	            },
	            () -> {
	                BlogPage blogPage = new BlogPage();
	                blogPage.setVisible(true);
	                dispose();
	            },
	            () -> {
	                IncomePage incomePage = new IncomePage();
	                incomePage.setVisible(true);
	                dispose();
	            },
	            () -> {
	                LoginPage loginPage = new LoginPage();
	                loginPage.setVisible(true);
	                dispose();
	            }
	        };

	        int buttonWidth = 300;
	        int buttonHeight = 60;
	        int spacing = 0; // Odległość między przyciskami
	        int totalWidth = (buttonWidth + spacing) * buttonLabels.length - spacing;

	        // Pozycja pierwszego przycisku
	        int startX = 10;
	        int startY = 0;

	        for (int i = 0; i < buttonLabels.length; i++) {
	            JButton button = new JButton(buttonLabels[i]);
	            button.setFont(new Font("Arial", Font.BOLD, 14));
	            button.setBackground(new Color(70, 130, 180));
	            button.setForeground(Color.WHITE);
	            button.setFocusPainted(false);
	            button.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
	            button.setBounds(startX + i * (buttonWidth + spacing), startY, buttonWidth, buttonHeight);

	            int actionIndex = i; // Finalna zmienna dla lambdy
	            button.addActionListener(e -> buttonActions[actionIndex].run());
	            contentPane.add(button);
	        }
	    }

	 
    public void addCutFlowerFromFields() {
        String idStr = textField_8.getText();
        String typStr = txtCite.getText();
        String rodzajStr = textField_10.getText();
        String odmianaStr = textField_11.getText();
        String kolorStr = textField_12.getText();
        String iloscStr = textField_13.getText();
        String cenaZStr = textField_14.getText();
        String dataZStr = textField_15.getText();
        String cenaSStr = textField_16.getText();
        String dostepStr = textField_17.getText();
        String obrazekStr = textField_18.getText();

        int id = Integer.parseInt(idStr);  // Zamień tekst na liczby, jeśli to konieczne
        int ilosc = Integer.parseInt(iloscStr);
        double cenaZ = Double.parseDouble(cenaZStr); 
        LocalDate dataZ = LocalDate.parse(dataZStr);  // Parsowanie daty w formacie YYYY-MM-DD
        double cenaS = Double.parseDouble(cenaSStr); 
        int dostep = Integer.parseInt(dostepStr);

        // Tworzymy obiekt Kwiatek i wywołujemy funkcję fDodaj
        Kwiatek kwiatek = new CutFlowers(id, typStr, rodzajStr, kolorStr, odmianaStr, ilosc, cenaZ, dataZ, 0, cenaS, null, dostep, obrazekStr);
        kwiatek.fDodaj();  // Wywołanie metody do dodania kwiatu
        
    }

    
    public void addPottedFlowerFromFields() {
        String idStr = textField_81.getText();
        String typStr = txtCite1.getText();
        String rodzajStr = textField_101.getText();
        String rozmiarStr = textField_111.getText();
        String kolorStr = textField_121.getText();
        String iloscStr = textField_131.getText();
        String cenaZStr = textField_141.getText();
        String dataZStr = textField_151.getText();
        String cenaSStr = textField_161.getText();
        String dostepStr = textField_171.getText();
        String obrazekStr = textField_181.getText();

        int id = Integer.parseInt(idStr);  // Zamień tekst na liczby, jeśli to konieczne
        int ilosc = Integer.parseInt(iloscStr);
        double cenaZ = Double.parseDouble(cenaZStr); 
        LocalDate dataZ = LocalDate.parse(dataZStr);  // Parsowanie daty w formacie YYYY-MM-DD
        double cenaS = Double.parseDouble(cenaSStr); 
        int dostep = Integer.parseInt(dostepStr);

        // Tworzymy obiekt Kwiatek i wywołujemy funkcję fDodaj
        Kwiatek kwiatek = new PottedFlowers(id, typStr, rodzajStr, kolorStr, rozmiarStr, ilosc, cenaZ, dataZ, 0, cenaS, null, dostep, obrazekStr);
        kwiatek.fDodaj();  // Wywołanie metody do dodania kwiatu
        
    }
    
    public void loadCutFlowersFromFile() {
        // Usuwamy poprzednią tabelę (jeśli istnieje)
    	Component[] components = contentPane.getComponents();
    	for (Component component : components) {
    	    if (component instanceof JScrollPane) {
    	        JScrollPane scrollPane = (JScrollPane) component;
    	        if ( scrollPane == scrollPanePottedFlowers) {
    	            // Nie usuwamy paneli przewijania dla istniejących tabel
    	            continue;
    	        }
    	        contentPane.remove(component);  // Usuwamy inne JScrollPane
    	    }
    	}

        // Tworzymy nową tabelę i ładujemy dane
        Kwiatek kwiatek = new CutFlowers(0, "", "", "", "", 0, 0, null, 0, 0, null, 0, "");  // placeholder params
        List<String[]> flowersData = kwiatek.fCzytaj();

        // Przygotowujemy dane do tabeli
        String[][] data = new String[flowersData.size()][10];
        for (int i = 0; i < flowersData.size(); i++) {
            data[i] = flowersData.get(i);
        }

        // Tworzymy nową tabelę i dodajemy ją do panelu
        String[] columnNames = {"ID", "Typ", "Rodzaj", "Odmiana", "Kolor", "Ilość zakupionych", "Cena zakupu", "Data zakupu", "Cena sprzedaży", "Dostępnych"};
        tableAvailable = new JTable(data, columnNames);

        // Jeśli panel przewijania dla Cut Flowers już istnieje, usuwamy go
        if (scrollPaneCutFlowers != null) {
            contentPane.remove(scrollPaneCutFlowers);
        }

        scrollPaneCutFlowers = new JScrollPane(tableAvailable);
        scrollPaneCutFlowers.setBounds(305, 177, 818, 100); // Ustawienie pozycji i rozmiaru panelu przewijania
        contentPane.add(scrollPaneCutFlowers);

        // Odświeżamy widok komponentu
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    public void loadPottedFlowersFromFile() {
        // Usuwamy poprzednią tabelę (jeśli istnieje)
    	Component[] components = contentPane.getComponents();
    	for (Component component : components) {
    	    if (component instanceof JScrollPane) {
    	        JScrollPane scrollPane = (JScrollPane) component;
    	        if (scrollPane == scrollPaneCutFlowers) {
    	            // Nie usuwamy paneli przewijania dla istniejących tabel
    	            continue;
    	        }
    	        contentPane.remove(component);  // Usuwamy inne JScrollPane
    	    }
    	}

        Kwiatek kwiatek = new PottedFlowers(0, "", "", "", "", 0, 0, null, 0, 0, null, 0, "");  // placeholder params
        List<String[]> flowersData = kwiatek.fCzytaj();

        // Przygotowujemy dane do tabeli
        String[][] data = new String[flowersData.size()][10];
        for (int i = 0; i < flowersData.size(); i++) {
            data[i] = flowersData.get(i);
        }

        // Tworzymy nową tabelę i dodajemy ją do panelu
        String[] columnNames = {"ID", "Typ", "Rodzaj", "Rozmiar", "Kolor", "Ilość zakupionych", "Cena zakupu", "Data zakupu", "Cena sprzedaży", "Dostępnych"};
        tableAvailable1 = new JTable(data, columnNames);

        // Jeśli panel przewijania dla Potted Flowers już istnieje, usuwamy go
        if (scrollPanePottedFlowers != null) {
            contentPane.remove(scrollPanePottedFlowers);
        }

        scrollPanePottedFlowers = new JScrollPane(tableAvailable1);
        scrollPanePottedFlowers.setBounds(305, 518, 818, 100); // Ustawienie pozycji i rozmiaru panelu przewijania
        contentPane.add(scrollPanePottedFlowers);

        // Odświeżamy widok komponentu
        contentPane.revalidate();
        contentPane.repaint();
    }

    private void choosePictureCut() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File wybranyObrazek = fileChooser.getSelectedFile();
            try {
            	File selectedFile = fileChooser.getSelectedFile();
                
                // Załaduj obrazek do ImageIcon
                ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                
                // Skalowanie obrazu do rozmiarów JLabel z zachowaniem proporcji
                Image originalImage = imageIcon.getImage();
                
                // Maksymalne wymiary dla JLabel
                int maxWidth = obrazekLabel.getWidth(); // Szerokość JLabel
                int maxHeight = obrazekLabel.getHeight(); // Wysokość JLabel
                
                int originalWidth = originalImage.getWidth(null);
                int originalHeight = originalImage.getHeight(null);
                
                // Obliczanie współczynnika skalowania z zachowaniem proporcji
                double scale = Math.min((double) maxWidth / originalWidth, (double) maxHeight / originalHeight);
                int newWidth = (int) (originalWidth * scale);
                int newHeight = (int) (originalHeight * scale);
                
                // Skalowanie obrazu
                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                
                // Ustawienie skalowanego obrazu w JLabel
                obrazekLabel.setIcon(new ImageIcon(scaledImage));
                obrazekLabel.setText(""); // Usunięcie tekstu "Brak podglądu obrazka"
                
                // Dopasowanie rozmiaru JLabel do obrazu
                obrazekLabel.setPreferredSize(new Dimension(newWidth, newHeight));
                obrazekLabel.setHorizontalAlignment(SwingConstants.CENTER);
                obrazekLabel.setVerticalAlignment(SwingConstants.CENTER);
                obrazekLabel.repaint();
                obrazekLabel.revalidate();
                
                
                String tekst = wybranyObrazek.getAbsolutePath();
                textField_18.setText(tekst);

            } catch (Exception e) {
            	obrazekLabel.setIcon(iconN);
                JOptionPane.showMessageDialog(this, "Nie udało się wczytać obrazka!", "Błąd", JOptionPane.ERROR_MESSAGE);
               
            }
            
        }
    }

    private void choosePicturePotted() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File wybranyObrazek = fileChooser.getSelectedFile();
            try {
            	File selectedFile = fileChooser.getSelectedFile();
                
                // Załaduj obrazek do ImageIcon
                ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                
                // Skalowanie obrazu do rozmiarów JLabel z zachowaniem proporcji
                Image originalImage = imageIcon.getImage();
                
                // Maksymalne wymiary dla JLabel
                int maxWidth = obrazekLabel1.getWidth(); // Szerokość JLabel
                int maxHeight = obrazekLabel1.getHeight(); // Wysokość JLabel
                
                int originalWidth = originalImage.getWidth(null);
                int originalHeight = originalImage.getHeight(null);
                
                // Obliczanie współczynnika skalowania z zachowaniem proporcji
                double scale = Math.min((double) maxWidth / originalWidth, (double) maxHeight / originalHeight);
                int newWidth = (int) (originalWidth * scale);
                int newHeight = (int) (originalHeight * scale);
                
                // Skalowanie obrazu
                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                
                // Ustawienie skalowanego obrazu w JLabel
                obrazekLabel1.setIcon(new ImageIcon(scaledImage));
                obrazekLabel1.setText(""); // Usunięcie tekstu "Brak podglądu obrazka"
                
                // Dopasowanie rozmiaru JLabel do obrazu
                obrazekLabel1.setPreferredSize(new Dimension(newWidth, newHeight));
                obrazekLabel1.setHorizontalAlignment(SwingConstants.CENTER);
                obrazekLabel1.setVerticalAlignment(SwingConstants.CENTER);
                obrazekLabel1.repaint();
                obrazekLabel1.revalidate();
                
                
                String tekst = wybranyObrazek.getAbsolutePath();
                textField_181.setText(tekst);

            } catch (Exception e) {
            	obrazekLabel1.setIcon(iconN);
                JOptionPane.showMessageDialog(this, "Nie udało się wczytać obrazka!", "Błąd", JOptionPane.ERROR_MESSAGE);
               
            }
            
        }
    }


   
    
    
    /**
     * Konstruktor ramki.
     */
    public MainPage() {
    	 
    	 
        setTitle("Kwiaciarnia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Pełny rozmiar ekranu
        setLocationRelativeTo(null); // Ustawienie okna na środku
        // Ustawienie głównego panelu
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);
        setupButtons(contentPane);
        loadCutFlowersFromFile();
        loadPottedFlowersFromFile();
              
        // Dodanie etykiet
        JLabel cutFlowersLabel = new JLabel("Kwiaty Cięte");
        cutFlowersLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        cutFlowersLabel.setBounds(686, 123, 159, 43);
        contentPane.add(cutFlowersLabel);
        
    

        JLabel pottedFlowersLabel = new JLabel("Kwiaty doniczkowe");
        pottedFlowersLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        pottedFlowersLabel.setBounds(686, 473, 175, 43);
        contentPane.add(pottedFlowersLabel);
        
        
        JButton AddButton = new JButton("Dodaj");
        AddButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 // Sprawdzenie, czy wszystkie pola są uzupełnione
                if (textField_81.getText().isEmpty() || txtCite1.getText().isEmpty() || 
                    textField_101.getText().isEmpty() || textField_111.getText().isEmpty() || 
                    textField_121.getText().isEmpty() || textField_131.getText().isEmpty() || 
                    textField_141.getText().isEmpty() || textField_151.getText().isEmpty() ||
                    textField_161.getText().isEmpty() || textField_171.getText().isEmpty() ||
                    textField_181.getText().isEmpty()) {
                    
                    // Wyświetlenie okienka z błędem
                    JOptionPane.showMessageDialog(null, 
                        "Wszystkie pola muszą być uzupełnione!", 
                        "Błąd", 
                        JOptionPane.ERROR_MESSAGE);
                    return; // Przerwanie dalszego przetwarzania
                }

                try {
                    // Przekazanie danych do obiektu Kwiatek i wywołanie metody fDodaj()
                	 Kwiatek kwiatek = new PottedFlowers(
                             Integer.parseInt(textField_81.getText()),  // ID
                             txtCite1.getText(),  // Typ
                             textField_101.getText(),  // Rodzaj
                             textField_111.getText(),  // Rozmiar
                             textField_121.getText(),  // Kolor
                             Integer.parseInt(textField_131.getText()),  // Ilość zakupionych
                             Double.parseDouble(textField_141.getText()),  // Cena zakupu
                             LocalDate.parse(textField_151.getText()),    // Data zakupu
                             0,                                          //ilosc sprzedanych
                             Double.parseDouble(textField_161.getText()),   // Pole cena sprzedaży
                             null,                                          // Data sprzedaży
                             Integer.parseInt(textField_171.getText()),    // Dostepnosc
                             textField_181.getText()
                     );  
           	          

                    // Wywołanie polimorficznej metody fDodaj()
                    kwiatek.fDodaj();
                    
                    textField_81.setText("");   // Pole ID
        	        textField_101.setText(""); // Pole Rodzaj
        	        textField_111.setText(""); // Pole Odmiana
        	        textField_121.setText(""); // Pole Kolor
        	        textField_131.setText(""); // Pole Ilość
        	        textField_141.setText(""); // Pole Cena zakupu
        	        textField_151.setText(""); // Pole Cena sprzedaży
        	        textField_161.setText(""); // Pole cena sprz
        	        textField_171.setText(""); // Pole dostepnosc
        	        textField_181.setText(""); // Pole obrazek
        	        obrazekLabel1.setIcon(iconN);
                    
                    loadPottedFlowersFromFile(); // Odświeżenie danych w tabeli

                } catch (NumberFormatException ex) {
                    // Wyświetlenie błędu, jeśli wartości liczbowe są nieprawidłowe
                    JOptionPane.showMessageDialog(null, 
                        "Niektóre pola zawierają nieprawidłowe wartości liczbowe!", 
                        "Błąd", 
                        JOptionPane.ERROR_MESSAGE);
                }
        	}
        });
        AddButton.setBounds(458, 656, 85, 21);
        AddButton.setFont(new Font("Arial", Font.BOLD, 14));
        AddButton.setBackground(new Color(70, 130, 180));
        AddButton.setForeground(Color.WHITE);
        AddButton.setFocusPainted(false);
        AddButton.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(AddButton);
        
        JButton DeleteButton = new JButton("Usuń");
        DeleteButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 // Uzyskanie indexu wybranego wiersza
                int selectedRow = tableAvailable1.getSelectedRow();
                
                if (selectedRow != -1) {  // Sprawdzenie, czy wiersz został wybrany
                    // Uzyskanie ID kwiatu z pierwszej kolumny
                    int flowerId = Integer.parseInt(tableAvailable1.getValueAt(selectedRow, 0).toString());

                    // Usuwanie kwiatu z listy
                    Kwiatek kwiatek = new PottedFlowers(0, "", "", "", "", 0, 0, null, 0, 0, null, 0, "");  // Placeholder params
                    kwiatek.fUsun(flowerId);  // Zakładając, że masz metodę do usuwania kwiatu z pliku/danych

                    // Ponowne załadowanie danych do tabeli
                    loadPottedFlowersFromFile();
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Proszę wybrać kwiat do usunięcia.");
                }
            
        	}
        });
        DeleteButton.setBounds(561, 656, 85, 21);
        DeleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        DeleteButton.setBackground(new Color(70, 130, 180));
        DeleteButton.setForeground(Color.WHITE);
        DeleteButton.setFocusPainted(false);
        DeleteButton.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(DeleteButton);
        
        JButton EditButton = new JButton("Edytuj");
        EditButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 // Sprawdzenie, czy wiersz został zaznaczony
                int selectedRow = tableAvailable1.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Proszę zaznaczyć wiersz do edycji!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

             // Pobieranie danych z zaznaczonego wiersza
                textField_81.setText(tableAvailable1.getValueAt(selectedRow, 0).toString());   // ID
                textField_101.setText(tableAvailable1.getValueAt(selectedRow, 2).toString()); // Rodzaj
                textField_111.setText(tableAvailable1.getValueAt(selectedRow, 3).toString()); // Rozmiar
                textField_121.setText(tableAvailable1.getValueAt(selectedRow, 4).toString()); // Kolor
                textField_131.setText(tableAvailable1.getValueAt(selectedRow, 5).toString()); // Ilość zakupionych
                textField_141.setText(tableAvailable1.getValueAt(selectedRow, 6).toString()); // Cena zakupu
                textField_151.setText(tableAvailable1.getValueAt(selectedRow, 7).toString()); // Data zakupu
                textField_161.setText(tableAvailable1.getValueAt(selectedRow, 8).toString()); // Cena sprzedaży
                textField_171.setText(tableAvailable1.getValueAt(selectedRow, 9).toString()); // Dostępnych
               
                int flowerId = Integer.parseInt(textField_81.getText());   // ID
                Kwiatek kwiatek = new PottedFlowers(0, "", "", "", "", 0, 0, null, 0, 0, null, 0, ""); 
                textField_181.setText(kwiatek.fObrazekById(flowerId));  
                
              
               
                ImageIcon icon = new ImageIcon(textField_181.getText());
                Image originalImage = icon.getImage();
                // Maksymalne wymiary dla JLabel
                int maxWidth = obrazekLabel.getWidth(); // Szerokość JLabel
                int maxHeight = obrazekLabel.getHeight(); // Wysokość JLabel
                
                int originalWidth = originalImage.getWidth(null);
                int originalHeight = originalImage.getHeight(null);
                
                // Obliczanie współczynnika skalowania z zachowaniem proporcji
                double scale = Math.min((double) maxWidth / originalWidth, (double) maxHeight / originalHeight);
                int newWidth = (int) (originalWidth * scale);
                int newHeight = (int) (originalHeight * scale);

                // Skalowanie obrazu
                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                obrazekLabel1.setIcon(new ImageIcon(scaledImage));

        	}
        });
        EditButton.setBounds(663, 656, 85, 21);
        EditButton.setFont(new Font("Arial", Font.BOLD, 14));
        EditButton.setBackground(new Color(70, 130, 180));
        EditButton.setForeground(Color.WHITE);
        EditButton.setFocusPainted(false);
        EditButton.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(EditButton);
        
        
        
        JButton btnNewButton_1 = new JButton("Zapisz");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 if (textField_81.getText().isEmpty() || txtCite1.getText().isEmpty() || 
                         textField_101.getText().isEmpty() || textField_111.getText().isEmpty() || 
                         textField_121.getText().isEmpty() || textField_131.getText().isEmpty() || 
                         textField_141.getText().isEmpty() || textField_151.getText().isEmpty() ||
                         textField_161.getText().isEmpty() || textField_171.getText().isEmpty() ||
                         textField_181.getText().isEmpty()) {
                         
                         // Wyświetlenie okienka z błędem
                         JOptionPane.showMessageDialog(null, 
                             "Wszystkie pola muszą być uzupełnione!", 
                             "Błąd", 
                             JOptionPane.ERROR_MESSAGE);
                         return; // Przerwanie dalszego przetwarzania
                  }
        		
        		  int flowerId =  Integer.parseInt(textField_81.getText());   // Pole ID
      	          textField_101.getText(); // Pole Rodzaj
      	          textField_111.getText(); // Pole Odmiana
      	          textField_121.getText(); // Pole Kolor
      	          textField_131.getText(); // Pole Ilość zakupionych
      	          textField_141.getText(); // Pole Cena zakupu
      	          textField_151.getText(); // Pole data zakupu
      	          textField_161.getText(); // Pole cena sprz
      	          textField_171.getText(); // Pole dostepnosc
      	          textField_181.getText(); // Pole obrazek
      	          
      	          Kwiatek kwiatek = new PottedFlowers(
                        Integer.parseInt(textField_81.getText()),  // ID
                        txtCite1.getText(),  // Typ
                        textField_101.getText(),  // Rodzaj
                        textField_111.getText(),  // Rozmiar
                        textField_121.getText(),  // Kolor
                        Integer.parseInt(textField_131.getText()),  // Ilość zakupionych
                        Double.parseDouble(textField_141.getText()),  // Cena zakupu
                        LocalDate.parse(textField_151.getText()),    // Data zakupu
                        0,                                          //ilosc sprzedanych
                        Double.parseDouble(textField_161.getText()),   // Pole cena sprzedaży
                        null,                                          // Data sprzedaży
                        Integer.parseInt(textField_171.getText()),    // Dostepnosc
                        textField_181.getText()
                        
                    );  
      	          
                kwiatek.fUsunv2(flowerId);  
                
                kwiatek.fDodajPoId(flowerId-1, kwiatek);
                loadPottedFlowersFromFile(); // Odświeżenie danych w tabeli

        	}
        });
        btnNewButton_1.setBounds(761, 656, 85, 21);
        btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 14));
        btnNewButton_1.setBackground(new Color(70, 130, 180));
        btnNewButton_1.setForeground(Color.WHITE);
        btnNewButton_1.setFocusPainted(false);
        btnNewButton_1.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(btnNewButton_1);
        
        JButton ClearButton = new JButton("Wyczyść");
        ClearButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		  // Czyszczenie zawartości pól tekstowych
    	        textField_81.setText("");   // Pole ID
    	        textField_101.setText(""); // Pole Rodzaj
    	        textField_111.setText(""); // Pole Odmiana
    	        textField_121.setText(""); // Pole Kolor
    	        textField_131.setText(""); // Pole Ilość
    	        textField_141.setText(""); // Pole Cena zakupu
    	        textField_151.setText(""); // Pole Cena sprzedaży
    	        textField_161.setText(""); // Pole cena sprz
    	        textField_171.setText(""); // Pole dostepnosc
    	        textField_181.setText(""); // Pole obrazek
    	        obrazekLabel1.setText("");
    	        obrazekLabel1.setIcon(iconN);
        	}
        });
        ClearButton.setBounds(856, 656, 85, 21);
        ClearButton.setFont(new Font("Arial", Font.BOLD, 14));
        ClearButton.setBackground(new Color(70, 130, 180));
        ClearButton.setForeground(Color.WHITE);
        ClearButton.setFocusPainted(false);
        ClearButton.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(ClearButton);
       
        
        textField_81 = new JTextField();
        textField_81.setBounds(187, 712, 96, 19);
        contentPane.add(textField_81);
        textField_81.setColumns(10);
        
        txtCite1 = new JTextField();
        txtCite1.setText("Doniczkowe");
        txtCite1.setBounds(293, 712, 96, 19);
        contentPane.add(txtCite1);
        txtCite1.setColumns(10);
        
        textField_101 = new JTextField();
        textField_101.setBounds(399, 712, 96, 19);
        contentPane.add(textField_101);
        textField_101.setColumns(10);
        
        textField_111 = new JTextField();
        textField_111.setBounds(505, 712, 96, 19);
        contentPane.add(textField_111);
        textField_111.setColumns(10);
        
        textField_121 = new JTextField();
        textField_121.setBounds(611, 712, 96, 19);
        contentPane.add(textField_121);
        textField_121.setColumns(10);
        
        textField_131 = new JTextField();
        textField_131.setBounds(717, 712, 96, 19);
        contentPane.add(textField_131);
        textField_131.setColumns(10);
        
        textField_141 = new JTextField();
        textField_141.setBounds(823, 712, 96, 19);
        contentPane.add(textField_141);
        textField_141.setColumns(10);
        
        textField_151 = new JTextField();
        textField_151.setBounds(929, 712, 96, 19);
        contentPane.add(textField_151);
        textField_151.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("ID");
        lblNewLabel.setBounds(187, 688, 45, 13);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Typ");
        lblNewLabel_1.setBounds(293, 689, 45, 13);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Rodzaj");
        lblNewLabel_2.setBounds(399, 688, 45, 13);
        contentPane.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Rozmiar");
        lblNewLabel_3.setBounds(505, 688, 85, 13);
        contentPane.add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("Kolor");
        lblNewLabel_4.setBounds(611, 688, 45, 13);
        contentPane.add(lblNewLabel_4);
        
        JLabel lblNewLabel_5 = new JLabel("Ilość zakupiona");
        lblNewLabel_5.setBounds(717, 688, 96, 13);
        contentPane.add(lblNewLabel_5);
        
        JLabel lblNewLabel_6 = new JLabel("Cena zakupu");
        lblNewLabel_6.setBounds(823, 688, 76, 13);
        contentPane.add(lblNewLabel_6);
        
        JLabel lblNewLabel_7 = new JLabel("Cena sprzedaży");
        lblNewLabel_7.setBounds(1034, 688, 98, 13);
        contentPane.add(lblNewLabel_7);
        
       
        
        JLabel lblNewLabel_8 = new JLabel("ID");
        lblNewLabel_8.setBounds(189, 343, 45, 13);
        contentPane.add(lblNewLabel_8);
        
        textField_8 = new JTextField();
        textField_8.setColumns(10);
        textField_8.setBounds(187, 367, 96, 19);
        contentPane.add(textField_8);
        
        JLabel lblNewLabel_1_1 = new JLabel("Typ");
        lblNewLabel_1_1.setBounds(303, 344, 45, 13);
        contentPane.add(lblNewLabel_1_1);
        
        txtCite = new JTextField();
        txtCite.setText("Cięte");
        txtCite.setColumns(10);
        txtCite.setBounds(293, 367, 96, 19);
        contentPane.add(txtCite);
        
        textField_10 = new JTextField();
        textField_10.setColumns(10);
        textField_10.setBounds(400, 367, 96, 19);
        contentPane.add(textField_10);
        
        textField_11 = new JTextField();
        textField_11.setColumns(10);
        textField_11.setBounds(505, 367, 96, 19);
        contentPane.add(textField_11);
        
        textField_12 = new JTextField();
        textField_12.setColumns(10);
        textField_12.setBounds(611, 367, 96, 19);
        contentPane.add(textField_12);
        
        textField_13 = new JTextField();
        textField_13.setColumns(10);
        textField_13.setBounds(717, 367, 96, 19);
        contentPane.add(textField_13);
        
        textField_14 = new JTextField();
        textField_14.setColumns(10);
        textField_14.setBounds(823, 367, 96, 19);
        contentPane.add(textField_14);
        
        textField_15 = new JTextField();
        textField_15.setColumns(10);
        textField_15.setBounds(929, 367, 96, 19);
        contentPane.add(textField_15);
        
        JLabel lblNewLabel_7_1 = new JLabel("Cena sprzedaży");
        lblNewLabel_7_1.setBounds(1035, 343, 96, 13);
        contentPane.add(lblNewLabel_7_1);
        
        JLabel lblNewLabel_6_1 = new JLabel("Cena zakupu");
        lblNewLabel_6_1.setBounds(823, 343, 76, 13);
        contentPane.add(lblNewLabel_6_1);
        
        JLabel lblNewLabel_5_1 = new JLabel("Ilość zakupiona");
        lblNewLabel_5_1.setBounds(721, 344, 101, 13);
        contentPane.add(lblNewLabel_5_1);
        
        JLabel lblNewLabel_4_1 = new JLabel("Kolor");
        lblNewLabel_4_1.setBounds(611, 343, 45, 13);
        contentPane.add(lblNewLabel_4_1);
        
        JLabel lblNewLabel_3_1 = new JLabel("Odmiana");
        lblNewLabel_3_1.setBounds(505, 343, 85, 13);
        contentPane.add(lblNewLabel_3_1);
        
        JLabel lblNewLabel_2_1 = new JLabel("Rodzaj");
        lblNewLabel_2_1.setBounds(410, 344, 45, 13);
        contentPane.add(lblNewLabel_2_1);
        
        JButton AddButton_1 = new JButton("Dodaj");
        AddButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Sprawdzenie, czy wszystkie pola są uzupełnione
                if (textField_8.getText().isEmpty() || txtCite.getText().isEmpty() || 
                    textField_10.getText().isEmpty() || textField_11.getText().isEmpty() || 
                    textField_12.getText().isEmpty() || textField_13.getText().isEmpty() || 
                    textField_14.getText().isEmpty() || textField_15.getText().isEmpty() ||
                    textField_16.getText().isEmpty() || textField_17.getText().isEmpty() ||
                    textField_18.getText().isEmpty()) {
                    
                    // Wyświetlenie okienka z błędem
                    JOptionPane.showMessageDialog(null, 
                        "Wszystkie pola muszą być uzupełnione!", 
                        "Błąd", 
                        JOptionPane.ERROR_MESSAGE);
                    return; // Przerwanie dalszego przetwarzania
                }

                try {
                    // Przekazanie danych do obiektu Kwiatek i wywołanie metody fDodaj()
                	 Kwiatek kwiatek = new CutFlowers(
                             Integer.parseInt(textField_8.getText()),  // ID
                             txtCite.getText(),  // Typ
                             textField_10.getText(),  // Rodzaj
                             textField_11.getText(),  // Odmiana
                             textField_12.getText(),  // Kolor
                             Integer.parseInt(textField_13.getText()),  // Ilość zakupionych
                             Double.parseDouble(textField_14.getText()),  // Cena zakupu
                             LocalDate.parse(textField_15.getText()),    // Data zakupu
                             0,                                          //ilosc sprzedanych
                             Double.parseDouble(textField_16.getText()),   // Pole cena sprzedaży
                             null,                                          // Data sprzedaży
                             Integer.parseInt(textField_17.getText()),    // Dostepnosc
                             textField_18.getText()
                     );  
           	          

                    // Wywołanie polimorficznej metody fDodaj()
                    kwiatek.fDodaj();
                    
                    textField_8.setText("");   // Pole ID
        	        textField_10.setText(""); // Pole Rodzaj
        	        textField_11.setText(""); // Pole Odmiana
        	        textField_12.setText(""); // Pole Kolor
        	        textField_13.setText(""); // Pole Ilość
        	        textField_14.setText(""); // Pole Cena zakupu
        	        textField_15.setText(""); // Pole Cena sprzedaży
        	        textField_16.setText(""); // Pole cena sprz
        	        textField_17.setText(""); // Pole dostepnosc
        	        textField_18.setText(""); // Pole obrazek
        	        obrazekLabel.setIcon(iconN);
                    
                    loadCutFlowersFromFile(); // Odświeżenie danych w tabeli

                } catch (NumberFormatException ex) {
                    // Wyświetlenie błędu, jeśli wartości liczbowe są nieprawidłowe
                    JOptionPane.showMessageDialog(null, 
                        "Niektóre pola zawierają nieprawidłowe wartości liczbowe!", 
                        "Błąd", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        AddButton_1.setBounds(458, 309, 85, 21);
        AddButton_1.setFont(new Font("Arial", Font.BOLD, 14));
        AddButton_1.setBackground(new Color(70, 130, 180));
        AddButton_1.setForeground(Color.WHITE);
        AddButton_1.setFocusPainted(false);
        AddButton_1.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(AddButton_1);
        
        JButton DeleteButton_1 = new JButton("Usuń");
        DeleteButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 // Uzyskanie indexu wybranego wiersza
                int selectedRow = tableAvailable.getSelectedRow();
                
                if (selectedRow != -1) {  // Sprawdzenie, czy wiersz został wybrany
                    // Uzyskanie ID kwiatu z pierwszej kolumny
                    int flowerId = Integer.parseInt(tableAvailable.getValueAt(selectedRow, 0).toString());

                    // Usuwanie kwiatu z listy
                    Kwiatek kwiatek = new CutFlowers(0, "", "", "", "", 0, 0, null, 0, 0, null, 0, "");  // Placeholder params
                    kwiatek.fUsun(flowerId);  // Zakładając, że masz metodę do usuwania kwiatu z pliku/danych

                    // Ponowne załadowanie danych do tabeli
                    loadCutFlowersFromFile();
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Proszę wybrać kwiat do usunięcia.");
                }
            
        	}
        });
        DeleteButton_1.setBounds(561, 309, 85, 21);
        DeleteButton_1.setFont(new Font("Arial", Font.BOLD, 14));
        DeleteButton_1.setBackground(new Color(70, 130, 180));
        DeleteButton_1.setForeground(Color.WHITE);
        DeleteButton_1.setFocusPainted(false);
        DeleteButton_1.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(DeleteButton_1);
        
        JButton EditButton_1 = new JButton("Edytuj");
        EditButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 // Sprawdzenie, czy wiersz został zaznaczony
                int selectedRow = tableAvailable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Proszę zaznaczyć wiersz do edycji!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

             // Pobieranie danych z zaznaczonego wiersza
                textField_8.setText(tableAvailable.getValueAt(selectedRow, 0).toString());   // ID
                textField_10.setText(tableAvailable.getValueAt(selectedRow, 2).toString()); // Rodzaj
                textField_11.setText(tableAvailable.getValueAt(selectedRow, 3).toString()); // Odmiana
                textField_12.setText(tableAvailable.getValueAt(selectedRow, 4).toString()); // Kolor
                textField_13.setText(tableAvailable.getValueAt(selectedRow, 5).toString()); // Ilość zakupionych
                textField_14.setText(tableAvailable.getValueAt(selectedRow, 6).toString()); // Cena zakupu
                textField_15.setText(tableAvailable.getValueAt(selectedRow, 7).toString()); // Data zakupu
                textField_16.setText(tableAvailable.getValueAt(selectedRow, 8).toString()); // Cena sprzedaży
                textField_17.setText(tableAvailable.getValueAt(selectedRow, 9).toString()); // Dostępnych
               
                int flowerId = Integer.parseInt(textField_8.getText());   // ID
                Kwiatek kwiatek = new CutFlowers(0, "", "", "", "", 0, 0, null, 0, 0, null, 0, ""); 
                textField_18.setText(kwiatek.fObrazekById(flowerId));  
                
                ImageIcon icon = new ImageIcon(textField_18.getText());
                Image originalImage = icon.getImage();
                // Maksymalne wymiary dla JLabel
                int maxWidth = obrazekLabel.getWidth(); // Szerokość JLabel
                int maxHeight = obrazekLabel.getHeight(); // Wysokość JLabel
                
                int originalWidth = originalImage.getWidth(null);
                int originalHeight = originalImage.getHeight(null);
                
                // Obliczanie współczynnika skalowania z zachowaniem proporcji
                double scale = Math.min((double) maxWidth / originalWidth, (double) maxHeight / originalHeight);
                int newWidth = (int) (originalWidth * scale);
                int newHeight = (int) (originalHeight * scale);

                // Skalowanie obrazu
                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                obrazekLabel.setIcon(new ImageIcon(scaledImage));

        	}
        });
        EditButton_1.setBounds(663, 309, 85, 21);
        EditButton_1.setFont(new Font("Arial", Font.BOLD, 14));
        EditButton_1.setBackground(new Color(70, 130, 180));
        EditButton_1.setForeground(Color.WHITE);
        EditButton_1.setFocusPainted(false);
        EditButton_1.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(EditButton_1);
        
        JButton ClearButton_1 = new JButton("Wyczyść");
        ClearButton_1.addActionListener(new ActionListener() {
        	  public void actionPerformed(ActionEvent e) {
        	        // Czyszczenie zawartości pól tekstowych
        	        textField_8.setText("");   // Pole ID
        	        textField_10.setText(""); // Pole Rodzaj
        	        textField_11.setText(""); // Pole Odmiana
        	        textField_12.setText(""); // Pole Kolor
        	        textField_13.setText(""); // Pole Ilość
        	        textField_14.setText(""); // Pole Cena zakupu
        	        textField_15.setText(""); // Pole Cena sprzedaży
        	        textField_16.setText(""); // Pole cena sprz
        	        textField_17.setText(""); // Pole dostepnosc
        	        textField_18.setText(""); // Pole obrazek
        	        obrazekLabel.setText("");
        	        obrazekLabel.setIcon(iconN);
        	    }
        	
        	
        });
        ClearButton_1.setBounds(863, 309, 85, 21);
        ClearButton_1.setFont(new Font("Arial", Font.BOLD, 14));
        ClearButton_1.setBackground(new Color(70, 130, 180));
        ClearButton_1.setForeground(Color.WHITE);
        ClearButton_1.setFocusPainted(false);
        ClearButton_1.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(ClearButton_1);
        
        JButton btnNewButton = new JButton("Zapisz");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 if (textField_8.getText().isEmpty() || txtCite.getText().isEmpty() || 
                         textField_10.getText().isEmpty() || textField_11.getText().isEmpty() || 
                         textField_12.getText().isEmpty() || textField_13.getText().isEmpty() || 
                         textField_14.getText().isEmpty() || textField_15.getText().isEmpty() ||
                         textField_16.getText().isEmpty() || textField_17.getText().isEmpty() ||
                         textField_18.getText().isEmpty()) {
                         
                         // Wyświetlenie okienka z błędem
                         JOptionPane.showMessageDialog(null, 
                             "Wszystkie pola muszą być uzupełnione!", 
                             "Błąd", 
                             JOptionPane.ERROR_MESSAGE);
                         return; // Przerwanie dalszego przetwarzania
                  }
        		
        		  int flowerId =  Integer.parseInt(textField_8.getText());   // Pole ID
      	          textField_10.getText(); // Pole Rodzaj
      	          textField_11.getText(); // Pole Odmiana
      	          textField_12.getText(); // Pole Kolor
      	          textField_13.getText(); // Pole Ilość zakupionych
      	          textField_14.getText(); // Pole Cena zakupu
      	          textField_15.getText(); // Pole data zakupu
      	          textField_16.getText(); // Pole cena sprz
      	          textField_17.getText(); // Pole dostepnosc
      	          textField_18.getText(); // Pole obrazek
      	          
      	          Kwiatek kwiatek = new CutFlowers(
                        Integer.parseInt(textField_8.getText()),  // ID
                        txtCite.getText(),  // Typ
                        textField_10.getText(),  // Rodzaj
                        textField_11.getText(),  // Odmiana
                        textField_12.getText(),  // Kolor
                        Integer.parseInt(textField_13.getText()),  // Ilość zakupionych
                        Double.parseDouble(textField_14.getText()),  // Cena zakupu
                        LocalDate.parse(textField_15.getText()),    // Data zakupu
                        0,                                          //ilosc sprzedanych
                        Double.parseDouble(textField_16.getText()),   // Pole cena sprzedaży
                        null,                                          // Data sprzedaży
                        Integer.parseInt(textField_17.getText()),    // Dostepnosc
                        textField_18.getText()
                        
                    );  
      	          
                kwiatek.fUsunv2(flowerId);  
                
                kwiatek.fDodajPoId(flowerId-1, kwiatek);
                loadCutFlowersFromFile(); // Odświeżenie danych w tabeli

        	}
        });
      
        btnNewButton.setBounds(768, 309, 85, 21);
        btnNewButton.setFont(new Font("Arial", Font.BOLD, 14));
        btnNewButton.setBackground(new Color(70, 130, 180));
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setFocusPainted(false);
        btnNewButton.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(btnNewButton);
       
        
        textField_17 = new JTextField();
        textField_17.setBounds(1140, 367, 96, 19);
        contentPane.add(textField_17);
        textField_17.setColumns(10);
        
        textField_16 = new JTextField();
        textField_16.setBounds(1035, 367, 96, 19);
        contentPane.add(textField_16);
        textField_16.setColumns(10);
        
        JLabel lblNewLabel_9 = new JLabel("Data zakupu");
        lblNewLabel_9.setBounds(929, 343, 86, 13);
        contentPane.add(lblNewLabel_9);
        
        JLabel lblNewLabel_10 = new JLabel("Dostępnych");
        lblNewLabel_10.setBounds(1140, 344, 76, 13);
        contentPane.add(lblNewLabel_10);
        
        JButton wybierzObrazekButton = new JButton("Wybierz obrazek");
        wybierzObrazekButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		choosePictureCut();
        	}
        });
        wybierzObrazekButton.setBounds(1245, 309, 141, 21);
        wybierzObrazekButton.setFont(new Font("Arial", Font.BOLD, 14));
        wybierzObrazekButton.setBackground(new Color(70, 130, 180));
        wybierzObrazekButton.setForeground(Color.WHITE);
        wybierzObrazekButton.setFocusPainted(false);
        wybierzObrazekButton.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(wybierzObrazekButton);
        getContentPane().add(wybierzObrazekButton);        
        
        // Label na podgląd obrazka
        
        obrazekLabel = new JLabel("");
        obrazekLabel.setIcon(iconN);
        obrazekLabel.setBounds(1245, 168, 141, 129);
        contentPane.add(obrazekLabel);
        obrazekLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        textField_18 = new JTextField();
        textField_18.setBounds(1245, 339, 141, 19);
        contentPane.add(textField_18);
        textField_18.setColumns(10);
        
        JLabel lblNewLabel_11 = new JLabel("Data zakupu");
        lblNewLabel_11.setBounds(929, 687, 95, 14);
        contentPane.add(lblNewLabel_11);
        
        textField_161 = new JTextField();
        textField_161.setColumns(10);
        textField_161.setBounds(1035, 712, 96, 19);
        contentPane.add(textField_161);
        
        textField_171 = new JTextField();
        textField_171.setColumns(10);
        textField_171.setBounds(1142, 712, 96, 19);
        contentPane.add(textField_171);
        
        textField_181 = new JTextField();
        textField_181.setColumns(10);
        textField_181.setBounds(1245, 685, 141, 19);
        contentPane.add(textField_181);
        
        JButton wybierzObrazekButton_1 = new JButton("Wybierz obrazek");
        wybierzObrazekButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		choosePicturePotted();
        	}
        });
        wybierzObrazekButton_1.setBounds(1245, 658, 141, 21);
        wybierzObrazekButton_1.setFont(new Font("Arial", Font.BOLD, 14));
        wybierzObrazekButton_1.setBackground(new Color(70, 130, 180));
        wybierzObrazekButton_1.setForeground(Color.WHITE);
        wybierzObrazekButton_1.setFocusPainted(false);
        wybierzObrazekButton_1.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(wybierzObrazekButton_1);
        
        obrazekLabel1 = new JLabel("");
        obrazekLabel1.setIcon(new ImageIcon("C:\\Users\\AnnaK\\eclipse-workspace\\FlowerShopProject\\image.png"));
        obrazekLabel1.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        obrazekLabel1.setBounds(1245, 518, 141, 129);
        contentPane.add(obrazekLabel1);
        
        JLabel lblNewLabel_10_1 = new JLabel("Dostępnych");
        lblNewLabel_10_1.setBounds(1142, 688, 76, 13);
        contentPane.add(lblNewLabel_10_1);
        
        
       

        
       
    }
}
