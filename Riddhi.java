//import java.sql.*;
//import java.util.*;
//import java.util.concurrent.*;
//
//class ResortManagementSystem {
//    // Fix 1: Added ?createDatabaseIfNotExist=true so the database is created automatically
//     // Enter your password here if required
//
//    private static Connection connection;
//    private static Scanner scanner = new Scanner(System.in);
//
//    // Data structures
//    private static ArrayList<String> guestList = new ArrayList<>();
//    private static LinkedList<String> bookingQueue = new LinkedList<>();
//    private static ArrayDeque<String> serviceQueue = new ArrayDeque<>();
//
//    // Producer-Consumer shared buffer replaced with ArrayDeque
//    private static final int MAX_ORDER_CAPACITY = 10;
//    private static ArrayDeque<String> orderQueue = new ArrayDeque<>(MAX_ORDER_CAPACITY);
//
//    public static void main(String[] args) {
//        try {
//            // Fix 2: Enabled class loading (useful for older drivers)
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(DB_URL, USER, PASS);
//            System.out.println((connection != null) ? "Connected to database successfully!" : "Connection Failed");
//
//            // Create all tables
//            createTables();
//
//            // Start Consumer thread (Producer is the main thread placing orders)
//            startConsumer();
//
//            // Main menu
//            while (true) {
//                displayMainMenu();
//                int choice = scanner.nextInt();
//                scanner.nextLine(); // consume newline
//
//                switch (choice) {
//                    case 1: guestManagement(); break;
//                    case 2: roomManagement(); break;
//                    case 3: bookingManagement(); break;
//                    case 4: checkInOut(); break;
//                    case 5: restaurantManagement(); break;
//                    case 6: gameZoneManagement(); break;
//                    case 7: paymentAndBilling(); break;
//                    case 8: staffManagement(); break;
//                    case 9: feedbackManagement(); break;
//                    case 10: demonstrateDataStructures(); break;
//                    case 11: demonstrateMultithreading(); break;
//                    case 0:
//                        System.out.println("Thank you for using Resort Management System!");
//                        if (connection != null) connection.close();
//                        System.exit(0);
//                    default:
//                        System.out.println("Invalid choice! Please try again.");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("\n[ERROR] Could not connect to the database. Please check if XAMPP/MySQL is running and MySQL Connector is added.");
//        }
//    }
//
//    private static void displayMainMenu() {
//        System.out.println("\n========== RESORT MANAGEMENT SYSTEM ==========");
//        System.out.println("1. Guest Management");
//        System.out.println("2. Room Management");
//        System.out.println("3. Booking Management");
//        System.out.println("4. Check-In / Check-Out");
//        System.out.println("5. Restaurant Management");
//        System.out.println("6. Game Zone Management");
//        System.out.println("7. Payment & Billing");
//        System.out.println("8. Staff Management");
//        System.out.println("9. Feedback Management");
//        System.out.println("10. Data Structure Demonstrations");
//        System.out.println("11. Multithreading Demonstrations");
//        System.out.println("0. Exit");
//        System.out.print("Enter your choice: ");
//    }
//
//    private static void createTables() throws Exception {
//
//    private static void guestManagement() throws SQLException {
//        System.out.println("\n===== GUEST MANAGEMENT =====");
//        System.out.println("1. Add New Guest");
//        System.out.println("2. View All Guests");
//        System.out.println("3. Search Guest");
//        System.out.println("4. Update Guest");
//        System.out.print("Enter choice: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1: addGuest(); break;
//            case 2: viewAllGuests(); break;
//            case 3: searchGuest(); break;
//            case 4: updateGuest(); break;
//        }
//    }
//
//    private static void addGuest() throws SQLException {
//        System.out.println("Enter Guest Details,");
//        System.out.print("Guest Name: ");
//        String name = scanner.nextLine();
//        System.out.print("Email: ");
//        String email = scanner.nextLine();
//        System.out.print("Phone: ");
//        String phone = scanner.nextLine();
//        System.out.print("Address: ");
//        String address = scanner.nextLine();
//        System.out.print("ID Proof: ");
//        String idProof = scanner.nextLine();
//
//        // Fix 4: Proper condition to prevent Scanner from passing empty data strings
//        if (!name.trim().isEmpty() && !phone.trim().isEmpty()) {
//            String sql = "INSERT INTO guest (name, email, phone, address, id_proof) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement pstmt = connection.prepareStatement(sql);
//            pstmt.setString(1, name);
//            pstmt.setString(2, email);
//            pstmt.setString(3, phone);
//            pstmt.setString(4, address);
//            pstmt.setString(5, idProof);
//
//            int rows = pstmt.executeUpdate();
//            if (rows > 0) {
//                System.out.println("Guest added successfully!");
//                guestList.add(name);
//            }
//            pstmt.close();
//        } else {
//            System.out.println("Add Failed! Name and Phone cannot be empty.");
//        }
//    }
//
//    private static void viewAllGuests() throws SQLException {
//        String sql = "SELECT * FROM guest";
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//
//        System.out.println("\n===== ALL GUESTS =====");
//        while (rs.next()) {
//            System.out.println("ID: " + rs.getInt("guest_id") +
//                    " | Name: " + rs.getString("name") +
//                    " | Phone: " + rs.getString("phone") +
//                    " | Email: " + rs.getString("email"));
//        }
//        rs.close();
//        stmt.close();
//    }
//
//    private static void searchGuest() throws SQLException {
//        System.out.print("Enter Guest Name or Phone to search: ");
//        String search = scanner.nextLine();
//
//        String sql = "SELECT * FROM guest WHERE name LIKE ? OR phone LIKE ?";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setString(1, "%" + search + "%");
//        pstmt.setString(2, "%" + search + "%");
//
//        ResultSet rs = pstmt.executeQuery();
//        System.out.println("\n===== SEARCH RESULTS =====");
//        while (rs.next()) {
//            System.out.println("ID: " + rs.getInt("guest_id"));
//            System.out.println("Name: " + rs.getString("name"));
//            System.out.println("Phone: " + rs.getString("phone"));
//            System.out.println("Email: " + rs.getString("email"));
//            System.out.println("Address: " + rs.getString("address"));
//            System.out.println("------------------------");
//        }
//        rs.close();
//        pstmt.close();
//    }
//
//    private static void updateGuest() throws SQLException {
//        System.out.print("Enter Guest ID to update: ");
//        int guestId = scanner.nextInt();
//        scanner.nextLine();
//
//        System.out.print("Enter New Phone: ");
//        String phone = scanner.nextLine();
//        System.out.print("Enter New Email: ");
//        String email = scanner.nextLine();
//
//        String sql = "UPDATE guest SET phone = ?, email = ? WHERE guest_id = ?";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setString(1, phone);
//        pstmt.setString(2, email);
//        pstmt.setInt(3, guestId);
//
//        int rows = pstmt.executeUpdate();
//        if (rows > 0) {
//            System.out.println("Guest updated successfully!");
//        } else {
//            System.out.println("Guest not found!");
//        }
//        pstmt.close();
//    }
//
//    private static void roomManagement() throws SQLException {
//        System.out.println("\n===== ROOM MANAGEMENT =====");
//        System.out.println("1. Add Room Type");
//        System.out.println("2. Add Room");
//        System.out.println("3. View All Rooms");
//        System.out.println("4. Check Room Availability");
//        System.out.print("Enter choice: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1: addRoomType(); break;
//            case 2: addRoom(); break;
//            case 3: viewAllRooms(); break;
//            case 4: checkRoomAvailability(); break;
//        }
//    }
//
//    private static void addRoomType() throws SQLException {
//        System.out.print("Enter Room Type Name: ");
//        String typeName = scanner.nextLine();
//        System.out.print("Enter Base Price: ");
//        double price = scanner.nextDouble();
//        System.out.print("Enter Capacity: ");
//        int capacity = scanner.nextInt();
//        scanner.nextLine();
//        System.out.print("Enter Description: ");
//        String description = scanner.nextLine();
//
//        String sql = "INSERT INTO room_types (type_name, base_price, capacity, description) VALUES (?, ?, ?, ?)";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setString(1, typeName);
//        pstmt.setDouble(2, price);
//        pstmt.setInt(3, capacity);
//        pstmt.setString(4, description);
//
//        int rows = pstmt.executeUpdate();
//        if (rows > 0) {
//            System.out.println("Room type added successfully!");
//        }
//        pstmt.close();
//    }
//
//    private static void addRoom() throws SQLException {
//        System.out.print("Enter Room Number: ");
//        String roomNumber = scanner.nextLine();
//        System.out.print("Enter Type ID: ");
//        int typeId = scanner.nextInt();
//        System.out.print("Enter Floor: ");
//        int floor = scanner.nextInt();
//        scanner.nextLine();
//
//        String sql = "INSERT INTO room (room_number, type_id, floor, status) VALUES (?, ?, ?, 'Available')";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setString(1, roomNumber);
//        pstmt.setInt(2, typeId);
//        pstmt.setInt(3, floor);
//
//        int rows = pstmt.executeUpdate();
//        if (rows > 0) {
//            System.out.println("Room added successfully!");
//        }
//        pstmt.close();
//    }
//
//    private static void viewAllRooms() throws SQLException {
//        String sql = "SELECT r.room_id, r.room_number, rt.type_name, r.status, r.floor, rt.base_price " +
//                "FROM room r JOIN room_types rt ON r.type_id = rt.type_id";
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//
//        System.out.println("\n===== ALL ROOMS =====");
//        while (rs.next()) {
//            System.out.println("Room ID: " + rs.getInt("room_id") +
//                    " | Number: " + rs.getString("room_number") +
//                    " | Type: " + rs.getString("type_name") +
//                    " | Floor: " + rs.getInt("floor") +
//                    " | Status: " + rs.getString("status") +
//                    " | Price: Rs." + rs.getDouble("base_price"));
//        }
//        rs.close();
//        stmt.close();
//    }
//
//    private static void checkRoomAvailability() throws SQLException {
//        String sql = "SELECT r.room_number, rt.type_name, r.floor, rt.base_price " +
//                "FROM room r JOIN room_types rt ON r.type_id = rt.type_id " +
//                "WHERE r.status = 'Available'";
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//
//        System.out.println("\n===== AVAILABLE ROOMS =====");
//        while (rs.next()) {
//            System.out.println("Room: " + rs.getString("room_number") +
//                    " | Type: " + rs.getString("type_name") +
//                    " | Floor: " + rs.getInt("floor") +
//                    " | Price: Rs." + rs.getDouble("base_price"));
//        }
//        rs.close();
//        stmt.close();
//    }
//
//    private static void bookingManagement() throws SQLException {
//        System.out.println("\n===== BOOKING MANAGEMENT =====");
//        System.out.println("1. Create New Booking");
//        System.out.println("2. View All Bookings");
//        System.out.println("3. Cancel Booking");
//        System.out.print("Enter choice: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1: createBooking(); break;
//            case 2: viewAllBookings(); break;
//            case 3: cancelBooking(); break;
//        }
//    }
//
//    private static void createBooking() throws SQLException {
//        System.out.print("Enter Guest ID: ");
//        int guestId = scanner.nextInt();
//        System.out.print("Enter Room ID: ");
//        int roomId = scanner.nextInt();
//        scanner.nextLine();
//        System.out.print("Enter Check-in Date (YYYY-MM-DD): ");
//        String checkIn = scanner.nextLine();
//        System.out.print("Enter Check-out Date (YYYY-MM-DD): ");
//        String checkOut = scanner.nextLine();
//        System.out.print("Enter Total Amount: ");
//        double amount = scanner.nextDouble();
//        scanner.nextLine();
//
//        String sql = "INSERT INTO bookings (guest_id, room_id, check_in_date, check_out_date, total_amount) VALUES (?, ?, ?, ?, ?)";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setInt(1, guestId);
//        pstmt.setInt(2, roomId);
//        pstmt.setString(3, checkIn);
//        pstmt.setString(4, checkOut);
//        pstmt.setDouble(5, amount);
//
//        int rows = pstmt.executeUpdate();
//        if (rows > 0) {
//            String updateRoom = "UPDATE room SET status = 'Booked' WHERE room_id = ?";
//            PreparedStatement updateStmt = connection.prepareStatement(updateRoom);
//            updateStmt.setInt(1, roomId);
//            updateStmt.executeUpdate();
//            updateStmt.close();
//
//            System.out.println("Booking created successfully!");
//            bookingQueue.add("Booking for Guest ID: " + guestId);
//        }
//        pstmt.close();
//    }
//
//    private static void viewAllBookings() throws SQLException {
//        String sql = "SELECT b.booking_id, g.name, r.room_number, b.check_in_date, b.check_out_date, b.status, b.total_amount " +
//                "FROM bookings b " +
//                "JOIN guest g ON b.guest_id = g.guest_id " +
//                "JOIN room r ON b.room_id = r.room_id";
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//
//        System.out.println("\n===== ALL BOOKINGS =====");
//        while (rs.next()) {
//            System.out.println("Booking ID: " + rs.getInt("booking_id") +
//                    " | Guest: " + rs.getString("name") +
//                    " | Room: " + rs.getString("room_number") +
//                    " | Check-in: " + rs.getString("check_in_date") +
//                    " | Check-out: " + rs.getString("check_out_date") +
//                    " | Status: " + rs.getString("status") +
//                    " | Amount: Rs." + rs.getDouble("total_amount"));
//        }
//        rs.close();
//        stmt.close();
//    }
//
//    private static void cancelBooking() throws SQLException {
//        System.out.print("Enter Booking ID to cancel: ");
//        int bookingId = scanner.nextInt();
//        scanner.nextLine();
//
//        String getRoomSql = "SELECT room_id FROM bookings WHERE booking_id = ?";
//        PreparedStatement getRoomStmt = connection.prepareStatement(getRoomSql);
//        getRoomStmt.setInt(1, bookingId);
//        ResultSet rs = getRoomStmt.executeQuery();
//
//        if (rs.next()) {
//            int roomId = rs.getInt("room_id");
//
//            String sql = "UPDATE bookings SET status = 'Cancelled' WHERE booking_id = ?";
//            PreparedStatement pstmt = connection.prepareStatement(sql);
//            pstmt.setInt(1, bookingId);
//            pstmt.executeUpdate();
//            pstmt.close();
//
//            String updateRoom = "UPDATE room SET status = 'Available' WHERE room_id = ?";
//            PreparedStatement updateStmt = connection.prepareStatement(updateRoom);
//            updateStmt.setInt(1, roomId);
//            updateStmt.executeUpdate();
//            updateStmt.close();
//
//            System.out.println("Booking cancelled successfully!");
//        } else {
//            System.out.println("Booking not found!");
//        }
//        rs.close();
//        getRoomStmt.close();
//    }
//
//    private static void checkInOut() throws SQLException {
//        System.out.println("\n===== CHECK-IN / CHECK-OUT =====");
//        System.out.println("1. Check-In Guest");
//        System.out.println("2. Check-Out Guest");
//        System.out.print("Enter choice: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        if (choice == 1) checkIn();
//        else if (choice == 2) checkOut();
//    }
//
//    private static void checkIn() throws SQLException {
//        System.out.print("Enter Booking ID: ");
//        int bookingId = scanner.nextInt();
//        System.out.print("Enter Staff ID (who is checking in): ");
//        int staffId = scanner.nextInt();
//        scanner.nextLine();
//
//        String sql = "INSERT INTO check_in_out (booking_id, actual_check_in, checked_in_by) VALUES (?, NOW(), ?)";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setInt(1, bookingId);
//        pstmt.setInt(2, staffId);
//
//        int rows = pstmt.executeUpdate();
//        if (rows > 0) {
//            String updateRoom = "UPDATE room r JOIN bookings b ON r.room_id = b.room_id " +
//                    "SET r.status = 'Occupied' WHERE b.booking_id = ?";
//            PreparedStatement updateStmt = connection.prepareStatement(updateRoom);
//            updateStmt.setInt(1, bookingId);
//            updateStmt.executeUpdate();
//            updateStmt.close();
//
//            System.out.println("Guest checked in successfully!");
//        }
//        pstmt.close();
//    }
//
//    private static void checkOut() throws SQLException {
//        System.out.print("Enter Booking ID: ");
//        int bookingId = scanner.nextInt();
//        System.out.print("Enter Staff ID (who is checking out): ");
//        int staffId = scanner.nextInt();
//        scanner.nextLine();
//
//        String sql = "UPDATE check_in_out SET actual_check_out = NOW(), checked_out_by = ? WHERE booking_id = ?";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setInt(1, staffId);
//        pstmt.setInt(2, bookingId);
//
//        int rows = pstmt.executeUpdate();
//        if (rows > 0) {
//            String updateRoom = "UPDATE room r JOIN bookings b ON r.room_id = b.room_id " +
//                    "SET r.status = 'Available' WHERE b.booking_id = ?";
//            PreparedStatement updateStmt = connection.prepareStatement(updateRoom);
//            updateStmt.setInt(1, bookingId);
//            updateStmt.executeUpdate();
//            updateStmt.close();
//
//            String updateBooking = "UPDATE bookings SET status = 'Completed' WHERE booking_id = ?";
//            PreparedStatement updateBookingStmt = connection.prepareStatement(updateBooking);
//            updateBookingStmt.setInt(1, bookingId);
//            updateBookingStmt.executeUpdate();
//            updateBookingStmt.close();
//
//            System.out.println("Guest checked out successfully!");
//        }
//        pstmt.close();
//    }
//
//    private static void restaurantManagement() throws SQLException {
//        System.out.println("\n===== RESTAURANT MANAGEMENT =====");
//        System.out.println("1. Add Menu Item");
//        System.out.println("2. View Menu");
//        System.out.println("3. Place Food Order");
//        System.out.println("4. View Food Orders");
//        System.out.print("Enter choice: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1: addMenuItem(); break;
//            case 2: viewMenu(); break;
//            case 3: placeFoodOrder(); break;
//            case 4: viewFoodOrders(); break;
//        }
//    }
//
//    private static void addMenuItem() throws SQLException {
//        System.out.print("Enter Item Name: ");
//        String itemName = scanner.nextLine();
//        System.out.print("Enter Category: ");
//        String category = scanner.nextLine();
//        System.out.print("Enter Price: ");
//        double price = scanner.nextDouble();
//        scanner.nextLine();
//
//        String sql = "INSERT INTO restaurant (item_name, category, price) VALUES (?, ?, ?)";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setString(1, itemName);
//        pstmt.setString(2, category);
//        pstmt.setDouble(3, price);
//
//        int rows = pstmt.executeUpdate();
//        if (rows > 0) {
//            System.out.println("Menu item added successfully!");
//        }
//        pstmt.close();
//    }
//
//    private static void viewMenu() throws SQLException {
//        String sql = "SELECT * FROM restaurant WHERE availability = TRUE";
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//
//        System.out.println("\n===== RESTAURANT MENU =====");
//        while (rs.next()) {
//            System.out.println("Item ID: " + rs.getInt("item_id") +
//                    " | Name: " + rs.getString("item_name") +
//                    " | Category: " + rs.getString("category") +
//                    " | Price: Rs." + rs.getDouble("price"));
//        }
//        rs.close();
//        stmt.close();
//    }
//
//    private static void placeFoodOrder() throws SQLException {
//        System.out.print("Enter Guest ID: ");
//        int guestId = scanner.nextInt();
//        System.out.print("Enter Room ID: ");
//        int roomId = scanner.nextInt();
//        System.out.print("Enter Item ID: ");
//        int itemId = scanner.nextInt();
//        System.out.print("Enter Quantity: ");
//        int quantity = scanner.nextInt();
//        scanner.nextLine();
//
//        String getPriceSql = "SELECT price FROM restaurant WHERE item_id = ?";
//        PreparedStatement getPriceStmt = connection.prepareStatement(getPriceSql);
//        getPriceStmt.setInt(1, itemId);
//        ResultSet rs = getPriceStmt.executeQuery();
//
//        if (rs.next()) {
//            double price = rs.getDouble("price");
//            double totalPrice = price * quantity;
//
//            String sql = "INSERT INTO food_order (guest_id, room_id, item_id, quantity, total_price) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement pstmt = connection.prepareStatement(sql);
//            pstmt.setInt(1, guestId);
//            pstmt.setInt(2, roomId);
//            pstmt.setInt(3, itemId);
//            pstmt.setInt(4, quantity);
//            pstmt.setDouble(5, totalPrice);
//
//            int rows = pstmt.executeUpdate();
//            if (rows > 0) {
//                System.out.println("Food order placed successfully! Total: Rs." + totalPrice);
//
//                // Manual synchronization to behave like BlockingQueue using ArrayDeque
//                synchronized (orderQueue) {
//                    while (orderQueue.size() >= MAX_ORDER_CAPACITY) {
//                        try {
//                            orderQueue.wait();
//                        } catch (InterruptedException e) {
//                            Thread.currentThread().interrupt();
//                        }
//                    }
//                    orderQueue.offer("Order for Guest " + guestId + " (Item: " + itemId + ")");
//                    orderQueue.notifyAll(); // Notify consumer that data is available
//                }
//            }
//            pstmt.close();
//        }
//        rs.close();
//        getPriceStmt.close();
//    }
//
//    private static void viewFoodOrders() throws SQLException {
//        String sql = "SELECT fo.order_id, g.name, r.room_number, i.item_name, fo.quantity, fo.total_price, fo.status " +
//                "FROM food_order fo " +
//                "JOIN guest g ON fo.guest_id = g.guest_id " +
//                "JOIN room r ON fo.room_id = r.room_id " +
//                "JOIN restaurant i ON fo.item_id = i.item_id";
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//
//        System.out.println("\n===== FOOD ORDERS =====");
//        while (rs.next()) {
//            System.out.println("Order ID: " + rs.getInt("order_id") +
//                    " | Guest: " + rs.getString("name") +
//                    " | Room: " + rs.getString("room_number") +
//                    " | Item: " + rs.getString("item_name") +
//                    " | Qty: " + rs.getInt("quantity") +
//                    " | Total: Rs." + rs.getDouble("total_price") +
//                    " | Status: " + rs.getString("status"));
//        }
//        rs.close();
//        stmt.close();
//    }
//
//    private static void gameZoneManagement() throws SQLException {
//        System.out.println("\n===== GAME ZONE MANAGEMENT =====");
//        System.out.println("1. Add Game");
//        System.out.println("2. View All Games");
//        System.out.println("3. Book Game");
//        System.out.println("4. View Game Bookings");
//        System.out.print("Enter choice: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1: addGame(); break;
//            case 2: viewAllGames(); break;
//            case 3: bookGame(); break;
//            case 4: viewGameBookings(); break;
//        }
//    }
//
//    private static void addGame() throws SQLException {
//        System.out.print("Enter Game Name: ");
//        String gameName = scanner.nextLine();
//        System.out.print("Enter Game Type: ");
//        String gameType = scanner.nextLine();
//        System.out.print("Enter Price Per Hour: ");
//        double pricePerHour = scanner.nextDouble();
//        scanner.nextLine();
//
//        String sql = "INSERT INTO play_zone (game_name, game_type, price_per_hour) VALUES (?, ?, ?)";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setString(1, gameName);
//        pstmt.setString(2, gameType);
//        pstmt.setDouble(3, pricePerHour);
//
//        int rows = pstmt.executeUpdate();
//        if (rows > 0) {
//            System.out.println("Game added successfully!");
//        }
//        pstmt.close();
//    }
//
//    private static void viewAllGames() throws SQLException {
//        String sql = "SELECT * FROM play_zone WHERE available = TRUE";
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//
//        System.out.println("\n===== GAME ZONE =====");
//        while (rs.next()) {
//            System.out.println("Game ID: " + rs.getInt("game_id") +
//                    " | Name: " + rs.getString("game_name") +
//                    " | Type: " + rs.getString("game_type") +
//                    " | Price/Hour: Rs." + rs.getDouble("price_per_hour"));
//        }
//        rs.close();
//        stmt.close();
//    }
//
//    private static void bookGame() throws SQLException {
//        System.out.print("Enter Guest ID: ");
//        int guestId = scanner.nextInt();
//        System.out.print("Enter Game ID: ");
//        int gameId = scanner.nextInt();
//        System.out.print("Enter Duration (hours): ");
//        double duration = scanner.nextDouble();
//        scanner.nextLine();
//
//        String getPriceSql = "SELECT price_per_hour FROM play_zone WHERE game_id = ?";
//        PreparedStatement getPriceStmt = connection.prepareStatement(getPriceSql);
//        getPriceStmt.setInt(1, gameId);
//        ResultSet rs = getPriceStmt.executeQuery();
//
//        if (rs.next()) {
//            double pricePerHour = rs.getDouble("price_per_hour");
//            double totalAmount = pricePerHour * duration;
//
//            String sql = "INSERT INTO game_bookings (guest_id, game_id, start_time, duration_hours, total_amount) VALUES (?, ?, NOW(), ?, ?)";
//            PreparedStatement pstmt = connection.prepareStatement(sql);
//            pstmt.setInt(1, guestId);
//            pstmt.setInt(2, gameId);
//            pstmt.setDouble(3, duration);
//            pstmt.setDouble(4, totalAmount);
//
//            int rows = pstmt.executeUpdate();
//            if (rows > 0) {
//                System.out.println("Game booked successfully! Total: Rs." + totalAmount);
//            }
//            pstmt.close();
//        }
//        rs.close();
//        getPriceStmt.close();
//    }
//
//    private static void viewGameBookings() throws SQLException {
//        String sql = "SELECT gb.game_booking_id, g.name, p.game_name, gb.duration_hours, gb.total_amount " +
//                "FROM game_bookings gb " +
//                "JOIN guest g ON gb.guest_id = g.guest_id " +
//                "JOIN play_zone p ON gb.game_id = p.game_id";
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//
//        System.out.println("\n===== GAME BOOKINGS =====");
//        while (rs.next()) {
//            System.out.println("Booking ID: " + rs.getInt("game_booking_id") +
//                    " | Guest: " + rs.getString("name") +
//                    " | Game: " + rs.getString("game_name") +
//                    " | Duration: " + rs.getDouble("duration_hours") + " hours" +
//                    " | Amount: Rs." + rs.getDouble("total_amount"));
//        }
//        rs.close();
//        stmt.close();
//    }
//
//    private static void paymentAndBilling() throws SQLException {
//        System.out.println("\n===== PAYMENT & BILLING =====");
//        System.out.println("1. Generate Billing Folio");
//        System.out.println("2. Make Payment");
//        System.out.println("3. View Payment History");
//        System.out.print("Enter choice: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1: generateBillingFolio(); break;
//            case 2: makePayment(); break;
//            case 3: viewPaymentHistory(); break;
//        }
//    }
//
//    private static void generateBillingFolio() throws SQLException {
//        System.out.print("Enter Guest ID: ");
//        int guestId = scanner.nextInt();
//        System.out.print("Enter Booking ID: ");
//        int bookingId = scanner.nextInt();
//        scanner.nextLine();
//
//        String roomChargesSql = "SELECT total_amount FROM bookings WHERE booking_id = ?";
//        PreparedStatement roomStmt = connection.prepareStatement(roomChargesSql);
//        roomStmt.setInt(1, bookingId);
//        ResultSet roomRs = roomStmt.executeQuery();
//        double roomCharges = 0;
//        if (roomRs.next()) {
//            roomCharges = roomRs.getDouble("total_amount");
//        }
//        roomRs.close();
//        roomStmt.close();
//
//        String foodChargesSql = "SELECT SUM(total_price) as food_total FROM food_order WHERE guest_id = ?";
//        PreparedStatement foodStmt = connection.prepareStatement(foodChargesSql);
//        foodStmt.setInt(1, guestId);
//        ResultSet foodRs = foodStmt.executeQuery();
//        double foodCharges = 0;
//        if (foodRs.next()) {
//            foodCharges = foodRs.getDouble("food_total");
//        }
//        foodRs.close();
//        foodStmt.close();
//
//        String gameChargesSql = "SELECT SUM(total_amount) as game_total FROM game_bookings WHERE guest_id = ?";
//        PreparedStatement gameStmt = connection.prepareStatement(gameChargesSql);
//        gameStmt.setInt(1, guestId);
//        ResultSet gameRs = gameStmt.executeQuery();
//        double gameCharges = 0;
//        if (gameRs.next()) {
//            gameCharges = gameRs.getDouble("game_total");
//        }
//        gameRs.close();
//        gameStmt.close();
//
//        double serviceCharges = (roomCharges + foodCharges + gameCharges) * 0.05;
//        double subtotal = roomCharges + foodCharges + serviceCharges + gameCharges;
//        double gstPercentage = 18.00;
//        double gstAmount = subtotal * (gstPercentage / 100);
//        double totalAmount = subtotal + gstAmount;
//
//        String sql = "INSERT INTO billing_folio (guest_id, booking_id, room_charges, food_charges, service_charges, game_charges, subtotal, gst_amount, gst_percentage, total_amount) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setInt(1, guestId);
//        pstmt.setInt(2, bookingId);
//        pstmt.setDouble(3, roomCharges);
//        pstmt.setDouble(4, foodCharges);
//        pstmt.setDouble(5, serviceCharges);
//        pstmt.setDouble(6, gameCharges);
//        pstmt.setDouble(7, subtotal);
//        pstmt.setDouble(8, gstAmount);
//        pstmt.setDouble(9, gstPercentage);
//        pstmt.setDouble(10, totalAmount);
//
//        int rows = pstmt.executeUpdate();
//        if (rows > 0) {
//            System.out.println("\n========== BILLING FOLIO ==========");
//            System.out.println("Guest ID: " + guestId);
//            System.out.println("Booking ID: " + bookingId);
//            System.out.println("Room Charges: Rs." + String.format("%.2f", roomCharges));
//            System.out.println("Food Charges: Rs." + String.format("%.2f", foodCharges));
//            System.out.println("Game Zone Charges: Rs." + String.format("%.2f", gameCharges));
//            System.out.println("Service Charges: Rs." + String.format("%.2f", serviceCharges));
//            System.out.println("Subtotal: Rs." + String.format("%.2f", subtotal));
//            System.out.println("GST (" + gstPercentage + "%): Rs." + String.format("%.2f", gstAmount));
//            System.out.println("====================================");
//            System.out.println("TOTAL AMOUNT: Rs." + String.format("%.2f", totalAmount));
//            System.out.println("====================================");
//        }
//        pstmt.close();
//    }
//
//    private static void makePayment() throws SQLException {
//        System.out.print("Enter Booking ID: ");
//        int bookingId = scanner.nextInt();
//        System.out.print("Enter Amount: ");
//        double amount = scanner.nextDouble();
//        scanner.nextLine();
//        System.out.print("Enter Payment Method (Cash/Card/UPI): ");
//        String method = scanner.nextLine();
//
//        String sql = "INSERT INTO payment (booking_id, amount, payment_method) VALUES (?, ?, ?)";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setInt(1, bookingId);
//        pstmt.setDouble(2, amount);
//        pstmt.setString(3, method);
//
//        int rows = pstmt.executeUpdate();
//        if (rows > 0) {
//            System.out.println("Payment successful! Amount: Rs." + amount);
//        }
//        pstmt.close();
//    }
//
//    private static void viewPaymentHistory() throws SQLException {
//        String sql = "SELECT p.payment_id, b.booking_id, g.name, p.amount, p.payment_method, p.payment_date, p.status " +
//                "FROM payment p " +
//                "JOIN bookings b ON p.booking_id = b.booking_id " +
//                "JOIN guest g ON b.guest_id = g.guest_id";
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//
//        System.out.println("\n===== PAYMENT HISTORY =====");
//        while (rs.next()) {
//            System.out.println("Payment ID: " + rs.getInt("payment_id") +
//                    " | Booking ID: " + rs.getInt("booking_id") +
//                    " | Guest: " + rs.getString("name") +
//                    " | Amount: Rs." + rs.getDouble("amount") +
//                    " | Method: " + rs.getString("payment_method") +
//                    " | Date: " + rs.getString("payment_date") +
//                    " | Status: " + rs.getString("status"));
//        }
//        rs.close();
//        stmt.close();
//    }
//
//    private static void staffManagement() throws SQLException {
//        System.out.println("\n===== STAFF MANAGEMENT =====");
//        System.out.println("1. Add Staff");
//        System.out.println("2. View All Staff");
//        System.out.print("Enter choice: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        if (choice == 1) addStaff();
//        else if (choice == 2) viewAllStaff();
//    }
//
//    private static void addStaff() throws SQLException {
//        System.out.print("Enter Staff Name: ");
//        String name = scanner.nextLine();
//        System.out.print("Enter Designation: ");
//        String designation = scanner.nextLine();
//        System.out.print("Enter Phone: ");
//        String phone = scanner.nextLine();
//        System.out.print("Enter Salary: ");
//        double salary = scanner.nextDouble();
//        scanner.nextLine();
//        System.out.print("Enter Join Date (YYYY-MM-DD): ");
//        String joinDate = scanner.nextLine();
//        System.out.print("Enter Shift: ");
//        String shift = scanner.nextLine();
//
//        String sql = "INSERT INTO staff (name, designation, phone, salary, join_date, shift) VALUES (?, ?, ?, ?, ?, ?)";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setString(1, name);
//        pstmt.setString(2, designation);
//        pstmt.setString(3, phone);
//        pstmt.setDouble(4, salary);
//        pstmt.setString(5, joinDate);
//        pstmt.setString(6, shift);
//
//        int rows = pstmt.executeUpdate();
//        if (rows > 0) {
//            System.out.println("Staff added successfully!");
//        }
//        pstmt.close();
//    }
//
//    private static void viewAllStaff() throws SQLException {
//        String sql = "SELECT * FROM staff";
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//
//        System.out.println("\n===== ALL STAFF =====");
//        while (rs.next()) {
//            System.out.println("ID: " + rs.getInt("staff_id") +
//                    " | Name: " + rs.getString("name") +
//                    " | Designation: " + rs.getString("designation") +
//                    " | Phone: " + rs.getString("phone") +
//                    " | Salary: Rs." + rs.getDouble("salary") +
//                    " | Shift: " + rs.getString("shift"));
//        }
//        rs.close();
//        stmt.close();
//    }
//
//    private static void feedbackManagement() throws SQLException {
//        System.out.println("\n===== FEEDBACK MANAGEMENT =====");
//        System.out.println("1. Add Feedback");
//        System.out.println("2. View All Feedback");
//        System.out.print("Enter choice: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        if (choice == 1) addFeedback();
//        else if (choice == 2) viewAllFeedback();
//    }
//
//    private static void addFeedback() throws SQLException {
//        System.out.print("Enter Guest ID: ");
//        int guestId = scanner.nextInt();
//        System.out.print("Enter Rating (1-5): ");
//        int rating = scanner.nextInt();
//        scanner.nextLine();
//        System.out.print("Enter Comments: ");
//        String comments = scanner.nextLine();
//
//        String sql = "INSERT INTO feedback (guest_id, rating, comments) VALUES (?, ?, ?)";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setInt(1, guestId);
//        pstmt.setInt(2, rating);
//        pstmt.setString(3, comments);
//
//        int rows = pstmt.executeUpdate();
//        if (rows > 0) {
//            System.out.println("Feedback submitted successfully!");
//        }
//        pstmt.close();
//    }
//
//    private static void viewAllFeedback() throws SQLException {
//        String sql = "SELECT f.feedback_id, g.name, f.rating, f.comments, f.feedback_date " +
//                "FROM feedback f JOIN guest g ON f.guest_id = g.guest_id";
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//
//        System.out.println("\n===== ALL FEEDBACK =====");
//        while (rs.next()) {
//            System.out.println("Feedback ID: " + rs.getInt("feedback_id") +
//                    " | Guest: " + rs.getString("name") +
//                    " | Rating: " + rs.getInt("rating") + "/5" +
//                    " | Comments: " + rs.getString("comments") +
//                    " | Date: " + rs.getString("feedback_date"));
//        }
//        rs.close();
//        stmt.close();
//    }
//
//    private static void demonstrateDataStructures() {
//        System.out.println("\n===== DATA STRUCTURE DEMONSTRATIONS =====");
//        System.out.println("1. ArrayList Demo");
//        System.out.println("2. LinkedList Demo");
//        System.out.println("3. ArrayDeque Demo");
//        System.out.println("4. Collection Operations");
//        System.out.print("Enter choice: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1: arrayListDemo(); break;
//            case 2: linkedListDemo(); break;
//            case 3: arrayDequeDemo(); break;
//            case 4: collectionOperations(); break;
//        }
//    }
//
//    private static void arrayListDemo() {
//        System.out.println("\n===== ARRAYLIST DEMONSTRATION =====");
//        ArrayList<String> guests = new ArrayList<>();
//
//        System.out.print("Enter number of guests to add: ");
//        int n = scanner.nextInt();
//        scanner.nextLine();
//
//        for (int i = 0; i < n; i++) {
//            System.out.print("Enter guest name " + (i + 1) + ": ");
//            String name = scanner.nextLine();
//            guests.add(name);
//        }
//
//        System.out.println("\nGuests in ArrayList:");
//        for (int i = 0; i < guests.size(); i++) {
//            System.out.println((i + 1) + ". " + guests.get(i));
//        }
//
//        System.out.print("\nEnter name to search: ");
//        String searchName = scanner.nextLine();
//        if (guests.contains(searchName)) {
//            System.out.println("Found at index: " + guests.indexOf(searchName));
//        } else {
//            System.out.println("Not found!");
//        }
//
//        System.out.print("\nEnter index to remove: ");
//        int index = scanner.nextInt();
//        scanner.nextLine();
//        if (index >= 0 && index < guests.size()) {
//            String removed = guests.remove(index);
//            System.out.println("Removed: " + removed);
//        }
//
//        Collections.sort(guests);
//        System.out.println("\nSorted Guests:");
//        for (String guest : guests) {
//            System.out.println("- " + guest);
//        }
//
//        System.out.println("\nSize before clear: " + guests.size());
//        guests.clear();
//        System.out.println("Size after clear: " + guests.size());
//    }
//
//    private static void linkedListDemo() {
//        System.out.println("\n===== LINKEDLIST DEMONSTRATION =====");
//        LinkedList<String> bookings = new LinkedList<>();
//
//        System.out.print("Enter number of bookings to add: ");
//        int n = scanner.nextInt();
//        scanner.nextLine();
//
//        for (int i = 0; i < n; i++) {
//            System.out.print("Enter booking info " + (i + 1) + ": ");
//            String booking = scanner.nextLine();
//            bookings.add(booking);
//        }
//
//        System.out.print("\nEnter booking to add at first: ");
//        String first = scanner.nextLine();
//        bookings.addFirst(first);
//
//        System.out.print("Enter booking to add at last: ");
//        String last = scanner.nextLine();
//        bookings.addLast(last);
//
//        System.out.println("\nAll Bookings:");
//        for (String booking : bookings) {
//            System.out.println("- " + booking);
//        }
//
//        System.out.println("\nFirst booking: " + bookings.getFirst());
//        System.out.println("Last booking: " + bookings.getLast());
//
//        System.out.println("\nRemoving first: " + bookings.removeFirst());
//        System.out.println("Removing last: " + bookings.removeLast());
//
//        System.out.println("\nBookings after removal:");
//        for (int i = 0; i < bookings.size(); i++) {
//            System.out.println((i + 1) + ". " + bookings.get(i));
//        }
//
//        Collections.reverse(bookings);
//        System.out.println("\nReversed Bookings:");
//        for (String booking : bookings) {
//            System.out.println("- " + booking);
//        }
//    }
//
//    private static void arrayDequeDemo() {
//        System.out.println("\n===== ARRAYDEQUE DEMONSTRATION =====");
//
//        // Fix 5: Using the global variable directly rather than shadowing it
//        serviceQueue.clear();
//
//        System.out.print("Enter number of service requests: ");
//        int n = scanner.nextInt();
//        scanner.nextLine();
//
//        for (int i = 0; i < n; i++) {
//            System.out.print("Enter service request " + (i + 1) + ": ");
//            String service = scanner.nextLine();
//            serviceQueue.offer(service);
//        }
//
//        System.out.println("\nService Queue:");
//        for (String service : serviceQueue) {
//            System.out.println("- " + service);
//        }
//
//        System.out.println("\nProcessing services:");
//        while (!serviceQueue.isEmpty()) {
//            System.out.println("Processing: " + serviceQueue.poll());
//        }
//
//        System.out.println("\nQueue is now empty: " + serviceQueue.isEmpty());
//    }
//
//    private static void collectionOperations() {
//        System.out.println("\n===== COLLECTION OPERATIONS =====");
//
//        ArrayList<String> list1 = new ArrayList<>();
//        ArrayList<String> list2 = new ArrayList<>();
//
//        System.out.print("Enter number of items for list 1: ");
//        int n1 = scanner.nextInt();
//        scanner.nextLine();
//        for (int i = 0; i < n1; i++) {
//            System.out.print("Item " + (i + 1) + ": ");
//            list1.add(scanner.nextLine());
//        }
//
//        System.out.print("\nEnter number of items for list 2: ");
//        int n2 = scanner.nextInt();
//        scanner.nextLine();
//        for (int i = 0; i < n2; i++) {
//            System.out.print("Item " + (i + 1) + ": ");
//            list2.add(scanner.nextLine());
//        }
//
//        ArrayList<String> union = new ArrayList<>(list1);
//        union.addAll(list2);
//        System.out.println("\nUnion: " + union);
//
//        ArrayList<String> intersection = new ArrayList<>(list1);
//        intersection.retainAll(list2);
//        System.out.println("Intersection: " + intersection);
//
//        ArrayList<String> difference = new ArrayList<>(list1);
//        difference.removeAll(list2);
//        System.out.println("Difference (list1 - list2): " + difference);
//    }
//
//    private static void demonstrateMultithreading() {
//        System.out.println("\n===== MULTITHREADING DEMONSTRATIONS =====");
//        System.out.println("1. Basic Thread Demo");
//        System.out.println("2. Synchronized Method Demo");
//        System.out.println("3. Producer-Consumer Info");
//        System.out.print("Enter choice: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1: basicThreadDemo(); break;
//            case 2: synchronizedDemo(); break;
//            case 3: producerConsumerInfo(); break;
//        }
//    }
//
//    private static void basicThreadDemo() {
//        System.out.println("\n===== BASIC THREAD DEMO =====");
//
//        Thread t1 = new Thread(() -> {
//            for (int i = 1; i <= 5; i++) {
//                System.out.println("Thread 1 - Count: " + i);
//                try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
//            }
//        });
//
//        Thread t2 = new Thread(() -> {
//            for (int i = 1; i <= 5; i++) {
//                System.out.println("Thread 2 - Count: " + i);
//                try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
//            }
//        });
//
//        t1.start();
//        t2.start();
//
//        try {
//            t1.join();
//            t2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Both threads completed!");
//    }
//
//    private static void synchronizedDemo() {
//        System.out.println("\n===== SYNCHRONIZED METHOD DEMO =====");
//
//        Counter counter = new Counter();
//
//        Thread t1 = new Thread(() -> {
//            for (int i = 0; i < 1000; i++) counter.increment();
//        });
//
//        Thread t2 = new Thread(() -> {
//            for (int i = 0; i < 1000; i++) counter.increment();
//        });
//
//        t1.start();
//        t2.start();
//
//        try {
//            t1.join();
//            t2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Final count (should be 2000): " + counter.getCount());
//    }
//
//    private static void producerConsumerInfo() {
//        System.out.println("\n===== PRODUCER-CONSUMER INFORMATION =====");
//        System.out.println("Main Thread (User) acts as the Producer by placing food orders.");
//        System.out.println("Kitchen Consumer thread is running in background to process them!");
//
//        // Fix to calculate remaining capacity manually since ArrayDeque doesn't have it
//        synchronized (orderQueue) {
//            int currentSize = orderQueue.size();
//            int remaining = MAX_ORDER_CAPACITY - currentSize;
//            System.out.println("\nCurrent Queue size: " + currentSize);
//            System.out.println("Remaining capacity: " + (remaining > 0 ? remaining : 0));
//        }
//    }
//
//    // Fix 6: Implemented manual wait/notify consumer logic to support ArrayDeque thread safety
//    private static void startConsumer() {
//        Thread consumer = new Thread(() -> {
//            while (true) {
//                String order = null;
//                try {
//                    synchronized (orderQueue) {
//                        while (orderQueue.isEmpty()) {
//                            orderQueue.wait(); // Wait until producer adds an item
//                        }
//                        order = orderQueue.poll();
//                        orderQueue.notifyAll(); // Notify producer that space is available
//                    }
//
//                    if (order != null) {
//                        System.out.println("\n[KITCHEN] Processing: " + order);
//                        Thread.sleep(4000); // Simulate processing time (4 seconds)
//                        System.out.println("\n[KITCHEN] Completed: " + order);
//                    }
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                    break;
//                }
//            }
//        });
//        consumer.setDaemon(true);
//        consumer.start();
//    }
//}
//
//class Counter {
//    private int count = 0;
//
//    public synchronized void increment() {
//        count++;
//    }
//
//    public synchronized int getCount() {
//        return count;
//    }
//}