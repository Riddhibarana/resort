package menu;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import model.Booking;
import service.BookingService;
import service.RoomService;

public class BookingMenu {

    private Scanner scanner;
    private BookingService bookingService;
    private int customerId;

    // Constructor
    public BookingMenu(int customerId) {

        scanner = new Scanner(System.in);
        bookingService = new BookingService();
        this.customerId = customerId;

    }

    // ================= BOOKING MENU =================

    public void displayMenu() {

        int choice;

        do {

            try {

                System.out.println("\n========== BOOKING MANAGEMENT ==========");
                System.out.println("1. Create New Booking");
                System.out.println("2. View My Booking");
                System.out.println("3. Cancel My Booking");
                System.out.println("0. Back");

                System.out.print("Enter Choice : ");

                choice = Integer.parseInt(scanner.nextLine());

                switch(choice) {

                    case 1:
                        createBooking();
                        break;

                    case 2:
                        bookingService.viewMyBooking(customerId);
                        break;

                    case 3:
                        cancelMyBooking();
                        break;

                    case 0:
                        System.out.println("Returning...");
                        break;

                    default:
                        System.out.println("Invalid Choice.");

                }

            }
            catch(Exception e) {

                System.out.println("Enter Number Only.");
                choice = -1;

            }

        } while(choice != 0);

    }
    private void createBooking() {

        try {

            RoomService roomService = new RoomService();

            roomService.displayAvailableRooms();

            Booking booking = new Booking();

            System.out.println("\n========== CREATE BOOKING ==========");

            // Booking ID
            // Booking ID
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

                } catch (Exception e) {

                    System.out.println("Enter Numbers Only.");
                    continue;

                }

                if (bookingId < 100 || bookingId > 999) {

                    System.out.println("Booking ID Must Be Exactly 3 Digits.");
                    continue;

                }

                if (bookingService.checkBookingId(bookingId)) {

                    System.out.println("Booking ID Already Exists.");

                } else {

                    booking.setBookingId(bookingId);
                    break;

                }

            }booking.setCustomerId(customerId);

            // Room ID
            int roomCapacity = 0;

            while (true) {

                System.out.print("Enter Room ID : ");

                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {

                    System.out.println("Room ID Cannot Be Empty.");
                    continue;

                }

                int roomId;

                try {

                    roomId = Integer.parseInt(input);

                } catch (Exception e) {

                    System.out.println("Enter Numbers Only.");
                    continue;

                }

                if (!roomService.isRoomIdExists(roomId)) {

                    System.out.println("Room ID Not Found.");
                    continue;

                }

                if (!roomService.isRoomAvailable(roomId)) {

                    System.out.println("Room Already Booked.");
                    continue;

                }

                booking.setRoomId(roomId);

                roomCapacity = roomService.getRoomCapacity(roomId);

                break;

            }

            // Check-In Date
            String checkInDate =
                    getValidDate("Enter Check-In Date (yyyy-MM-dd): ");

            booking.setCheckInDate(checkInDate);

            // Check-Out Date
            while (true) {

                String checkOutDate =
                        getValidDate("Enter Check-Out Date (yyyy-MM-dd): ");

                LocalDate checkIn =
                        LocalDate.parse(checkInDate);

                LocalDate checkOut =
                        LocalDate.parse(checkOutDate);

                if (!checkOut.isAfter(checkIn)) {

                    System.out.println("Check-Out Date Must Be Greater Than Check-In Date.");
                    continue;

                }

                long totalDays =
                        ChronoUnit.DAYS.between(checkIn, checkOut);

                if (totalDays > 15) {

                    System.out.println("Maximum Stay Allowed Is 15 Days.");
                    continue;

                }

                booking.setCheckOutDate(checkOutDate);
                break;

            }

            while (true) {

                int guests = getValidGuestCount(roomCapacity);

                if (guests == -1) {

                    while (true) {

                        System.out.print("Enter Another Room ID : ");

                        String input = scanner.nextLine().trim();

                        int roomId;

                        try {

                            roomId = Integer.parseInt(input);

                        } catch (Exception e) {

                            System.out.println("Enter Numbers Only.");
                            continue;

                        }

                        if (!roomService.isRoomIdExists(roomId)) {

                            System.out.println("Room ID Not Found.");
                            continue;

                        }

                        if (!roomService.isRoomAvailable(roomId)) {

                            System.out.println("Room Already Booked.");
                            continue;

                        }

                        booking.setRoomId(roomId);
                        roomCapacity = roomService.getRoomCapacity(roomId);
                        break;

                    }

                    continue;

                }

                booking.setNumberOfGuests(guests);
                break;

            }
            booking.setBookingStatus("Confirmed");

            bookingService.addBooking(booking);

        }
        catch (Exception e) {

            System.out.println("Booking Failed : " + e.getMessage());

        }

    }

    private void viewBookings() {

        System.out.println("\n========== MY BOOKINGS ==========");

        bookingService.viewMyBooking(customerId);

    }
    private void cancelMyBooking()
    {
        int latestId = bookingService.getLatestBookingIdByCustomer(customerId);

        if(latestId == -1) {

            System.out.println("\nNo Active Bookings Are Available.");
            System.out.println("Cancellation Failed.");
            return;

        }

        bookingService.viewLatestBooking(customerId);

        System.out.print("\nEnter Booking ID To Cancel : ");

        int bookingId = Integer.parseInt(scanner.nextLine());

        if(bookingService.checkCustomerBooking(bookingId, customerId)) {

            bookingService.cancelBooking(
                    bookingId,
                    customerId);
        }
        else {

            System.out.println("Invalid Booking ID.");

        }

    }
    private String getValidDate(String message) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate today = LocalDate.now();

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {

                LocalDate date =
                        LocalDate.parse(input, formatter);

                // Past date not allowed
                if (date.isBefore(today)) {

                    System.out.println("Past Date Not Allowed.");
                    continue;

                }

                // Only current year and next year allowed
                if (date.getYear() < today.getYear()
                        || date.getYear() > today.getYear() + 1) {

                    System.out.println("Booking Allowed Only Till Next Year.");
                    continue;

                }

                return date.toString();

            } catch (DateTimeParseException e) {

                System.out.println("Invalid Date Format. Use yyyy-MM-dd");

            }

        }
    }

        private int getValidGuestCount(int roomCapacity) {

            while (true) {

                System.out.print("Enter Number Of Guests : ");

                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {

                    System.out.println("Guest Count Cannot Be Empty.");
                    continue;

                }

                try {

                    int guests = Integer.parseInt(input);

                    if (guests <= 0) {

                        System.out.println("Minimum 1 Guest Is Required.");
                        continue;

                    }

                    if (guests > 5) {

                        System.out.println("Maximum 5 Guests Are Allowed In One Room.");
                        continue;

                    }

                    if (guests > roomCapacity) {

                        while (true) {

                            System.out.println("Room Capacity Is Only " + roomCapacity + " Guest(s).");
                            System.out.println("1. Enter Another Room ID");
                            System.out.println("2. Enter Guest Capacity Again");
                            System.out.print("Enter Choice : ");

                            String choice = scanner.nextLine().trim();

                            if (choice.equals("1")) {

                                return -1;

                            }

                            else if (choice.equals("2")) {

                                break;

                            }

                            else {

                                System.out.println("Invalid Choice.");

                            }

                        }

                        continue;

                    }
                    return guests;

                }

                catch (NumberFormatException e) {

                    System.out.println("Enter Numbers Only.");

                }

            }

        }


    }
