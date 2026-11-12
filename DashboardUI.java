import javax.swing.*;
import java.awt.*;

// The desktop window (built with Java Swing)
public class DashboardUI extends JFrame {

    private static final String RUPEE = "\u20B9";

    public DashboardUI(KPIEngine kpi) {
        setTitle("Executive Business & KPI Analytics Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen
        setLayout(new BorderLayout(10, 10));

        add(createHeader(kpi), BorderLayout.NORTH);
        add(createBody(kpi), BorderLayout.CENTER);
        add(createHealthBanner(kpi), BorderLayout.SOUTH);
    }

    // ---------- Zone 1: Header bar ----------
    private JPanel createHeader(KPIEngine kpi) {
        JPanel header = new JPanel(new GridLayout(3, 1));
        header.setBackground(new Color(15, 30, 60)); // dark navy
        header.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JLabel title = new JLabel("Executive Business & KPI Analytics Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        JLabel project = new JLabel("Project: Business Analytics & KPI Dashboard using Java");
        project.setFont(new Font("SansSerif", Font.PLAIN, 13));
        project.setForeground(new Color(190, 200, 220));

        JLabel status = new JLabel("Dataset loaded: " + kpi.getOrderCount() + " orders");
        status.setFont(new Font("SansSerif", Font.BOLD, 13));
        status.setForeground(new Color(120, 220, 150));

        header.add(title);
        header.add(project);
        header.add(status);
        return header;
    }

    // ---------- Zones 2 and 3: KPI cards + driver summary ----------
    private JPanel createBody(KPIEngine kpi) {
        JPanel body = new JPanel(new BorderLayout(10, 15));
        body.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Four KPI cards side by side
        JPanel cards = new JPanel(new GridLayout(1, 4, 15, 0));
        cards.setPreferredSize(new Dimension(0, 130));
        cards.add(createCard("Gross Revenue",
                RUPEE + String.format("%,.2f", kpi.getTotalRevenue()), new Color(30, 70, 190)));
        cards.add(createCard("Net Profit",
                RUPEE + String.format("%,.2f", kpi.getNetProfit()), new Color(20, 150, 90)));
        cards.add(createCard("Profit Margin",
                String.format("%.2f%%", kpi.getProfitMargin()), new Color(0, 150, 160)));
        cards.add(createCard("Average Order Value",
                RUPEE + String.format("%,.2f", kpi.getAverageOrderValue()), new Color(120, 60, 170)));

        // Drivers panel (light gray, framed)
        JPanel drivers = new JPanel(new GridLayout(4, 1, 0, 8));
        drivers.setBackground(new Color(240, 240, 240));
        drivers.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Category and Product Drivers"));
        drivers.setPreferredSize(new Dimension(480, 0));
        drivers.add(createDriverRow("Top Category:", kpi.getTopCategory()));
        drivers.add(createDriverRow("Top Product:", kpi.getTopProduct()));
        drivers.add(createDriverRow("Top Customer:", kpi.getTopCustomer()));
        drivers.add(createDriverRow("Valid Orders Processed:", String.valueOf(kpi.getOrderCount())));

        body.add(cards, BorderLayout.NORTH);
        body.add(drivers, BorderLayout.WEST);
        return body;
    }

    // Creates one rounded, colored KPI card
    private JPanel createCard(String name, String value, Color color) {
        JPanel card = new JPanel(new GridLayout(2, 1)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                super.paintComponent(g);
            }
        };
        card.setOpaque(false); // so the rounded corners show
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        valueLabel.setForeground(Color.WHITE);

        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameLabel.setForeground(Color.WHITE);

        card.add(valueLabel);
        card.add(nameLabel);
        return card;
    }

    private JPanel createDriverRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(240, 240, 240));
        row.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JLabel left = new JLabel(label);
        left.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JLabel right = new JLabel(value);
        right.setFont(new Font("SansSerif", Font.BOLD, 15));

        row.add(left, BorderLayout.WEST);
        row.add(right, BorderLayout.EAST);
        return row;
    }

    // ---------- Zone 4: Financial health banner ----------
    private JPanel createHealthBanner(KPIEngine kpi) {
        JPanel banner = new JPanel(new BorderLayout());
        banner.setPreferredSize(new Dimension(0, 70));

        JLabel text = new JLabel("", SwingConstants.CENTER);
        text.setFont(new Font("SansSerif", Font.BOLD, 18));
        text.setForeground(Color.WHITE);

        // Color and message depend on the margin threshold (20%)
        if (kpi.getProfitMargin() >= 20) {
            banner.setBackground(new Color(34, 120, 60)); // forest green
            text.setText("Financial Health: OPTIMAL (Healthy Gross Margin > 20%)");
        } else {
            banner.setBackground(new Color(240, 100, 90)); // coral red
            text.setText("Financial Health: WARNING (Margin Erosion Risk Detected - Review Cost of Goods Sold)");
        }

        banner.add(text, BorderLayout.CENTER);
        return banner;
    }
}
