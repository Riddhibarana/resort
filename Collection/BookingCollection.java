package Collection;

import java.util.ArrayList;
import java.util.Iterator;
import model.Booking;

public class BookingCollection {

    private ArrayList<Booking> bookingCollection;


    // Constructor
    public BookingCollection() {

        bookingCollection = new ArrayList<>();
    }


    // Add Booking
    public void addBooking(Booking booking) {

        bookingCollection.add(booking);
    }


    // Remove Booking
    public void removeBooking(int bookingId) {

        Iterator<Booking> iterator = bookingCollection.iterator();

        while(iterator.hasNext()) {

            Booking b = iterator.next();

            if(b.getBookingId() == bookingId) {

                iterator.remove();

                return;
            }
        }

        System.out.println("Booking Not Found");
    }


    // Search Booking
    public Booking searchBooking(int bookingId) {

        for(Booking b : bookingCollection) {

            if(b.getBookingId() == bookingId) {

                return b;
            }
        }

        return null;
    }


    // Display All Bookings
    public void displayBookings() {

        if(bookingCollection.isEmpty()) {

            System.out.println("No Booking Available");
            return;
        }


        System.out.println("----- Booking Collection -----");


        for(Booking b : bookingCollection) {

            System.out.println(b);
        }
    }


    // Collection Size
    public int getSize() {

        return bookingCollection.size();
    }


    // Check Empty
    public boolean isEmpty() {

        return bookingCollection.isEmpty();
    }
}