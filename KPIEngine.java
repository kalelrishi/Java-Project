import java.util.ArrayList;
import java.util.HashMap;

// Calculates all business metrics from the list of orders
public class KPIEngine {
    private double totalRevenue = 0;
    private double totalCost = 0;
    private int orderCount;

    private HashMap<String, Double> categoryRevenue = new HashMap<>();
    private HashMap<String, Double> productRevenue = new HashMap<>();
    private HashMap<String, Double> customerRevenue = new HashMap<>();

    public KPIEngine(ArrayList<Order> orders) {
        orderCount = orders.size();

        for (Order o : orders) {
            totalRevenue += o.getGrossRevenue();
            totalCost += o.getProductCost();

            // Add this order's revenue into each HashMap (create the key if new)
            addToMap(categoryRevenue, o.getCategory(), o.getGrossRevenue());
            addToMap(productRevenue, o.getProductName(), o.getGrossRevenue());
            addToMap(customerRevenue, o.getCustomerName(), o.getGrossRevenue());
        }
    }

    private void addToMap(HashMap<String, Double> map, String key, double value) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + value);
        } else {
            map.put(key, value);
        }
    }

    // Finds the key with the highest value in a map
    private String findTopKey(HashMap<String, Double> map) {
        String topKey = "N/A";
        double max = -1;
        for (String key : map.keySet()) {
            if (map.get(key) > max) {
                max = map.get(key);
                topKey = key;
            }
        }
        return topKey;
    }

    public double getTotalRevenue() { return totalRevenue; }

    public double getNetProfit() { return totalRevenue - totalCost; }

    public double getProfitMargin() {
        if (totalRevenue == 0) return 0;
        return (getNetProfit() / totalRevenue) * 100;
    }

    public double getAverageOrderValue() {
        if (orderCount == 0) return 0;
        return totalRevenue / orderCount;
    }

    public int getOrderCount()      { return orderCount; }
    public String getTopCategory()  { return findTopKey(categoryRevenue); }
    public String getTopProduct()   { return findTopKey(productRevenue); }
    public String getTopCustomer()  { return findTopKey(customerRevenue); }
}
