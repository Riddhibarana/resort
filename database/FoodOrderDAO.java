package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import model.FoodOrder;

public class FoodOrderDAO {



    // ================= ADD FOOD ORDER =================

    public void addOrder(FoodOrder order) {


        String priceQuery =
                "SELECT price FROM food_menu WHERE foodId=?";


        String insertQuery =
                "INSERT INTO food_order(customerId, bookingId, foodId, quantity, totalBill, orderStatus) VALUES(?,?,?,?,?,?)";


        try {


            Connection con = DBConnection.getConnection();


            // Quantity Validation

            if (order.getQuantity() <= 0) {

                System.out.println("Quantity must be greater than 0.");
                return;

            }


            // Food Price Get

            PreparedStatement ps1 =
                    con.prepareStatement(priceQuery);


            ps1.setInt(1, order.getFoodId());


            ResultSet rs =
                    ps1.executeQuery();


            if (!rs.next()) {


                System.out.println("Invalid Food ID.");
                return;

            }


            double price =
                    rs.getDouble("price");


            double totalBill =
                    price * order.getQuantity();


            PreparedStatement ps2 =
                    con.prepareStatement(insertQuery);


            ps2.setInt(1, order.getCustomerId());

            if(order.getBookingId() == 0){
                ps2.setNull(2, java.sql.Types.INTEGER);
            }else{
                ps2.setInt(2, order.getBookingId());
            }
            ps2.setInt(3, order.getFoodId());

            ps2.setInt(4, order.getQuantity());

            ps2.setDouble(5, totalBill);

            ps2.setString(6, order.getOrderStatus());


            int rows =
                    ps2.executeUpdate();


            if (rows > 0) {


                System.out.println(
                        "Food Order Placed Successfully");


                System.out.println(
                        "Total Bill : ₹" + totalBill);


            }


        } catch (Exception e) {


            System.out.println(
                    "Food Order Error : "
                            + e.getMessage());


        }


    }


    // ================= GET CUSTOMER ID BY NAME =================


    public int getCustomerIdByName(String name) {


        String query =
                "SELECT customerId FROM customer WHERE LOWER(name)=LOWER(?)";


        try {


            Connection con =
                    DBConnection.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(query);


            ps.setString(1, name);


            ResultSet rs =
                    ps.executeQuery();


            if (rs.next()) {


                return rs.getInt("customerId");


            }


        } catch (Exception e) {


            e.printStackTrace();


        }


        return -1;


    }


    // ================= VIEW FOOD MENU =================

    public void viewFoodMenu() {

        String query = "SELECT * FROM food_menu";

        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);


            System.out.println("\n========== FOOD MENU ==========");

            System.out.printf("%-10s %-20s %-10s%n",
                    "Food ID",
                    "Food Name",
                    "Price");


            System.out.println("----------------------------------------------");


            while (rs.next()) {

                System.out.printf("%-10d %-20s ₹%-10.2f%n",
                        rs.getInt("foodId"),
                        rs.getString("foodName"),
                        rs.getDouble("price"));

            }


        } catch (Exception e) {

            System.out.println(
                    "Unable to display food menu.");

        }

    }


// ================= VIEW ALL ORDERS =================

    public void displayOrders() {


        String query =
                "SELECT fo.orderId, fo.customerId, fo.bookingId, " +
                        "fm.foodName, fo.quantity, fm.price, fo.totalBill, fo.orderStatus " +
                        "FROM food_order fo " +
                        "JOIN food_menu fm ON fo.foodId=fm.foodId";


        try {


            Connection con =
                    DBConnection.getConnection();


            Statement st =
                    con.createStatement();


            ResultSet rs =
                    st.executeQuery(query);


            while (rs.next()) {


                System.out.println("--------------------------------");

                System.out.println(
                        "Order ID      : "
                                + rs.getInt("orderId"));


                System.out.println(
                        "Customer ID   : "
                                + rs.getInt("customerId"));


                System.out.println(
                        "Booking ID    : "
                                + rs.getInt("bookingId"));


                System.out.println(
                        "Food Name     : "
                                + rs.getString("foodName"));


                System.out.println(
                        "Quantity      : "
                                + rs.getInt("quantity"));


                System.out.println(
                        "Price         : "
                                + rs.getDouble("price"));


                System.out.println(
                        "Total Bill    : "
                                + rs.getDouble("totalBill"));


                System.out.println(
                        "Order Status  : "
                                + rs.getString("orderStatus"));


            }


        } catch (Exception e) {

            System.out.println(
                    "Unable to display orders.");

        }


    }
    // ================= CHECK ORDER ID =================


    public boolean checkOrderId(int orderId) {


        String query =
                "SELECT orderId FROM food_order WHERE orderId=?";


        try {


            Connection con =
                    DBConnection.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(query);


            ps.setInt(1, orderId);


            ResultSet rs =
                    ps.executeQuery();


            return rs.next();


        } catch (Exception e) {


            e.printStackTrace();


        }


        return false;


    }
    // ================= CHECK CUSTOMER =================

    public boolean checkCustomerId(int customerId) {

        String query =
                "SELECT customerId FROM customer WHERE customerId=?";


        try {

            Connection con =
                    DBConnection.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(query);


            ps.setInt(1, customerId);


            ResultSet rs =
                    ps.executeQuery();


            return rs.next();


        } catch (Exception e) {

            e.printStackTrace();

        }


        return false;

    }


