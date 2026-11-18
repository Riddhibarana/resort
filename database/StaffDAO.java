package database;

import java.sql.*;
import model.staff;


public class StaffDAO {


    // ADD STAFF

    public void addStaff(staff s)
    {



       String query="INSERT INTO staff"+
                "(staffId, staffName, gender, age, phone, email, designation, salary, shift)"+
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {



            Connection con = DBConnection.getConnection();

            if (checkPhoneExists(s.getPhone())) {

                System.out.println("Mobile Number Already Exists.");
                return;

            }

            PreparedStatement ps = con.prepareStatement(query);





            ps.setInt(1, s.getStaffId());
            ps.setString(2, s.getStaffName());
            ps.setString(3, s.getGender());
            ps.setInt(4, s.getAge());
            ps.setString(5, s.getPhone());
            ps.setString(6, s.getEmail());
            ps.setString(7, s.getDesignation());
            ps.setDouble(8, s.getSalary());
            ps.setString(9, s.getShift());
            ps.executeUpdate();


            System.out.println("Staff Added Successfully");


        }
        catch(Exception e){

            System.out.println(e);

        }

    }



    // VIEW STAFF


    public void viewStaff(){


        try{


            Connection con = DBConnection.getConnection();


            Statement st = con.createStatement();


            ResultSet rs = st.executeQuery("SELECT * FROM staff");


            while(rs.next()) {

                System.out.println("----------------------------------------------");
                System.out.println("Staff ID      : " + rs.getInt("staffId"));
                System.out.println("Staff Name    : " + rs.getString("staffName"));
                System.out.println("Staff Age     : " + rs.getInt("Age"));
                System.out.println("Designation   : " + rs.getString("Designation"));
                System.out.println("Phone         : " + rs.getString("phone"));
                System.out.println("Salary        : " + rs.getDouble("salary"));
                System.out.println("shift       : " + rs.getString("Shift"));
                System.out.println("Email      : " + rs.getString("Email"));
                System.out.println("----------------------------------------------");

            }

        }

        catch(Exception e){

            System.out.println(e);

        }

    }




    // SEARCH STAFF


    public void searchStaff(int id){


        try{


            Connection con = DBConnection.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT * FROM staff WHERE staffId=?");


            ps.setInt(1,id);


            ResultSet rs = ps.executeQuery();


            if(rs.next()){


                System.out.println("Staff Found");

                System.out.println("Name : "+rs.getString("staffName"));
                System.out.println("Designation : "+rs.getString("Designation"));
                System.out.println("Phone : "+rs.getString("phone"));
                System.out.println("Salary : "+rs.getDouble("salary"));

            }
            else{

                System.out.println("Staff Not Found");

            }


        }
        catch(Exception e){

            System.out.println(e);

        }


    }
    // CHECK STAFF ID EXIST

    public boolean checkStaffId(int id){

        boolean exists = false;

        try{

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT staffId FROM staff WHERE staffId=?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                exists = true;
            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return exists;
    }
    // ================= UPDATE STAFF =================

    public void updateStaff(int staffId, int choice, String value) {

        String query = "";

        if (choice == 1) {

            query = "UPDATE staff SET staffId=? WHERE staffId=?";

        } else if (choice == 2) {

            query = "UPDATE staff SET staffName=? WHERE staffId=?";

        } else if (choice == 3) {

            query = "UPDATE staff SET gender=? WHERE staffId=?";

        } else if (choice == 4) {

            query = "UPDATE staff SET age=? WHERE staffId=?";

        } else if (choice == 5) {

            query = "UPDATE staff SET phone=? WHERE staffId=?";

        } else if (choice == 6) {

            query = "UPDATE staff SET email=? WHERE staffId=?";

        }
        else if (choice == 7) {

            try {

                Connection con = DBConnection.getConnection();

                double salary = getSalaryByDesignation(value);

                PreparedStatement ps = con.prepareStatement(
                        "UPDATE staff SET designation=?, salary=? WHERE staffId=?");

                ps.setString(1, value);
                ps.setDouble(2, salary);
                ps.setInt(3, staffId);

                ps.executeUpdate();

                return;

            } catch (Exception e) {

                e.printStackTrace();
                return;

            }

        }
        else if (choice == 8) {

            query = "UPDATE staff SET shift=? WHERE staffId=?";

        }

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            switch (choice) {

                case 1:
                    ps.setInt(1, Integer.parseInt(value));
                    break;
                case 2:
                case 3:
                case 4:
                    ps.setInt(1, Integer.parseInt(value));
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                    ps.setString(1, value);
                    break;            }

            ps.setInt(2, staffId);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    public double getSalaryByDesignation(String designation) {

        double salary = 0;

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT salary FROM designation WHERE designationName=?");

            ps.setString(1, designation);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                salary = rs.getDouble("salary");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return salary;
    }
    public void updateStaffSalary(int staffId, double salary) {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE staff SET salary=? WHERE staffId=?");

            ps.setDouble(1, salary);
            ps.setInt(2, staffId);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    public void deleteStaff(int id){

        try{

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "DELETE FROM staff WHERE staffId=?");

            ps.setInt(1,id);

            int rows = ps.executeUpdate();

            if(rows > 0){

                System.out.println("Staff Deleted Successfully.");

            }
            else{

                System.out.println("Staff ID Not Found.");

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

    }
    public boolean checkPhoneExists(String phone) {

        String query = "SELECT staffId FROM staff WHERE phone=?";

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
    public boolean checkPhoneExistsExcept(int staffId, String phone) {

        String query =
                "SELECT staffId FROM staff WHERE phone=? AND staffId<>?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, phone);
            ps.setInt(2, staffId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }
}