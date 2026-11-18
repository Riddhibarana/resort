package datastructure;

import model.customer;

public class CustomerArrayList {

    private customer[] customerList;
    private int size;

    // Constructor
    public CustomerArrayList() {
        customerList = new customer[100];
        size = 0;
    }

    // Insert Customer
    public void addGuest(customer c) {

        if (size == customerList.length) {
            System.out.println("Customer List is Full");
            return;
        }

        customerList[size] = c;
        size++;

    }

    // Delete Customer
    public void removeGuest(int customerId) {

        for (int i = 0; i < size; i++) {

            if (customerList[i].getcustomerId() == customerId) {

                for (int j = i; j < size - 1; j++) {
                    customerList[j] = customerList[j + 1];
                }

                customerList[size - 1] = null;
                size--;

                System.out.println("Customer Removed Successfully");
                return;
            }
        }

    }

    // Search Customer
    public customer searchGuest(int customerId) {

        for (int i = 0; i < size; i++) {

            if (customerList[i].getcustomerId() == customerId) {
                return customerList[i];
            }
        }

        return null;
    }

    // Display All Customers
    public void displayGuests() {

        if (size == 0) {
            System.out.println("No Customers Available");
            return;
        }

        System.out.println("----- Customer List -----");

        for (int i = 0; i < size; i++) {
            System.out.println(customerList[i]);
        }
    }

    // Get Size
    public int getSize() {
        return size;
    }

    // Check Empty
    public boolean isEmpty() {
        return size == 0;
    }
}