// ================= CHECK BOOKING =================

    public boolean checkBookingId(int bookingId) {


        String query =
                "SELECT bookingId FROM booking WHERE bookingId=?";


        try {


            Connection con =
                    DBConnection.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(query);


            ps.setInt(1, bookingId);


            ResultSet rs =
                    ps.executeQuery();


            return rs.next();


        } catch (Exception e) {

            e.printStackTrace();

        }


        return false;

    }


// ================= CHECK FOOD ID =================

    public boolean checkFoodId(int foodId) {


        String query =
                "SELECT foodId FROM food_menu WHERE foodId=?";


        try {


            Connection con =
                    DBConnection.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(query);


            ps.setInt(1, foodId);


            ResultSet rs =
                    ps.executeQuery();


            return rs.next();


        } catch (Exception e) {


            e.printStackTrace();


        }


        return false;


    }


// ================= GET RESTAURANT BILL =================


    public double getRestaurantBill(int customerId) {


        String query =
                "SELECT SUM(totalBill) AS bill FROM food_order WHERE customerId=? AND orderStatus='Ordered'";
      try {


            Connection con =
                    DBConnection.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(query);


            ps.setInt(1, customerId);


            ResultSet rs =
                    ps.executeQuery();


            if (rs.next()) {


                double bill =
                        rs.getDouble("bill");


                return bill;


            }


        } catch (Exception e) {


            e.printStackTrace();


        }


        return 0;


    }


