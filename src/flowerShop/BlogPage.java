package flowerShop;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class BlogPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JPanel innerPanel; // Panel wewnętrzny dla wszystkich komponentów
	private String fileName = "blog.txt";
	private JTextArea textArea;
	private JTextArea wpisField;
	private ImageIcon iconN = new ImageIcon("C:\\Users\\AnnaK\\eclipse-workspace\\FlowerShopProject\\image.png");
	private JLabel lblNewLabel = new JLabel(); 
    private String obrazekSciezka = null;  // Zmienna do przechowywania ścieżki obrazu
    private int selectedPostIndex = -1; // Zmienna do przechowywania indeksu klikniętego wpisu
    private List<Wpis> wpisy = new ArrayList<>();

    
   
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

	     
    private class Wpis {
        String tresc;
        String obrazekSciezka;

        public Wpis(String tresc, String obrazekSciezka) {
            this.tresc = tresc;
            this.obrazekSciezka = obrazekSciezka;
        }
    }

    private void wyswietlWpisy() {
    	   wpisy.clear();  // Opróżni listę wpisów
        // Usunięcie wszystkich komponentów (zarówno tekstu jak i obrazków) z contentPane
        for (Component comp : contentPane.getComponents()) {
            if (comp instanceof JScrollPane) {
                contentPane.remove(comp); // Usuwamy JScrollPane z JTextArea
            }
            if (comp instanceof JLabel) {
                contentPane.remove(comp); // Usuwamy JLabel z obrazkiem
            }
        }
        // Tworzymy JTextArea
        textArea = new JTextArea();
        textArea.setBounds(322, 180, 655, 198);
        // Ustawienie zawijania linii i słów
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Tworzymy JScrollPane dla JTextArea
        JScrollPane scrollPane1 = new JScrollPane(textArea);
        // Ustawiamy pozycję i rozmiar JScrollPane
        scrollPane1.setBounds(322, 180, 655, 198);
        // Dodanie JScrollPane do contentPane
        contentPane.add(scrollPane1);

        lblNewLabel.setIcon(iconN);
        lblNewLabel.setBounds(101, 219, 204, 159);
        contentPane.add(lblNewLabel);

        int yPosition = 470; // Pozycja początkowa dla wpisów

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            StringBuilder wpis = new StringBuilder();
            boolean isReadingWpis = false;

            while ((line = reader.readLine()) != null) {
                if (line.equals("start")) {
                    isReadingWpis = true;
                    wpis.setLength(0); // Resetowanie zawartości wpisu
                    obrazekSciezka = null; // Resetowanie ścieżki obrazka
                } else if (line.equals("koniec")) {
                    isReadingWpis = false;

                    // Odczytanie ścieżki do obrazka
                    line = reader.readLine(); // Odczytanie ścieżki do obrazka
                    if (line != null && !line.trim().isEmpty()) {
                        obrazekSciezka = line.trim(); // Przypisanie ścieżki obrazu
                    }

                    // Dodanie wpisu do listy
                    wpisy.add(new Wpis(wpis.toString(), obrazekSciezka));

                    // Tworzenie nowego pola JTextArea dla wpisu
                    wpisField = new JTextArea(wpis.toString());
                    wpisField.setEditable(false);
                    wpisField.setLineWrap(true);
                    wpisField.setWrapStyleWord(true);

                    // Tworzenie JScrollPane dla JTextArea
                    JScrollPane wpisScrollPane = new JScrollPane(wpisField);
                    wpisScrollPane.setBounds(322, yPosition, 655, 150); // Rozmiar obszaru przewijania dla każdego wpisu
                    wpisScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    wpisScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                    // Dodanie nasłuchiwania na kliknięcia
                    int index = wpisy.size() - 1; // Indeks bieżącego wpisu
                    wpisField.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                            // Ustawienie tekstu w głównym JTextArea
                            textArea.setText(wpisy.get(index).tresc);

                            // Wyświetlenie odpowiedniego obrazka w lblNewLabel
                            String sciezka = wpisy.get(index).obrazekSciezka;
                            if (sciezka != null) {
                                ImageIcon imageIcon = new ImageIcon(sciezka);
                                Image image = imageIcon.getImage();
                                Image scaledImage = image.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH);
                                lblNewLabel.setIcon(new ImageIcon(scaledImage));  // Ustawienie obrazu w lblNewLabel
                            } else {
                                lblNewLabel.setIcon(null); // Jeśli brak obrazka, usuń ikonę
                            }

                            // Zapisywanie indeksu klikniętego wpisu
                            selectedPostIndex = index;
                            obrazekSciezka = sciezka;
                        }
                    });

                    // Dodanie JScrollPane do contentPane
                    contentPane.add(wpisScrollPane);

                    // Jeśli istnieje ścieżka do obrazka, dodaj obrazek obok tekstu
                    if (obrazekSciezka != null) {
                        ImageIcon imageIcon = new ImageIcon(obrazekSciezka);
                        Image image = imageIcon.getImage();
                        Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Skalowanie obrazu
                        JLabel obrazekLabel = new JLabel(new ImageIcon(scaledImage));
                        obrazekLabel.setBounds(101, yPosition, 204, 159); // Umieszczenie obrazu obok tekstu
                        contentPane.add(obrazekLabel);
                    }

                    // Zwiększenie pozycji Y dla kolejnego wpisu
                    yPosition += 200;
                } else if (isReadingWpis) {
                    wpis.append(line).append("\n"); // Dodawanie linii do wpisu
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Błąd przy odczycie wpisów!", "Błąd", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        // Odświeżenie widoku panelu po dodaniu elementów
        contentPane.revalidate();
        contentPane.repaint();
    }

	
	
	
	public BlogPage() {
		
		
		

		Blog AdminBlog = new Blog() { // Klasa anonimowa implementująca interfejs Blog
			// Zmieniona metoda fDodajWpis, która zapisuje ścieżkę do obrazu po słowie "koniec"
			@Override
	        public boolean fDodajWpis() {
	            // Pobieranie tekstu z JTextArea
	            String wpis = textArea.getText();

	            if (wpis.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Proszę wprowadzić treść wpisu!", "Błąd", JOptionPane.ERROR_MESSAGE);
	                return false;
	            }

	            // Pobranie ścieżki do obrazu, jeśli został wybrany
	            String obrazekSciezkaDoZapisania = obrazekSciezka;
	           

	            // Zapis do pliku z dodaniem "start", tekstu wpisu, "koniec" i ścieżki obrazu (jeśli istnieje)
	            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
	                writer.write("start");  // Zaczynamy wpis
	                writer.newLine();
	                writer.write(wpis);    // Dodajemy zawartość wpisu
	                writer.newLine();
	                writer.write("koniec"); // Kończymy wpis

	                // Zapisz ścieżkę do obrazka (jeśli istnieje)
	                if (obrazekSciezkaDoZapisania != null) {
	                    writer.newLine();
	                    writer.write(obrazekSciezkaDoZapisania); // Zapisujemy pełną ścieżkę obrazu
	                }
	                writer.newLine(); // Oddzielenie wpisów nową linią

	                textArea.setText(""); // Wyczyść pole tekstowe po zapisaniu

	            } catch (IOException ex) {
	                JOptionPane.showMessageDialog(null, "Błąd przy zapisywaniu wpisu!", "Błąd", JOptionPane.ERROR_MESSAGE);
	                ex.printStackTrace();
	                return false;
	            }

	            wyswietlWpisy();
	            return true; 
	        }

			
			@Override
			public boolean fUsunWpis() {
			    if (selectedPostIndex == -1) {
			        JOptionPane.showMessageDialog(null, "Proszę wybrać wpis do usunięcia!", "Błąd", JOptionPane.ERROR_MESSAGE);
			        return false;
			    }

			    List<Wpis> wpisy = new ArrayList<>();
			    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			        String line;
			        StringBuilder wpis = new StringBuilder();
			        String obrazekSciezka = null;
			        boolean isReadingWpis = false;

			        // Odczytujemy wpisy i zapisujemy je do listy
			        while ((line = reader.readLine()) != null) {
			            if (line.equals("start")) {
			                isReadingWpis = true;
			                wpis.setLength(0); // Resetowanie zawartości wpisu
			                obrazekSciezka = null; // Resetowanie ścieżki obrazka
			            } else if (line.equals("koniec")) {
			                isReadingWpis = false;

			                // Odczytanie ścieżki do obrazka
			                line = reader.readLine(); // Odczytanie ścieżki do obrazka
			                if (line != null && !line.trim().isEmpty()) {
			                    obrazekSciezka = line.trim(); // Przypisanie ścieżki obrazu
			                }

			                // Dodanie wpisu do listy
			                wpisy.add(new Wpis(wpis.toString(), obrazekSciezka));
			            } else if (isReadingWpis) {
			                wpis.append(line).append("\n"); // Dodawanie linii do wpisu
			            }
			        }
			    } catch (IOException ex) {
			        JOptionPane.showMessageDialog(null, "Błąd przy odczycie wpisów!", "Błąd", JOptionPane.ERROR_MESSAGE);
			        ex.printStackTrace();
			        return false;
			    }

			    // Sprawdzamy stan listy przed usunięciem
			    System.out.println("Wpisy przed usunięciem: " + wpisy.size());
			    System.out.println("Indeks przed usunięciem: " + selectedPostIndex);
			    // Usuwamy wpis na podstawie indeksu
			    if (selectedPostIndex >= 0 && selectedPostIndex < wpisy.size()) {
			        wpisy.remove(selectedPostIndex); // Usuwamy wpis z listy
			        System.out.println("Usunięto wpis nr: " + selectedPostIndex);
			    } else {
			        JOptionPane.showMessageDialog(null, "Nie znaleziono wpisu do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
			        return false;
			    }
			    
			    // Zapisujemy zaktualizowane wpisy do pliku
			    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			        for (Wpis wpis : wpisy) {
			            writer.write("start");
			            writer.newLine();
			            writer.write(wpis.tresc);
			            writer.newLine();
			            writer.write("koniec");
			            writer.newLine();
			            if (wpis.obrazekSciezka != null) {
			                writer.write(wpis.obrazekSciezka);
			                writer.newLine();
			            }
			        }
			    } catch (IOException ex) {
			        JOptionPane.showMessageDialog(null, "Błąd przy zapisywaniu zmian!", "Błąd", JOptionPane.ERROR_MESSAGE);
			        ex.printStackTrace();
			        return false;
			    }

			    
			    selectedPostIndex = -1; // Jeśli lista jest pusta, ustawiamy na -1
			    

			    // Odświeżenie widoku wpisów
			    wyswietlWpisy();  // Wywołanie tej metody powoduje załadowanie zaktualizowanych wpisów
			    textArea.setText("");
			    lblNewLabel.setIcon(iconN);
			    return true;
			}



			@Override
			public boolean fEdytujWpis() {
			    // Sprawdzamy, czy użytkownik kliknął na jakiś wpis
			    if (selectedPostIndex == -1) {
			        JOptionPane.showMessageDialog(null, "Proszę wybrać wpis do edycji!", "Błąd", JOptionPane.ERROR_MESSAGE);
			        return false;
			    }

			    // Pobieramy tekst z textArea (po edycji)
			    String nowaTresc = textArea.getText();

			    if (nowaTresc.isEmpty()) {
			        JOptionPane.showMessageDialog(null, "Proszę wprowadzić treść wpisu!", "Błąd", JOptionPane.ERROR_MESSAGE);
			        return false;
			    }
			    fDodajWpis() ;
			    fUsunWpis();

			    textArea.setText("");  // Czyszczenie pola tekstowego
			    lblNewLabel.setIcon(iconN);  // Usuwanie obrazu z widoku, jeżeli był
			    return true;
			}



        };
		
		
        setTitle("Kwiaciarnia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Pełny rozmiar ekranu
        setLocationRelativeTo(null); // Ustawienie okna na środku

        // Tworzenie wewnętrznego panelu
        contentPane = new JPanel();
        contentPane.setLayout(null); // Używanie absolutnego rozmieszczenia
        contentPane.setPreferredSize(new java.awt.Dimension(800, 2000)); // Początkowy rozmiar panelu
        setupButtons(contentPane);

        // Dodanie JScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setContentPane(scrollPane); // JScrollPane jest głównym komponentem

        
        JButton btnNewButton_5 = new JButton("Dodaj wpis");
        btnNewButton_5.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 // Sprawdzenie, czy wpis został pomyślnie dodany
                boolean wpisDodany = AdminBlog.fDodajWpis();

                if (wpisDodany) {
                    // Jeśli wpis został dodany, zapisujemy raport
                    RaportZapis.zapiszRaport("Dodano nowy wpis na blogu.");
                } else {
                    // Jeśli wpis nie został dodany, można dodać raport o błędzie (opcjonalnie)
                    RaportZapis.zapiszRaport("Błąd przy dodawaniu wpisu na blogu.");
                }

                
        	}
        });
        btnNewButton_5.setBounds(521, 146, 116, 21);
        btnNewButton_5.setFont(new Font("Arial", Font.BOLD, 14));
        btnNewButton_5.setBackground(new Color(70, 130, 180));
        btnNewButton_5.setForeground(Color.WHITE);
        btnNewButton_5.setFocusPainted(false);
        btnNewButton_5.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(btnNewButton_5);
        
        JButton btnNewButton_6 = new JButton("Edytuj wpis");
        btnNewButton_6.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 boolean edytowano = AdminBlog.fEdytujWpis();
     	        if (edytowano) {
     	            RaportZapis.zapiszRaport("Wpis został pomyślnie edytowany.");
     	        } else {
     	            RaportZapis.zapiszRaport("Błąd przy edytowaniu wpisu.");
     	        }
        	       
        	}
        });
        btnNewButton_6.setBounds(660, 146, 116, 21);
        btnNewButton_6.setFont(new Font("Arial", Font.BOLD, 14));
        btnNewButton_6.setBackground(new Color(70, 130, 180));
        btnNewButton_6.setForeground(Color.WHITE);
        btnNewButton_6.setFocusPainted(false);
        btnNewButton_6.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(btnNewButton_6);
        
        JButton btnNewButton_7 = new JButton("Usuń wpis");
        btnNewButton_7.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 boolean usunieto = AdminBlog.fUsunWpis();
     	        if (usunieto) {
     	            RaportZapis.zapiszRaport("Wpis został pomyślnie usunięty.");
     	        } else {
     	            RaportZapis.zapiszRaport("Błąd przy usuwaniu wpisu.");
     	        }
        	        
        	}
        });
        btnNewButton_7.setBounds(799, 146, 116, 21);
        btnNewButton_7.setFont(new Font("Arial", Font.BOLD, 14));
        btnNewButton_7.setBackground(new Color(70, 130, 180));
        btnNewButton_7.setForeground(Color.WHITE);
        btnNewButton_7.setFocusPainted(false);
        btnNewButton_7.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(btnNewButton_7);        

        JButton btnNewButton_9 = new JButton("Wyświetl");
        btnNewButton_9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPostIndex == -1) {
                    JOptionPane.showMessageDialog(null, "Proszę wybrać wpis do wyświetlenia!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Otwórz nowe okno z pełnym wpisem
                Wpis wybranyWpis = wpisy.get(selectedPostIndex);
                String tresc = wybranyWpis.tresc;
                String obrazekSciezka = wybranyWpis.obrazekSciezka;

                // Utwórz nowe okno z treścią
                WpisWindow wpisWindow = new WpisWindow(tresc, obrazekSciezka);
                wpisWindow.setVisible(true);
            }
        });
        btnNewButton_9.setBounds(948, 146, 116, 21);
        btnNewButton_9.setFont(new Font("Arial", Font.BOLD, 14));
        btnNewButton_9.setBackground(new Color(70, 130, 180));
        btnNewButton_9.setForeground(Color.WHITE);
        btnNewButton_9.setFocusPainted(false);
        btnNewButton_9.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(btnNewButton_9);
        
        

        
        JButton btnNewButton_8 = new JButton("Dodaj obrazek");
        btnNewButton_8.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 // Otwórz okno dialogowe do wyboru obrazu
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Wybierz obrazek");
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Obrazy", "jpg", "png", "gif"));
                
                // Jeśli użytkownik wybierze plik
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    
                    // Załaduj obrazek do ImageIcon
                    ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                    
                    // Skalowanie obrazu do rozmiarów JLabel
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH);
                    
                    // Ustaw skalowany obrazek w JLabel
                    lblNewLabel.setIcon(new ImageIcon(scaledImage));
                  
                    obrazekSciezka = selectedFile.getAbsolutePath();  // Zmienna do przechowywania ścieżki obrazu

                }
        		
        	}
        });
        btnNewButton_8.setBounds(116, 173, 123, 28);
        btnNewButton_8.setFont(new Font("Arial", Font.BOLD, 14));
        btnNewButton_8.setBackground(new Color(70, 130, 180));
        btnNewButton_8.setForeground(Color.WHITE);
        btnNewButton_8.setFocusPainted(false);
        btnNewButton_8.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(btnNewButton_8);
        
     
        
		
		

        
        wyswietlWpisy();
        
		
		
	}
}
