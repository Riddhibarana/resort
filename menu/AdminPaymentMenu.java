package menu;

import java.util.Scanner;
import database.AdminPaymentDAO;

public class AdminPaymentMenu {

    private Scanner scanner;
    private AdminPaymentDAO dao;

    public AdminPaymentMenu() {

        scanner = new Scanner(System.in);
        dao = new AdminPaymentDAO();

    }

    public void displayMenu() {

        while (true) {

            System.out.println("\n========== PAYMENT MANAGEMENT ==========");
            System.out.println("1. View All Payments");
            System.out.println("2. Search Payment");
            System.out.println("3. Delete Payment");
            System.out.println("0. Back");

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

            switch (choice) {

                case 1:

                    dao.displayAllPayments();
                    break;

                case 2:

                    searchPayment();
                    break;

                case 3:

                    deletePayment();
                    break;

                case 0:

                    return;

                default:

                    System.out.println("Invalid Choice.");

            }

        }

    }

    private void searchPayment() {

        System.out.print("Enter Payment ID : ");

        try {

            int paymentId = Integer.parseInt(scanner.nextLine());

            dao.searchPayment(paymentId);

        } catch (NumberFormatException e) {

            System.out.println("Enter Numbers Only.");

        }

    }

    private void deletePayment() {

        System.out.print("Enter Payment ID : ");

        try {

            int paymentId = Integer.parseInt(scanner.nextLine());

            dao.deletePayment(paymentId);

        } catch (NumberFormatException e) {

            System.out.println("Enter Numbers Only.");

        }

    }

}