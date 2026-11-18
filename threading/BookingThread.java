package threading;

public class BookingThread extends Thread {

    private int bookingId;


    public BookingThread(int bookingId) {

        this.bookingId = bookingId;

    }


    @Override
    public void run() {

        System.out.println("Booking process started...");

        try {

            Thread.sleep(2000);

        }
        catch(Exception e) {

            e.printStackTrace();

        }


        System.out.println("Booking ID " + bookingId + " completed successfully");

    }

}