package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.AdminFood;

public class AdminFoodDAO {

    // ================= ADD FOOD =================

    public void addFood(AdminFood food) {

        String query =
                "INSERT INTO food_menu(foodName,price) VALUES(?,?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, food.getFoodName());
            ps.setDouble(2, food.getPrice());

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // ================= VIEW FOOD =================

    public void viewFood() {

        String query =
                "SELECT * FROM food_menu ORDER BY foodId";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n========== FOOD MENU ==========");
            System.out.printf("%-10s %-25s %-10s%n",
                    "Food ID", "Food Name", "Price");

            while (rs.next()) {

                System.out.printf("%-10d %-25s ₹%-10.2f%n",
                        rs.getInt("foodId"),
                        rs.getString("foodName"),
                        rs.getDouble("price"));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // ================= CHECK FOOD ID =================

    public boolean checkFoodId(int foodId) {

        String query =
                "SELECT foodId FROM food_menu WHERE foodId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, foodId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }

    // ================= UPDATE FOOD =================
// ================= UPDATE FOOD =================

    public void updateFood(int foodId,
                           int choice,
                           String value) {

        String query = "";

        if (choice == 1) {

            query = "UPDATE food_menu SET foodName=? WHERE foodId=?";

        }
        else if (choice == 2) {

            query = "UPDATE food_menu SET price=? WHERE foodId=?";

        }
        else {

            System.out.println("Invalid Choice.");
            return;

        }

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            if (choice == 1) {

                ps.setString(1, value);

            }
            else {

                ps.setDouble(1, Double.parseDouble(value));

            }

            ps.setInt(2, foodId);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                System.out.println("Food Updated Successfully.");

            }
            else {

                System.out.println("Food Not Found.");

            }

        }
        catch (Exception e) {

            System.out.println("Unable To Update Food.");

        }

    }
    // ================= DELETE FOOD =================

    public void deleteFood(int foodId) {

        String query =
                "DELETE FROM food_menu WHERE foodId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, foodId);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    // ================= VIEW ALL FOOD ORDERS =================

    public void viewAllFoodOrders() {

        String query =
                "SELECT fo.orderId,c.name,fm.foodName," +
                        "fo.quantity,fo.totalBill,fo.orderStatus " +
                        "FROM food_order fo " +
                        "JOIN customer c ON fo.customerId=c.customerId " +
                        "JOIN food_menu fm ON fo.foodId=fm.foodId " +
                        "ORDER BY fo.orderId";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            System.out.println("\n========== ALL FOOD ORDERS ==========");

            while(rs.next()) {

                found = true;

                System.out.println("--------------------------------");
                System.out.println("Order ID      : " + rs.getInt("orderId"));
                System.out.println("Customer Name : " + rs.getString("name"));
                System.out.println("Food Name     : " + rs.getString("foodName"));
                System.out.println("Quantity      : " + rs.getInt("quantity"));
                System.out.println("Total Bill    : ₹" + rs.getDouble("totalBill"));
                System.out.println("Status        : " + rs.getString("orderStatus"));

            }

            if(!found) {

                System.out.println("No Food Orders Found.");

            }

        }

        catch(Exception e) {

            e.printStackTrace();

        }

    }
    // ================= CHECK FOOD NAME =================

    public boolean checkFoodName(String foodName) {

        String query =
                "SELECT foodId FROM food_menu WHERE LOWER(foodName)=LOWER(?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, foodName);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }
    public boolean checkFoodNameForUpdate(int foodId, String foodName) {

        String query =
                "SELECT foodId FROM food_menu " +
                        "WHERE LOWER(foodName)=LOWER(?) AND foodId<>?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, foodName);
            ps.setInt(2, foodId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }

}