package database;

import java.sql.Connection;
import java.sql.Statement;

public class createtable {

        public static void createTables(Connection connection) {

                try {

                        Connection con = DBConnection.getConnection();
                        Statement st = con.createStatement();


                        // ================= CUSTOMER TABLE =================

                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS customer(" +
                                        "customerId INT AUTO_INCREMENT PRIMARY KEY," +
                                        "name VARCHAR(100) NOT NULL," +
                                        "gender VARCHAR(10)," +
                                        "age INT," +
                                        "phone VARCHAR(15)," +
                                        "email VARCHAR(100)," +
                                        "address VARCHAR(200)," +
                                        "idProof VARCHAR(50)," +
                                        "username VARCHAR(50) UNIQUE," +
                                        "password VARCHAR(100)," +
                                        "checkInDate DATE," +
                                        "checkOutDate DATE)"
                        );
                        // ================= ROOM TABLE =================

                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS room(" +
                                        "roomId INT AUTO_INCREMENT PRIMARY KEY," +
                                        "roomNumber VARCHAR(20) UNIQUE," +
                                        "roomType VARCHAR(50)," +
                                        "pricePerNight DOUBLE," +
                                        "status VARCHAR(20)," +
                                        "capacity INT," +
                                        "floor INT)"
                        );

                        // ================= BOOKING TABLE =================

                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS booking(" +
                                        "bookingId INT AUTO_INCREMENT PRIMARY KEY," +
                                        "customerId INT," +
                                        "roomId INT," +
                                        "checkInDate DATE," +
                                        "checkOutDate DATE," +
                                        "numberOfGuests INT," +
                                        "totalAmount DOUBLE," +
                                        "bookingStatus VARCHAR(30)," +
                                        "createdTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                                        "canCancel BOOLEAN DEFAULT TRUE," +
                                        "FOREIGN KEY(customerId) REFERENCES customer(customerId)," +
                                        "FOREIGN KEY(roomId) REFERENCES room(roomId))"
                        );

                        // ================= CHECK IN OUT TABLE =================

                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS checkinout(" +
                                        "checkId INT AUTO_INCREMENT PRIMARY KEY," +
                                        "bookingId INT," +
                                        "checkInDate DATE," +
                                        "checkOutDate DATE," +
                                        "status VARCHAR(30)," +
                                        "FOREIGN KEY(bookingId) REFERENCES booking(bookingId))"
                        );
                        // ================= FOOD MENU TABLE =================

                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS food_menu(" +
                                        "foodId INT AUTO_INCREMENT PRIMARY KEY," +
                                        "foodName VARCHAR(100)," +
                                        "category VARCHAR(50)," +
                                        "price DOUBLE," +
                                        "availability VARCHAR(20))"
                        );

                        // ================= FOOD ORDER TABLE =================

                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS food_order(" +
                                        "orderId INT AUTO_INCREMENT PRIMARY KEY," +
                                        "customerId INT," +
                                        "bookingId INT," +
                                        "foodId INT," +
                                        "quantity INT," +
                                        "totalBill DOUBLE," +
                                        "orderStatus VARCHAR(30) DEFAULT 'Pending'," +
                                        "FOREIGN KEY(customerId) REFERENCES customer(customerId)," +
                                        "FOREIGN KEY(bookingId) REFERENCES booking(bookingId)," +
                                        "FOREIGN KEY(foodId) REFERENCES food_menu(foodId))"
                        );

                        // ================= GAME TABLE =================

                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS game(" +
                                        "gameId INT AUTO_INCREMENT PRIMARY KEY," +
                                        "gameName VARCHAR(100) NOT NULL," +
                                        "gameType VARCHAR(50)," +
                                        "price DOUBLE NOT NULL)"
                        );

                        // ================= GAME BOOKING TABLE =================

                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS game_booking(" +
                                        "gameBookingId INT AUTO_INCREMENT PRIMARY KEY," +
                                        "customerId INT NOT NULL," +
                                        "bookingId INT NOT NULL," +
                                        "gameId INT NOT NULL," +
                                        "numberOfPlayers INT NOT NULL," +
                                        "totalAmount DOUBLE," +
                                        "bookingStatus VARCHAR(30) DEFAULT 'Booked'," +
                                        "FOREIGN KEY(customerId) REFERENCES customer(customerId)," +
                                        "FOREIGN KEY(bookingId) REFERENCES booking(bookingId)," +
                                        "FOREIGN KEY(gameId) REFERENCES game(gameId))"
                        );
                        // ================= PAYMENT TABLE =================

                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS payment(" +
                                        "paymentId INT AUTO_INCREMENT PRIMARY KEY," +
                                        "customerId INT," +
                                        "bookingId INT," +
                                        "roomCharges DOUBLE DEFAULT 0," +
                                        "restaurantCharges DOUBLE DEFAULT 0," +
                                        "gameCharges DOUBLE DEFAULT 0," +
                                        "subTotal DOUBLE DEFAULT 0," +
                                        "gst DOUBLE DEFAULT 0," +
                                        "totalAmount DOUBLE DEFAULT 0," +
                                        "paymentMethod VARCHAR(20)," +
                                        "paymentDate DATE," +
                                        "paymentStatus VARCHAR(20) DEFAULT 'Pending'," +
                                        "FOREIGN KEY(customerId) REFERENCES customer(customerId)," +
                                        "FOREIGN KEY(bookingId) REFERENCES booking(bookingId))"
                        );

                        // ================= STAFF TABLE =================

                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS staff(" +
                                        "staffId INT AUTO_INCREMENT PRIMARY KEY," +
                                        "staffName VARCHAR(100)," +
                                        "gender VARCHAR(20)," +
                                        "age INT," +
                                        "phone VARCHAR(15)," +
                                        "email VARCHAR(100)," +
                                        "designation VARCHAR(50)," +
                                        "salary DOUBLE," +
                                        "shift VARCHAR(20))"
                        );

                        // ================= FEEDBACK TABLE =================

                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS feedback(" +
                                        "feedbackId INT AUTO_INCREMENT PRIMARY KEY," +
                                        "customerId INT," +
                                        "rating INT," +
                                        "comment VARCHAR(255)," +
                                        "feedbackDate DATE," +
                                        "FOREIGN KEY(customerId) REFERENCES customer(customerId))"
                        );
// ================= ADMIN FOOD TABLE =================

                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS food(" +
                                        "foodId INT AUTO_INCREMENT PRIMARY KEY," +
                                        "foodName VARCHAR(100) NOT NULL," +
                                        "category VARCHAR(50) NOT NULL," +
                                        "price DOUBLE NOT NULL," +
                                        "status VARCHAR(20) DEFAULT 'Available'" +
                                        ")"
                        );
// ================= DESIGNATION TABLE =================

                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS designation(" +
                                        "designationId INT AUTO_INCREMENT PRIMARY KEY," +
                                        "designationName VARCHAR(50) UNIQUE NOT NULL," +
                                        "salary DOUBLE NOT NULL" +
                                        ")"
                        );
                        System.out.println("====================================");
                        System.out.println("All Tables Created Successfully!");
                        System.out.println("====================================");

                        st.close();
                        con.close();

                } catch (Exception e) {

                        e.printStackTrace();

                }
        }
}