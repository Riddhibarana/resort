package menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import database.CustomerDAO;
import model.customer;
import service.CheckInOutService;
import service.CustomerService;
import model.CheckInOut;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import model.CheckInOut;
import menu.FoodOrderMenu;
import menu.GameMenu;
import menu.FeedbackMenu;
import service.PaymentService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import menu.PaymentMenu;
import service.PaymentService;
import database.DBConnection;

public class CustomerMenu {

    private Scanner scanner;
    private CustomerService customerService;
    private CheckInOutService checkInOutService;
    private BookingMenu bookingMenu;


    // Constructor
    public CustomerMenu() {

        scanner = new Scanner(System.in);

        customerService = new CustomerService();

        checkInOutService = new CheckInOutService();


    }


    // ================= CUSTOMER PORTAL =================

    public void displayMenu() {

        int choice;

        while (true) {

            System.out.println("\n===== CUSTOMER PORTAL =====");
            System.out.println("1. Customer Registration");
            System.out.println("2. Customer Login");
            System.out.println("0. Back");

            System.out.print("Enter Choice: ");


            try {

                choice = Integer.parseInt(scanner.nextLine());


            } catch (Exception e) {

                System.out.println("Invalid Input! Enter number only.");
                continue;

            }


            switch (choice) {


                case 1:

                    addCustomer();

                    break;


                case 2:

                    customerLogin();

                    break;


                case 0:

                    System.out.println("Returning to Main Menu...");
                    return;


                default:

                    System.out.println("Invalid Choice!");

            }

        }

    }


    // ================= ADD CUSTOMER =================

