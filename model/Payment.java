package model;

import java.time.LocalDate;

public class Payment {

    private int paymentId;
    private int customerId;
    private int bookingId;

    private double roomCharges;
    private double restaurantCharges;
    private double gameCharges;

    private double subTotal;
    private double gst;
    private double totalAmount;

    private String paymentMethod;
    private LocalDate paymentDate;
    private String paymentStatus;
    private String customerName;
    // Default Constructor
    public Payment() {

    }

    // Parameterized Constructor
    public Payment(int paymentId, int customerId, int bookingId,
                   double roomCharges, double restaurantCharges,
                   double gameCharges, double subTotal,
                   double gst, double totalAmount,
                   String paymentMethod,
                   LocalDate paymentDate,
                   String paymentStatus) {

        this.paymentId = paymentId;
        this.customerId = customerId;
        this.bookingId = bookingId;
        this.roomCharges = roomCharges;
        this.restaurantCharges = restaurantCharges;
        this.gameCharges = gameCharges;
        this.subTotal = subTotal;
        this.gst = gst;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getRoomCharges() {
        return roomCharges;
    }

    public void setRoomCharges(double roomCharges) {
        this.roomCharges = roomCharges;
    }

    public double getRestaurantCharges() {
        return restaurantCharges;
    }

    public void setRestaurantCharges(double restaurantCharges) {
        this.restaurantCharges = restaurantCharges;
    }

    public double getGameCharges() {
        return gameCharges;
    }

    public void setGameCharges(double gameCharges) {
        this.gameCharges = gameCharges;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Display Method
    public void displayPayment() {

        System.out.println("\n========== PAYMENT DETAILS ==========");
        System.out.println("Payment ID          : " + paymentId);
        System.out.println("Customer ID         : " + customerId);
        System.out.println("Booking ID          : " + bookingId);
        System.out.println("Room Charges        : ₹" + roomCharges);
        System.out.println("Restaurant Charges  : ₹" + restaurantCharges);
        System.out.println("Game Charges        : ₹" + gameCharges);
        System.out.println("Sub Total           : ₹" + subTotal);
        System.out.println("GST                 : ₹" + gst);
        System.out.println("Total Amount        : ₹" + totalAmount);
        System.out.println("Payment Method      : " + paymentMethod);
        System.out.println("Payment Date        : " + paymentDate);
        System.out.println("Payment Status      : " + paymentStatus);
        System.out.println("=====================================");
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}