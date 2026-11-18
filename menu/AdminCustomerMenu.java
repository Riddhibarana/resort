package menu;

import java.util.Scanner;
import model.customer;
import service.CustomerService;

public class AdminCustomerMenu {

    private Scanner scanner;
    private CustomerService customerService;

    // Constructor
    public AdminCustomerMenu() {

        scanner = new Scanner(System.in);
        customerService = new CustomerService();

    }

    // ================= CUSTOMER MANAGEMENT =================

    public void displayMenu() {

        int choice;

        while (true) {

            System.out.println("\n========== CUSTOMER MANAGEMENT ==========");
            System.out.println("1. Add Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Search Customer");
            System.out.println("4. Update Customer");
            System.out.println("5. Delete Customer");
            System.out.println("0. Back");
            System.out.print("Enter Choice : ");

            String input = scanner.nextLine().trim();

            // Empty Input
            if (input.isEmpty()) {

                System.out.println("Choice Cannot Be Empty.");
                continue;

            }

            // Number Validation
            try {

                choice = Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");
                continue;

            }

            switch (choice) {

                case 1:
                    addCustomer();
                    break;

                case 2:
                    viewAllCustomers();
                    break;

                case 3:
                    searchCustomer();
                    break;

                case 4:
                    updateCustomer();
                    break;

                case 5:
                    deleteCustomer();
                    break;

                case 0:
                    System.out.println("Returning...");
                    return;

                default:
                    System.out.println("Invalid Choice. Please Enter 0-5.");
            }

        }

    }
    // ================= ADD CUSTOMER =================

    private void addCustomer() {

        customer c = new customer();

        // ================= NAME =================

        while (true) {

            System.out.print("Enter Name : ");

            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {

                System.out.println("Name Cannot Be Empty.");

            } else {

                c.setName(name);
                break;

            }

        }

        // ================= AGE =================

        while (true) {

            System.out.print("Enter Age : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Age Cannot Be Empty.");
                continue;

            }

            try {

                int age = Integer.parseInt(input);

                if (age >= 20 && age <= 100) {

                    c.setAge(age);
                    break;

                } else {

                    System.out.println("Age Must Be Between 20 And 100.");

                }

            } catch (NumberFormatException e) {

                System.out.println("Enter Valid Age.");

            }

        }

        // ================= PHONE =================

        c.setPhone(getValidPhone());

        // ================= GENDER =================

        while (true) {

            System.out.println("\nSelect Gender");
            System.out.println("1. Male");
            System.out.println("2. Female");
            System.out.println("3. Other");

            System.out.print("Enter Choice : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Choice Cannot Be Empty.");
                continue;

            }

            try {

                int choice = Integer.parseInt(input);

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

                    System.out.println("Invalid Choice.");

                }

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");

            }

        }

        // ================= EMAIL =================

        String email = getValidEmail();

        c.setEmail(email);

        System.out.println("Generated Email ID : " + email);
        // ================= ADDRESS =================

        while (true) {

            System.out.print("Enter Address : ");

            String address = scanner.nextLine().trim();

            if (address.isEmpty()) {

                System.out.println("Address Cannot Be Empty.");

            } else {

                c.setAddress(address);
                break;

            }

        }
        // ================= ID PROOF =================

        while (true) {

            System.out.println("\nSelect ID Proof");
            System.out.println("1. Aadhaar Card");
            System.out.println("2. PAN Card");
            System.out.println("3. Passport");

            System.out.print("Enter Choice : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Choice Cannot Be Empty.");
                continue;

            }

            try {

                int choice = Integer.parseInt(input);

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

                    System.out.println("Invalid Choice.");

                }

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");

            }

        }

        // ================= PASSWORD =================

        while (true) {

            System.out.print("Enter Password : ");
            String password = scanner.nextLine();

            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$")) {

                System.out.println("Password must contain:");
                System.out.println("- 8 to 20 characters");
                System.out.println("- At least 1 Uppercase");
                System.out.println("- At least 1 Lowercase");
                System.out.println("- At least 1 Number");
                System.out.println("- At least 1 Special Character");
                continue;

            }

            System.out.print("Confirm Password : ");
            String confirm = scanner.nextLine();

            if (!password.equals(confirm)) {

                System.out.println("Password Does Not Match.");
                continue;

            }

            c.setPassword(password);
            break;

        }
        c.setFirstLogin(1);

        customerService.addCustomer(c);

        System.out.println("Customer Added Successfully.");

    }


// ================= VIEW ALL CUSTOMERS =================

    private void viewAllCustomers() {

        customerService.displayCustomers();

    }

    // ================= SEARCH CUSTOMER =================

