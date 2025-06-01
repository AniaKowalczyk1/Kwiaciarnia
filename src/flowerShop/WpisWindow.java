package flowerShop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

class WpisWindow extends JFrame {
    private JTextArea textArea;
    private JLabel imageLabel;

    public WpisWindow(String tresc, String obrazekSciezka) {
        setTitle("Wpis");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel główny
        JPanel panel = new JPanel();
        panel.setBackground(new Color(250, 250, 250)); // Subtelne tło
        panel.setLayout(null);
        setContentPane(panel);

        // Jeśli istnieje ścieżka do obrazka, wyświetl obrazek
        if (obrazekSciezka != null && !obrazekSciezka.trim().isEmpty()) {
            ImageIcon imageIcon = new ImageIcon(obrazekSciezka);
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(scaledImage));

            // Obliczanie pozycji środka okna
            int x = (getWidth() - imageLabel.getPreferredSize().width) / 2;
            int y = 20; // Ustalamy pozycję obrazka na samej górze
            imageLabel.setBounds(x, y, 200, 200);
            panel.add(imageLabel);
        }

        // JTextArea do wyświetlenia treści
        textArea = new JTextArea();
        textArea.setText(tresc);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16)); // Zwiększona czcionka dla lepszej czytelności
        textArea.setBackground(new Color(245, 245, 245)); // Delikatne szare tło
        textArea.setForeground(new Color(50, 50, 50)); // Ciemniejszy tekst dla lepszej widoczności
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10)); // Odstępy wewnętrzne

        // ScrollPane z JTextArea
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 240, 550, 520);
        scrollPane.setBorder(null); // Usuwamy obramowanie scrolla, by było bardziej minimalistycznie
        panel.add(scrollPane);
    }
}
