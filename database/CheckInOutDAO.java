package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.CheckInOut;

public class CheckInOutDAO {

    // ================= CHECK CHECK ID EXISTS =================

    public boolean checkCheckId(int checkId) {


        String query =
                "SELECT checkId FROM checkinout WHERE checkId=?";


        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {


            ps.setInt(1,checkId);


            ResultSet rs = ps.executeQuery();


            return rs.next();


        }
        catch(Exception e) {

            e.printStackTrace();

        }


        return false;

    }





    // ================= CHECK CUSTOMER OWNERSHIP =================

    public boolean checkCustomerCheckId(int checkId,int customerId) {


        String query =
                "SELECT ci.checkId " +
                        "FROM checkinout ci " +
                        "JOIN booking b ON ci.bookingId=b.bookingId " +
                        "WHERE ci.checkId=? AND b.customerId=?";


        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {


            ps.setInt(1,checkId);
            ps.setInt(2,customerId);


            ResultSet rs = ps.executeQuery();


            return rs.next();


        }
        catch(Exception e) {

            e.printStackTrace();

        }


        return false;

    }





    // ================= ALREADY CHECKED-IN =================

    public boolean isAlreadyCheckedIn(int checkId) {


        String query =
                "SELECT checkId FROM checkinout " +
                        "WHERE checkId=? AND status='Checked-In'";


        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {


            ps.setInt(1,checkId);


            ResultSet rs = ps.executeQuery();


            return rs.next();


        }
        catch(Exception e) {

            e.printStackTrace();

        }


        return false;

    }
    // ================= UPDATE STATUS =================

    public void updateStatus(int checkId,String status) {


        String query =
                "UPDATE checkinout SET status=? WHERE checkId=?";


        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {


            ps.setString(1,status);
            ps.setInt(2,checkId);


            ps.executeUpdate();


        }
        catch(Exception e) {

            System.out.println(
                    "Status Update Error : "
                            + e.getMessage()
            );

        }

    }





    // ================= DISPLAY ALL CHECK-IN RECORDS =================

    public void displayRecords() {


        String query =
                "SELECT ci.checkId, c.name, b.bookingId, " +
                        "ci.checkInDate, ci.checkOutDate, ci.status " +
                        "FROM checkinout ci " +
                        "JOIN booking b ON ci.bookingId=b.bookingId " +
                        "JOIN customer c ON b.customerId=c.customerId";


        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {


            boolean found=false;


            System.out.println(
                    "\n========== ALL CHECK-IN RECORDS =========="
            );


            while(rs.next()) {


                found=true;


                System.out.println("----------------------------");


                System.out.println(
                        "Check-In ID   : "
                                + rs.getInt("checkId")
                );


                System.out.println(
                        "Customer Name : "
                                + rs.getString("name")
                );


                System.out.println(
                        "Booking ID    : "
                                + rs.getInt("bookingId")
                );


                System.out.println(
                        "Check-In Date : "
                                + rs.getDate("checkInDate")
                );


                System.out.println(
                        "Check-Out Date: "
                                + rs.getDate("checkOutDate")
                );


                System.out.println(
                        "Status        : "
                                + rs.getString("status")
                );

            }


            if(!found) {

                System.out.println(
                        "No Check-In Records Found."
                );

            }


        }
        catch(Exception e) {

            System.out.println(
                    "Display Error : "
                            + e.getMessage()
            );

        }

    }





    // ================= ADD CHECK-IN =================

