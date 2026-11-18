package menu;

import java.util.Scanner;

import database.StaffDAO;
import model.staff;
import service.StaffService;

public class StaffMenu {

    public static void displayMenu() {

        Scanner sc = new Scanner(System.in);

        StaffService service = new StaffService();

        StaffDAO dao = new StaffDAO();
        staff staff = new staff();

        while (true) {

            System.out.println("\n===== STAFF MANAGEMENT =====");
            System.out.println("1. Add Staff");
            System.out.println("2. View Staff");
            System.out.println("3. Search Staff");
            System.out.println("4. Update Staff");
            System.out.println("5. Delete Staff");
            System.out.println("0. Back");

            System.out.print("Enter Choice : ");

            int choice;

            try {

                choice = Integer.parseInt(sc.nextLine().trim());

            } catch (Exception e) {

                System.out.println("Enter Numbers Only.");
                continue;

            }

            if (choice < 0 || choice > 5) {

                System.out.println("Invalid Choice.");
                continue;

            }

            switch (choice) {

                case 1:

                    staff = new staff();
                    // ================= STAFF ID =================

                    while (true) {

                        System.out.print("Enter Staff ID : ");

                        String input = sc.nextLine().trim();

                        if (input.isEmpty()) {

                            System.out.println("Staff ID Cannot Be Empty.");
                            continue;

                        }

                        if (!input.matches("^[1-9][0-9]{2}$")) {

                            System.out.println("Invalid Staff ID! Enter Exactly 3 Digits (100-999).");
                            continue;

                        }

                        int id;

                        try {

                            id = Integer.parseInt(input);

                        } catch (NumberFormatException e) {

                            System.out.println("Enter Numbers Only.");
                            continue;

                        }

                        if (dao.checkStaffId(id)) {

                            System.out.println("Staff ID Already Exists. Enter Another ID.");
                            continue;

                        }

                        staff.setStaffId(id);
                        break;

                    }

                    // ================= NAME =================

                    while (true) {

                        System.out.print("Enter Staff Name : ");

                        String name = sc.nextLine().trim();

                        if (name.matches("[A-Za-z ]{3,30}")) {

                            staff.setStaffName(name);
                            break;

                        }

                        System.out.println("Name Must Contain Only Alphabets.");

                    }

                    // ================= GENDER =================

                    while (true) {

                        System.out.print("Enter Gender (Male/Female/Other) : ");

                        String gender = sc.nextLine().trim();

                        if (gender.equalsIgnoreCase("Male")
                                || gender.equalsIgnoreCase("Female")
                                || gender.equalsIgnoreCase("Other")) {

                            staff.setGender(gender);
                            break;

                        }

                        System.out.println("Enter Male, Female Or Other.");

                    }

                    // ================= AGE =================

                    while (true) {

                        System.out.print("Enter Age : ");

                        String input = sc.nextLine().trim();

                        if (input.isEmpty()) {

                            System.out.println("Age Cannot Be Empty.");
                            continue;

                        }

                        int age;

                        try {

                            age = Integer.parseInt(input);

                        } catch (Exception e) {

                            System.out.println("Enter Numbers Only.");
                            continue;

                        }

                        if (age >= 20 && age <= 60) {

                            staff.setAge(age);
                            break;

                        }

                        System.out.println("Age Must Be Between 20 And 60.");

                    }
                    // ================= PHONE =================

                    while (true) {

                        System.out.print("Enter Phone Number : ");

                        String phone = sc.nextLine().trim();

                        if (phone.isEmpty()) {

                            System.out.println("Phone Number Cannot Be Empty.");
                            continue;

                        }

                        if (!phone.matches("[6-9][0-9]{9}")) {

                            System.out.println("Phone Must Start With 6/7/8/9 And Be 10 Digits.");
                            continue;

                        }

                        if (service.checkPhoneExists(phone)) {

                            System.out.println("Mobile Number Already Exists.");
                            continue;

                        }

                        staff.setPhone(phone);
                        break;

                    }

                    // ================= EMAIL =================

                    while (true) {

                        System.out.print("Enter Email : ");

                        String email = sc.nextLine().trim();

                        if (email.isEmpty()) {

                            System.out.println("Email Cannot Be Empty.");
                            continue;

                        }

                        if (email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {

                            staff.setEmail(email);
                            break;

                        }

                        System.out.println("Invalid Gmail Address.");

                    }

                    // ================= DESIGNATION =================

                    while (true) {

                        System.out.println("\nSelect Designation");
                        System.out.println("1. Manager");
                        System.out.println("2. Receptionist");
                        System.out.println("3. Housekeeping");
                        System.out.println("4. Chef");
                        System.out.println("5. Security");
                        System.out.println("6. Waiter");

                        System.out.print("Enter Choice : ");

                        String input = sc.nextLine().trim();

                        if (input.isEmpty()) {

                            System.out.println("Choice Cannot Be Empty.");
                            continue;

                        }

                        int d;

                        try {

                            d = Integer.parseInt(input);

                        } catch (Exception e) {

                            System.out.println("Enter Numbers Only.");
                            continue;

                        }

                        switch (d) {

                            case 1:
                                staff.setDesignation("Manager");
                                break;

                            case 2:
                                staff.setDesignation("Receptionist");
                                break;

                            case 3:
                                staff.setDesignation("Housekeeping");
                                break;

                            case 4:
                                staff.setDesignation("Chef");
                                break;

                            case 5:
                                staff.setDesignation("Security");
                                break;

                            case 6:
                                staff.setDesignation("Waiter");
                                break;

                            default:
                                System.out.println("Invalid Choice.");
                                continue;

                        }

                        break;

                    }

                    // ================= SHIFT =================

                    while (true) {

                        System.out.print("Enter Shift (Morning/Evening/Night) : ");

                        String shift = sc.nextLine().trim();

                        if (shift.isEmpty()) {

                            System.out.println("Shift Cannot Be Empty.");
                            continue;

                        }

                        if (shift.equalsIgnoreCase("Morning")
                                || shift.equalsIgnoreCase("Evening")
                                || shift.equalsIgnoreCase("Night")) {

                            staff.setShift(shift);
                            break;

                        }

                        System.out.println("Enter Morning, Evening Or Night.");

                    }

                    service.addStaff(staff);

                    break;
                // ================= VIEW STAFF =================

                case 2:

                    service.viewStaff();

                    break;

                // ================= SEARCH STAFF =================

                case 3:

                    while (true) {

                        System.out.print("Enter Staff ID : ");

                        String searchInput = sc.nextLine().trim();

                        if (searchInput.isEmpty()) {

                            System.out.println("Staff ID Cannot Be Empty.");
                            continue;

                        }

                        if (!searchInput.matches("^[1-9][0-9]{2}$")) {

                            System.out.println("Invalid Staff ID! Enter Exactly 3 Digits (100-999).");
                            continue;

                        }

                        int searchId = Integer.parseInt(searchInput);

                        if (!dao.checkStaffId(searchId)) {

                            System.out.println("Staff ID Not Found.");
                            continue;

                        }

                        service.searchStaff(searchId);
                        break;

                    }

                    break;

                // ================= UPDATE STAFF =================

                case 4:

                    int staffId;

                    while (true) {

                        System.out.print("Enter Staff ID : ");

                        String input = sc.nextLine().trim();

                        if (input.isEmpty()) {

                            System.out.println("Staff ID Cannot Be Empty.");
                            continue;

                        }

                        if (!input.matches("^[1-9][0-9]{2}$")) {

                            System.out.println("Invalid Staff ID! Enter Exactly 3 Digits (100-999).");
                            continue;

                        }

                        staffId = Integer.parseInt(input);

                        if (!dao.checkStaffId(staffId)) {

                            System.out.println("Staff ID Not Found.");
                            continue;

                        }

                        break;

                    }

                    System.out.println("\n===== UPDATE STAFF =====");
                    System.out.println("1. Staff ID");
                    System.out.println("2. Staff Name");
                    System.out.println("3. Gender");
                    System.out.println("4. Age");
                    System.out.println("5. Phone");
                    System.out.println("6. Email");
                    System.out.println("7. Designation");
                    System.out.println("8. Shift");

                    System.out.print("Enter Choice : ");

                    String updateInput = sc.nextLine().trim();

                    if (updateInput.isEmpty()) {

                        System.out.println("Choice Cannot Be Empty.");
                        break;

                    }

                    int updateChoice;

                    try {

                        updateChoice = Integer.parseInt(updateInput);

                    } catch (Exception e) {

                        System.out.println("Enter Numbers Only.");
                        break;

                    }

                    if (updateChoice < 1 || updateChoice > 8) {

                        System.out.println("Invalid Choice.");
                        break;

                    }

                    String value = "";
                    // ================= UPDATE DESIGNATION =================

                    if (updateChoice == 7) {

                        while (true) {

                            System.out.println("\nSelect Designation");
                            System.out.println("1. Manager");
                            System.out.println("2. Receptionist");
                            System.out.println("3. Housekeeping");
                            System.out.println("4. Chef");
                            System.out.println("5. Security");
                            System.out.println("6. Waiter");

                            System.out.print("Enter Choice : ");

                            String input = sc.nextLine().trim();

                            if (input.isEmpty()) {

                                System.out.println("Choice Cannot Be Empty.");
                                continue;

                            }

                            int d;

                            try {

                                d = Integer.parseInt(input);

                            } catch (Exception e) {

                                System.out.println("Enter Numbers Only.");
                                continue;

                            }

                            switch (d) {

                                case 1:
                                    value = "Manager";
                                    break;

                                case 2:
                                    value = "Receptionist";
                                    break;

                                case 3:
                                    value = "Housekeeping";
                                    break;

                                case 4:
                                    value = "Chef";
                                    break;

                                case 5:
                                    value = "Security";
                                    break;

                                case 6:
                                    value = "Waiter";
                                    break;

                                default:
                                    System.out.println("Invalid Choice.");
                                    continue;

                            }

                            break;

                        }

                    }

                    // ================= UPDATE SHIFT =================

                    else if (updateChoice == 8) {

                        while (true) {

                            System.out.print("Enter Shift (Morning/Evening/Night) : ");

                            value = sc.nextLine().trim();

                            if (value.isEmpty()) {

                                System.out.println("Shift Cannot Be Empty.");
                                continue;

                            }

                            if (value.equalsIgnoreCase("Morning")
                                    || value.equalsIgnoreCase("Evening")
                                    || value.equalsIgnoreCase("Night")) {

                                break;

                            }

                            System.out.println("Enter Morning, Evening Or Night.");

                        }

                    }

                    // ================= OTHER FIELDS =================

                    else {

                        while (true) {

                            System.out.print("Enter New Value : ");

                            value = sc.nextLine().trim();

                            if (value.isEmpty()) {

                                System.out.println("Value Cannot Be Empty.");
                                continue;

                            }

                            break;

                        }

                    }

                    service.updateStaff(staffId, updateChoice, value);

                    break;
                // ================= DELETE STAFF =================

                case 5:

                    while (true) {

                        System.out.print("Enter Staff ID : ");

                        String deleteInput = sc.nextLine().trim();

                        if (deleteInput.isEmpty()) {

                            System.out.println("Staff ID Cannot Be Empty.");
                            continue;

                        }

                        if (!deleteInput.matches("^[1-9][0-9]{2}$")) {

                            System.out.println("Invalid Staff ID! Enter Exactly 3 Digits (100-999).");
                            continue;

                        }

                        int deleteId = Integer.parseInt(deleteInput);

                        if (!dao.checkStaffId(deleteId)) {

                            System.out.println("Staff ID Not Found.");
                            continue;

                        }

                        service.deleteStaff(deleteId);
                        break;

                    }

                    break;

                // ================= BACK =================

                case 0:

                    return;

                // ================= INVALID =================

                default:

                    System.out.println("Invalid Choice.");

            }

        }

    }

}