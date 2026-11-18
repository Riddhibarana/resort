package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminPaymentDAO {

    // ================= DISPLAY ALL PAYMENTS =================

    public void displayAllPayments() {

        String query =
                "SELECT p.paymentId, c.name, p.customerId, p.bookingId, " +
                        "p.roomCharges, p.restaurantCharges, p.gameCharges, " +
                        "p.subTotal, p.gst, p.totalAmount, " +
                        "p.paymentMethod, p.paymentDate, p.paymentStatus " +
                        "FROM payment p " +
                        "JOIN customer c ON p.customerId = c.customerId " +
                        "ORDER BY p.paymentId";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("\n======================================");
                System.out.println("Payment ID          : " + rs.getInt("paymentId"));
                System.out.println("Customer Name       : " + rs.getString("name"));
                System.out.println("Customer ID         : " + rs.getInt("customerId"));
                System.out.println("Booking ID          : " + rs.getInt("bookingId"));
                System.out.println("--------------------------------------");
                System.out.println("Room Charges        : ₹" + rs.getDouble("roomCharges"));
                System.out.println("Restaurant Charges  : ₹" + rs.getDouble("restaurantCharges"));
                System.out.println("Game Charges        : ₹" + rs.getDouble("gameCharges"));
                System.out.println("Sub Total           : ₹" + rs.getDouble("subTotal"));
                System.out.println("GST                 : ₹" + rs.getDouble("gst"));
                System.out.println("Grand Total         : ₹" + rs.getDouble("totalAmount"));
                System.out.println("Payment Method      : " + rs.getString("paymentMethod"));
                System.out.println("Payment Date        : " + rs.getDate("paymentDate"));
                System.out.println("Payment Status      : " + rs.getString("paymentStatus"));
                System.out.println("======================================");

            }

            if (!found) {

                System.out.println("No Payment Records Found.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    // ================= SEARCH PAYMENT =================

    public void searchPayment(int paymentId) {

        String query =
                "SELECT p.paymentId, c.name, p.customerId, p.bookingId, " +
                        "p.roomCharges, p.restaurantCharges, p.gameCharges, " +
                        "p.subTotal, p.gst, p.totalAmount, " +
                        "p.paymentMethod, p.paymentDate, p.paymentStatus " +
                        "FROM payment p " +
                        "JOIN customer c ON p.customerId = c.customerId " +
                        "WHERE p.paymentId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, paymentId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\n======================================");
                System.out.println("Payment ID          : " + rs.getInt("paymentId"));
                System.out.println("Customer Name       : " + rs.getString("name"));
                System.out.println("Customer ID         : " + rs.getInt("customerId"));
                System.out.println("Booking ID          : " + rs.getInt("bookingId"));
                System.out.println("--------------------------------------");
                System.out.println("Room Charges        : ₹" + rs.getDouble("roomCharges"));
                System.out.println("Restaurant Charges  : ₹" + rs.getDouble("restaurantCharges"));
                System.out.println("Game Charges        : ₹" + rs.getDouble("gameCharges"));
                System.out.println("Sub Total           : ₹" + rs.getDouble("subTotal"));
                System.out.println("GST                 : ₹" + rs.getDouble("gst"));
                System.out.println("Grand Total         : ₹" + rs.getDouble("totalAmount"));
                System.out.println("Payment Method      : " + rs.getString("paymentMethod"));
                System.out.println("Payment Date        : " + rs.getDate("paymentDate"));
                System.out.println("Payment Status      : " + rs.getString("paymentStatus"));
                System.out.println("======================================");

            } else {

                System.out.println("Payment Not Found.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    // ================= DELETE PAYMENT =================

    public void deletePayment(int paymentId) {

        String query =
                "DELETE FROM payment WHERE paymentId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, paymentId);

            int rows = ps.executeUpdate();

            if (rows > 0)
            {
                System.out.println("Payment Deleted Successfully.");


            } else {

                System.out.println("Payment ID Not Found.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
