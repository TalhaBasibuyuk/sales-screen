import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerInfoPage extends JPanel implements ActionListener {
    private DataChangeListener dataChangeListener;
    private JTextField textField1 = new JTextField("Adınız Soyadınız");
    private JTextField textField2 = new JTextField("Telefon numaranız");
    private JTextField textField3 = new JTextField("Email adresiniz");
    private JButton button1 = new JButton("Sonraki");
    private JButton button2 = new JButton("Önceki");
    private final CardLayout cardLayout;
    private final JPanel parentPanel;

    public CustomerInfoPage(CardLayout cardLayout, JPanel parentPanel) {
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;

        setLayout(new BorderLayout());

        // Title styling
        JLabel title = new JLabel("Müşteri Bilgileri");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(0x003366)); // Dark blue color
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20)); // Padding around title

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0xEAF2F8)); // Light blue background
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS)); // Vertical layout
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around panel


        // Adding components to input panel with styling
        addStyledTextField(textField1, "Adınız Soyadınız");
        addStyledTextField(textField2, "Telefon numaranız");
        addStyledTextField(textField3, "Email adresiniz");

        // Panel for buttons with horizontal layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0)); // 20px gap between buttons

        {
        button1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button1.setBackground(new Color(0x003366)); // Dark blue button
        button1.setForeground(Color.WHITE);
        button1.setOpaque(true);
        button1.setFocusable(false);
        button1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding inside button
        button1.addActionListener(this); // Adding action listener for button

        button2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button2.setBackground(new Color(0x003366)); // Dark blue button
        button2.setForeground(Color.WHITE);
        button2.setOpaque(true);
        button2.setFocusable(false);
        button2.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding inside button
        button2.addActionListener(this); // Adding action listener for button

        buttonPanel.add(button2); // Add first button
        buttonPanel.add(button1); // Add second button
        }

        inputPanel.add(textField1);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between components
        inputPanel.add(textField2);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(textField3);


        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPanel.add(buttonPanel); // Add button panel to the input panel

        add(inputPanel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel("© 2024 Sipariş Yönetimi Inc.");
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footer.setForeground(Color.LIGHT_GRAY); // Lighter text to make it subtle
        add(footer, BorderLayout.SOUTH);
    }

    private void addStyledTextField(JTextField textField, String placeholder) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textField.setForeground(Color.GRAY);  // Set the color of the placeholder
        textField.setText(placeholder);  // Set the initial placeholder text
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7)), // Border color
                BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Padding inside text field

        // Add focus listener to handle the placeholder effect
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");  // Clear the placeholder when focused
                    textField.setForeground(Color.BLACK);  // Set text color to black when typing
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);  // Set color back to gray when losing focus
                    textField.setText(placeholder);  // Re-apply the placeholder text if field is empty
                }
            }
        });
    }
    public String getName() {
        return textField1.getText();
    }
    public String getPhone() {
        return textField2.getText();
    }
    public String getEmail() {
        return textField3.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
                notifyDataChanged(textField3.getText()); // Send email address to OrderCompletePage
                cardLayout.show(parentPanel, "ProductSelectionPage");
        }
        if (e.getSource() == button2){
            cardLayout.show(parentPanel, "LandingPage");
        }
    }

    public void setDataChangeListener(DataChangeListener listener) {
        this.dataChangeListener = listener;
    }
    private void notifyDataChanged(String newData) {
        if (dataChangeListener != null) {
            dataChangeListener.onDataChange(newData);
        }
    }
}

