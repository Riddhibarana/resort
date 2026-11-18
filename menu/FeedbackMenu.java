package menu;

import java.time.LocalDate;
import java.util.Scanner;

import model.Feedback;
import service.FeedbackService;

public class FeedbackMenu {

    private Scanner scanner;
    private FeedbackService service;
    private int customerId;

    public FeedbackMenu(int customerId) {

        scanner = new Scanner(System.in);
        service = new FeedbackService();
        this.customerId = customerId;
    }

    // ================= FEEDBACK MENU =================

    public void displayMenu() {

        Feedback feedback = new Feedback();

        feedback.setCustomerId(customerId);

        System.out.println("\n========== FEEDBACK ==========");

        // ================= RATING =================

        while (true) {

            System.out.print("Enter Rating (1-5) : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Rating Cannot Be Empty.");
                continue;
            }

            try {

                int rating = Integer.parseInt(input);

                if (rating >= 1 && rating <= 5) {

                    feedback.setRating(rating);
                    break;

                } else {

                    System.out.println("Rating Must Be Between 1 And 5.");
                }

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");
            }
        }

        // ================= COMMENT =================

        while (true) {

            System.out.println("\nSelect Comment");
            System.out.println("1. Excellent");
            System.out.println("2. Good");
            System.out.println("3. Ok");
            System.out.println("4. Bad");
            System.out.println("5. Very Bad");

            System.out.print("Enter Choice : ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    feedback.setComment("Excellent");
                    break;

                case "2":
                    feedback.setComment("Good");
                    break;

                case "3":
                    feedback.setComment("Ok");
                    break;

                case "4":
                    feedback.setComment("Bad");
                    break;

                case "5":
                    feedback.setComment("Very Bad");
                    break;

                default:
                    System.out.println("Invalid Choice. Please Select Between 1 To 5.");
                    continue;
            }

            break;
        }

        // ================= FEEDBACK DATE =================

        feedback.setFeedbackDate(LocalDate.now());

        // ================= SAVE =================

        service.addFeedback(feedback);
    }
}