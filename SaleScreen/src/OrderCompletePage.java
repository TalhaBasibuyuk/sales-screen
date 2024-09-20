import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class OrderCompletePage extends JPanel implements ActionListener, DataChangeListener {
    private CardLayout cardLayout;
    private JPanel parentPanel;

    private JButton homeButton;
    private JButton newOrderButton;
    private JButton emailReceiptButton;
    private GmailHandler emailHandler;
    private String emailAddress;
    private ProductSelectionPage psPage;

    public OrderCompletePage(CardLayout cardLayout, JPanel parentPanel, ProductSelectionPage productSelectionPage) throws Exception {
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;
        this.emailHandler = new GmailHandler();
        this.psPage = productSelectionPage;

        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JLabel titleLabel = new JLabel("Siparişiniz Tamamlandı", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(40, 40, 40));
        add(titleLabel, gbc);

        JLabel messageLabel = new JLabel("Teşekkür ederiz! Siparişiniz başarıyla alındı.", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        messageLabel.setForeground(new Color(80, 80, 80));
        gbc.gridy++;
        add(messageLabel, gbc);

        JLabel infoLabel = new JLabel("placeholder", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoLabel.setForeground(new Color(80, 80, 80));
        gbc.gridy++;
        add(infoLabel, gbc);

        homeButton = new JButton("Ana Sayfa");
        newOrderButton = new JButton("Yeni Sipariş");
        emailReceiptButton = new JButton("Sipariş Dökümünü Email Gönder");

        {homeButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        homeButton.setBackground(new Color(0x003366));
        homeButton.setForeground(Color.WHITE);
        homeButton.setOpaque(true);
        homeButton.setFocusable(false);
        homeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        newOrderButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        newOrderButton.setBackground(new Color(0x003366));
        newOrderButton.setForeground(Color.WHITE);
        newOrderButton.setOpaque(true);
        newOrderButton.setFocusable(false);
        newOrderButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        emailReceiptButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        emailReceiptButton.setBackground(new Color(0x003366));
        emailReceiptButton.setForeground(Color.WHITE);
        emailReceiptButton.setOpaque(true);
        emailReceiptButton.setFocusable(false);
        emailReceiptButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));}

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(homeButton);
        buttonPanel.add(newOrderButton);
        buttonPanel.add(emailReceiptButton);

        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(buttonPanel, gbc);

        homeButton.addActionListener(this);
        newOrderButton.addActionListener(this);
        emailReceiptButton.addActionListener(this);


        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                infoLabel.setText(String.format("Sipariş takip numaranız: %d", psPage.getOrder_id()));
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source == homeButton) {
            cardLayout.show(parentPanel, "LandingPage");
        } else if (source == newOrderButton) {
            cardLayout.show(parentPanel, "ProductSelectionPage");
        } else if (source == emailReceiptButton) {

            try {
                emailHandler.sendMail(emailAddress, "Test Email",
                        """
                                <!DOCTYPE html>
                                <html lang="en">
                                <head>
                                    <meta charset="UTF-8">
                                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                    <title>Important Notice</title>
                                </head>
                                <body style="font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; text-align: center;">
                                    <table cellpadding="0" cellspacing="0" border="0" style="width: 100%; height: 100vh; background-color: #f4f4f4; padding: 0; margin: 0;">
                                        <tr>
                                            <td align="center" valign="middle">
                                                <table cellpadding="20" cellspacing="0" border="0" style="background-color: #fff; border: 2px solid #ff0000; border-radius: 8px; max-width: 600px; width: 100%; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">
                                                    <tr>
                                                        <td style="text-align: center;">
                                                            <!-- English Section -->
                                                            <div>
                                                                <h1 style="color: #ff0000; font-size: 24px; margin: 0;">IMPORTANT NOTICE</h1>
                                                                <p style="font-size: 16px; line-height: 1.5; color: #333; margin: 15px 0;">This email is part of a fictional scenario and is not intended for real communication. If you received this email in error, please disregard it.</p>
                                                                <p style="font-size: 16px; line-height: 1.5; color: #333; margin: 15px 0;">If you are not expecting this message or if it seems out of place, it is a test email and not related to any real service or offer. We apologize for any confusion this may cause.</p>
                                                            </div>
                                                            <!-- Turkish Section in English Characters -->
                                                            <div>
                                                                <h1 style="color: #ff0000; font-size: 24px; margin: 0;">ONEMLI BILDIRIM</h1>
                                                                <p style="font-size: 16px; line-height: 1.5; color: #333; margin: 15px 0;">Bu e-posta kurgusal bir senaryonun parcasidir ve gercek iletisim amaci tasimamaktadir. Bu e-postayi yanlislikla aldiysaniz, lutfen dikkate almayin.</p>
                                                                <p style="font-size: 16px; line-height: 1.5; color: #333; margin: 15px 0;">Bu mesaji beklemiyorsaniz veya yerinde gorunmuyorsa, bu bir test e-postasidir ve herhangi bir gercek hizmet veya teklif ile ilgili degildir. Olusabilecek karisiklik icin ozur dileriz.</p>
                                                            </div>
                                                            <!-- Order Tracking Number -->
                                                            <p style="font-size: 16px; line-height: 1.5; color: #333; margin: 15px 0;"> <strong> """
                                                            + String.format("Siparis takip numaraniz: %d", psPage.getOrder_id())
                                                            + """
                                                          </strong></p>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </body>
                                </html>
                                """);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void onDataChange(String newData) {
        setEmailAddress(newData);
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
