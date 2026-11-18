package menu;

import java.util.Scanner;
import database.AdminGameBookingDAO;

public class AdminGameMenu {

    private Scanner scanner;
    private AdminGameBookingDAO dao;

    public AdminGameMenu() {

        scanner = new Scanner(System.in);
        dao = new AdminGameBookingDAO();

    }

    public void displayMenu() {

        while (true) {

            System.out.println("\n========== GAME ZONE MANAGEMENT ==========");
            System.out.println("1. View All Game Bookings");
            System.out.println("2. Search Game Booking");
            System.out.println("3. Delete Game Booking");
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

                    dao.displayAllGameBookings();
                    break;

                case 2:

                    searchGameBooking();
                    break;
                case 3:

                    deleteGameBooking();
                    break;

                case 0:

                    System.out.println("Returning...");
                    return;

                default:

                    System.out.println("Invalid Choice.");

            }

        }

    }

    // ================= SEARCH GAME BOOKING =================

    private void searchGameBooking() {

        System.out.print("Enter Game Booking ID : ");

        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {

            System.out.println("Game Booking ID Cannot Be Empty.");
            return;

        }

        try {

            int gameBookingId = Integer.parseInt(input);

            dao.searchGameBooking(gameBookingId);

        } catch (NumberFormatException e) {

            System.out.println("Enter Numbers Only.");

        }

    }

    // ================= DELETE GAME BOOKING =================

    private void deleteGameBooking() {

        System.out.print("Enter Game Booking ID : ");

        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {

            System.out.println("Game Booking ID Cannot Be Empty.");
            return;

        }

        try {

            int gameBookingId = Integer.parseInt(input);

            dao.deleteGameBooking(gameBookingId);

        } catch (NumberFormatException e) {

            System.out.println("Enter Numbers Only.");

        }

    }


}