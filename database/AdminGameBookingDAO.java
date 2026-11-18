package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminGameBookingDAO {

    // ================= DISPLAY ALL GAME BOOKINGS =================

    public void displayAllGameBookings() {

        String query =
                "SELECT gb.gameBookingId, c.name, g.gameName, " +
                        "gb.numberOfPlayers, gb.totalAmount, gb.bookingStatus " +
                        "FROM game_booking gb " +
                        "JOIN customer c ON gb.customerId = c.customerId " +
                        "JOIN game g ON gb.gameId = g.gameId " +
                        "ORDER BY gb.gameBookingId";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("----------------------------------");
                System.out.println("Game Booking ID : " + rs.getInt("gameBookingId"));
                System.out.println("Customer Name   : " + rs.getString("name"));
                System.out.println("Game Name       : " + rs.getString("gameName"));
                System.out.println("Players         : " + rs.getInt("numberOfPlayers"));
                System.out.println("Total Amount    : ₹" + rs.getDouble("totalAmount"));
                System.out.println("Status          : " + rs.getString("bookingStatus"));
                System.out.println("----------------------------------");

            }

            if (!found) {

                System.out.println("No Game Bookings Found.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // ================= SEARCH GAME BOOKING =================

    public void searchGameBooking(int gameBookingId) {

        String query =
                "SELECT gb.gameBookingId, c.name, g.gameName, " +
                        "gb.numberOfPlayers, gb.totalAmount, gb.bookingStatus " +
                        "FROM game_booking gb " +
                        "JOIN customer c ON gb.customerId = c.customerId " +
                        "JOIN game g ON gb.gameId = g.gameId " +
                        "WHERE gb.gameBookingId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, gameBookingId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("----------------------------------");
                System.out.println("Game Booking ID : " + rs.getInt("gameBookingId"));
                System.out.println("Customer Name   : " + rs.getString("name"));
                System.out.println("Game Name       : " + rs.getString("gameName"));
                System.out.println("Players         : " + rs.getInt("numberOfPlayers"));
                System.out.println("Total Amount    : ₹" + rs.getDouble("totalAmount"));
                System.out.println("Status          : " + rs.getString("bookingStatus"));
                System.out.println("----------------------------------");

            } else {

                System.out.println("Game Booking Not Found.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
// ================= DELETE GAME BOOKING =================

    public void deleteGameBooking(int gameBookingId) {

        try (Connection con = DBConnection.getConnection()) {

            // Check Current Status
            String checkQuery =
                    "SELECT bookingStatus FROM game_booking WHERE gameBookingId=?";

            PreparedStatement checkPs =
                    con.prepareStatement(checkQuery);

            checkPs.setInt(1, gameBookingId);

            ResultSet rs = checkPs.executeQuery();

            if (!rs.next()) {

                System.out.println("Game Booking Not Found.");
                return;

            }

            if (rs.getString("bookingStatus").equalsIgnoreCase("Cancelled")) {

                System.out.println("Game Booking Is Already Cancelled.");
                return;

            }

            // Cancel Booking
            String updateQuery =
                    "UPDATE game_booking SET bookingStatus='Cancelled' WHERE gameBookingId=?";

            PreparedStatement updatePs =
                    con.prepareStatement(updateQuery);

            updatePs.setInt(1, gameBookingId);

            int rows = updatePs.executeUpdate();

            if (rows > 0) {

                System.out.println("Game Booking Cancelled Successfully.");
                System.out.println("Status Updated Successfully.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}