// ================= SEARCH CUSTOMER =================

    private void searchCustomer() {

        while (true) {

            System.out.print("Enter Customer Name : ");

            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {

                System.out.println("Customer Name Cannot Be Empty.");
                continue;

            }

            customerService.searchCustomerByName(name);
            return;

        }

    }
// ================= UPDATE CUSTOMER =================
private void updateCustomer() {

    try {

        System.out.print("Enter Customer ID : ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("\n1. Name");
        System.out.println("2. Gender");
        System.out.println("3. Age");
        System.out.println("4. Phone");
        System.out.println("5. Email");
        System.out.println("6. Address");
        System.out.println("7. ID Proof");

        System.out.print("Enter Choice : ");
        int choice = Integer.parseInt(scanner.nextLine());

        String value = "";

        switch (choice) {

            case 1:

                while (true) {

                    System.out.print("Enter New Name : ");
                    value = scanner.nextLine().trim();

                    if (value.matches("[A-Za-z ]{3,30}")) {

                        break;

                    }

                    System.out.println("Enter Valid Name.");

                }

                break;

            case 2:

                while (true) {

                    System.out.println("1. Male");
                    System.out.println("2. Female");
                    System.out.println("3. Other");

                    System.out.print("Enter Choice : ");

                    int gender = Integer.parseInt(scanner.nextLine());

                    if (gender == 1) {

                        value = "Male";
                        break;

                    } else if (gender == 2) {

                        value = "Female";
                        break;

                    } else if (gender == 3) {

                        value = "Other";
                        break;

                    }

                    System.out.println("Invalid Choice.");

                }

                break;

            case 3:

                while (true) {

                    System.out.print("Enter New Age : ");
                    value = scanner.nextLine();

                    try {

                        int age = Integer.parseInt(value);

                        if (age >= 20 && age <= 100) {

                            break;

                        }

                    } catch (Exception e) {
                    }

                    System.out.println("Age Must Be Between 20 And 100.");

                }

                break;

            case 4:

                while (true) {

                    System.out.print("Enter New Phone : ");
                    value = scanner.nextLine().trim();

                    if (value.matches("[6-9][0-9]{9}")) {

                        break;

                    }

                    System.out.println("Enter Valid 10 Digit Mobile Number.");

                }

                break;

            case 5:

                while (true) {

                    System.out.print("Enter New Email : ");
                    value = scanner.nextLine().trim();

                    if (!value.contains("@")) {

                        value = value + "@gmail.com";

                    }

                    if (value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

                        break;

                    }

                    System.out.println("Invalid Email.");

                }

                break;

            case 6:

                while (true) {

                    System.out.print("Enter New Address : ");
                    value = scanner.nextLine().trim();

                    if (!value.isEmpty()) {

                        break;

                    }

                    System.out.println("Address Cannot Be Empty.");

                }

                break;

            case 7:

                while (true) {

                    System.out.println("1. Aadhaar Card");
                    System.out.println("2. PAN Card");
                    System.out.println("3. Passport");

                    System.out.print("Enter Choice : ");

                    int proof = Integer.parseInt(scanner.nextLine());

                    if (proof == 1) {

                        value = "Aadhaar Card";
                        break;

                    } else if (proof == 2) {

                        value = "PAN Card";
                        break;

                    } else if (proof == 3) {

                        value = "Passport";
                        break;

                    }

                    System.out.println("Invalid Choice.");

                }

                break;

            default:

                System.out.println("Invalid Choice.");
                return;

        }

        customerService.updateCustomer(id, choice, value);

    } catch (NumberFormatException e) {

        System.out.println("Enter Numbers Only.");

    } catch (Exception e) {

        System.out.println("Invalid Input.");

    }

}// ================= DELETE CUSTOMER =================

    private void deleteCustomer() {

        while (true) {

            System.out.print("Enter Customer ID : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Customer ID Cannot Be Empty.");
                return;

            }

            try {

                int id = Integer.parseInt(input);

                customerService.deleteCustomer(id);
                return;

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");

            }

        }

    }


// ================= EMAIL VALIDATION =================

    private String getValidEmail() {

        while (true) {

            System.out.print("Enter Email : ");

            String email = scanner.nextLine().trim();

            if (!email.contains("@")) {

                email = email + "@gmail.com";

            }

            if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

                return email;

            }

            System.out.println("Invalid Email.");

        }

    }
// ================= PHONE VALIDATION =================

    private String getValidPhone() {

        while (true) {

            System.out.print("Enter Phone : ");

            String phone = scanner.nextLine().trim();

            if (!phone.matches("[6-9][0-9]{9}")) {

                System.out.println("Enter Valid 10 Digit Mobile Number.");
                continue;

            }

            if (customerService.checkPhoneExists(phone)) {

                System.out.println("This Phone Number Already Exists.");
                continue;

            }

            return phone;

        }

    }
}