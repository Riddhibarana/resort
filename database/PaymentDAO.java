package database;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;
import Module.ReceiptFile;
import model.Payment;
import java.sql.*;

public class PaymentDAO {


    CustomerDAO customerDAO = new CustomerDAO();
    BookingDAO bookingDAO = new BookingDAO();
    FoodOrderDAO foodOrderDAO = new FoodOrderDAO();



    // ================= SAVE PAYMENT =================

    public void savePayment(Payment payment) {


        if(payment == null) {

            System.out.println("Invalid Payment Data.");
            return;

        }


        String query =
                "INSERT INTO payment(customerId,bookingId," +
                        "roomCharges,restaurantCharges,gameCharges," +
                        "subTotal,gst,totalAmount,paymentMethod," +
                        "paymentDate,paymentStatus)" +
                        " VALUES(?,?,?,?,?,?,?,?,?,?,?)";


        try {


            Connection con =
                    DBConnection.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(query);



            ps.setInt(1, payment.getCustomerId());
            if (payment.getBookingId() == 0) {
                ps.setNull(2, java.sql.Types.INTEGER);
            } else {
                ps.setInt(2, payment.getBookingId());
            }
            ps.setDouble(3, payment.getRoomCharges());

            ps.setDouble(4, payment.getRestaurantCharges());

            ps.setDouble(5, payment.getGameCharges());

            ps.setDouble(6, payment.getSubTotal());

            ps.setDouble(7, payment.getGst());

            ps.setDouble(8, payment.getTotalAmount());

            ps.setString(9, payment.getPaymentMethod());

            ps.setDate(10,
                    java.sql.Date.valueOf(
                            payment.getPaymentDate()));

            ps.setString(11,
                    payment.getPaymentStatus());



            int rows =
                    ps.executeUpdate();


            if(rows > 0) {

                updateOrdersAndBookingsToPaid(payment.getCustomerId());
                return;
            }


        }
        catch(Exception e) {

            e.printStackTrace();

        }

    }







    // ================= UPDATE STATUS AFTER PAYMENT =================


    public void updateOrdersAndBookingsToPaid(int customerId) {


        String updateFood = "UPDATE food_order SET orderStatus='Paid' WHERE customerId=? AND orderStatus='Ordered'";


        String updateGame = "UPDATE game_booking SET bookingStatus='Paid' WHERE customerId=? AND bookingStatus='Booked'";


        try (Connection con = DBConnection.getConnection()) {


            try (PreparedStatement ps1 = con.prepareStatement(updateFood)) {


                ps1.setInt(1, customerId);


                ps1.executeUpdate();


            }


            try (PreparedStatement ps2 = con.prepareStatement(updateGame)) {


                ps2.setInt(1, customerId);


                ps2.executeUpdate();


            }


        } catch (Exception e) {


            e.printStackTrace();


        }


    }







    // ================= CUSTOMER ID =================


    public int getCustomerId(String customerName) {


        if(customerName == null ||
                customerName.trim().isEmpty()) {

            return -1;

        }


        return customerDAO.getCustomerIdByName(customerName);


    }







    // ================= BOOKING ID =================


    public int getBookingId(int customerId) {


        return bookingDAO.getBookingIdByCustomerId(customerId);


    }







    // ================= ROOM CHARGES =================


    public double getRoomCharges(int bookingId) {


        return bookingDAO.getRoomCharges(bookingId);


    }







    // ================= RESTAURANT CHARGES =================


    public double getRestaurantCharges(int customerId) {


        return foodOrderDAO.getRestaurantBill(customerId);


    }







    // ================= GAME CHARGES =================


    public double getGameCharges(int customerId) {


        double amount = 0;


        String query =
                "SELECT IFNULL(SUM(totalAmount),0) " +
                        "FROM game_booking WHERE customerId=? AND bookingStatus='Booked'";


        try {


            Connection con =
                    DBConnection.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(query);


            ps.setInt(1, customerId);


            ResultSet rs =
                    ps.executeQuery();



            if(rs.next()) {


                amount =
                        rs.getDouble(1);

            }


        }
        catch(Exception e) {


            e.printStackTrace();

        }


        return amount;


    }







    // ================= CALCULATION =================


    public double calculateSubTotal(double room,
                                    double food,
                                    double game) {


        return room + food + game;

    }





    public double calculateGST(double subtotal) {


        return subtotal * 18 / 100;

    }





    public double calculateGrandTotal(double subtotal,
                                      double gst) {


        return subtotal + gst;

    }







    // ================= CHECK IF ROOM IS PAID =================


