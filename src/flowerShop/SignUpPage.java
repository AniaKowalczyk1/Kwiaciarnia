package flowerShop;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class SignUpPage extends JFrame {

    private JTextField userIDField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private List<User> users;

    HashMap<String, String> loginInfo = new HashMap<String, String>();

    public SignUpPage() {
        users = UserManager.loadUsers();

        setTitle("Rejestracja");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 245, 245)); // Jasnoszare tło
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Ustawienie okna na środku ekranu
        setLocationRelativeTo(null);

        // Etykieta "Login"
        JLabel userIDLabel = new JLabel("Login");
        userIDLabel.setBounds(53, 36, 45, 18);
        userIDLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Zmieniono czcionkę
        contentPane.add(userIDLabel);

        // Pole do wpisania Loginu
        userIDField = new JTextField();
        userIDField.setBounds(154, 24, 150, 30); // Zwiększono szerokość i wysokość pola
        userIDField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(userIDField);
        userIDField.setColumns(10);

        // Etykieta "Hasło"
        JLabel passwordLabel = new JLabel("Hasło");
        passwordLabel.setBounds(53, 70, 45, 13);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Zmieniono czcionkę
        contentPane.add(passwordLabel);

        // Pole do wpisania hasła
        passwordField = new JPasswordField();
        passwordField.setBounds(154, 62, 150, 30); // Zwiększono szerokość i wysokość pola
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(passwordField);

        // Etykieta "Powtórz hasło"
        JLabel confirmPasswordLabel = new JLabel("Powtórz hasło");
        confirmPasswordLabel.setBounds(53, 100, 100, 13);
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Zmieniono czcionkę
        contentPane.add(confirmPasswordLabel);

        // Pole do potwierdzenia hasła
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(154, 98, 150, 30); // Zwiększono szerokość i wysokość pola
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(confirmPasswordField);

        // Etykieta komunikatu
        JLabel messageLabel = new JLabel();
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(71, 230, 250, 20);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(messageLabel);

        // Przycisk "Zarejestruj"
        JButton registerButton = new JButton("Zarejestruj");
        registerButton.setBounds(154, 139, 150, 35); // Zwiększono szerokość przycisku
        registerButton.setBackground(new Color(70, 130, 180)); // Kolor przycisku
        registerButton.setForeground(Color.WHITE); // Kolor tekstu na przycisku
        registerButton.setFont(new Font("Arial", Font.BOLD, 14)); // Zmieniono czcionkę
        registerButton.setFocusPainted(false);
        registerButton.setBorder(new LineBorder(new Color(50, 80, 120), 2, true));
        contentPane.add(registerButton);

        // Przycisk "Powrót do logowania"
        JButton backToLoginButton = new JButton("Powrót do logowania");
        backToLoginButton.setBounds(154, 185, 150, 35); // Zwiększono szerokość przycisku
        backToLoginButton.setBackground(new Color(70, 130, 180)); // Kolor przycisku
        backToLoginButton.setForeground(Color.WHITE); // Kolor tekstu na przycisku
        backToLoginButton.setFont(new Font("Arial", Font.BOLD, 14)); // Zmieniono czcionkę
        backToLoginButton.setFocusPainted(false);
        backToLoginButton.setBorder(new LineBorder(new Color(70, 130, 180), 2, true));
        contentPane.add(backToLoginButton);

        // Akcja po kliknięciu przycisku "Powrót do logowania"
        backToLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
                dispose(); // Zamknięcie okna rejestracji
            }
        });

        // Akcja po kliknięciu przycisku "Zarejestruj"
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	  String userID = userIDField.getText();
                  String password = String.valueOf(passwordField.getPassword());
                  String confirmPassword = String.valueOf(confirmPasswordField.getPassword());

                  // Sprawdzenie, czy wszystkie pola są wypełnione
                  if (userID.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                      messageLabel.setText("Wszystkie pola muszą być wypełnione.");
                      messageLabel.setForeground(Color.RED);
                  }
                  // Sprawdzenie, czy login już istnieje
                  else if (users.stream().anyMatch(u -> u.getUsername().equals(userID))) {
                      messageLabel.setText("Ten login już istnieje. Wybierz inny.");
                      messageLabel.setForeground(Color.RED);
                  }
                  // Sprawdzenie, czy hasła się zgadzają
                  else if (password.equals(confirmPassword)) {
                      // Dodanie użytkownika do listy
                      users.add(new User(userID, password));
                      // Zapisz użytkowników do pliku
                      UserManager.saveUsers(users);
                      messageLabel.setText("Użytkownik zarejestrowany!");
                      messageLabel.setForeground(Color.GREEN);
                  } else {
                      messageLabel.setText("Hasła się nie zgadzają");
                      messageLabel.setForeground(Color.RED);
                  }
            }
        });
    }
}
