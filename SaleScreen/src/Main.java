import javax.swing.*;
import java.awt.*;

public class Main {
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the frame
            JFrame frame = new JFrame("Order System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600); // Adjust size as needed
            frame.setLocationRelativeTo(null); // Center the frame

            // Create a panel with CardLayout
            cardLayout = new CardLayout();
            cardPanel = new JPanel(cardLayout);

            // Create instances of the pages
            LandingPage landingPage = new LandingPage(cardLayout, cardPanel);
            RefundPage refundPage = new RefundPage(cardLayout, cardPanel);
            CustomerInfoPage customerInfoPage = new CustomerInfoPage(cardLayout, cardPanel);
            ProductSelectionPage productSelectionPage = new ProductSelectionPage(cardLayout, cardPanel, customerInfoPage);
            OrderCompletePage orderCompletePage;
            try {
                orderCompletePage = new OrderCompletePage(cardLayout, cardPanel, productSelectionPage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            customerInfoPage.setDataChangeListener(orderCompletePage);


            // Add pages to the CardLayout panel
            cardPanel.add(landingPage, "LandingPage");
            cardPanel.add(customerInfoPage, "CustomerInfoPage");
            cardPanel.add(productSelectionPage, "ProductSelectionPage");
            cardPanel.add(orderCompletePage, "OrderCompletePage");
            cardPanel.add(refundPage, "RefundPage");

            // Show the first page initially
            cardLayout.show(cardPanel, "LandingPage");

            // Add the cardPanel to the frame
            frame.add(cardPanel);

            // Display the frame
            frame.setVisible(true);
        });
    }
}
