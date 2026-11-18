package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.GameBooking;


public class GameBookingDAO {


    // ================= CHECK CUSTOMER ID =================

    public boolean checkCustomerId(int customerId) {


        String query =
                "SELECT customerId FROM customer WHERE customerId=?";


        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query))
        {


            ps.setInt(1, customerId);


            ResultSet rs =
                    ps.executeQuery();


            return rs.next();


        }
        catch(Exception e) {

            e.printStackTrace();

        }


        return false;

    }







    // ================= GET BOOKING ID =================

    public int getBookingIdByCustomerId(int customerId) {


        String query =
                "SELECT bookingId FROM booking " +
                        "WHERE customerId=? " +
                        "ORDER BY bookingId DESC LIMIT 1";



        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query))
        {


            ps.setInt(1, customerId);


            ResultSet rs =
                    ps.executeQuery();



            if(rs.next()) {

                return rs.getInt("bookingId");

            }


        }
        catch(Exception e) {

            e.printStackTrace();

        }


        return -1;

    }








    // ================= ADD GAME BOOKING =================

    public void addGameBooking(GameBooking gb) {


        String query =
                "INSERT INTO game_booking " +
                        "(customerId,bookingId,gameId,numberOfPlayers,totalAmount,bookingStatus) " +
                        "VALUES(?,?,?,?,?,?)";



        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, gb.getCustomerId());

            if(gb.getBookingId() == 0){
                ps.setNull(2, java.sql.Types.INTEGER);
            }else{
                ps.setInt(2, gb.getBookingId());
            }

            ps.setInt(3, gb.getGameId());
            ps.setInt(4, gb.getPlayers());
            ps.setDouble(5, gb.getTotalAmount());
            ps.setString(6, gb.getBookingStatus());
            int rows = ps.executeUpdate();

            if(rows > 0) {

return;
            }
            else {

                System.out.println("Game Booking Failed.");

            }


        }
        catch(Exception e) {

            e.printStackTrace();

        }


    }









    // ================= VIEW MY GAME BOOKINGS =================

    public void displayGameBookingsByCustomer(int customerId) {



        String query =

                "SELECT gb.gameBookingId, g.gameName, " +
                        "gb.numberOfPlayers, gb.totalAmount, gb.bookingStatus " +
                        "FROM game_booking gb " +
                        "JOIN game g ON gb.gameId=g.gameId " +
                        "WHERE gb.customerId=? " +
                        "AND gb.bookingStatus='Booked'";




        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query))
        {



            ps.setInt(1,customerId);



            ResultSet rs =
                    ps.executeQuery();



            boolean found=false;



            System.out.println(
                    "\n========== MY GAME BOOKINGS ==========");



            while(rs.next()) {


                found=true;


                System.out.println("------------------------------");


                System.out.println(
                        "Booking ID : "
                                + rs.getInt("gameBookingId"));



                System.out.println(
                        "Game Name : "
                                + rs.getString("gameName"));



                System.out.println(
                        "Players : "
                                + rs.getInt("numberOfPlayers"));



                System.out.println(
                        "Amount : ₹"
                                + rs.getDouble("totalAmount"));



                System.out.println(
                        "Status : "
                                + rs.getString("bookingStatus"));

            }





            if(!found) {

                System.out.println(
                        "No Active Game Booking Found.");

            }



        }
        catch(Exception e) {

            e.printStackTrace();

        }


    }
    // ================= CANCEL GAME BOOKING =================
    // ================= CANCEL LATEST GAME BOOKING =================

    public boolean cancelGameBooking(int customerId, int gameBookingId) {


        // First get latest active booking

        String checkQuery =
                "SELECT gameBookingId FROM game_booking " +
                        "WHERE customerId=? " +
                        "AND bookingStatus='Booked' " +
                        "ORDER BY gameBookingId DESC LIMIT 1";


        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps1 = con.prepareStatement(checkQuery))
        {


            ps1.setInt(1, customerId);


            ResultSet rs = ps1.executeQuery();


            if(rs.next())
            {

                int latestId =
                        rs.getInt("gameBookingId");


                if(gameBookingId != latestId)
                {

                    System.out.println(
                            "Only Latest Game Booking Can Be Cancelled.");

                    return false;

                }

            }



            String updateQuery =
                    "UPDATE game_booking SET bookingStatus='Cancelled' WHERE gameBookingId=? AND customerId=?";

            PreparedStatement ps2 =
                    con.prepareStatement(updateQuery);


            ps2.setInt(1, gameBookingId);
            ps2.setInt(2, customerId);



            int rows =
                    ps2.executeUpdate();


            return rows > 0;


        }
        catch(Exception e)
        {

            e.printStackTrace();

        }


        return false;

    }

    // ================= GET LATEST ACTIVE GAME BOOKING =================

    public GameBooking getLatestActiveGameBooking(int customerId)
    {

        GameBooking gb = null;


        String query =

                "SELECT gb.gameBookingId, gb.gameId, " +
                        "gb.numberOfPlayers, gb.totalAmount, gb.bookingStatus " +
                        "FROM game_booking gb " +
                        "WHERE gb.customerId=? " +
                        "AND gb.bookingStatus='Booked' " +
                        "ORDER BY gb.gameBookingId DESC LIMIT 1";


        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query))
        {

            ps.setInt(1, customerId);


            ResultSet rs = ps.executeQuery();


            if(rs.next())
            {

                gb = new GameBooking();


                gb.setGameBookingId(
                        rs.getInt("gameBookingId"));


                gb.setGameId(
                        rs.getInt("gameId"));


                gb.setPlayers(
                        rs.getInt("numberOfPlayers"));


                gb.setTotalAmount(
                        rs.getDouble("totalAmount"));


                gb.setBookingStatus(
                        rs.getString("bookingStatus"));

            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        return gb;
    }
// ================= DISPLAY ALL GAME BOOKINGS (ADMIN) =================

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
    // ================= DELETE GAME BOOKING (ADMIN) =================

    public void deleteGameBooking(int gameBookingId) {

        String query =
                "UPDATE game_booking SET bookingStatus='Cancelled' WHERE gameBookingId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, gameBookingId);

            int rows = ps.executeUpdate();

            if (rows > 0) {

return;
            } else {

                System.out.println("Game Booking Not Found.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }



}