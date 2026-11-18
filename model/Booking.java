package model;

public class Booking {

    private int bookingId;
    private int customerId;
    private int roomId;
    private int numberOfGuests;
    private double totalAmount;
    private String bookingStatus;
private String CheckIndate;
private String CheckOutdate;

    // Default Constructor
    public Booking() {

        this.bookingStatus = "Confirmed";

    }

    // Parameterized Constructor
    public Booking(int bookingId,
                   int customerId,
                   int roomId,String CheckIndate,String CheckOutdate,
                   int numberOfGuests,
                   double totalAmount,
                   String bookingStatus) {


        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomId = roomId;
        this.CheckIndate=CheckIndate;
        this.CheckOutdate=CheckOutdate;
        this.numberOfGuests = numberOfGuests;
        this.totalAmount = totalAmount;


        if(bookingStatus == null || bookingStatus.trim().isEmpty()) {

            this.bookingStatus = "Confirmed";

        }
        else {

            this.bookingStatus = bookingStatus;

        }

    }



    public int getBookingId() {

        return bookingId;

    }


    public void setBookingId(int bookingId) {

        this.bookingId = bookingId;

    }



    // Customer ID
    public int getcustomerId() {

        return customerId;

    }


    public int getCustomerId() {

        return customerId;

    }



    public void setcustomerId(int customerId) {

        this.customerId = customerId;

    }


    public void setCustomerId(int customerId) {

        this.customerId = customerId;

    }



    public int getRoomId() {

        return roomId;

    }


    public void setRoomId(int roomId) {

        this.roomId = roomId;

    }






    public int getNumberOfGuests() {

        return numberOfGuests;

    }


    public void setNumberOfGuests(int numberOfGuests) {

        this.numberOfGuests = numberOfGuests;

    }



    public double getTotalAmount() {

        return totalAmount;

    }


    public void setTotalAmount(double totalAmount) {

        this.totalAmount = totalAmount;

    }



    public String getBookingStatus() {

        return bookingStatus;

    }


    public void setBookingStatus(String bookingStatus) {


        if(bookingStatus == null || bookingStatus.trim().isEmpty()) {

            this.bookingStatus = "Confirmed";

        }
        else {

            this.bookingStatus = bookingStatus;

        }

    }



    public void displayBooking() {


        System.out.println("---------------------------");
        System.out.println("Booking ID       : " + bookingId);
        System.out.println("Customer ID      : " + customerId);
        System.out.println("Room ID          : " + roomId);
        System.out.println("CheckIndate      :" +CheckIndate);
        System.out.println("CheckOutdate      :" +CheckOutdate);
        System.out.println("No. Of Guests    : " + numberOfGuests);
        System.out.println("Total Amount     : " + totalAmount);
        System.out.println("Booking Status   : " + bookingStatus);
        System.out.println("---------------------------");

    }

    public String getCheckInDate() {
        return CheckIndate;
    }

    public void setCheckInDate(String checkInDate) {
        this.CheckIndate = checkInDate;
    }

    public String getCheckOutDate() {
        return CheckOutdate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.CheckOutdate = checkOutDate;
    }



}