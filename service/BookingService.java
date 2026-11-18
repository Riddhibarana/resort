package service;

import datastructure.BookingLinkedList;
import exception.BookingException;
import database.BookingDAO;
import model.Booking;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import database.CustomerDAO;
import database.CheckInOutDAO;

public class BookingService {


    private BookingDAO bookingDAO;
    private RoomService roomService;
    private BookingLinkedList bookingDS;
    private CustomerDAO customerDAO;



    // ================= CONSTRUCTOR =================

    public BookingService() {


        bookingDAO = new BookingDAO();

        roomService = new RoomService();

        bookingDS = new BookingLinkedList();

        customerDAO = new CustomerDAO();

    }




    // ================= CREATE BOOKING =================

    public void createBooking(int customerId, int roomId)
            throws BookingException {


        if(customerId <= 0 || roomId <= 0) {


            throw new BookingException(
                    "Invalid Booking Details"
            );

        }


        System.out.println(
                "Booking Created Successfully"
        );

    }





    // ================= CALCULATE DAYS =================

    public long calculateDays(String checkIn,
                              String checkOut) {


        LocalDate inDate =
                LocalDate.parse(checkIn);


        LocalDate outDate =
                LocalDate.parse(checkOut);



        return ChronoUnit.DAYS.between(
                inDate,
                outDate
        );

    }





    // ================= ADD BOOKING =================

    public void addBooking(Booking booking) {



        if(booking == null) {


            System.out.println(
                    "Booking Data Missing."
            );

            return;

        }




        // CUSTOMER CHECK

        if(!bookingDAO.checkCustomerId(
                booking.getCustomerId())) {


            System.out.println(
                    "Customer ID Does Not Exist."
            );

            return;

        }
        // ACTIVE BOOKING CHECK






        // ROOM CHECK

        if(!roomService.isRoomAvailable(
                booking.getRoomId())) {



            System.out.println(
                    "Room Is Not Available."
            );

            return;

        }





        long days;


        try {


            days = calculateDays(
                    booking.getCheckInDate(),
                    booking.getCheckOutDate()
            );


        }
        catch(Exception e) {


            System.out.println(
                    "Invalid Date Format."
            );

            return;

        }





        if(days <= 0) {


            System.out.println(
                    "Check-Out Date Must Be Greater Than Check-In Date."
            );

            return;

        }





        double roomPrice =
                roomService.getRoomPrice(
                        booking.getRoomId()
                );



        double totalAmount =
                roomPrice * days;



        booking.setTotalAmount(totalAmount);



        booking.setBookingStatus(
                "Confirmed"
        );





        // SAVE BOOKING
        // BookingDAO automatically creates Check-In ID

        bookingDAO.addBooking(booking);




        // DS STORE

        bookingDS.addBooking(booking);





        // UPDATE ROOM

        roomService.updateRoomStatus(
                booking.getRoomId(),
                "Booked"
        );






        System.out.println();


        System.out.println("===============================");
        System.out.println("     BOOKING CONFIRMATION");
        System.out.println("===============================");


        System.out.println(
                "Customer ID : "
                        + booking.getCustomerId()
        );


        System.out.println(
                "Room ID : "
                        + booking.getRoomId()
        );


        System.out.println(
                "Check-In Date : "
                        + booking.getCheckInDate()
        );


        System.out.println(
                "Check-Out Date : "
                        + booking.getCheckOutDate()
        );


        System.out.println(
                "Total Nights : "
                        + days
        );


        System.out.println(
                "Room Price/Night : ₹"
                        + roomPrice
        );


        System.out.println(
                "Total Amount : ₹"
                        + totalAmount
        );


        System.out.println(
                "Booking Status : Confirmed"
        );


        System.out.println("===============================");


        System.out.println(
                "BOOKING CREATED SUCCESSFULLY"
        );


    }








    // ================= DISPLAY =================


    public void displayBookings() {


        bookingDAO.displayBookings();

    }







    // ================= SEARCH =================


    public void searchBooking(int bookingId) {


        if(bookingId <= 0) {


            System.out.println(
                    "Invalid Booking ID"
            );

            return;

        }


        bookingDAO.searchBooking(bookingId);

    }







    // ================= UPDATE STATUS =================


    public void updateBookingStatus(
            int bookingId,
            String status) {



        bookingDAO.updateBookingStatus(
                bookingId,
                status
        );

    }








    // ================= CHECK BOOKING =================


    public boolean checkBookingId(int bookingId) {


        return bookingDAO.checkBookingId(
                bookingId
        );

    }






    // ================= CHECK CUSTOMER =================


    public boolean checkCustomerId(int customerId) {


        return bookingDAO.checkCustomerId(
                customerId
        );

    }







    // ================= CUSTOMER NAME =================


    public int getCustomerIdByName(String name) {


        return bookingDAO.getCustomerIdByName(name);

    }








    // ================= ROOM SERVICE =================


    public RoomService getRoomService() {


        return roomService;

    }








    public String[] getCustomerBookingDates(int customerId) {


        return customerDAO.getCustomerBookingDates(
                customerId
        );

    }








    public boolean checkCustomerBooking(
            int bookingId,
            int customerId) {


        return bookingDAO.checkCustomerBooking(
                bookingId,
                customerId
        );

    }








    public void viewMyBooking(int customerId) {


        bookingDAO.viewMyBooking(customerId);

    }








    public int getLatestBookingId() {


        return bookingDS.getLastBookingId();

    }








    public void viewLatestBooking(int customerId) {


        bookingDAO.viewLatestBooking(customerId);

    }
    public void deleteCustomerBooking(int bookingId, int customerId) {

        int latestBookingId =
                bookingDAO.getLatestBookingIdByCustomer(customerId);

        if (latestBookingId == -1) {

            System.out.println("No Booking Found.");
            return;

        }

        if (bookingId != latestBookingId) {

            System.out.println("Only Latest Booking Can Be Cancelled.");
            return;

        }

        int roomId =
                bookingDAO.getRoomIdFromBooking(bookingId);

        bookingDAO.deleteCustomerBooking(bookingId);

        roomService.updateRoomStatus(roomId, "Available");

    }







    public int getLatestBookingIdByCustomer(int customerId) {


        return bookingDAO.getLatestBookingIdByCustomer(
                customerId
        );
    }
     //=============== DELETE BOOKING =================

    public void deleteBooking(int bookingId) {

        if (bookingId <= 0) {

            System.out.println("Invalid Booking ID.");
            return;

        }

        bookingDAO.deleteBooking(bookingId);

    }



// ================= CANCEL BOOKING =================

    public void cancelBooking(int bookingId, int customerId) {

        boolean cancelled =
                bookingDAO.cancelLatestBooking(customerId, bookingId);

        if (!cancelled) {

            return;

        }

        int roomId =
                bookingDAO.getRoomIdFromBooking(bookingId);

        if (roomId != -1) {

            roomService.updateRoomStatus(
                    roomId,
                    "Available"
            );

        }

        System.out.println("\n================================");
        System.out.println(" BOOKING CANCELLED SUCCESSFULLY ");
        System.out.println("================================");

    }

}