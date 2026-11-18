package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Booking;

public class BookingDAO {
    private BookingDAO dao;
    // ================= ADD BOOKING =================

    public void addBooking(Booking booking) {

        String query =
                "INSERT INTO booking " +
                        "(bookingId, customerId, roomId, checkInDate, checkOutDate, numberOfGuests, totalAmount, bookingStatus) " +
                        "VALUES(?,?,?,?,?,?,?,?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, booking.getBookingId());
            ps.setInt(2, booking.getCustomerId());
            ps.setInt(3, booking.getRoomId());
            ps.setString(4, booking.getCheckInDate());
            ps.setString(5, booking.getCheckOutDate());
            ps.setInt(6, booking.getNumberOfGuests());
            ps.setDouble(7, booking.getTotalAmount());
            ps.setString(8, booking.getBookingStatus());

            int rows = ps.executeUpdate();
            if (rows > 0) {



                // Create Check-In ID Automatically

                String checkQuery =
                        "INSERT INTO checkinout(customerId, bookingId, checkInDate, checkOutDate, status) VALUES(?,?,?,?,?)";


                PreparedStatement cps =
                        con.prepareStatement(checkQuery,
                                java.sql.Statement.RETURN_GENERATED_KEYS);


                cps.setInt(1, booking.getCustomerId());
                cps.setInt(2, booking.getBookingId());
                cps.setString(3, booking.getCheckInDate());
                cps.setString(4, booking.getCheckOutDate());
                cps.setString(5, "Not Checked-In");


                cps.executeUpdate();


                ResultSet key =
                        cps.getGeneratedKeys();


                if(key.next()) {

                    int checkId = key.getInt(1);

                    System.out.println("Your Check-In ID : " + checkId);

                }


            }
            else {
                System.out.println("Booking Not Added");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= DISPLAY BOOKINGS =================

    public void displayBookings() {
        String query =
                "SELECT b.bookingId,c.name,r.roomNumber," +
                        "b.checkInDate,b.checkOutDate," +
                        "b.numberOfGuests,b.totalAmount,b.bookingStatus " +
                        "FROM booking b " +
                        "JOIN customer c ON b.customerId=c.customerId " +
                        "JOIN room r ON b.roomId=r.roomId " +
                        "ORDER BY b.bookingId";        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("--------------------------------------");
                System.out.println("Booking ID     : " + rs.getInt("bookingId"));
                System.out.println("Customer Name  : " + rs.getString("name"));
                System.out.println("Room Number    : " + rs.getString("roomNumber"));
                System.out.println("Check In       : " + rs.getDate("checkInDate"));
                System.out.println("Check Out      : " + rs.getDate("checkOutDate"));
                System.out.println("Guests         : " + rs.getInt("numberOfGuests"));
                System.out.println("Amount         : ₹" + rs.getDouble("totalAmount"));
                System.out.println("Status         : " + rs.getString("bookingStatus"));
                System.out.println("--------------------------------------");
            }

            if (!found) {
                System.out.println("No Booking Found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ================= SEARCH BOOKING =================

    public void searchBooking(int bookingId) {

        String query =
                "SELECT b.bookingId,c.name,r.roomNumber," +
                        "b.checkInDate,b.checkOutDate," +
                        "b.numberOfGuests,b.totalAmount,b.bookingStatus " +
                        "FROM booking b " +
                        "JOIN customer c ON b.customerId=c.customerId " +
                        "JOIN room r ON b.roomId=r.roomId " +
                        "WHERE b.bookingId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, bookingId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("--------------------------------------");
                System.out.println("Booking ID     : " + rs.getInt("bookingId"));
                System.out.println("Customer Name  : " + rs.getString("name"));
                System.out.println("Room Number    : " + rs.getString("roomNumber"));
                System.out.println("Check In       : " + rs.getDate("checkInDate"));
                System.out.println("Check Out      : " + rs.getDate("checkOutDate"));
                System.out.println("Guests         : " + rs.getInt("numberOfGuests"));
                System.out.println("Amount         : ₹" + rs.getDouble("totalAmount"));
                System.out.println("Status         : " + rs.getString("bookingStatus"));
            } else {

                System.out.println("Booking Not Found.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
    // ================= CHECK BOOKING EXISTS =================

    public boolean checkBookingExists(int bookingId) {

        String query =
                "SELECT bookingId FROM booking WHERE bookingId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, bookingId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }


    // ================= CHECK BOOKING ID =================

    public boolean checkBookingId(int bookingId) {

        return checkBookingExists(bookingId);

    }


    // ================= CHECK CUSTOMER =================

    public boolean checkCustomerId(int customerId) {

        String query =
                "SELECT customerId FROM customer WHERE customerId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }
    // ================= GET ROOM ID =================

    public int getRoomIdFromBooking(int bookingId) {

        String query =
                "SELECT roomId FROM booking WHERE bookingId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, bookingId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                return rs.getInt("roomId");

            }

        }
        catch(Exception e) {

            e.printStackTrace();

        }

        return -1;

    }


    // ================= GET BOOKING ID =================

    public int getBookingIdByCustomerId(int customerId) {


        String query =
                "SELECT bookingId FROM booking " +
                        "WHERE customerId=? " +
                        "AND LOWER(bookingStatus) != 'cancelled' " +
                        "ORDER BY bookingId DESC LIMIT 1";
        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                return rs.getInt("bookingId");

            }

        }
        catch(Exception e) {

            e.printStackTrace();

        }

        return -1;

    }


    // ================= GET CUSTOMER ID =================

    public int getCustomerIdByName(String name) {

        String query =
                "SELECT customerId FROM customer WHERE LOWER(name)=LOWER(?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                return rs.getInt("customerId");

            }

        }
        catch(Exception e) {

            e.printStackTrace();

        }

        return -1;

    }


    // ================= UPDATE BOOKING STATUS =================

    public void updateBookingStatus(int bookingId, String status) {

        String query =
                "UPDATE booking SET bookingStatus=? WHERE bookingId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, status);

            ps.setInt(2, bookingId);

            ps.executeUpdate();

        }
        catch(Exception e) {

            e.printStackTrace();

        }

    }


    // ================= GET BOOKING DETAILS =================

    public Booking getBookingDetailsByCustomerName(String customerName) {

        String query =
                "SELECT b.* FROM booking b " +
                        "JOIN customer c ON b.customerId=c.customerId " +
                        "WHERE LOWER(c.name)=LOWER(?) " +
                        "AND LOWER(b.bookingStatus)!='cancelled'";

        Booking booking = null;

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, customerName);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                booking = new Booking();

                booking.setBookingId(rs.getInt("bookingId"));
                booking.setCustomerId(rs.getInt("customerId"));
                booking.setRoomId(rs.getInt("roomId"));
                booking.setCheckInDate(rs.getString("checkInDate"));
                booking.setCheckOutDate(rs.getString("checkOutDate"));
                booking.setNumberOfGuests(rs.getInt("numberOfGuests"));
                booking.setTotalAmount(rs.getDouble("totalAmount"));
                booking.setBookingStatus(rs.getString("bookingStatus"));

            }

        }
        catch(Exception e) {

            e.printStackTrace();

        }

        return booking;

    }
    public String[] getCustomerBookingDates(int customerId) {

        String query =
                "SELECT checkindate, checkoutdate FROM customer WHERE customerId=?";


        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, customerId);


            ResultSet rs = ps.executeQuery();


            if(rs.next()) {

                return new String[] {
                        rs.getString("checkindate"),
                        rs.getString("checkoutdate")
                };

            }


        }
        catch(Exception e) {

            e.printStackTrace();

        }


        return null;

    }
    public boolean checkCustomerBooking(int bookingId, int customerId) {


        String query =
                "SELECT bookingId " +
                        "FROM booking " +
                        "WHERE bookingId=? AND customerId=?";


        try {

            Connection con =
                    DBConnection.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(query);


            ps.setInt(1, bookingId);
            ps.setInt(2, customerId);


            ResultSet rs =
                    ps.executeQuery();


            return rs.next();


        }
        catch(Exception e) {

            e.printStackTrace();

        }


        return false;

    }
    public void viewMyBooking(int customerId)
    {
        String query =
                "SELECT c.name, b.bookingId, b.roomId, " +
                        "b.checkInDate, b.checkOutDate, " +
                        "b.numberOfGuests, b.bookingStatus " +
                        "FROM booking b " +
                        "JOIN customer c ON b.customerId = c.customerId " +
                        "WHERE b.customerId=? " +
                        "AND LOWER(b.bookingStatus)!='cancelled'";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();


            boolean found = false;

            while(rs.next()) {

                found = true;

                System.out.println("\n===== MY BOOKING =====");

                System.out.println("Customer Name : " + rs.getString("name"));
                System.out.println("Booking ID : " + rs.getInt("bookingId"));
                System.out.println("Room ID    : " + rs.getInt("roomId"));
                System.out.println("Check In   : " + rs.getDate("checkInDate"));
                System.out.println("Check Out  : " + rs.getDate("checkOutDate"));
                System.out.println("Guests     : " + rs.getInt("numberOfGuests"));
                System.out.println("Status     : " + rs.getString("bookingStatus"));

                System.out.println("----------------------");
            }


            if(!found) {

                System.out.println("No Booking Found.");

            }


        }
        catch(Exception e) {

            System.out.println("Unable to view booking.");

        }

    }
    public boolean isLatestBooking(int bookingId) {

        String query =
                "SELECT bookingId FROM booking ORDER BY bookingId DESC LIMIT 1";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                return rs.getInt("bookingId") == bookingId;

            }

        }
        catch(Exception e) {

            e.printStackTrace();

        }

        return false;
    }
    public int getLatestBookingId() {

        int id = -1;

        String query = "SELECT MAX(bookingId) AS bookingId FROM booking";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                id = rs.getInt("bookingId");

            }

        }
        catch(Exception e) {

            e.printStackTrace();

        }

        return id;
    }
    public void viewLatestBooking(int customerId)
    {
        String query =
                "SELECT c.customerId,c.name,b.bookingId,b.roomId," +
                        "b.checkInDate,b.checkOutDate," +
                        "b.numberOfGuests,b.totalAmount,b.bookingStatus " +
                        "FROM booking b " +
                        "JOIN customer c ON b.customerId=c.customerId " +
                        "WHERE b.customerId=? " +
                        "AND LOWER(b.bookingStatus)!='cancelled' " +
                        "ORDER BY b.bookingId DESC LIMIT 1";
        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {


                System.out.println("\n===== LATEST BOOKING =====");

                System.out.println("Customer ID : " + rs.getInt("customerId"));
                System.out.println("Customer Name : " + rs.getString("name"));
                System.out.println("Booking ID : " + rs.getInt("bookingId"));
                System.out.println("Room ID : " + rs.getInt("roomId"));
                System.out.println("Check In : " + rs.getDate("checkInDate"));
                System.out.println("Check Out : " + rs.getDate("checkOutDate"));
                System.out.println("Guests : " + rs.getInt("numberOfGuests"));
                System.out.println("Amount : ₹" + rs.getDouble("totalAmount"));
                System.out.println("Status : " + rs.getString("bookingStatus"));

            } else {

                System.out.println("No Booking Found.");

            }

        }
        catch(Exception e) {

            e.printStackTrace();

        }

    }
    public void deleteCustomerBooking(int bookingId) {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement check = con.prepareStatement(
                    "SELECT bookingStatus FROM booking WHERE bookingId=?");

            check.setInt(1, bookingId);

            ResultSet rs = check.executeQuery();

            if (!rs.next()) {

                System.out.println("Booking Not Found.");
                return;

            }

            if (rs.getString("bookingStatus").equalsIgnoreCase("Cancelled")) {

                System.out.println("Booking Is Already Cancelled.");
                return;

            }

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE booking SET bookingStatus='Cancelled' WHERE bookingId=?");

            ps.setInt(1, bookingId);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                System.out.println("Booking Cancelled Successfully.");

            } else {

                System.out.println("Booking Not Found.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    public void deleteBooking(int bookingId) {

        Connection con = null;

        try {

            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // ================= CHECK BOOKING =================

            String checkQuery =
                    "SELECT bookingStatus FROM booking WHERE bookingId=?";

            PreparedStatement checkPs =
                    con.prepareStatement(checkQuery);

            checkPs.setInt(1, bookingId);

            ResultSet rs = checkPs.executeQuery();

            if (!rs.next()) {

                System.out.println("Booking Not Found.");
                return;

            }

            if (rs.getString("bookingStatus").equalsIgnoreCase("Cancelled")) {

                System.out.println("Booking Is Already Cancelled.");
                return;

            }

            // ================= DELETE FOOD ORDER =================

            PreparedStatement ps1 = con.prepareStatement(
                    "DELETE FROM food_order WHERE bookingId=?");

            ps1.setInt(1, bookingId);

            ps1.executeUpdate();

            // ================= DELETE GAME BOOKING =================

            PreparedStatement ps2 = con.prepareStatement(
                    "DELETE FROM game_booking WHERE bookingId=?");

            ps2.setInt(1, bookingId);

            ps2.executeUpdate();

            // ================= DELETE PAYMENT =================

            PreparedStatement ps3 = con.prepareStatement(
                    "DELETE FROM payment WHERE bookingId=?");

            ps3.setInt(1, bookingId);

            ps3.executeUpdate();

            // ================= DELETE CHECK IN OUT =================

            PreparedStatement ps4 = con.prepareStatement(
                    "UPDATE checkinout SET status='Cancelled' WHERE bookingId=?");

            ps4.setInt(1, bookingId);

            ps4.executeUpdate();

            // ================= ROOM AVAILABLE =================

            PreparedStatement ps5 = con.prepareStatement(
                    "UPDATE room SET status='Available' " +
                            "WHERE roomId=(SELECT roomId FROM booking WHERE bookingId=?)");

            ps5.setInt(1, bookingId);

            ps5.executeUpdate();

            // ================= CANCEL BOOKING =================

            PreparedStatement ps6 = con.prepareStatement(
                    "UPDATE booking SET bookingStatus='Cancelled' WHERE bookingId=?");

            ps6.setInt(1, bookingId);

            int rows = ps6.executeUpdate();

            con.commit();

            if (rows > 0) {

                System.out.println("Booking Cancelled Successfully.");
                System.out.println("Status Updated Successfully.");

            } else {

                System.out.println("Booking Not Found.");

            }

        } catch (Exception e) {

            try {

                if (con != null) {

                    con.rollback();

                }

            } catch (Exception ex) {

                ex.printStackTrace();

            }

            e.printStackTrace();

        } finally {

            try {

                if (con != null) {

                    con.setAutoCommit(true);
                    con.close();

                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

    }
    public boolean isBookingCheckedIn(int bookingId) {

        String query =
                "SELECT checkId FROM checkinout " +
                        "WHERE bookingId=? AND status='Checked-In'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, bookingId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }
    public boolean cancelLatestBooking(int customerId, int bookingId) {

        // Latest Booking Check
        int latestBookingId = getLatestBookingIdByCustomer(customerId);

        if (latestBookingId == -1) {

            System.out.println("No Active Booking Found.");
            return false;

        }

        if (latestBookingId != bookingId) {

            System.out.println("Only Latest Booking Can Be Cancelled.");
            return false;

        }

        // Check-In Check
        if (isBookingCheckedIn(bookingId)) {

            System.out.println("Booking Already Checked-In. Cancellation Not Allowed.");
            return false;

        }

        // Cancel Booking
        deleteBooking(bookingId);

        return true;
    }
    public int getLatestBookingIdByCustomer(int customerId) {

        String query =
                "SELECT bookingId " +
                        "FROM booking " +
                        "WHERE customerId=? " +
                        "AND LOWER(bookingStatus)!='cancelled' " +
                        "ORDER BY bookingId DESC " +
                        "LIMIT 1";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                return rs.getInt("bookingId");

            }

        }
        catch(Exception e) {

            e.printStackTrace();

        }

        return -1;
    }
// ================= COMPLETE BOOKING =================

    public void completeBooking(int bookingId) {

        String query =
                "UPDATE booking " +
                        "SET bookingStatus='Completed' " +
                        "WHERE bookingId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, bookingId);

            ps.executeUpdate();

        }
        catch(Exception e) {

            e.printStackTrace();

        }

    }
    // ================= ROOM CHARGES =================
    public double getRoomCharges(int bookingId) {

        String query =
                "SELECT IFNULL(totalAmount,0) AS totalAmount " +
                        "FROM booking " +
                        "WHERE bookingId=? " +
                        "AND LOWER(bookingStatus)!='cancelled'";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, bookingId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return 0;
    }
}