import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingPage extends JPanel implements ActionListener {
    private final JButton button1 = new JButton("Yeni sipariş");
    private final JButton button2 = new JButton("Sipariş sorgulama");
    private final CardLayout cardLayout;
    private final JPanel parentPanel;

    public LandingPage(CardLayout cardLayout, JPanel parentPanel) {
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;

        setLayout(new BorderLayout());

        // Gradient background for the landing page
        setBackground(new Color(60, 63, 65)); // Dark background color

        JLabel title = new JLabel("Sipariş Sistemine Hoş Geldiniz");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32)); // Large, modern font
        title.setForeground(Color.WHITE); // White text for contrast
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(60, 63, 65)); // Match the background color
        GridBagConstraints gbc = new GridBagConstraints();

        setupButton(button1);
        setupButton(button2);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(30, 0, 30, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(button1, gbc);

        gbc.gridy = 1;
        buttonPanel.add(button2, gbc);

        add(buttonPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel("© 2024 Sipariş Yönetimi Inc.");
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footer.setForeground(Color.LIGHT_GRAY); // Lighter text to make it subtle
        add(footer, BorderLayout.SOUTH);

        button1.addActionListener(this);
        button2.addActionListener(this);
    }

    private void setupButton(JButton button) {
        button.setBackground(new Color(102, 205, 170)); // Medium aquamarine color
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(250, 50));
        button.setFocusable(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(72, 209, 204)); // Change color on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(102, 205, 170)); // Original color
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            // Transition to CustomerInfoPage panel
            // Handle navigation logic here
            //System.out.println("Navigate to CustomerInfoPage");
            cardLayout.show(parentPanel, "CustomerInfoPage");
        }
        if (e.getSource() == button2) {
            // Transition to RefundPage panel
            // Handle navigation logic here
            //System.out.println("Navigate to RefundPage");
            cardLayout.show(parentPanel, "RefundPage");
        }
    }
}
