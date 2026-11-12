// Model class: represents one completed sale (one CSV row)
public class Order {
    private String orderId;
    private String customerName;
    private String productName;
    private String category;
    private int quantity;
    private double sellingPrice;
    private double costPrice;
    private String orderDate;

    public Order(String orderId, String customerName, String productName, String category,
                 int quantity, double sellingPrice, double costPrice, String orderDate) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.costPrice = costPrice;
        this.orderDate = orderDate;
    }

    public String getOrderId()      { return orderId; }
    public String getCustomerName() { return customerName; }
    public String getProductName()  { return productName; }
    public String getCategory()     { return category; }
    public int getQuantity()        { return quantity; }
    public double getSellingPrice() { return sellingPrice; }
    public double getCostPrice()    { return costPrice; }
    public String getOrderDate()    { return orderDate; }

    // Gross Revenue = Selling Price x Quantity
    public double getGrossRevenue() {
        return sellingPrice * quantity;
    }

    // Product Cost = Cost Price x Quantity
    public double getProductCost() {
        return costPrice * quantity;
    }

    // Net Profit = Gross Revenue - Product Cost
    public double getNetProfit() {
        return getGrossRevenue() - getProductCost();
    }
}
