package database;

import java.sql.*;
import model.customer;

public class CustomerDAO {

    // Insert Customer
    public void addCustomer(customer customer) {

        String query = "INSERT INTO customer (name,gender,age,phone," +
                "email,address,idProof,password,firstLogin) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);


            ps.setString(1, customer.getName());
            ps.setString(2, customer.getGender());
            ps.setInt(3, customer.getAge());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getEmail());
            ps.setString(6, customer.getAddress());
            ps.setString(7, customer.getIdProof());
            ps.setString(8, customer.getPassword());
            ps.setInt(9, customer.getFirstLogin());

            ps.executeUpdate();



        } catch(Exception e) {
            System.out.println(e);
        }
    }


    // Display All Customers
    public void displayCustomer() {

        String query = "SELECT * FROM customer";

        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);


            while(rs.next()) {

                System.out.println(
                        "Customer ID: " + rs.getInt(1) +
                                "\nName: " + rs.getString(2) +
                                "\nGender: " + rs.getString(3) +
                                "\nAge: " + rs.getInt(4) +
                                "\nPhone: " + rs.getString(5) +
                                "\nEmail: " + rs.getString(6) +
                                "\nAddress: " + rs.getString(7) +
                                "\nID Proof: " + rs.getString(8)
                );

                System.out.println("------------------");
            }


        } catch(Exception e) {
            System.out.println(e);
        }
    }


    public boolean deleteCustomer(int id)
    {

        try {

            Connection con = DBConnection.getConnection();

            // 1. Delete Check-In/Out Records
            PreparedStatement ps1 = con.prepareStatement(
                    "DELETE FROM checkinout WHERE bookingId IN " +
                            "(SELECT bookingId FROM booking WHERE customerId=?)");
            ps1.setInt(1, id);
            ps1.executeUpdate();

            // 2. Delete Payment Records
            PreparedStatement ps2 = con.prepareStatement(
                    "DELETE FROM payment WHERE customerId=?");
            ps2.setInt(1, id);
            ps2.executeUpdate();

            // 3. Delete Food Orders
            PreparedStatement ps3 = con.prepareStatement(
                    "DELETE FROM food_order WHERE customerId=?");
            ps3.setInt(1, id);
            ps3.executeUpdate();

            // 4. Delete Game Bookings
            PreparedStatement ps4 = con.prepareStatement(
                    "DELETE FROM game_booking WHERE customerId=?");
            ps4.setInt(1, id);
            ps4.executeUpdate();

            // 5. Delete Feedback
            PreparedStatement ps5 = con.prepareStatement(
                    "DELETE FROM feedback WHERE customerId=?");
            ps5.setInt(1, id);
            ps5.executeUpdate();

            // 6. Delete Bookings
            PreparedStatement ps6 = con.prepareStatement(
                    "DELETE FROM booking WHERE customerId=?");
            ps6.setInt(1, id);
            ps6.executeUpdate();

            // 7. Delete Customer
            PreparedStatement ps7 = con.prepareStatement(
                    "DELETE FROM customer WHERE customerId=?");
            ps7.setInt(1, id);

            int rows = ps7.executeUpdate();

            if (rows > 0) {

                return true;

            } else {

                System.out.println("Customer ID Not Found.");
                return false;

            }

        } catch (Exception e) {

            System.out.println("Unable To Delete Customer.");
            return false;

        }
    }
    // Search Customer
    public void searchCustomer(int id)
    {

        if(id <= 0) {

            System.out.println("Invalid Customer ID.");
            return;

        }


        String query = "SELECT * FROM customer WHERE customerId = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                System.out.println("\n===== CUSTOMER FOUND =====");

                System.out.println("Customer ID : " + rs.getInt("customerId"));
                System.out.println("Name        : " + rs.getString("name"));
                System.out.println("Gender      : " + rs.getString("gender"));
                System.out.println("Age         : " + rs.getInt("age"));
                System.out.println("Phone       : " + rs.getString("phone"));
                System.out.println("Email       : " + rs.getString("email"));
                System.out.println("Address     : " + rs.getString("address"));
                System.out.println("ID Proof    : " + rs.getString("idProof"));

            } else {

                System.out.println("Customer Not Found.");

            }


        }
        catch(Exception e) {

            System.out.println("Unable to search customer. Please try again.");

        }
    }
    public int customerLogin(String name, String password) {
        String query =
                "SELECT customerId " +
                        "FROM customer " +
                        "WHERE name=? AND BINARY password=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);


            ps.setString(1, name.trim());
            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();


            if(rs.next()) {

                return rs.getInt("customerId");

            }


        }
        catch(Exception e) {

            System.out.println("Login Error : " + e.getMessage());

        }


        return -1;

    }
    public String[] getCustomerBookingDates(int customerId) {

        String query =
                "SELECT checkindate, checkoutdate FROM customer WHERE customerId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();


            if(rs.next()) {

                String checkIn = rs.getString("checkindate");
                String checkOut = rs.getString("checkoutdate");

                return new String[]{checkIn, checkOut};

            }


        }
        catch(Exception e) {

            e.printStackTrace();

        }


        return null;
    }
    public boolean checkCustomerExists(String name) {

        String query =
                "SELECT customerId FROM customer WHERE LOWER(name)=LOWER(?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name.trim());

            ResultSet rs = ps.executeQuery();

            return rs.next();

        }
        catch(Exception e) {

            e.printStackTrace();

        }

        return false;
    }
    // ================= SEARCH CUSTOMER BY NAME =================

    public void searchCustomerByName(String name) {

        String query = "SELECT * FROM customer WHERE name LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("\n===== CUSTOMER FOUND =====");
                System.out.println("Customer ID : " + rs.getInt("customerId"));
                System.out.println("Name        : " + rs.getString("name"));
                System.out.println("Gender      : " + rs.getString("gender"));
                System.out.println("Age         : " + rs.getInt("age"));
                System.out.println("Phone       : " + rs.getString("phone"));
                System.out.println("Email       : " + rs.getString("email"));
                System.out.println("Address     : " + rs.getString("address"));
                System.out.println("ID Proof    : " + rs.getString("idProof"));

            }

            if (!found) {

                System.out.println("Customer Not Found.");

            }

        } catch (Exception e) {

            System.out.println("Search Error : " + e.getMessage());

        }

    }
    public void updatePassword(int customerId, String password) {

        String query =
                "UPDATE customer SET password=?, firstLogin=0 WHERE customerId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, password);
            ps.setInt(2, customerId);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    public int getFirstLoginStatus(int customerId) {

        String query = "SELECT firstLogin FROM customer WHERE customerId=?";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int value = rs.getInt("firstLogin");


                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    public String getPasswordByCustomerId(int customerId) {

        String query = "SELECT password FROM customer WHERE customerId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getString("password");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }
    // ================= GET CUSTOMER PASSWORD =================

    public String getPassword(int customerId) {

        String query = "SELECT password FROM customer WHERE customerId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getString("password");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }
    public int getCustomerIdByName(String name) {

        String query =
                "SELECT customerId FROM customer WHERE LOWER(name)=LOWER(?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name.trim());

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                return rs.getInt("customerId");

            }

        }
        catch(Exception e) {

            e.printStackTrace();

        }

        return -1;

    }
    public String getCustomerPassword(int customerId) {

        String query = "SELECT password FROM customer WHERE customerId=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getString("password");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "";
    }
    // Update Customer
    public boolean updateCustomer(int id, int choice, String value) {

        String query = "";

        switch (choice) {

            case 1:
                query = "UPDATE customer SET name=? WHERE customerId=?";
                break;

            case 2:
                query = "UPDATE customer SET gender=? WHERE customerId=?";
                break;

            case 3:
                query = "UPDATE customer SET age=? WHERE customerId=?";
                break;

            case 4:
                query = "UPDATE customer SET phone=? WHERE customerId=?";
                break;

            case 5:
                query = "UPDATE customer SET email=? WHERE customerId=?";
                break;

            case 6:
                query = "UPDATE customer SET address=? WHERE customerId=?";
                break;

            case 7:
                query = "UPDATE customer SET idProof=? WHERE customerId=?";
                break;

            default:
                System.out.println("Invalid Choice");
                return false;
        }

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            if (choice == 3) {

                ps.setInt(1, Integer.parseInt(value));

            } else {

                ps.setString(1, value);

            }

            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                return true;

            } else {

                System.out.println("Customer ID Not Found.");
                return false;

            }

        } catch (Exception e) {

            System.out.println("Unable To Update Customer.");
            return false;

        }
    }
    public String getCustomerNameById(int customerId) {

        String query = "SELECT name FROM customer WHERE customerId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    public int getCustomerIdByPhone(String phone) {

        String query = "SELECT customerId FROM customer WHERE phone=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, phone);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt("customerId");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return -1;
    }
    public boolean isPhoneExists(String phone) {

        String query = "SELECT customerId FROM customer WHERE phone=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, phone);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }
    // ================= CHECK PHONE EXISTS =================

    public boolean checkPhoneExists(String phone) {

        String query = "SELECT customerId FROM customer WHERE phone=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, phone);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }
    // ================= CHECK PHONE EXISTS EXCEPT SAME CUSTOMER =================

    public boolean checkPhoneExistsExcept(int customerId, String phone) {

        String query =
                "SELECT customerId FROM customer WHERE phone=? AND customerId<>?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, phone);
            ps.setInt(2, customerId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }
    // ================= CHECK PHONE FOR FORGOT PASSWORD =================

    public boolean verifyCustomerPhone(int customerId, String phone) {

        String query =
                "SELECT customerId FROM customer WHERE customerId=? AND phone=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);
            ps.setString(2, phone);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }

    }


