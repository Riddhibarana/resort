package service;

import datastructure.OrderDeque;
import database.FoodOrderDAO;
import model.FoodOrder;


public class FoodOrderService {


    private FoodOrderDAO dao;
    private OrderDeque orderQueue = new OrderDeque(100);


    public FoodOrderService(){

        dao = new FoodOrderDAO();

    }
    // ================= ADD ORDER BY ID =================
    public void addOrder(FoodOrder order)
    {
        if(!dao.checkCustomerId(order.getCustomerId())){
            System.out.println("Invalid Customer");
            return;
        }

        // Booking optional

        if(order.getBookingId() != 0 &&
                !dao.checkBookingId(order.getBookingId())){
            System.out.println("Invalid Booking");
            return;
        }

        if(!dao.checkFoodId(order.getFoodId())){
            System.out.println("Invalid Food");
            return;
        }

        if(order.getQuantity() < 1 || order.getQuantity() > 50){
            System.out.println("Quantity must be between 1 and 50.");
            return;
        }

        double price = dao.getFoodPrice(order.getFoodId());

        order.setTotalBill(price * order.getQuantity());

        dao.addOrder(order);

        orderQueue.addOrder(order.toString());

        System.out.println("Food Order Placed Successfully");
    }
    // ================= DISPLAY ALL ORDERS =================


    public void displayOrders(){

        dao.displayOrders();

        orderQueue.displayOrders();

    }






    // ================= VIEW FOOD MENU =================


    public void viewFoodMenu(){

        dao.viewFoodMenu();

    }










    // ================= CHECK METHODS =================


    public boolean checkCustomerId(int id)
    {

        return dao.checkCustomerId(id);

    }




    public boolean checkOrderId(int orderId)
    {

        return dao.checkOrderId(orderId);

    }




    public boolean checkBookingId(int bookingId)
    {

        return dao.checkBookingId(bookingId);

    }




    public boolean checkFoodId(int foodId)
    {

        return dao.checkFoodId(foodId);

    }
    // ================= DELETE LATEST ORDER =================

    public void deleteOrder(int customerId, int orderId) {

        if(orderId <= 0) {

            System.out.println("Invalid Order ID");
            return;

        }

        if(!dao.checkCustomerOrder(customerId, orderId)) {

            System.out.println("You can cancel only your latest active order.");
            return;

        }

        dao.deleteOrder(orderId);

        System.out.println("Food Order Cancelled Successfully.");

    }
    // ================= ADD ORDER USING CUSTOMER NAME =================


    public void addOrderByCustomerName(String customerName, FoodOrder order)
    {

        if(customerName == null || customerName.trim().isEmpty()){
            System.out.println("Customer Name Cannot Be Empty");
            return;
        }

        int customerId = dao.getCustomerIdByName(customerName);

        if(customerId == -1){
            System.out.println("Customer Not Found");
            return;
        }

        order.setCustomerId(customerId);

        int bookingId = dao.getBookingIdByCustomerId(customerId);

        if(bookingId != -1){
            order.setBookingId(bookingId);
        }else{
            order.setBookingId(0);
        }

        addOrder(order);
    }






    // ================= CHECK CUSTOMER BY NAME =================


    public boolean checkCustomer(String customerName) {


        if(customerName == null || customerName.trim().isEmpty()){

            return false;

        }



        return dao.getCustomerIdByName(customerName) != -1;


    }








    // ================= DISPLAY CUSTOMER ORDERS =================


    public void displayCustomerOrders(String customerName) {


        dao.displayCustomerOrders(customerName);


    }


// ================= VIEW MY FOOD ORDERS =================

    public void displayMyOrders(int customerId) {

        dao.displayMyOrders(customerId);

    }
    public void displayLatestOrder(int customerId) {

        dao.displayLatestOrder(customerId);

    }
    public boolean checkCustomerOrder(int customerId, int orderId) {

        return dao.checkCustomerOrder(customerId, orderId);

    }
    // ================= CANCEL LATEST ORDER =================

    public boolean cancelLatestOrder(int customerId, int orderId)
    {

        FoodOrder latest = getLatestActiveOrder(customerId);


        if(latest == null)
        {
            System.out.println("No Active Food Order Available. Cancellation Not Possible.");
            return false;
        }


        // Only latest order cancel allowed

        if(latest.getOrderId() != orderId)
        {
            System.out.println("Only Latest Food Order Can Be Cancelled.");
            return false;
        }


        return dao.cancelOrder(orderId, customerId);

    }

    // ================= GET LATEST ORDER =================

    public FoodOrder getLatestActiveOrder(int customerId)
    {
        FoodOrder order = dao.getLatestActiveOrder(customerId);

        if(order == null)
        {
            return null;
        }


        // अगर latest order already cancelled hai
        if(order.getOrderStatus().equalsIgnoreCase("Cancelled"))
        {
            return null;
        }


        return order;
    }
    // ================= ADD ORDER AFTER LOGIN =================

    public void addOrder(int customerId, FoodOrder order) {

        order.setCustomerId(customerId);

        // Booking optional
        int bookingId = dao.getBookingIdByCustomerId(customerId);

        if(bookingId != -1) {
            order.setBookingId(bookingId);
        } else {
            order.setBookingId(0);   // ya -1, jo database allow kare
        }

        // Food Validation
        if(!dao.checkFoodId(order.getFoodId())) {
            System.out.println("Invalid Food");
            return;
        }

        if(order.getQuantity() < 1 || order.getQuantity() > 50) {
            System.out.println("Quantity must be between 1 and 50.");
            return;
        }

        double price = dao.getFoodPrice(order.getFoodId());
        order.setTotalBill(price * order.getQuantity());

        dao.addOrder(order);

        orderQueue.addOrder(order.toString());

        System.out.println("Food Order Placed Successfully.");
    }

}