    private void addCustomer() {


        customer c = new customer();


        // NAME

        while (true) {


            System.out.print("Enter Name : ");

            String name = scanner.nextLine().trim();


            if (name.isEmpty()) {

                System.out.println("Name cannot be empty.");

            } else {

                c.setName(name);
                break;

            }

        }


        // AGE

        while (true) {


            try {


                System.out.print("Enter Age : ");

                int age = Integer.parseInt(scanner.nextLine());


                if (age >= 20 && age <= 100) {

                    c.setAge(age);
                    break;

                } else {

                    System.out.println("Age must be between 20 and 100.");

                }


            } catch (Exception e) {

                System.out.println("Enter valid age.");

            }


        }


        // PHONE

        c.setPhone(getValidPhone());


        // GENDER

        while (true) {


            System.out.println("\nSelect Gender");
            System.out.println("1. Male");
            System.out.println("2. Female");
            System.out.println("3. Other");


            System.out.print("Enter Choice : ");


            try {


                int choice =
                        Integer.parseInt(scanner.nextLine());


                if (choice == 1) {

                    c.setGender("Male");
                    break;

                } else if (choice == 2) {

                    c.setGender("Female");
                    break;

                } else if (choice == 3) {

                    c.setGender("Other");
                    break;

                } else {

                    System.out.println("Invalid choice.");

                }


            } catch (Exception e) {

                System.out.println("Enter number only.");

            }

        }
        // EMAIL

        c.setEmail(getValidEmail());
        // ADDRESS


        while (true) {


            System.out.print("Enter Address : ");

            String address =
                    scanner.nextLine().trim();


            if (address.isEmpty()) {

                System.out.println("Address cannot be empty.");

            } else {

                c.setAddress(address);
                break;

            }


        }


        // ID PROOF


        while (true) {


            System.out.println("\nSelect ID Proof");
            System.out.println("1. Aadhaar Card");
            System.out.println("2. PAN Card");
            System.out.println("3. Passport");


            System.out.print("Enter Choice : ");


            try {


                int choice =
                        Integer.parseInt(scanner.nextLine());


                if (choice == 1) {

                    c.setIdProof("Aadhaar Card");
                    break;

                } else if (choice == 2) {

                    c.setIdProof("PAN Card");
                    break;

                } else if (choice == 3) {

                    c.setIdProof("Passport");
                    break;

                } else {

                    System.out.println("Invalid Choice");

                }


            } catch (Exception e) {

                System.out.println("Enter number only.");

            }


        }
        // PASSWORD

        while (true) {

            System.out.print("Enter Password : ");
            String password = scanner.nextLine();

            // Password Validation
            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$")) {

                System.out.println("Password must contain:");
                System.out.println("- 8 to 20 characters");
                System.out.println("- At least 1 Uppercase letter");
                System.out.println("- At least 1 Lowercase letter");
                System.out.println("- At least 1 Number");
                System.out.println("- At least 1 Special Character");
                continue;
            }

            System.out.print("Confirm Password : ");
            String confirm = scanner.nextLine();

            if (!password.equals(confirm)) {
                System.out.println("Password does not match.");
                continue;
            }

            c.setPassword(password);
            break;
        }
        c.setFirstLogin(0);
        customerService.addCustomer(c);


    }
    // ================= VIEW CUSTOMERS =================

    private void viewCustomers() {

        String query = "SELECT * FROM customer";


        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);


            while (rs.next()) {

                System.out.println("\nCustomer ID : " + rs.getInt("customerId"));
                System.out.println("Name        : " + rs.getString("name"));
                System.out.println("Email       : " + rs.getString("email"));
                System.out.println("Phone       : " + rs.getString("phone"));
                System.out.println("-----------------------------");

            }


            rs.close();
            st.close();
            con.close();


        } catch (Exception e) {

            System.out.println("Unable to fetch customers.");

        }

    }


    // ================= SEARCH CUSTOMER =================

    private void searchCustomer() {

        try {

            System.out.print("Enter Customer ID : ");

            int id = Integer.parseInt(scanner.nextLine());

            customerService.searchCustomer(id);


        } catch (Exception e) {

            System.out.println("Invalid Customer ID.");

        }

    }


    // ================= UPDATE CUSTOMER =================

    private void updateCustomer() {

        try {


            System.out.print("Enter Customer ID : ");

            int id = Integer.parseInt(scanner.nextLine());


            System.out.println("\n1.Name");
            System.out.println("2.Gender");
            System.out.println("3.Age");
            System.out.println("4.Phone");
            System.out.println("5.Email");
            System.out.println("6.Address");
            System.out.println("7.ID Proof");
            System.out.println("8.Check-In Date");
            System.out.println("9.Check-Out Date");


            System.out.print("Enter Choice : ");

            int choice =
                    Integer.parseInt(scanner.nextLine());


            System.out.print("Enter New Value : ");

            String value =
                    scanner.nextLine();


            customerService.updateCustomer(id, choice, value);


        } catch (Exception e) {

            System.out.println("Invalid Input.");

        }

    }


    // ================= DELETE CUSTOMER =================

    private void deleteCustomer() {

        try {

            System.out.print("Enter Customer ID : ");

            int id =
                    Integer.parseInt(scanner.nextLine());


            customerService.deleteCustomer(id);


        } catch (Exception e) {

            System.out.println("Invalid Customer ID.");

        }

    }


    // ================= PHONE VALIDATION =================

    private String getValidPhone() {


        while (true) {


            System.out.print("Enter Phone : ");

            String phone =
                    scanner.nextLine().trim();


            if (phone.matches("[6-9][0-9]{9}")) {

                return phone;

            } else {

                System.out.println(
                        "Enter valid 10 digit mobile number."
                );

            }

        }

    }
    public void customerLogin() {

        while (true) {

            System.out.print("Enter Customer Name : ");
            String name = scanner.nextLine().trim();

            if (!customerService.checkCustomerExists(name)) {

                System.out.println("\nCustomer Not Found.");
                System.out.println("If You Are Not Registered Yet,");
                System.out.println("Please Complete Your Registration First.");
                return;
            }
            int customerId = customerService.getCustomerIdByName(name);

            int firstLogin = customerService.getFirstLoginStatus(customerId);

            // ================= ADMIN ADDED CUSTOMER =================

            if (firstLogin == 1) {

                while (true) {
                    String adminPassword = customerService.getCustomerPassword(customerId);

                    System.out.println("\n==================================");
                    System.out.println(" ADMIN ADDED YOU");
                    System.out.println(" ADMIN GENERATED PASSWORD : " + adminPassword);
                    System.out.println("==================================");
                    System.out.println("1. Login With This Password");
                    System.out.println("2. Change Password");
                    System.out.print("Enter Choice : ");

                    int option;

                    try {

                        option = Integer.parseInt(scanner.nextLine());

                    } catch (Exception e) {

                        System.out.println("Enter Numbers Only.");
                        continue;
                    }

                    // ================= LOGIN WITH ADMIN PASSWORD =================
                    if (option == 1) {

                        System.out.println("\n==================================");
                        System.out.println(" Login Successful!");
                        System.out.println(" Welcome " + name);
                        System.out.println("==================================");

                        customerDashboard(customerId);
                        return;

                    }
                    // ================= CHANGE PASSWORD =================

                    else if (option == 2) {

                        while (true) {

                            System.out.print("Enter New Password : ");
                            String newPassword = scanner.nextLine();

                            if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$")) {

                                System.out.println("Invalid Password Format.");
                                continue;

                            }

                            System.out.print("Confirm Password : ");
                            String confirm = scanner.nextLine();

                            if (!newPassword.equals(confirm)) {

                                System.out.println("Password Does Not Match.");
                                continue;

                            }

                            customerService.updatePassword(customerId, newPassword);

                            System.out.println("\nPassword Changed Successfully.");

                            System.out.println("\n==================================");
                            System.out.println(" Login Successful!");
                            System.out.println(" Welcome " + name);
                            System.out.println("==================================");

                            customerDashboard(customerId);

                            return;

                        }

                    }

                    else {

                        System.out.println("Invalid Choice.");

                    }

                }

            }

            // ================= NORMAL CUSTOMER LOGIN =================

            else {

                while (true) {

                    System.out.print("Enter Password : ");

                    String password = scanner.nextLine();

                    int login = customerService.customerLogin(name, password);

                    if (login != -1) {

                        System.out.println("\n==================================");
                        System.out.println(" Login Successful!");
                        System.out.println(" Welcome " + name);
                        System.out.println("==================================");

                        customerDashboard(customerId);

                        return;

                    }
                    else {

                        System.out.println("\nWrong Password.");
                        System.out.println("1. Try Login Again");
                        System.out.println("2. Customer Registration");
                        System.out.println("3. Forget Password");
                        System.out.print("Enter Choice : ");

                        int option;

                        try {

                            option = Integer.parseInt(scanner.nextLine());

                        } catch (Exception e) {

                            System.out.println("Invalid Choice.");
                            continue;

                        }

                        if(option == 1) {

                            continue;

                        }
                        else if(option == 2) {

                            addCustomer();
                            return;

                        }
                        else if(option == 3) {

                            String phone;

                            while(true) {

                                System.out.print("Enter Registered Mobile Number : ");
                                phone = scanner.nextLine().trim();

                                if(!phone.matches("[6-9][0-9]{9}")) {

                                    System.out.println("Enter Valid 10 Digit Mobile Number.");
                                    continue;

                                }

                                if(!customerService.verifyCustomerPhone(customerId, phone)) {

                                    System.out.println("Mobile Number Does Not Match.");
                                    continue;

                                }

                                break;

                            }

                            System.out.println("\nVerifying...");

                            try {

                                Thread.sleep(2000);

                            } catch (InterruptedException e) {

                                e.printStackTrace();

                            }

                            System.out.println("Verification Successful.");

                            while(true) {

                                System.out.print("Confirm New Password : ");
                                String confirm = scanner.nextLine();

                                if(!confirm.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$")) {

                                    System.out.println("Password must contain:");
                                    System.out.println("- 8 to 20 characters");
                                    System.out.println("- At least 1 Uppercase");
                                    System.out.println("- At least 1 Lowercase");
                                    System.out.println("- At least 1 Number");
                                    System.out.println("- At least 1 Special Character");
                                    continue;

                                }

                                System.out.print("Re-enter Confirm Password : ");
                                String confirmAgain = scanner.nextLine();

                                if(!confirm.equals(confirmAgain)) {

                                    System.out.println("Password Does Not Match.");
                                    continue;

                                }

                                customerService.updatePassword(customerId, confirm);

                                System.out.println("\nPassword Updated Successfully.");

                                System.out.println("\n==================================");
                                System.out.println(" Login Successful!");
                                System.out.println(" Welcome " + name);
                                System.out.println("==================================");

                                customerDashboard(customerId);

                                return;

                            }

                        }

                        else {

                            System.out.println("Invalid Choice.");

                        }

                    }
                }

            }

        }

    }

    // ================= EMAIL VALIDATION =================

    private String getValidEmail() {


        while (true) {


            System.out.print("Enter Email : ");

            String email =
                    scanner.nextLine().trim();


            if (!email.contains("@")) {

                email =
                        email + "@gmail.com";

            }


            if (email.matches(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

                return email;

            }


            System.out.println("Invalid Email.");

        }

    }


    // ================= DATE VALIDATION =================

    private String getValidDate(String message) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");


        LocalDate today =
                LocalDate.now();


        while (true) {

            System.out.print(message);

            String date =
                    scanner.nextLine().trim();


            if (date.isEmpty()) {

                return today.toString();

            }


            try {

                LocalDate entered =
                        LocalDate.parse(date, formatter);


                if (entered.isBefore(today)) {

                    System.out.println(
                            "Past date not allowed."
                    );

                    continue;

                }


                return entered.toString();


            } catch (DateTimeParseException e) {

                System.out.println(
                        "Enter date format yyyy-MM-dd"
                );

            }

        }

    }
    // ================= CUSTOMER DASHBOARD =================


    private void customerDashboard(int customerId) {


        while (true) {


            System.out.println("\n===== CUSTOMER DASHBOARD =====");

            System.out.println("1. Book Room");
            System.out.println("2. Check-In");
            System.out.println("3. Check-Out");
            System.out.println("4. Food Order");
            System.out.println("5. Game Zone");
            System.out.println("6. Payment");
            System.out.println("7. Give Feedback");
            System.out.println("0. Logout");


            System.out.print("Enter Choice : ");


            int choice;


            try {

                choice =
                        Integer.parseInt(scanner.nextLine());


            } catch (Exception e) {

                System.out.println("Enter number only.");
                continue;

            }


            switch (choice) {

                case 1:

                    // Booking
                    if (bookingMenu == null) {
                        bookingMenu = new BookingMenu(customerId);
                    }

                    bookingMenu.displayMenu();

                    break;
                case 2:

                    checkInOutService.checkIn(customerId);

                    break;
                case 3: {

                    System.out.print("Enter Check ID : ");

                    String input = scanner.nextLine().trim();

                    if (input.isEmpty()) {

                        System.out.println("Check ID Cannot Be Empty.");
                        break;

                    }

                    int checkOutId;

                    try {

                        checkOutId = Integer.parseInt(input);

                    } catch (Exception e) {

                        System.out.println("Enter Numbers Only.");
                        break;

                    }

                    if (checkOutId <= 0) {

                        System.out.println("Invalid Check ID.");
                        break;

                    }

                    checkInOutService.checkOut(checkOutId);

                    break;

                }
                case 4:

                    // Food Order
                    FoodOrderMenu foodMenu =
                            new FoodOrderMenu(customerId);

                    foodMenu.displayMenu();

                    break;


                case 5:

                    // Game Zone
                    GameMenu gameMenu =
                            new GameMenu(customerId);

                    gameMenu.displayMenu();

                    break;


                case 6:

                    // Payment
                    PaymentMenu paymentMenu =
                            new PaymentMenu(customerId);
                    paymentMenu.displayMenu();

                    break;

                case 7:

                    FeedbackMenu feedbackMenu = new FeedbackMenu(customerId);

                    feedbackMenu.displayMenu();

                    break;
                case 0:

                    PaymentService paymentService = new PaymentService();

                    if (paymentService.hasPendingPayment(customerId)) {

                        System.out.println("Pending Payment Found.");
                        System.out.println("Please Complete Payment Before Logout.");
                        break;

                    }

                    System.out.println("Logout Successful.");

                    return;

                default:

                    System.out.println(
                            "Invalid Choice.");

            }

        }


    }


    // ================= PAYMENT MENU =================


    private void paymentMenu(int customerId) {

        while (true) {

            System.out.println("\n===== PAYMENT MENU =====");

            System.out.println("1. Make Payment");
            System.out.println("2. Show My Transaction");
            System.out.println("0. Back");


            System.out.print("Enter Choice : ");


            int choice;


            try {

                choice =
                        Integer.parseInt(scanner.nextLine());

            } catch (Exception e) {

                System.out.println("Enter number only.");
                continue;

            }


            switch (choice) {
                case 1:

                    PaymentMenu paymentMenu =
                            new PaymentMenu(customerId);

                    paymentMenu.displayMenu();

                    break;
                case 2:

                    PaymentService service = new PaymentService();

                    service.showMyTransaction(customerId);

                    break;


                case 0:

                    return;


                default:

                    System.out.println("Invalid Choice.");

            }

        }

    }


}