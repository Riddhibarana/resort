package service;

import database.StaffDAO;
import model.staff;


public class StaffService {


    StaffDAO dao;


    public StaffService(){

        dao = new StaffDAO();


    }


    public void addStaff(staff s){

        if(dao.checkStaffId(s.getStaffId()))
        {
            System.out.println("Staff ID already exists. Please enter another ID.");
            return;
        }

        double salary = dao.getSalaryByDesignation(s.getDesignation());

        s.setSalary(salary);

        dao.addStaff(s);

    }

    public void viewStaff(){

        dao.viewStaff();

    }



    public void searchStaff(int id){

        if(!dao.checkStaffId(id)){

            System.out.println("Staff ID Not Found.");
            return;

        }

        dao.searchStaff(id);

    }


    public void deleteStaff(int id){

        if(!dao.checkStaffId(id)){

            System.out.println("Staff ID Not Found.");
            return;

        }

        dao.deleteStaff(id);

    }
    // ================= UPDATE STAFF =================

    public void updateStaff(int staffId, int choice, String value) {

        if (!dao.checkStaffId(staffId)) {

            System.out.println("Staff ID Not Found.");
            return;

        }

        // Staff ID
        // Staff ID
        if (choice == 1) {

            if (!value.matches("^[1-9][0-9]{2}$")) {

                System.out.println("Invalid Staff ID! Enter Exactly 3 Digits (100-999).");
                return;

            }

            int newId = Integer.parseInt(value);

            if (dao.checkStaffId(newId)) {

                System.out.println("Staff ID Already Exists.");
                return;

            }

        }
        // Name
        if (choice == 2) {

            if (!value.matches("[A-Za-z ]{3,30}")) {

                System.out.println("Staff Name Must Contain Only Alphabets.");
                return;

            }

        }

        // Gender
        if (choice == 3) {

            if (!(value.equalsIgnoreCase("Male")
                    || value.equalsIgnoreCase("Female")
                    || value.equalsIgnoreCase("Other"))) {

                System.out.println("Enter Male, Female or Other.");
                return;

            }

        }

        // Age
        // Age
        if (choice == 4) {

            try {

                int age = Integer.parseInt(value);

                if (age < 20 || age > 60) {

                    System.out.println("Age Must Be Between 20 And 60.");
                    return;

                }

            } catch (Exception e) {

                System.out.println("Enter Valid Age.");
                return;

            }

        } // Phone
        if (choice == 5) {

            if (!value.matches("[6-9][0-9]{9}")) {

                System.out.println("Phone Number Must Start With 6, 7, 8 Or 9 And Must Be 10 Digits.");
                return;

            }

            if (dao.checkPhoneExistsExcept(staffId, value)) {

                System.out.println("Mobile Number Already Exists.");
                return;

            }

        }
        // Email
        if (choice == 6) {

            if (!value.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {

                System.out.println("Invalid Email.");
                return;

            }

        }

        if (choice == 7) {

            if (!(value.equalsIgnoreCase("Manager")
                    || value.equalsIgnoreCase("Receptionist")
                    || value.equalsIgnoreCase("Housekeeping")
                    || value.equalsIgnoreCase("Chef")
                    || value.equalsIgnoreCase("Security")
                    || value.equalsIgnoreCase("Waiter"))) {

                System.out.println("Invalid Designation.");
                return;

            }

            double salary = dao.getSalaryByDesignation(value);

            dao.updateStaffSalary(staffId, salary);

        }




        // Shift
        if (choice == 8) {

            if (!(value.equalsIgnoreCase("Morning")
                    || value.equalsIgnoreCase("Evening")
                    || value.equalsIgnoreCase("Night"))) {

                System.out.println("Enter Morning, Evening or Night.");
                return;

            }

        }

        dao.updateStaff(staffId, choice, value);

        System.out.println("Staff Updated Successfully.");

    }
    public boolean checkPhoneExists(String phone) {

        return dao.checkPhoneExists(phone);

    }

    public boolean checkPhoneExistsExcept(int staffId, String phone) {

        return dao.checkPhoneExistsExcept(staffId, phone);

    }
    public boolean checkStaffId(int staffId) {

        return dao.checkStaffId(staffId);

    }
}