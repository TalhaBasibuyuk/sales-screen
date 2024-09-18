import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProductSelectionPage extends JPanel {

    private final JComboBox<String> comboBox1;
    private final JComboBox<String> comboBox2;
    private final JComboBox<String> comboBox3;
    private final JLabel priceLabel;
    private final JLabel exchangeRateLabel;
    private final JButton calculateButton;
    private final JButton previousPageButton;
    private final JButton confirmPurchaseButton;
    private final CardLayout cardLayout;
    private final JPanel parentPanel;
    private final CustomerInfoPage ciPage;
    private double currentPrice;
    private int order_id;
    private static final Map<String, String[]> comboBox2Items = new HashMap<>();
    private static final Map<String, String[]> comboBox3Items = new HashMap<>();
    private static final Map<String, Double> itemPrices = new HashMap<>();

    static {
        // Initialize comboBox2 items based on comboBox1 selection
        comboBox2Items.put("Dizüstü Bilgisayar", new String[]{"Dell XPS 13", "MacBook Air", "HP Spectre x360", "Lenovo ThinkPad X1"});
        comboBox2Items.put("Telefon", new String[]{"iPhone 15", "Samsung Galaxy S24", "Google Pixel 9", "OnePlus 11"});
        comboBox2Items.put("Tablet", new String[]{"iPad Pro", "Samsung Galaxy Tab S9", "Microsoft Surface Pro 9", "Lenovo Tab P12"});

        // Initialize comboBox3 items based on comboBox2 selection
        comboBox3Items.put("Dell XPS 13", new String[]{"8GB RAM, 256GB SSD", "16GB RAM, 512GB SSD", "32GB RAM, 1TB SSD", "64GB RAM, 2TB SSD"});
        comboBox3Items.put("MacBook Air", new String[]{"8GB RAM, 256GB SSD", "16GB RAM, 512GB SSD", "32GB RAM, 1TB SSD", "64GB RAM, 2TB SSD"});
        comboBox3Items.put("HP Spectre x360", new String[]{"8GB RAM, 256GB SSD", "16GB RAM, 512GB SSD", "32GB RAM, 1TB SSD", "64GB RAM, 2TB SSD"});
        comboBox3Items.put("Lenovo ThinkPad X1", new String[]{"8GB RAM, 256GB SSD", "16GB RAM, 512GB SSD", "32GB RAM, 1TB SSD", "64GB RAM, 2TB SSD"});

        comboBox3Items.put("iPhone 15", new String[]{"128GB Storage", "256GB Storage", "512GB Storage", "1TB Storage"});
        comboBox3Items.put("Samsung Galaxy S24", new String[]{"128GB Storage", "256GB Storage", "512GB Storage", "1TB Storage"});
        comboBox3Items.put("Google Pixel 9", new String[]{"128GB Storage", "256GB Storage", "512GB Storage", "1TB Storage"});
        comboBox3Items.put("OnePlus 11", new String[]{"128GB Storage", "256GB Storage", "512GB Storage", "1TB Storage"});

        comboBox3Items.put("iPad Pro", new String[]{"128GB Storage", "256GB Storage", "512GB Storage", "1TB Storage"});
        comboBox3Items.put("Samsung Galaxy Tab S9", new String[]{"128GB Storage", "256GB Storage", "512GB Storage", "1TB Storage"});
        comboBox3Items.put("Microsoft Surface Pro 9", new String[]{"128GB Storage", "256GB Storage", "512GB Storage", "1TB Storage"});
        comboBox3Items.put("Lenovo Tab P12", new String[]{"128GB Storage", "256GB Storage", "512GB Storage", "1TB Storage"});

        // Laptops
        itemPrices.put("Dell XPS 13 - 8GB RAM, 256GB SSD", 1199.00);
        itemPrices.put("Dell XPS 13 - 16GB RAM, 512GB SSD", 1399.00);
        itemPrices.put("Dell XPS 13 - 32GB RAM, 1TB SSD", 1799.00);
        itemPrices.put("Dell XPS 13 - 64GB RAM, 2TB SSD", 2299.00);

        itemPrices.put("MacBook Air - 8GB RAM, 256GB SSD", 1299.00);
        itemPrices.put("MacBook Air - 16GB RAM, 512GB SSD", 1599.00);
        itemPrices.put("MacBook Air - 32GB RAM, 1TB SSD", 1999.00);
        itemPrices.put("MacBook Air - 64GB RAM, 2TB SSD", 2499.00);

        itemPrices.put("HP Spectre x360 - 8GB RAM, 256GB SSD", 1199.00);
        itemPrices.put("HP Spectre x360 - 16GB RAM, 512GB SSD", 1399.00);
        itemPrices.put("HP Spectre x360 - 32GB RAM, 1TB SSD", 1799.00);
        itemPrices.put("HP Spectre x360 - 64GB RAM, 2TB SSD", 2299.00);

        itemPrices.put("Lenovo ThinkPad X1 - 8GB RAM, 256GB SSD", 1099.00);
        itemPrices.put("Lenovo ThinkPad X1 - 16GB RAM, 512GB SSD", 1399.00);
        itemPrices.put("Lenovo ThinkPad X1 - 32GB RAM, 1TB SSD", 1699.00);
        itemPrices.put("Lenovo ThinkPad X1 - 64GB RAM, 2TB SSD", 2199.00);

        // Smartphones
        itemPrices.put("iPhone 15 - 128GB Storage", 799.00);
        itemPrices.put("iPhone 15 - 256GB Storage", 899.00);
        itemPrices.put("iPhone 15 - 512GB Storage", 1099.00);
        itemPrices.put("iPhone 15 - 1TB Storage", 1399.00);

        itemPrices.put("Samsung Galaxy S24 - 128GB Storage", 749.00);
        itemPrices.put("Samsung Galaxy S24 - 256GB Storage", 849.00);
        itemPrices.put("Samsung Galaxy S24 - 512GB Storage", 1049.00);
        itemPrices.put("Samsung Galaxy S24 - 1TB Storage", 1349.00);

        itemPrices.put("Google Pixel 9 - 128GB Storage", 699.00);
        itemPrices.put("Google Pixel 9 - 256GB Storage", 799.00);
        itemPrices.put("Google Pixel 9 - 512GB Storage", 999.00);
        itemPrices.put("Google Pixel 9 - 1TB Storage", 1299.00);

        itemPrices.put("OnePlus 11 - 128GB Storage", 679.00);
        itemPrices.put("OnePlus 11 - 256GB Storage", 779.00);
        itemPrices.put("OnePlus 11 - 512GB Storage", 979.00);
        itemPrices.put("OnePlus 11 - 1TB Storage", 1279.00);

        // Tablets
        itemPrices.put("iPad Pro - 128GB Storage", 899.00);
        itemPrices.put("iPad Pro - 256GB Storage", 1099.00);
        itemPrices.put("iPad Pro - 512GB Storage", 1299.00);
        itemPrices.put("iPad Pro - 1TB Storage", 1599.00);

        itemPrices.put("Samsung Galaxy Tab S9 - 128GB Storage", 849.00);
        itemPrices.put("Samsung Galaxy Tab S9 - 256GB Storage", 1049.00);
        itemPrices.put("Samsung Galaxy Tab S9 - 512GB Storage", 1299.00);
        itemPrices.put("Samsung Galaxy Tab S9 - 1TB Storage", 1549.00);

        itemPrices.put("Microsoft Surface Pro 9 - 128GB Storage", 899.00);
        itemPrices.put("Microsoft Surface Pro 9 - 256GB Storage", 1099.00);
        itemPrices.put("Microsoft Surface Pro 9 - 512GB Storage", 1299.00);
        itemPrices.put("Microsoft Surface Pro 9 - 1TB Storage", 1599.00);

        itemPrices.put("Lenovo Tab P12 - 128GB Storage", 849.00);
        itemPrices.put("Lenovo Tab P12 - 256GB Storage", 1049.00);
        itemPrices.put("Lenovo Tab P12 - 512GB Storage", 1299.00);
        itemPrices.put("Lenovo Tab P12 - 1TB Storage", 1549.00);
    }

    public ProductSelectionPage(CardLayout cardLayout, JPanel parentPanel, CustomerInfoPage customerInfoPage) {
        this.ciPage = customerInfoPage;
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;
        currentPrice = 0;
        order_id = 0;

        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240)); // Modern light gray

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Adjusted padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Create a title label
        JLabel titleLabel = new JLabel("Ürün seçiniz");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Modern font
        titleLabel.setForeground(new Color(40, 40, 40)); // Dark Gray
        add(titleLabel, gbc);

        // Create and style JComboBoxes
        comboBox1 = createStyledComboBox(new String[]{"", "Dizüstü Bilgisayar", "Telefon", "Tablet"});
        comboBox2 = createStyledComboBox(new String[]{""});
        comboBox3 = createStyledComboBox(new String[]{""});

        // Create and style price label
        priceLabel = new JLabel("Fiyat: ₺0.00", SwingConstants.CENTER);
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 22)); // Modern font
        priceLabel.setForeground(new Color(0, 150, 136)); // Teal
        priceLabel.setBorder(new EmptyBorder(15, 0, 15, 0)); // Add padding

        // Add components to the panel
        gbc.gridy++;
        add(createLabeledPanel("Kategori:", comboBox1), gbc);

        gbc.gridy++;
        add(createLabeledPanel("Tür:", comboBox2), gbc);

        gbc.gridy++;
        add(createLabeledPanel("Detay:", comboBox3), gbc);

        // Add price label
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(priceLabel, gbc);

        // Create and add the calculate button
        calculateButton = new JButton("Fiyat Hesapla");
        calculateButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        calculateButton.setFocusable(false);
        gbc.gridy++;
        add(calculateButton, gbc);

        exchangeRateLabel = new JLabel("Döviz kuru: ???", SwingConstants.CENTER);
        exchangeRateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        exchangeRateLabel.setForeground(new Color(100, 100, 100)); // Gray color
        gbc.gridy++;
        add(exchangeRateLabel, gbc);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false); // Transparent background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Add padding
        {
        // Create previous page button
        previousPageButton = new JButton("Önceki Sayfa");
        previousPageButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        previousPageButton.setBackground(new Color(0x003366)); // Dark blue button
        previousPageButton.setForeground(Color.WHITE);
        previousPageButton.setOpaque(true);
        previousPageButton.setFocusable(false);
        previousPageButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding inside button

        // Create confirm purchase button
        confirmPurchaseButton = new JButton("Satın Al");
        confirmPurchaseButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        confirmPurchaseButton.setBackground(new Color(0x003366)); // Dark blue button
        confirmPurchaseButton.setForeground(Color.WHITE);
        confirmPurchaseButton.setOpaque(true);
        confirmPurchaseButton.setFocusable(false);
        confirmPurchaseButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding inside button

        // Add buttons to the buttonPanel
        buttonPanel.add(previousPageButton);
        buttonPanel.add(confirmPurchaseButton);
        }
        // Add buttonPanel to the main panel
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(buttonPanel, gbc);

        // Set up listeners for comboBoxes and button
        setUpListeners();
    }

    private JPanel createLabeledPanel(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false); // Transparent background
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Spacing around components

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Modern font
        label.setForeground(new Color(80, 80, 80)); // Gray text

        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Modern font
        comboBox.setForeground(Color.DARK_GRAY);
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1)); // Subtle border

        JPanel comboBoxPanel = new JPanel(new BorderLayout());
        comboBoxPanel.add(comboBox, BorderLayout.CENTER);
        comboBoxPanel.setPreferredSize(new Dimension(250, 30)); // Set preferred size for uniformity

        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(label, BorderLayout.WEST);
        labelPanel.setPreferredSize(new Dimension(150, 30)); // Adjust width to align labels

        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(comboBoxPanel, BorderLayout.CENTER);

        return panel;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Modern font
        comboBox.setForeground(Color.DARK_GRAY);
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1)); // Subtle border
        return comboBox;
    }

    private void setUpListeners() {
        comboBox1.addActionListener(e -> {
            String selected = (String) comboBox1.getSelectedItem();
            String[] items = comboBox2Items.getOrDefault(selected, new String[]{"Tür"});
            comboBox2.setModel(new DefaultComboBoxModel<>(items));
            comboBox2.setSelectedIndex(0); // Reset to default

            comboBox3.setModel(new DefaultComboBoxModel<>(new String[]{"Detay"}));
            comboBox3.setSelectedIndex(0); // Reset to default

            priceLabel.setText("Fiyat: ₺0.00");
            currentPrice = 0;
        });

        comboBox2.addActionListener(e -> {
            String selected = (String) comboBox2.getSelectedItem();
            String[] items = comboBox3Items.getOrDefault(selected, new String[]{"Detay"});
            comboBox3.setModel(new DefaultComboBoxModel<>(items));
            comboBox3.setSelectedIndex(0); // Reset to default
            priceLabel.setText("Fiyat: ₺0.00");
            currentPrice = 0;
        });
        comboBox3.addActionListener(e -> {
            priceLabel.setText("Fiyat: ₺0.00");
            currentPrice = 0;
        });


        // Button to calculate and display the price
        calculateButton.addActionListener(e -> {
            String selectedProduct = (String) comboBox2.getSelectedItem();
            String selectedDetail = (String) comboBox3.getSelectedItem();
            String key = selectedProduct + " - " + selectedDetail;
            Double price = itemPrices.getOrDefault(key, 0.00);
            if(price == 0){
                return;
            }

            String urlString = "https://hexarate.paikama.co/api/rates/latest/USD?target=TRY";
            double exchangeRate = 1;

            try {
                HttpURLConnection apiConnection = fetchApiResponse(urlString);

                // check response status
                if(apiConnection.getResponseCode() != 200){
                    System.out.println("Error: Could not connect to API");
                }//else System.out.println(apiConnection.getResponseCode());

                String jsonResponse = readApiResponse(apiConnection);

                JSONParser parser = new JSONParser();
                JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);
                JSONObject data = (JSONObject) resultsJsonObj.get("data");

                exchangeRate = (double) data.get("mid");

                exchangeRateLabel.setText(String.format("Döviz kuru: %.4f", exchangeRate));

            } catch (Exception exception) {
                exception.printStackTrace();
            }

            price *= exchangeRate;

            priceLabel.setText(String.format("Fiyat: ₺%.2f", price));
            currentPrice = price;
        });

        previousPageButton.addActionListener(e -> cardLayout.show(parentPanel, "CustomerInfoPage"));

        confirmPurchaseButton.addActionListener(e -> {
            String items = comboBox2.getSelectedItem() + " " + comboBox3.getSelectedItem();
            int new_order_id = OrderCompletion.completeOrder(ciPage.getName(), ciPage.getPhone(), ciPage.getEmail(),
             items, String.valueOf(currentPrice));
            order_id = new_order_id;
            System.out.println("suspicious " + order_id + new_order_id);
            if(order_id > 999){
                cardLayout.show(parentPanel, "OrderCompletePage");
            }

        });
    }

    public int getOrder_id() {
        return order_id;
    }
    private String readApiResponse(HttpURLConnection apiConnection) {
        try {

            StringBuilder resultsJson = new StringBuilder();

            Scanner scanner = new Scanner(apiConnection.getInputStream());

            while (scanner.hasNext()){
                resultsJson.append(scanner.nextLine());
            }

            scanner.close();

            return  resultsJson.toString();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    private HttpURLConnection fetchApiResponse(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
