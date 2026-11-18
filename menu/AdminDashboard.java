package menu;

import java.util.Scanner;

public class AdminDashboard {

    private Scanner scanner;

    public AdminDashboard() {
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {

        while (true) {

            System.out.println("\n========== ADMIN DASHBOARD ==========");
            System.out.println("1. Customer Management");
            System.out.println("2. Room Management");
            System.out.println("3. Booking Management");
            System.out.println("4. Food Order Management");
            System.out.println("5. Game Zone Management");
            System.out.println("6. Payment Management");
            System.out.println("7. Staff Management");
            System.out.println("8. Feedback Management");
            System.out.println("0. Logout");

            System.out.print("Enter Choice : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Choice Cannot Be Empty.");
                continue;

            }

            int choice;

            try {

                choice = Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");
                continue;

            }

            if (choice < 0 || choice > 8) {

                System.out.println("Invalid Choice.");
                continue;

            }
            switch (choice) {

                case 1:

                    AdminCustomerMenu customerMenu = new AdminCustomerMenu();
                    customerMenu.displayMenu();
                    break;

                case 2:

                    RoomMenu roomMenu = new RoomMenu();
                    roomMenu.displayMenu();
                    break;
                    case 3:

                    AdminBookingMenu bookingMenu = new AdminBookingMenu();
                    bookingMenu.displayMenu();
                    break;
                case 4:
                AdminFoodMenu menu = new AdminFoodMenu();
                menu.displayMenu();
                break;
                case 5:

                    AdminGameMenu gameMenu = new AdminGameMenu();
                    gameMenu.displayMenu();
                    break;
                case 6:

                    AdminPaymentMenu paymentMenu = new AdminPaymentMenu();
                    paymentMenu.displayMenu();
                    break;
                case 7:

                    StaffMenu staffMenu = new StaffMenu();
                    staffMenu.displayMenu();
                    break;
                    case 8:

                    AdminFeedbackMenu feedbackMenu = new AdminFeedbackMenu();
                    feedbackMenu.displayMenu();
                    break;
                case 0:

                    System.out.println("Logout Successful.");
                    return;

            }
        }

    }
}