// ================= DISPLAY CUSTOMER ORDERS =================


    public void displayCustomerOrders(String customerName) {


        String query =
                "SELECT fo.orderId, fm.foodName, fo.quantity, fo.totalBill " +
                        "FROM food_order fo " +
                        "JOIN customer c ON fo.customerId=c.customerId " +
                        "JOIN food_menu fm ON fo.foodId=fm.foodId " +
                        "WHERE LOWER(c.name)=LOWER(?)";


        try {


            Connection con =
                    DBConnection.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(query);


            ps.setString(1, customerName);


            ResultSet rs =
                    ps.executeQuery();


            boolean found = false;


            while (rs.next()) {


                found = true;


                System.out.println("--------------------------------");

                System.out.println(
                        "Order ID : "
                                + rs.getInt("orderId"));


                System.out.println(
                        "Food Name : "
                                + rs.getString("foodName"));


                System.out.println(
                        "Quantity : "
                                + rs.getInt("quantity"));


                System.out.println(
                        "Total Bill : ₹"
                                + rs.getDouble("totalBill"));


            }


            if (!found) {


                System.out.println(
                        "No Food Orders Found.");

            }


        } catch (Exception e) {


            e.printStackTrace();


        }


    }

    // ================= GET BOOKING ID =================
    public int getBookingIdByCustomerId(int customerId) {

        int bookingId = -1;

        String query = "SELECT bookingId FROM booking WHERE customerId=? ORDER BY bookingId DESC LIMIT 1";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bookingId = rs.getInt("bookingId");
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return bookingId;
    }

    public double getFoodPrice(int foodId) {

        double price = 0;

        String query = "SELECT price FROM food_menu WHERE foodId=?";


        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, foodId);


            ResultSet rs = ps.executeQuery();


            if (rs.next()) {

                price = rs.getDouble("price");

            }


            rs.close();
            ps.close();


        } catch (Exception e) {

            System.out.println(e.getMessage());

        }


        return price;

    }
    // ================= DISPLAY MY ORDERS =================

    public void displayMyOrders(int customerId) {

        String query =
                "SELECT fo.orderId, fm.foodName, fo.quantity, fo.totalBill, fo.orderStatus " +
                        "FROM food_order fo " +
                        "JOIN food_menu fm ON fo.foodId = fm.foodId " +
                        "WHERE fo.customerId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("--------------------------------");
                System.out.println("Order ID     : " + rs.getInt("orderId"));
                System.out.println("Food Name    : " + rs.getString("foodName"));
                System.out.println("Quantity     : " + rs.getInt("quantity"));
                System.out.println("Total Bill   : ₹" + rs.getDouble("totalBill"));
                System.out.println("Status       : " + rs.getString("orderStatus"));
            }

            if (!found) {
                System.out.println("No Food Orders Found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ================= DISPLAY LATEST ORDER =================

    public void displayLatestOrder(int customerId) {

        String query =
                "SELECT fo.orderId, fm.foodName, fo.quantity, fo.totalBill, fo.orderStatus " +
                        "FROM food_order fo " +
                        "JOIN food_menu fm ON fo.foodId = fm.foodId " +
                        "WHERE fo.customerId=? " +
                        "AND fo.orderStatus='Ordered' " +
                        "ORDER BY fo.orderId DESC LIMIT 1";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\n------ Latest Food Order ------");
                System.out.println("Order ID   : " + rs.getInt("orderId"));
                System.out.println("Food Name  : " + rs.getString("foodName"));
                System.out.println("Quantity   : " + rs.getInt("quantity"));
                System.out.println("Total Bill : ₹" + rs.getDouble("totalBill"));
                System.out.println("Status     : " + rs.getString("orderStatus"));
                System.out.println("-------------------------------");

            } else {

                System.out.println("No Active Food Order Found.");

            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    // ================= CHECK LATEST CUSTOMER ORDER =================

    public boolean checkCustomerOrder(int customerId, int orderId) {

        String query =
                "SELECT orderId " +
                        "FROM food_order " +
                        "WHERE customerId=? " +
                        "AND orderStatus='Ordered' " +
                        "ORDER BY orderId DESC LIMIT 1";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt("orderId") == orderId;

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }
    // ================= CANCEL ORDER =================

    public void deleteOrder(int orderId) {

        String query =
                "UPDATE food_order SET orderStatus='Cancelled' WHERE orderId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, orderId);

            int rows = ps.executeUpdate();

            if(rows == 0) {

                System.out.println("Order ID Not Found.");

            }

            ps.close();

        } catch(Exception e) {

            e.printStackTrace();

        }

    }
    // ================= CANCEL ORDER =================

    public boolean cancelOrder(int orderId, int customerId)
    {

        String query =
                "UPDATE food_order " +
                        "SET orderStatus='Cancelled' " +
                        "WHERE orderId=? AND customerId=?";



        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query))
        {


            ps.setInt(1, orderId);
            ps.setInt(2, customerId);



            int rows =
                    ps.executeUpdate();



            return rows > 0;


        }
        catch(Exception e)
        {

            e.printStackTrace();

        }


        return false;

    }
    public boolean cancelLatestOrder(int customerId, int orderId)
    {
        FoodOrder latest = getLatestActiveOrder(customerId);
        if(latest == null)
        {
            System.out.println("No Active Food Order Available. Cancellation Not Possible.");
            return false;
        }


        if(latest.getOrderId() != orderId)
        {
            System.out.println("Only Latest Food Order Can Be Cancelled.");
            return false;
        }


        return cancelOrder(orderId, customerId);
    }
    public FoodOrder getLatestActiveOrder(int customerId)
    {
        FoodOrder order = null;

        String query =
                "SELECT fo.orderId, fm.foodName, fo.quantity, " +
                        "fo.totalBill, fo.orderStatus " +
                        "FROM food_order fo " +
                        "JOIN food_menu fm ON fo.foodId = fm.foodId " +
                        "WHERE fo.customerId=? " +
                        "ORDER BY fo.orderId DESC LIMIT 1";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query))
        {

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                // Agar latest order already cancelled hai
                // to koi purana order cancel nahi hone denge
                if(rs.getString("orderStatus").equalsIgnoreCase("Cancelled"))
                {
                    return null;
                }

                order = new FoodOrder();

                order.setOrderId(rs.getInt("orderId"));
                order.setFoodName(rs.getString("foodName"));
                order.setQuantity(rs.getInt("quantity"));
                order.setTotalBill(rs.getDouble("totalBill"));
                order.setOrderStatus(rs.getString("orderStatus"));
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return order;
    }
}


