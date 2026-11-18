package service;

import java.util.Scanner;

import database.BookingDAO;
import database.CheckInOutDAO;

public class CheckInOutService {

    private CheckInOutDAO dao;

    public CheckInOutService() {

        dao = new CheckInOutDAO();

    }

    // ================= SHOW CUSTOMER CHECK-IN =================

    public void showCustomerCheckIns(int customerId) {

        dao.showCustomerCheckIns(customerId);

    }

    // ================= CUSTOMER CHECK-IN =================
// ================= CUSTOMER CHECK-IN =================

    public void checkIn(int customerId) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n================================");
        System.out.println("     YOUR BOOKING CHECK-IN");
        System.out.println("================================");

        boolean found = dao.showCustomerCheckIns(customerId);

        if (!found) {

            System.out.println("Please Book A Room First.");
            return;

        }

            System.out.print("\nEnter Check-In ID : ");

        int checkId;

        try {

            checkId = Integer.parseInt(scanner.nextLine());

        }
        catch(Exception e) {

            System.out.println("Invalid Input! Enter Number Only.");
            return;

        }


        // Check ID Exists
        if (!dao.checkCheckId(checkId)) {

            System.out.println("Invalid Check-In ID.");
            return;

        }


        // Customer Ownership
        if (!dao.checkCustomerCheckId(checkId, customerId)) {

            System.out.println("This Check-In ID Does Not Belong To You.");
            return;

        }


        // Already Checked-In
        if (dao.isAlreadyCheckedIn(checkId)) {

            System.out.println("This Booking Is Already Checked-In.");
            return;

        }
        int bookingId = dao.getBookingIdByCheckId(checkId);

        if (dao.isAlreadyCheckedOut(bookingId)) {

            System.out.println("Customer Has Already Checked-Out.");
            return;

        }
        // Update Status
        dao.updateStatus(checkId, "Checked-In");

        System.out.println("\n================================");
        System.out.println("     CHECK-IN SUCCESSFUL");
        System.out.println("================================");


        dao.showBookingByCheckId(checkId);

    }
    // ================= SHOW BOOKING DETAILS =================

    public void showBookingDetails(int checkId) {

        dao.showBookingByCheckId(checkId);

    }

    // ================= ADMIN VIEW RECORDS =================

    public void viewRecords() {

        dao.displayRecords();

    }
// ================= CHECK OUT =================
    public void checkOut(int checkId) {

        if (!dao.checkCheckId(checkId)) {

            System.out.println("Check-In ID Not Found.");
            return;

        }

        int bookingId = dao.getBookingIdByCheckId(checkId);

        if (dao.isAlreadyCheckedOut(bookingId)) {

            System.out.println("This Booking Is Already Checked-Out.");
            return;

        }

        if (!dao.isCheckedIn(checkId)) {

            System.out.println("Please Complete Check-In First.");
            return;

        }

        dao.checkOut(checkId);

        BookingDAO bookingDAO = new BookingDAO();
        bookingDAO.completeBooking(bookingId);

        System.out.println("\n================================");
        System.out.println("     CHECK-OUT SUCCESSFUL");
        System.out.println("================================");

    }
}