    public boolean isRoomPaid(int bookingId) {


        if (bookingId <= 0) {


            return true;


        }


        boolean paid = false;


        String query = "SELECT COUNT(*) FROM payment WHERE bookingId=? AND roomCharges > 0 AND paymentStatus='Paid'";


        try (Connection con = DBConnection.getConnection();


             PreparedStatement ps = con.prepareStatement(query)) {


            ps.setInt(1, bookingId);


            ResultSet rs = ps.executeQuery();


            if (rs.next()) {


                paid = rs.getInt(1) > 0;


            }


        } catch (Exception e) {


            e.printStackTrace();


        }


        return paid;


    }







    // ================= GENERATE PAYMENT =================


    public Payment generatePayment(int customerId,
                                   String paymentMethod) {

        Payment payment = new Payment();

        int bookingId = getBookingId(customerId);

        // Booking Optional
        if (bookingId == -1) {
            bookingId = 0;
        }

        double roomCharges = 0;

        if (bookingId != 0 && !isRoomPaid(bookingId)) {
            roomCharges = getRoomCharges(bookingId);
        }

        double restaurantCharges = getRestaurantCharges(customerId);
        double gameCharges = getGameCharges(customerId);

        double totalSubTotalCalculated = calculateSubTotal(
                roomCharges,
                restaurantCharges,
                gameCharges);

        if (totalSubTotalCalculated <= 0) {

            System.out.println("Payment Already Done.");
            return null;

        }

        double totalGstCalculated = calculateGST(totalSubTotalCalculated);

        double grandTotalAmount = calculateGrandTotal(totalSubTotalCalculated, totalGstCalculated);

        payment.setCustomerId(customerId);
        payment.setCustomerName(customerDAO.getCustomerNameById(customerId));
        payment.setBookingId(bookingId);

        payment.setRoomCharges(roomCharges);
        payment.setRestaurantCharges(restaurantCharges);
        payment.setGameCharges(gameCharges);

        payment.setSubTotal(totalSubTotalCalculated);
        payment.setGst(totalGstCalculated);

        payment.setTotalAmount(grandTotalAmount);

        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentStatus("Paid");

        return payment;
    }







    // ================= DISPLAY RECEIPT =================


    public void displayReceipt(Payment payment) {


        if(payment == null) {

            return;

        }



        System.out.println(
                "\n==========================================");

        System.out.println(
                "          RESORT PAYMENT RECEIPT");

        System.out.println(
                "==========================================");



        System.out.println(
                "Customer ID         : "
                        + payment.getCustomerId());
        System.out.println(
                "Customer Name       : "
                        + payment.getCustomerName());


        System.out.println(
                "Booking ID          : "
                        + payment.getBookingId());



        System.out.println(
                "------------------------------------------");



        System.out.println(
                "Room Charges        : ₹"
                        + payment.getRoomCharges());



        System.out.println(
                "Restaurant Charges  : ₹"
                        + payment.getRestaurantCharges());



        System.out.println(
                "Game Charges        : ₹"
                        + payment.getGameCharges());



        System.out.println(
                "------------------------------------------");



        System.out.println(
                "Sub Total           : ₹"
                        + payment.getSubTotal());



        System.out.println(
                "GST (18%)           : ₹"
                        + payment.getGst());



        System.out.println(
                "------------------------------------------");



        double pendingSubTotal =
                payment.getTotalAmount() / 1.18;

        double pendingGST =
                payment.getTotalAmount() - pendingSubTotal;


        System.out.println(
                "Pending Sub Total   : ₹"
                        + pendingSubTotal);


        System.out.println(
                "Pending GST (18%)   : ₹"
                        + pendingGST);


        System.out.println(
                "Pending Amount      : ₹"
                        + payment.getTotalAmount());
        System.out.println("----------------------------------------------------------------");


        System.out.println("Grand Total         : ₹"
                + payment.getTotalAmount());

        System.out.println("==========================================");
    }








    // ================= MAKE PAYMENT =================

    public void makePayment(int customerId,
                            String paymentMethod) {

        Payment payment =
                generatePayment(customerId, paymentMethod);

        if(payment == null) {

            return;

        }

        savePayment(payment);

        displayReceipt(payment);

        Scanner sc = new Scanner(System.in);

        System.out.print("\nDo You Want To Print Receipt? (Y/N) : ");

        String choice = sc.nextLine();

        if(choice.equalsIgnoreCase("Y")) {

            ReceiptFile receipt = new ReceiptFile();

            receipt.generateReceipt(payment);

        }
        else {

            System.out.println("Thank You For Visiting Our Resort.");

        }

    }







    // ================= DISPLAY PAYMENT HISTORY =================


