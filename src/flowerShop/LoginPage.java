package flowerShop;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LoginPage extends JFrame {


    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField userIDField;
    private JPasswordField userPasswordField;
    private JLabel userPasswordLabel;
    private JButton SignUpButton;
    private List<User> users;

    /**
     * Create the frame.
     */
    // Metoda inicjalizująca GUI
    LoginPage() {
        users = UserManager.loadUsers();

        setTitle("Kwiaciarnia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 245, 245)); // Jasnoszare tło
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Ustawienie okna na środku ekranu
        setLocationRelativeTo(null);

        // Pola wprowadzania danych
        userIDField = new JTextField();
        userIDField.setBounds(165, 26, 150, 30);
        userIDField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(userIDField);
        userIDField.setColumns(10);

        userPasswordField = new JPasswordField();
        userPasswordField.setBounds(165, 67, 150, 30);
        userPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(userPasswordField);

        // Etykiety
        JLabel userIDLabel = new JLabel("Login");
        userIDLabel.setBounds(113, 27, 61, 27);
        userIDLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(userIDLabel);

        userPasswordLabel = new JLabel("Hasło");
        userPasswordLabel.setBounds(113, 75, 45, 13);
        userPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(userPasswordLabel);

        JLabel messageLabel = new JLabel();
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(86, 230, 250, 20);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(messageLabel);

        // Przycisk Rejestracja
        SignUpButton = new JButton("Rejestracja");
        SignUpButton.setBounds(165, 180, 150, 35);
        SignUpButton.setBackground(new Color(70, 130, 180));
        SignUpButton.setForeground(Color.WHITE);
        SignUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        SignUpButton.setFocusPainted(false);
        SignUpButton.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(SignUpButton);
        SignUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SignUpPage signUpPage = new SignUpPage();
                signUpPage.setVisible(true);
                dispose();
            }
        });

        // Przycisk Zaloguj
        JButton loginButton = new JButton("Zaloguj");
        loginButton.setBounds(165, 134, 150, 35);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 String userID = userIDField.getText();
                 String password = String.valueOf(userPasswordField.getPassword());

                 boolean loginSuccessful = false;
                 
                 // Sprawdzamy, czy użytkownik to admin
                 if (userID.equals("admin") && password.equals("admin")) {
                     loginSuccessful = true;
                 } else {
                     // Przechodzimy przez użytkowników wczytanych z pliku
                     for (User user : users) {
                         if (user.getUsername().equals(userID) && user.getPassword().equals(password)) {
                             loginSuccessful = true;
                             break;
                         }
                     }
                 }

                 if (loginSuccessful) {
                     // Przejdź do MainPage lub UserPage
                     if (userID.equals("admin") && password.equals("admin")) {
                         MainPage mainPage = new MainPage();
                         mainPage.setVisible(true);
                     } else {
                         UserPage userPage = new UserPage();
                         userPage.setVisible(true);
                     }
                     dispose(); // Zamknięcie okna logowania
                 } else {
                     messageLabel.setForeground(Color.RED);
                     messageLabel.setText("Nieprawidłowy login lub hasło");
                 }
             
            }
        });
    }
}