    public void addCheckIn(CheckInOut check) {


        String query =
                "INSERT INTO checkinout " +
                        "(bookingId, checkInDate, checkOutDate, status) " +
                        "VALUES(?,?,?,?)";


        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {


            ps.setInt(1,check.getBookingId());

            ps.setString(2,check.getCheckInDate());

            ps.setString(3,check.getCheckOutDate());

            ps.setString(4,"Pending");


            int rows = ps.executeUpdate();


            if(rows > 0) {

                System.out.println(
                        "Check-In ID Generated Successfully"
                );

            }


        }
        catch(Exception e) {

            System.out.println(
                    "Check-In Error : "
                            + e.getMessage()
            );

        }

    }
    public void showBookingByCheckId(int checkId) {

        String query =
                "SELECT ci.checkId, c.name, b.bookingId, r.roomNumber, " +
                        "ci.checkInDate, ci.checkOutDate " +
                        "FROM checkinout ci " +
                        "JOIN booking b ON ci.bookingId = b.bookingId " +
                        "JOIN customer c ON b.customerId = c.customerId " +
                        "JOIN room r ON b.roomId = r.roomId " +
                        "WHERE ci.checkId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, checkId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\n========== CHECK-IN DETAILS ==========");
                System.out.println("Customer Name : " + rs.getString("name"));
                System.out.println("Check-In ID   : " + rs.getInt("checkId"));
                System.out.println("Booking ID    : " + rs.getInt("bookingId"));
                System.out.println("Room Number   : " + rs.getString("roomNumber"));
                System.out.println("Check-In Date : " + rs.getDate("checkInDate"));
                System.out.println("Check-Out Date: " + rs.getDate("checkOutDate"));
                System.out.println("======================================");

            } else {

                System.out.println("Booking Details Not Found.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
    public boolean isAlreadyCheckedInByCustomer(int customerId) {

        String query =
                "SELECT ci.checkId " +
                        "FROM checkinout ci " +
                        "JOIN booking b ON ci.bookingId = b.bookingId " +
                        "WHERE b.customerId=? " +
                        "AND ci.status='Checked-In'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }
    public boolean isBookingAlreadyCheckedIn(int bookingId) {

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
    // ================= CHECK-IN STATUS =================

    public boolean isCheckedIn(int checkId) {

        String query =
                "SELECT checkId FROM checkinout " +
                        "WHERE checkId=? AND status='Checked-In'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, checkId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }
    // ================= ALREADY CHECKED-OUT =================

    // ================= SHOW CUSTOMER CHECK-IN =================

    public boolean showCustomerCheckIns(int customerId) {
        String query =
                "SELECT co.checkId, b.bookingId, r.roomNumber, " +
                        "co.checkInDate, co.checkOutDate, co.status " +
                        "FROM checkinout co " +
                        "JOIN booking b ON co.bookingId = b.bookingId " +
                        "JOIN room r ON b.roomId = r.roomId " +
                        "WHERE b.customerId=?";boolean found = false;

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();


            while(rs.next()) {

                found = true;

                System.out.println("\n----------------------------");
                System.out.println("Check-In ID : " + rs.getInt("checkId"));
                System.out.println("Booking ID  : " + rs.getInt("bookingId"));
                System.out.println("Room Number : " + rs.getString("roomNumber"));
                System.out.println("Check In    : " + rs.getDate("checkInDate"));
                System.out.println("Check Out   : " + rs.getDate("checkOutDate"));
                System.out.println("Status      : " + rs.getString("status"));
                System.out.println("----------------------------");

            }


            con.close();

        } catch(Exception e) {

            e.printStackTrace();

        }


        return found;

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
    public boolean isAlreadyCheckedOut(int bookingId) {

        String query =
                "SELECT status FROM checkinout " +
                        "WHERE bookingId=? AND status='Checked-Out'";

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
    public int getBookingIdByCheckId(int checkId) {

        String query =
                "SELECT bookingId FROM checkinout WHERE checkId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, checkId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                return rs.getInt("bookingId");

            }

        } catch(Exception e) {

            e.printStackTrace();

        }

        return -1;
    }
    public void checkOut(int checkId) {

        Connection con = null;

        try {

            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // 1. Update CheckOut Status

            PreparedStatement ps1 = con.prepareStatement(
                    "UPDATE checkinout SET status='Checked-Out' WHERE checkId=?");

            ps1.setInt(1, checkId);

            ps1.executeUpdate();

            // 2. Room Available

            PreparedStatement ps2 = con.prepareStatement(
                    "UPDATE room SET status='Available' " +
                            "WHERE roomId=(" +
                            "SELECT roomId FROM booking WHERE bookingId=(" +
                            "SELECT bookingId FROM checkinout WHERE checkId=?))");

            ps2.setInt(1, checkId);

            ps2.executeUpdate();

            con.commit();

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

}