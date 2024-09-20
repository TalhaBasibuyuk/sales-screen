import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RefundPage extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel parentPanel;
    private final JTextField emailField;
    private final JTextField orderIdField;
    private final JButton refundButton;
    private final JLabel orderDetailsLabel;

    public RefundPage(CardLayout cardLayout, JPanel parentPanel) {
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        setBackground(new Color(240, 248, 255));

        JLabel emailLabel = new JLabel("Email Adresiniz:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        emailLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        add(emailLabel, gbc);

        emailField = new JTextField(25);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        add(emailField, gbc);

        JLabel orderIdLabel = new JLabel("Sipariş Takip Numaranız:");
        orderIdLabel.setFont(new Font("Arial", Font.BOLD, 16));
        orderIdLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        add(orderIdLabel, gbc);

        orderIdField = new JTextField(25);
        orderIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        orderIdField.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        add(orderIdField, gbc);

        JButton submitButton = new JButton("Kontrol Et");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(30, 144, 255));
        submitButton.setOpaque(true);
        submitButton.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 139), 2));
        submitButton.setPreferredSize(new Dimension(200, 50));
        submitButton.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 0;
        add(submitButton, gbc);

        orderDetailsLabel = new JLabel();
        orderDetailsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        orderDetailsLabel.setForeground(new Color(0, 0, 139));
        orderDetailsLabel.setVisible(false);
        gbc.gridy = 3;
        add(orderDetailsLabel, gbc);

        refundButton = new JButton("İade Et");
        refundButton.setFont(new Font("Arial", Font.BOLD, 16));
        refundButton.setForeground(Color.WHITE);
        refundButton.setBackground(new Color(255, 69, 0));
        refundButton.setOpaque(true);
        refundButton.setBorder(BorderFactory.createLineBorder(new Color(139, 0, 0), 2));
        refundButton.setPreferredSize(new Dimension(200, 50));
        refundButton.setFocusable(false);
        refundButton.setVisible(false);
        gbc.gridy = 4;
        add(refundButton, gbc);

        JButton homeButton = new JButton("Ana Sayfaya Dön");
        homeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        homeButton.setForeground(new Color(255, 105, 180));
        homeButton.setFocusPainted(false);
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setPreferredSize(new Dimension(50, 30));
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        add(homeButton, gbc);

        submitButton.addActionListener(e -> {
            String response = checkOrder(emailField.getText(), Integer.parseInt(orderIdField.getText()));
            if(response.equals("NO")){
                orderDetailsLabel.setVisible(false);
                refundButton.setVisible(false);
            }else{
                orderDetailsLabel.setText("<html><b>Sipariş Detayları:</b> "+ response +"</html>");
                orderDetailsLabel.setVisible(true);
                refundButton.setVisible(true);
            }
            revalidate();
            repaint();
        });

        refundButton.addActionListener(e -> {
            String response = refundOrder(emailField.getText(), Integer.parseInt(orderIdField.getText()));
            orderDetailsLabel.setText("<html><b>Sipariş Detayları:</b> "+ response +"</html>");
        });

        homeButton.addActionListener(e -> {
            cardLayout.show(parentPanel, "LandingPage");
            orderDetailsLabel.setVisible(false);
            refundButton.setVisible(false);
        });
    }

    private String checkOrder(String mail, int order_id){
        String urlString = "http://localhost:8080/order/" + order_id;

        try {
            HttpURLConnection apiConnection = fetchApiResponse(urlString, "GET");

            if(apiConnection.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
            }

            String jsonResponse = readApiResponse(apiConnection);

            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);

            JSONObject customer = (JSONObject) resultsJsonObj.get("customer");
            JSONObject order = (JSONObject) resultsJsonObj.get("order");

            String email = (String) customer.get("emailAddress");
            String name = (String) customer.get("fullName");
            String items = (String) order.get("item_description");
            String price = (String) order.get("price_paid");
            String status = (String) order.get("status");

            String orderDescription = String.format(
                    "Müşteri: %s (E-posta: %s) - Sipariş: %s, Ödenen Tutar: ₺%s, Sipariş Durumu: %s",
                    name, email, items, price, status);

            if(mail.equals(email)){
                return orderDescription;
            } else {
                return "NO";
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return "NO";
        }
    }
    private String refundOrder(String mail, int order_id){
        String urlString = "http://localhost:8080/orders/put/" + order_id;


        try {
            HttpURLConnection apiConnection = fetchApiResponse(urlString, "PUT");

            if (apiConnection.getResponseCode() != 200) {
                return "Error: Could not connect to API";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return checkOrder(mail, order_id);
    }

    private String readApiResponse(HttpURLConnection apiConnection) {
        try {
            StringBuilder resultsJson = new StringBuilder();
            Scanner scanner = new Scanner(apiConnection.getInputStream());

            while (scanner.hasNext()){
                resultsJson.append(scanner.nextLine());
            }

            scanner.close();
            return resultsJson.toString();

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private HttpURLConnection fetchApiResponse(String urlString, String requestMethod){
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
