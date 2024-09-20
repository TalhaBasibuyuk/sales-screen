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

        JLabel title = new JLabel("Müşteri Bilgileri");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(0x003366));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0xEAF2F8));
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addStyledTextField(textField1, "Adınız Soyadınız");
        addStyledTextField(textField2, "Telefon numaranız");
        addStyledTextField(textField3, "Email adresiniz");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        {
        button1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button1.setBackground(new Color(0x003366));
        button1.setForeground(Color.WHITE);
        button1.setOpaque(true);
        button1.setFocusable(false);
        button1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button1.addActionListener(this);

        button2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button2.setBackground(new Color(0x003366));
        button2.setForeground(Color.WHITE);
        button2.setOpaque(true);
        button2.setFocusable(false);
        button2.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button2.addActionListener(this);

        buttonPanel.add(button2);
        buttonPanel.add(button1);
        }

        inputPanel.add(textField1);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(textField2);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(textField3);

        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPanel.add(buttonPanel);

        add(inputPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel("© 2024 Sipariş Yönetimi Inc.");
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footer.setForeground(Color.LIGHT_GRAY);
        add(footer, BorderLayout.SOUTH);
    }

    private void addStyledTextField(JTextField textField, String placeholder) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));


        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
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
            if(!containsOnlyLetters(textField1.getText())){
                showError("Lütfen adınızı giriniz.");
            } else if (!containsOnlyNumbers(textField2.getText())) {
                showError("Lütfen geçerli bir telefon numarası giriniz.");
            } else if (!isValidEmail(textField3.getText())) {
                showError("Lütfen geçerli bir e-posta adresi giriniz.");
            }else {
                notifyDataChanged(textField3.getText());
                cardLayout.show(parentPanel, "ProductSelectionPage");
            }
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

    private boolean containsOnlyLetters(String str) {
        return str.matches("[a-zA-Z ']+");
    }

    private boolean containsOnlyNumbers(String str) {
        return str.matches("\\d+");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$");
    }
    private void showError(String message){
        JOptionPane.showMessageDialog(null, message, "Hata", JOptionPane.ERROR_MESSAGE);
    }
}

