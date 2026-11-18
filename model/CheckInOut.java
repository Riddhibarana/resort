package model;

public class CheckInOut {


    private int checkId;
    private int bookingId;
    private String checkInDate;
    private String checkOutDate;
    private String status;
    private int customerId;


    // Default Constructor
    public CheckInOut() {

        this.status = "Checked-In";

    }



    public int getCheckId() {

        return checkId;

    }


    public void setCheckId(int checkId) {

        this.checkId = checkId;

    }



    public int getBookingId() {

        return bookingId;

    }


    public void setBookingId(int bookingId) {

        this.bookingId = bookingId;

    }



    public String getCheckInDate() {

        return checkInDate;

    }


    public void setCheckInDate(String checkInDate) {

        this.checkInDate = checkInDate;

    }



    public String getCheckOutDate() {

        return checkOutDate;

    }


    public void setCheckOutDate(String checkOutDate) {

        this.checkOutDate = checkOutDate;

    }



    public String getStatus() {

        return status;

    }


    public void setStatus(String status) {


        if(status == null || status.trim().isEmpty()) {

            this.status = "Checked-In";

        }
        else {

            this.status = status;

        }

    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}