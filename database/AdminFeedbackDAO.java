package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminFeedbackDAO {

    // ================= VIEW ALL FEEDBACK =================

    public void displayAllFeedback() {

        String query =
                "SELECT f.feedbackId, c.name, f.rating, f.comment, f.feedbackDate " +
                        "FROM feedback f " +
                        "JOIN customer c ON f.customerId = c.customerId " +
                        "ORDER BY f.feedbackId";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("\n====================================");
                System.out.println("Feedback ID   : " + rs.getInt("feedbackId"));
                System.out.println("Customer Name : " + rs.getString("name"));
                System.out.println("Rating        : " + rs.getInt("rating"));
                System.out.println("Comment       : " + rs.getString("comment"));
                System.out.println("Date          : " + rs.getDate("feedbackDate"));
                System.out.println("====================================");

            }

            if (!found) {

                System.out.println("No Feedback Found.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // ================= SEARCH FEEDBACK =================

    public void searchFeedback(int feedbackId) {

        String query =
                "SELECT f.feedbackId, c.name, f.rating, f.comment, f.feedbackDate " +
                        "FROM feedback f " +
                        "JOIN customer c ON f.customerId = c.customerId " +
                        "WHERE f.feedbackId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, feedbackId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\n====================================");
                System.out.println("Feedback ID   : " + rs.getInt("feedbackId"));
                System.out.println("Customer Name : " + rs.getString("name"));
                System.out.println("Rating        : " + rs.getInt("rating"));
                System.out.println("Comment       : " + rs.getString("comment"));
                System.out.println("Date          : " + rs.getDate("feedbackDate"));
                System.out.println("====================================");

            } else {

                System.out.println("Feedback Not Found.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // ================= DELETE FEEDBACK =================

    // ================= DELETE FEEDBACK =================

    public boolean deleteFeedback(int feedbackId) {

        String query =
                "DELETE FROM feedback WHERE feedbackId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, feedbackId);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }
}