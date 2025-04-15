import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WelcomeScreen extends JFrame {

    public WelcomeScreen() {
        setTitle("🎰 Welcome to Java Pokie Machine");
        setSize(600, 400); // 👈 matches SlotMachineGUI
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 250, 255));

        JLabel title = new JLabel("Welcome to the Pokie Machine!", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JTextField depositField = new JTextField();
        depositField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        depositField.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea winKey = new JTextArea("""
            WINNING COMBINATIONS:
            - 3 matching symbols = $50
            - 2 matching symbols = $20
            - Others = $0
        """);
        winKey.setEditable(false);
        winKey.setBackground(new Color(230, 240, 255));
        winKey.setFont(new Font("Monospaced", Font.PLAIN, 14));
        winKey.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton playButton = new JButton("🎮 Start Playing");
        playButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        playButton.setBackground(new Color(30, 150, 255));
        playButton.setForeground(Color.WHITE);
        playButton.setFocusPainted(false);
        playButton.addActionListener((ActionEvent e) -> {
            try {
                int deposit = Integer.parseInt(depositField.getText().trim());
                if (deposit <= 0) throw new NumberFormatException();
                new SlotMachineGUI(deposit);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number greater than 0.");
            }
        });

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBackground(new Color(240, 250, 255));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        centerPanel.add(new JLabel("Enter deposit amount ($):", SwingConstants.CENTER));
        centerPanel.add(depositField);
        centerPanel.add(playButton);

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(winKey, BorderLayout.SOUTH);

        setVisible(true);
    }
}
