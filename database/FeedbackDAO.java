package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Feedback;

public class FeedbackDAO {

    // ================= ADD FEEDBACK =================

    public void addFeedback(Feedback feedback) {

        String query =
                "INSERT INTO feedback(customerId, rating, comment, feedbackDate) VALUES(?,?,?,?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, feedback.getCustomerId());
            ps.setInt(2, feedback.getRating());
            ps.setString(3, feedback.getComment());
            ps.setDate(4, java.sql.Date.valueOf(feedback.getFeedbackDate()));

            int rows = ps.executeUpdate();

            if(rows > 0) {

                System.out.println();
                System.out.println("=================================");
                System.out.println(" Feedback Submitted Successfully ");
                System.out.println("=================================");

            } else {

                System.out.println("Feedback Submission Failed.");

            }

        } catch(Exception e) {

            e.printStackTrace();

        }

    }

    // ================= CHECK CUSTOMER =================

    public boolean checkCustomerId(int customerId) {

        String query = "SELECT customerId FROM customer WHERE customerId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            return ps.executeQuery().next();

        } catch(Exception e) {

            e.printStackTrace();

        }

        return false;

    }
    // ================= GET CUSTOMER ID BY NAME =================

    public int getCustomerIdByName(String name) {

        String query = "SELECT customerId FROM customer WHERE LOWER(name)=LOWER(?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                return rs.getInt("customerId");

            }

        } catch(Exception e) {

            e.printStackTrace();

        }

        return -1;

    }

}