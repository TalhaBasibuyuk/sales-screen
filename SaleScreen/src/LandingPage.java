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

        setBackground(new Color(60, 63, 65));

        JLabel title = new JLabel("Sipariş Sistemine Hoş Geldiniz");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(60, 63, 65));
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
        footer.setForeground(Color.LIGHT_GRAY);
        add(footer, BorderLayout.SOUTH);

        button1.addActionListener(this);
        button2.addActionListener(this);
    }

    private void setupButton(JButton button) {
        button.setBackground(new Color(102, 205, 170));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(250, 50));
        button.setFocusable(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(72, 209, 204));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(102, 205, 170));
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            cardLayout.show(parentPanel, "CustomerInfoPage");
        }
        if (e.getSource() == button2) {
            cardLayout.show(parentPanel, "RefundPage");
        }
    }
}
