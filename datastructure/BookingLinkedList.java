package datastructure;

import model.Booking;

public class BookingLinkedList {

    // Node Class
    class Node {
        Booking data;
        Node next;

        Node(Booking data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;

    // Constructor
    public BookingLinkedList() {
        head = null;
    }

    // Insert Booking at Last
    public void addBooking(Booking booking) {

        Node newNode = new Node(booking);

        if (head == null) {
            head = newNode;
        } else {

            Node temp = head;

            while (temp.next != null) {
                temp = temp.next;
            }

            temp.next = newNode;
        }

        System.out.println("Booking Added Successfully");
    }

    // Insert at First Position
    public void addFirstBooking(Booking booking) {

        Node newNode = new Node(booking);
        newNode.next = head;
        head = newNode;

        System.out.println("Booking Added at First Position");
    }

    // Insert at Last Position
    public void addLastBooking(Booking booking) {
        addBooking(booking);
    }

    // Delete First Booking
    public Booking removeFirstBooking() {

        if (head == null) {
            System.out.println("No Booking Available");
            return null;
        }

        Booking booking = head.data;
        head = head.next;

        return booking;
    }

    // Delete Last Booking
    public Booking removeLastBooking() {

        if (head == null) {
            System.out.println("No Booking Available");
            return null;
        }

        if (head.next == null) {
            Booking booking = head.data;
            head = null;
            return booking;
        }

        Node temp = head;

        while (temp.next.next != null) {
            temp = temp.next;
        }

        Booking booking = temp.next.data;
        temp.next = null;

        return booking;
    }

    // Search Booking
    public Booking searchBooking(int bookingId) {

        Node temp = head;

        while (temp != null) {

            if (temp.data.getBookingId() == bookingId) {
                return temp.data;
            }

            temp = temp.next;
        }

        return null;
    }
    // Delete Booking By Booking ID
    public boolean removeBooking(int bookingId) {

        if (head == null) {
            return false;
        }

        // First Node
        if (head.data.getBookingId() == bookingId) {
            head = head.next;
            return true;
        }

        Node current = head;

        while (current.next != null) {

            if (current.next.data.getBookingId() == bookingId) {

                current.next = current.next.next;

                return true;

            }

            current = current.next;
        }

        return false;
    }

    // Display All Bookings
    public void displayBookings() {

        if (head == null) {
            System.out.println("No Bookings Found");
            return;
        }

        System.out.println("----- Booking List -----");

        Node temp = head;

        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }

    // Size
    public int getSize() {

        int count = 0;
        Node temp = head;

        while (temp != null) {
            count++;
            temp = temp.next;
        }

        return count;
    }
    public int getLastBookingId() {

        if(head == null) {

            return -1;
        }


        Node temp = head;


        while(temp.next != null) {

            temp = temp.next;
        }


        return temp.data.getBookingId();

    }

    // Check Empty
    public boolean isEmpty() {
        return head == null;
    }
}