import javax.swing.*;
import java.util.ArrayList;

// Entry point: load data -> calculate KPIs -> show dashboard
public class Main {
    public static void main(String[] args) {
        ArrayList<Order> orders = DataLoader.loadOrders("orders.csv");

        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No valid orders found. Make sure orders.csv is in the same folder you run the program from.",
                    "Data Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        KPIEngine kpi = new KPIEngine(orders);

        // Swing windows should be created on the event dispatch thread
        SwingUtilities.invokeLater(() -> new DashboardUI(kpi).setVisible(true));
    }
}
