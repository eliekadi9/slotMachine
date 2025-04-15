import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class SlotMachineGUI extends JFrame {

    private final JLabel[] slots = new JLabel[3];
    private final JLabel balanceLabel = new JLabel();
    private final JLabel resultLabel = new JLabel();

    private final JButton spinButton = new JButton("🎲 SPIN");
    private final JButton cashOutButton = new JButton("💵 Cash Out");

    private final int initialDeposit;
    private int balance;

    private final String[] symbols = {"🍒", "🔔", "7️⃣", "🍋", "⭐", "🍀"};
    private final Random rand = new Random();

    public SlotMachineGUI(int deposit) {
        this.initialDeposit = deposit;
        this.balance = deposit;

        setTitle("🎰 Java Pokie Machine");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 250, 255)); // Light background

        setupSlotArea();
        setupControls();

        setVisible(true);
    }

    private void setupSlotArea() {
        JPanel slotPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        slotPanel.setBackground(new Color(240, 250, 255));
        slotPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        for (int i = 0; i < 3; i++) {
            slots[i] = new JLabel("❔", SwingConstants.CENTER);
            slots[i].setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
            slots[i].setOpaque(true);
            slots[i].setBackground(Color.WHITE);
            slots[i].setBorder(new LineBorder(new Color(100, 100, 100), 3, true));
            slotPanel.add(slots[i]);
        }

        add(slotPanel, BorderLayout.CENTER);
    }

    private void setupControls() {
        JPanel controlPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        controlPanel.setBackground(new Color(220, 235, 255));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        resultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        updateBalanceLabel();

        spinButton.setFont(new Font("Segoe UI", Font.BOLD, 22));
        spinButton.setBackground(new Color(20, 150, 255));
        spinButton.setForeground(Color.WHITE);
        spinButton.setFocusPainted(false);
        spinButton.addActionListener(e -> spin());

        cashOutButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cashOutButton.setBackground(new Color(0, 180, 80));
        cashOutButton.setForeground(Color.WHITE);
        cashOutButton.setFocusPainted(false);
        cashOutButton.setEnabled(false); // initially disabled
        cashOutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "You cashed out with $" + balance);
            dispose();
            new WelcomeScreen(); // Return to welcome screen
        });

        controlPanel.add(balanceLabel);
        controlPanel.add(resultLabel);
        controlPanel.add(spinButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(240, 250, 255));
        bottomPanel.add(cashOutButton);

        add(controlPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: $" + balance + " | Deposit: $" + initialDeposit);
        cashOutButton.setEnabled(balance > initialDeposit || balance < initialDeposit || balance == initialDeposit); // enable cashout only if balance > deposit
    }

    private void spin() {
        if (balance < 10) {
            JOptionPane.showMessageDialog(this, "Not enough balance to spin!");
            return;
        }

        balance -= 10;
        updateBalanceLabel();
        resultLabel.setText("Spinning...");

        Timer spinTimer = new Timer(100, null);
        final int[] ticks = {0};
        final String[] result = new String[3];

        spinTimer.addActionListener((ActionEvent e) -> {
            ticks[0]++;
            for (int i = 0; i < 3; i++) {
                slots[i].setText(symbols[rand.nextInt(symbols.length)]);
            }

            if (ticks[0] >= 10) { // Stop after 10 ticks
                spinTimer.stop();

                for (int i = 0; i < 3; i++) {
                    result[i] = symbols[rand.nextInt(symbols.length)];
                    slots[i].setText(result[i]);
                }

                int payout = calculatePayout(result);
                balance += payout;
                updateBalanceLabel();
                showResultMessage(payout);
            }
        });

        spinTimer.start();
    }

    private int calculatePayout(String[] r) {
        if (r[0].equals(r[1]) && r[1].equals(r[2])) {
            return 50;
        } else if (r[0].equals(r[1]) || r[1].equals(r[2]) || r[0].equals(r[2])) {
            return 20;
        }
        return 0;
    }

    private void showResultMessage(int payout) {
        if (payout == 50) {
            resultLabel.setText("🎉 JACKPOT! You won $50!");
        } else if (payout == 20) {
            resultLabel.setText("💰 You won $20!");
        } else {
            resultLabel.setText("😞 No win. Try again!");
        }
    }
}
