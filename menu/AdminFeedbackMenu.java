package menu;

import java.util.Scanner;
import database.AdminFeedbackDAO;

public class AdminFeedbackMenu {

    private Scanner scanner;
    private AdminFeedbackDAO dao;

    public AdminFeedbackMenu() {

        scanner = new Scanner(System.in);
        dao = new AdminFeedbackDAO();

    }

    public void displayMenu() {

        while (true) {

            System.out.println("\n========== FEEDBACK MANAGEMENT ==========");
            System.out.println("1. View All Feedback");
            System.out.println("2. Search Feedback");
            System.out.println("3. Delete Feedback");
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

                    dao.displayAllFeedback();
                    break;

                case 2:

                    searchFeedback();
                    break;

                case 3:

                    deleteFeedback();
                    break;

                case 0:

                    System.out.println("Returning...");
                    return;

                default:

                    System.out.println("Invalid Choice.");

            }

        }

    }

    // ================= SEARCH FEEDBACK =================

    private void searchFeedback() {

        System.out.print("Enter Feedback ID : ");

        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {

            System.out.println("Feedback ID Cannot Be Empty.");
            return;

        }

        try {

            int feedbackId = Integer.parseInt(input);

            dao.searchFeedback(feedbackId);

        } catch (NumberFormatException e) {

            System.out.println("Enter Numbers Only.");

        }

    }

    // ================= DELETE FEEDBACK =================

    private void deleteFeedback() {

        System.out.print("Enter Feedback ID : ");

        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {

            System.out.println("Feedback ID Cannot Be Empty.");
            return;

        }
        try {

            int feedbackId = Integer.parseInt(input);

            if (dao.deleteFeedback(feedbackId)) {

                System.out.println("Feedback Deleted Successfully.");

            } else {

                System.out.println("Feedback Not Found.");

            }

        } catch (NumberFormatException e) {

            System.out.println("Enter Numbers Only.");

        }
    }

}