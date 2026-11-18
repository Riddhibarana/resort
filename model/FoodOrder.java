package model;
public class FoodOrder {

    private int orderId;
    private int customerId;
    private int bookingId;
    private int foodId;
    private int quantity;
    private double totalBill;
    private String orderStatus;
    private String foodName;


    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }


    public FoodOrder() {

    }


    public int getOrderId() {
        return orderId;
    }


    public void setOrderId(int orderId) {
        this.orderId = orderId;
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


    public int getFoodId() {
        return foodId;
    }


    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }


    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public double getTotalBill() {
        return totalBill;
    }


    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }


    public String getOrderStatus() {
        return orderStatus;
    }


    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }



    public void displayOrder(){

        System.out.println("----------------------------");
        System.out.println("Order ID : "+orderId);
        System.out.println("Customer ID : "+customerId);
        System.out.println("Booking ID : "+bookingId);
        System.out.println("Food ID : "+foodId);
        System.out.println("Quantity : "+quantity);
        System.out.println("Total Bill : "+totalBill);
        System.out.println("Status : "+orderStatus);
        System.out.println("----------------------------");

    }
    @Override
    public String toString() {

        return "Food ID: " + foodId +
                ", Customer ID: " + customerId +
                ", Booking ID: " + bookingId +
                ", Quantity: " + quantity +
                ", Total Bill: ₹" + totalBill +
                ", Status: " + orderStatus;

    }

}