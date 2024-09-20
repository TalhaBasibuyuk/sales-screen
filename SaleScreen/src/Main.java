import javax.swing.*;
import java.awt.*;

public class Main {
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Order System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            cardLayout = new CardLayout();
            cardPanel = new JPanel(cardLayout);

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

            cardPanel.add(landingPage, "LandingPage");
            cardPanel.add(customerInfoPage, "CustomerInfoPage");
            cardPanel.add(productSelectionPage, "ProductSelectionPage");
            cardPanel.add(orderCompletePage, "OrderCompletePage");
            cardPanel.add(refundPage, "RefundPage");

            cardLayout.show(cardPanel, "LandingPage");

            frame.add(cardPanel);

            frame.setVisible(true);
        });
    }
}
