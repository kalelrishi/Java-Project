import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// Reads orders.csv and converts each valid row into an Order object
public class DataLoader {

    public static ArrayList<Order> loadOrders(String filePath) {
        ArrayList<Order> orders = new ArrayList<>();
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip the header row
            lineNumber++;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue; // ignore blank lines
                }

                String[] parts = line.split(",");

                // Each row must have exactly 8 fields
                if (parts.length != 8) {
                    System.out.println("Skipped line " + lineNumber + ": expected 8 fields.");
                    continue;
                }

                try {
                    String orderId = parts[0].trim();
                    String customer = parts[1].trim();
                    String product = parts[2].trim();
                    String category = parts[3].trim();
                    int quantity = Integer.parseInt(parts[4].trim());
                    double sellingPrice = Double.parseDouble(parts[5].trim());
                    double costPrice = Double.parseDouble(parts[6].trim());
                    String date = parts[7].trim();

                    // Basic validation
                    if (quantity <= 0 || sellingPrice < 0 || costPrice < 0) {
                        System.out.println("Skipped line " + lineNumber + ": invalid numbers.");
                        continue;
                    }

                    orders.add(new Order(orderId, customer, product, category,
                                         quantity, sellingPrice, costPrice, date));

                } catch (NumberFormatException e) {
                    System.out.println("Skipped line " + lineNumber + ": number format error.");
                }
            }

        } catch (IOException e) {
            System.out.println("Could not read file '" + filePath + "': " + e.getMessage());
        }

        return orders;
    }
}
