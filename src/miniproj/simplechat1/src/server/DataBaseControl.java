package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseControl {
    private Connection conn;

    public DataBaseControl() {
        conn = connectToDB();
    }

    protected static Connection connectToDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver definition succeed");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver definition failed");
            ex.printStackTrace();
            return null;
        }

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/biteme?serverTimezone=IST","root", "nuthav");
            System.out.println("SQL connection succeed");
            return conn;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return null;
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            return stmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public String getOrderTable() {
        String query = "SELECT * FROM `Order`";
        ResultSet rs = executeQuery(query);
        StringBuilder result = new StringBuilder();

        try {
            if (rs != null) {
                while (rs.next()) {
                    String restaurant = rs.getString("Restaurant");
                    int orderNumber = rs.getInt("Order_number");
                    int totalPrice = rs.getInt("Total_price");
                    String orderAddress = rs.getString("Order_address");
                    int orderListNumber = rs.getInt("Order_list_number");

                    result.append("Restaurant: ").append(restaurant).append("\n")
                          .append("Order Number: ").append(orderNumber).append("\n")
                          .append("Total Price: ").append(totalPrice).append("\n")
                          .append("Order Address: ").append(orderAddress).append("\n")
                          .append("Order List Number: ").append(orderListNumber).append("\n")
                          .append("------------------------\n");
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }

        return result.toString();
    }

    public boolean updateOrder(int orderNumber, int totalPrice, String orderAddress) {
        String query = "UPDATE `Order` SET `Total_price` = ?, `Order_address` = ? WHERE `Order_number` = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, totalPrice);
            stmt.setString(2, orderAddress);
            stmt.setInt(3, orderNumber);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            ex.printStackTrace();
            return false;
        }
    }
}
