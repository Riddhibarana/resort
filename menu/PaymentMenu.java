package menu;

import java.util.Scanner;
import service.PaymentService;

public class PaymentMenu {

    private Scanner scanner;
    private PaymentService service;
    private int customerId;

    // Customer ke liye constructor
    public PaymentMenu(int customerId) {

        scanner = new Scanner(System.in);
        service = new PaymentService();
        this.customerId = customerId;

    }

    // ================= PAYMENT MENU =================

    public void displayMenu() {

        int choice;

        while (true) {

            System.out.println("\n========== PAYMENT ==========");
            System.out.println("1. Make Payment");
            System.out.println("2. My Transaction");
            System.out.println("0. Back");

            System.out.print("Enter Choice : ");

            String input = scanner.nextLine().trim();

            // Empty Input
            if(input.isEmpty()) {

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

                    makePayment();
                    break;

                case 2:

                    service.showMyTransaction(customerId);
                    break;

                case 0:

                    System.out.println("Returning...");
                    return;

                default:

                    System.out.println("Invalid Choice. Please Enter 0, 1 or 2.");

            }
        }
    }   // ================= MAKE PAYMENT =================
// ================= MAKE PAYMENT =================

    private void makePayment() {

        // Payment already completed
        if (service.isPaymentDone(customerId)) {

            System.out.println("\n================================");
            System.out.println("Payment Is Already Done.");
            System.out.println("================================");

            return;
        }

        System.out.println("\n========== MAKE PAYMENT ==========");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. Online");

        int method;

        while (true) {

            System.out.print("Enter Choice : ");

            String input = scanner.nextLine().trim();

            // Empty Input
            if (input.isEmpty()) {

                System.out.println("Choice Cannot Be Empty.");
                continue;
            }

            // Number Validation
            try {

                method = Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");
                continue;
            }

            // Range Validation
            if (method >= 1 && method <= 3) {
                break;
            }

            System.out.println("Invalid Choice. Please Enter 1, 2 or 3.");
        }

        service.makePayment(customerId, method);
    }
}