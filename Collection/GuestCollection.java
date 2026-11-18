package Collection;

import java.util.ArrayList;
import java.util.Iterator;
import model.customer;

public class GuestCollection {

    private ArrayList<customer> guestCollection;


    // Constructor
    public GuestCollection() {

        guestCollection = new ArrayList<>();
    }


    // Add Guest
    public void addGuest(customer c) {

        guestCollection.add(c);
    }


    // Remove Guest
    public void removeGuest(int customerId) {

        Iterator<customer> iterator = guestCollection.iterator();

        while(iterator.hasNext()) {

            customer c = iterator.next();

            if(c.getcustomerId() == customerId) {

                iterator.remove();
                return;
            }
        }

        System.out.println("Guest Not Found");
    }


    // Search Guest
    public customer searchGuest(int customerId) {

        for(customer c : guestCollection) {

            if(c.getcustomerId() == customerId) {

                return c;
            }
        }

        return null;
    }


    // Display Guests
    public void displayGuests() {

        if(guestCollection.isEmpty()) {

            System.out.println("No Guests Available");
            return;
        }


        System.out.println("----- Guest Collection -----");


        for(customer c : guestCollection) {

            System.out.println(c);
        }
    }


    // Size
    public int getSize() {

        return guestCollection.size();
    }


    // Check Empty
    public boolean isEmpty() {

        return guestCollection.isEmpty();
    }
}