    public void displayPayments() {


        String query =
                "SELECT p.*, c.name " +
                        "FROM payment p " +
                        "JOIN customer c " +
                        "ON p.customerId=c.customerId";



        try {


            Connection con =
                    DBConnection.getConnection();


            Statement st =
                    con.createStatement();


            ResultSet rs =
                    st.executeQuery(query);



            System.out.println(
                    "\n========== PAYMENT HISTORY ==========");



            while(rs.next()) {


                System.out.println(
                        "\n------------------------------------------");


                System.out.println(
                        "Payment ID          : "
                                + rs.getInt("paymentId"));


                System.out.println(
                        "Customer Name       : "
                                + rs.getString("name"));


                System.out.println(
                        "Customer ID         : "
                                + rs.getInt("customerId"));


                System.out.println(
                        "Booking ID          : "
                                + rs.getInt("bookingId"));



                System.out.println(
                        "------------------------------------------");



                System.out.println(
                        "Room Charges        : ₹"
                                + rs.getDouble("roomCharges"));


                System.out.println(
                        "Restaurant Charges  : ₹"
                                + rs.getDouble("restaurantCharges"));


                System.out.println(
                        "Game Charges        : ₹"
                                + rs.getDouble("gameCharges"));



                System.out.println(
                        "------------------------------------------");



                System.out.println(
                        "Sub Total           : ₹"
                                + rs.getDouble("subTotal"));



                System.out.println(
                        "GST (18%)           : ₹"
                                + rs.getDouble("gst"));



                System.out.println(
                        "Pending Amount      : ₹"
                                + rs.getDouble("totalAmount"));



                System.out.println(
                        "Grand Total         : ₹"
                                + rs.getDouble("totalAmount"));



                System.out.println(
                        "Payment Method      : "
                                + rs.getString("paymentMethod"));



                System.out.println(
                        "Payment Date        : "
                                + rs.getDate("paymentDate"));



                System.out.println(
                        "Payment Status      : "
                                + rs.getString("paymentStatus"));



            }



            System.out.println(
                    "==========================================");



        }
        catch(Exception e) {


            e.printStackTrace();


        }


    }







    // ================= CHECK PAYMENT =================







    // ================= CUSTOMER OWN TRANSACTION =================

    public void showMyTransaction(int customerId) {


        String query =
                "SELECT * FROM payment WHERE customerId=?";


        try {


            Connection con =
                    DBConnection.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(query);


            ps.setInt(1, customerId);


            ResultSet rs =
                    ps.executeQuery();



            boolean found = false;



            System.out.println("\n========== MY TRANSACTION ==========");



            while(rs.next()) {


                found = true;


                System.out.println("-----------------------------------");


                System.out.println(
                        "Payment ID : "
                                + rs.getInt("paymentId"));


                System.out.println(
                        "Booking ID : "
                                + rs.getInt("bookingId"));



                System.out.println(
                        "Room Charges : ₹"
                                + rs.getDouble("roomCharges"));



                System.out.println(
                        "Restaurant Charges : ₹"
                                + rs.getDouble("restaurantCharges"));



                System.out.println(
                        "Game Charges : ₹"
                                + rs.getDouble("gameCharges"));



                System.out.println(
                        "Sub Total : ₹"
                                + rs.getDouble("subTotal"));



                System.out.println(
                        "GST : ₹"
                                + rs.getDouble("gst"));



                System.out.println(
                        "Total Amount : ₹"
                                + rs.getDouble("totalAmount"));



                System.out.println(
                        "Payment Method : "
                                + rs.getString("paymentMethod"));



                System.out.println(
                        "Payment Date : "
                                + rs.getDate("paymentDate"));



                System.out.println(
                        "Status : "
                                + rs.getString("paymentStatus"));


                System.out.println("-----------------------------------");

            }



            if(!found) {

                System.out.println(
                        "No Transaction Found.");

            }



            System.out.println(
                    "===================================");



            rs.close();
            ps.close();
            con.close();



        }
        catch(Exception e) {


            System.out.println(
                    "Unable to display transaction : "
                            + e.getMessage());

        }

    }







    public double getAlreadyPaidAmount(int customerId) {

        double amount = 0;

        String query =
                "SELECT IFNULL(SUM(totalAmount),0) " +
                        "FROM payment " +
                        "WHERE customerId=? " +
                        "AND paymentStatus='Paid'";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                amount = rs.getDouble(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return amount;
    }







    public boolean isPaymentDone(int customerId) {

        int bookingId = getBookingId(customerId);

        double room = 0;

        if (bookingId != -1 && !isRoomPaid(bookingId)) {

            room = getRoomCharges(bookingId);

        }

        double food = getRestaurantCharges(customerId);

        double game = getGameCharges(customerId);

        double subtotal = room + food + game;

        return subtotal <= 0;
    }







    public boolean hasPendingPayment(int customerId) {

        return !isPaymentDone(customerId);

    }
}