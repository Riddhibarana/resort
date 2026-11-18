package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import datastructure.RoomArrayList;
import model.Room;

public class RoomDAO {

    // ================= ADD ROOM =================

    public boolean addRoom(Room room) {

        if (!room.getRoomNumber().matches("^[1-9][0-9]{0,2}$")) {

            System.out.println("Invalid Room Number! Enter 1 to 3 digits and it cannot start with 0.");
            return false;

        }

        String checkQuery =
                "SELECT * FROM room WHERE roomId=? OR roomNumber=?";

        String insertQuery =
                "INSERT INTO room(roomNumber, roomType, pricePerNight, status, capacity, floor) "
                        + "VALUES(?,?,?,?,?,?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement checkPs = con.prepareStatement(checkQuery);

            checkPs.setInt(1, room.getRoomId());
            checkPs.setString(2, room.getRoomNumber());

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {

                System.out.println("Room ID Or Room Number Already Exists.");
                return false;

            }

            PreparedStatement ps = con.prepareStatement(insertQuery);

            ps.setString(1, room.getRoomNumber());
            ps.setString(2, room.getRoomType());
            ps.setDouble(3, room.getPricePerNight());
            ps.setString(4, room.getStatus());
            ps.setInt(5, room.getCapacity());
            ps.setInt(6, room.getFloor());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }

    }

    // ================= DISPLAY ROOMS =================

    public void displayRooms() {

        String query = "SELECT * FROM room";

        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                System.out.println("------------------------------------");
                System.out.println("Room ID         : " + rs.getInt("roomId"));
                System.out.println("Room Number     : " + rs.getString("roomNumber"));
                System.out.println("Room Type       : " + rs.getString("roomType"));
                System.out.println("Price Per Night : " + rs.getDouble("pricePerNight"));
                System.out.println("Capacity        : " + rs.getInt("capacity"));
                System.out.println("Floor           : " + rs.getInt("floor"));
                System.out.println("Status          : " + rs.getString("status"));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // ================= SEARCH ROOM =================

    public void searchRoom(int roomId) {

        RoomArrayList roomList = new RoomArrayList();

        String query = "SELECT * FROM room";

        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                Room room = new Room();

                room.setRoomId(rs.getInt("roomId"));
                room.setRoomNumber(rs.getString("roomNumber"));
                room.setRoomType(rs.getString("roomType"));
                room.setPricePerNight(rs.getDouble("pricePerNight"));
                room.setStatus(rs.getString("status"));
                room.setCapacity(rs.getInt("capacity"));
                room.setFloor(rs.getInt("floor"));

                roomList.add(room);

            }

            Room found = roomList.search(roomId);

            if (found != null) {

                System.out.println("\n===== ROOM FOUND =====");
                found.displayRoom();

            } else {

                System.out.println("Room Not Found.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // ================= UPDATE ROOM =================

    public boolean updateRoom(Room room) {
        if (room.getRoomNumber() != null && !room.getRoomNumber().isEmpty()) {

            if (!room.getRoomNumber().matches("^[1-9][0-9]{0,2}$")) {

                System.out.println("Invalid Room Number! Enter 1 to 3 digits and it cannot start with 0.");
                return false;

            }


        }
        String query =
                "UPDATE room SET " +
                        "roomNumber = COALESCE(NULLIF(?,''), roomNumber), " +
                        "roomType = COALESCE(NULLIF(?,''), roomType), " +
                        "pricePerNight = CASE WHEN ? > 0 THEN ? ELSE pricePerNight END, " +
                        "status = COALESCE(NULLIF(?,''), status), " +
                        "capacity = CASE WHEN ? > 0 THEN ? ELSE capacity END, " +
                        "floor = CASE WHEN ? >= 0 THEN ? ELSE floor END " +
                        "WHERE roomId=?";
        try {

            Connection con = DBConnection.getConnection();
            String statusQuery = "SELECT status FROM room WHERE roomId=?";

            PreparedStatement statusPs = con.prepareStatement(statusQuery);
            statusPs.setInt(1, room.getRoomId());

            ResultSet statusRs = statusPs.executeQuery();

            if (statusRs.next()) {

                if (statusRs.getString("status").equalsIgnoreCase("Booked")) {

                    System.out.println("Booked Room Cannot Be Updated.");
                    return false;

                }

            }

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, room.getRoomNumber());
            ps.setString(2, room.getRoomType());

            ps.setDouble(3, room.getPricePerNight());
            ps.setDouble(4, room.getPricePerNight());

            ps.setString(5, room.getStatus());

            ps.setInt(6, room.getCapacity());
            ps.setInt(7, room.getCapacity());

            ps.setInt(8, room.getFloor());
            ps.setInt(9, room.getFloor());

            ps.setInt(10, room.getRoomId());
            int rows = ps.executeUpdate();

            if (rows > 0) {

                return true;

            } else {

                System.out.println("Room ID Not Found.");
                return false;

            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }

    }
    // ================= DELETE ROOM =================

    public boolean deleteRoom(int roomId) {

        try {

            Connection con = DBConnection.getConnection();

            String checkQuery =
                    "SELECT COUNT(*) FROM booking WHERE roomId=?";

            PreparedStatement checkPs = con.prepareStatement(checkQuery);
            checkPs.setInt(1, roomId);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {

                System.out.println("Room Cannot Be Deleted Because It Has Booking Records.");
                return false;

            }

            String deleteQuery =
                    "DELETE FROM room WHERE roomId=?";

            PreparedStatement deletePs = con.prepareStatement(deleteQuery);

            deletePs.setInt(1, roomId);

            int rows = deletePs.executeUpdate();

            if (rows > 0) {

                return true;

            } else {

                System.out.println("Room ID Not Found.");
                return false;

            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }

    }

    // ================= CHECK ROOM AVAILABILITY =================

    public boolean checkRoomAvailability(int roomId) {

        try {

            Connection con = DBConnection.getConnection();

            String query =
                    "SELECT status FROM room WHERE roomId=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, roomId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String status = rs.getString("status");

                if (status.equalsIgnoreCase("Available")) {

                    System.out.println("Room Is Available.");
                    return true;

                } else {

                    System.out.println("Room Is Not Available.");
                    return false;

                }

            } else {

                System.out.println("Room Not Found.");
                return false;

            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }

    }

    // ================= ROOM ID EXISTS =================

    public boolean isRoomIdExists(int roomId) {

        String query =
                "SELECT * FROM room WHERE roomId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, roomId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }

    // ================= ROOM NUMBER EXISTS =================

    public boolean isRoomNumberExists(String roomNumber) {

        String query =
                "SELECT * FROM room WHERE roomNumber=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, roomNumber);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }

    // ================= GET ROOM STATUS =================

    public String getRoomStatus(int roomId) {

        String query =
                "SELECT status FROM room WHERE roomId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, roomId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getString("status");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }
    // ================= UPDATE ROOM STATUS =================

    public void updateRoomStatus(int roomId, String status) {

        String query =
                "UPDATE room SET status=? WHERE roomId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, status);
            ps.setInt(2, roomId);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // ================= DISPLAY AVAILABLE ROOMS =================

    public void displayAvailableRooms() {

        String query =
                "SELECT * FROM room WHERE status='Available'";

        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            System.out.println("\n========== AVAILABLE ROOMS ==========");

            while (rs.next()) {

                System.out.println("-------------------------------------");
                System.out.println("Room ID      : " + rs.getInt("roomId"));
                System.out.println("Room Number  : " + rs.getString("roomNumber"));
                System.out.println("Room Type    : " + rs.getString("roomType"));
                System.out.println("Price/Night  : " + rs.getDouble("pricePerNight"));
                System.out.println("Capacity     : " + rs.getInt("capacity"));
                System.out.println("Floor        : " + rs.getInt("floor"));
                System.out.println("Status       : " + rs.getString("status"));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // ================= GET ROOM PRICE =================

    public double getRoomPrice(int roomId) {

        String query =
                "SELECT pricePerNight FROM room WHERE roomId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, roomId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getDouble("pricePerNight");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return 0;

    }

    // ================= ROOM AVAILABLE =================

    public boolean isRoomAvailable(int roomId) {

        String query =
                "SELECT status FROM room WHERE roomId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, roomId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getString("status")
                        .equalsIgnoreCase("Available");

            }

            System.out.println("Room Not Found.");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }

    // ================= GET ROOM CAPACITY =================

    public int getRoomCapacity(int roomId) {

        String query =
                "SELECT capacity FROM room WHERE roomId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, roomId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt("capacity");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return 0;

    }
    public boolean isRoomNumberExistsForOtherRoom(String roomNumber, int roomId) {

        String query = "SELECT * FROM room WHERE roomNumber=? AND roomId<>?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, roomNumber);
            ps.setInt(2, roomId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }

}