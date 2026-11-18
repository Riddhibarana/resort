package menu;

import java.util.Scanner;
import service.BookingService;

public class AdminBookingMenu {

    private Scanner scanner;
    private BookingService bookingService;

    public AdminBookingMenu() {

        scanner = new Scanner(System.in);
        bookingService = new BookingService();

    }

    public void displayMenu() {

        while (true) {

            System.out.println("\n========== BOOKING MANAGEMENT ==========");
            System.out.println("1. View All Bookings");
            System.out.println("2. Search Booking");
            System.out.println("3. Delete Booking");
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

                    bookingService.displayBookings();
                    break;

                case 2:

                    searchBooking();
                    break;

                case 3:

                    deleteBooking();
                    break;

                case 0:

                    return;

                default:

                    System.out.println("Invalid Choice.");

            }

        }

    }

    // ================= SEARCH BOOKING =================

    private void searchBooking() {

        while (true) {

            System.out.print("Enter Booking ID : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Booking ID Cannot Be Empty.");
                return;

            }

            try {

                int bookingId = Integer.parseInt(input);

                bookingService.searchBooking(bookingId);
                return;

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");

            }

        }

    }

    // ================= DELETE BOOKING =================

    private void deleteBooking() {

        while (true) {

            System.out.print("Enter Booking ID : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Booking ID Cannot Be Empty.");
                continue;

            }

            int bookingId;

            try {

                bookingId = Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");
                continue;

            }

            // Booking Exists Check
            if (!bookingService.checkBookingId(bookingId)) {

                System.out.println("Booking Not Found.");
                continue;

            }

            System.out.print("Are You Sure? (Y/N) : ");

            String confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("Y")) {

                bookingService.deleteBooking(bookingId);

            } else {

                System.out.println("Delete Cancelled.");

            }

            return;

        }

    }
}