package service;
import database.CustomerDAO;
import database.CustomerDAO;
import model.customer;
import datastructure.CustomerArrayList;

public class CustomerService {

    private CustomerDAO customerDAO;
    private CustomerArrayList customerDS = new CustomerArrayList();

    public CustomerService()
    {
        customerDAO = new CustomerDAO();
    }




    // Add Customer
    public void addCustomer(customer c) {

        if(c.getName() == null || c.getName().equals("")) {

            System.out.println("Name cannot be empty");
            return;
        }

        try {

            customerDAO.addCustomer(c);


        }
        catch(Exception e) {

            System.out.println("Customer cannot be added. Please try again.");

        }
        if(c.getPassword() == null || c.getPassword().trim().isEmpty()) {
            System.out.println("Password cannot be empty.");
            return;
        }

        if(c.getPassword().contains(" ")) {
            System.out.println("Password cannot contain spaces.");
            return;
        }

        if(c.getPassword().length() < 6 || c.getPassword().length() > 15) {
            System.out.println("Password must be between 6 and 15 characters.");
            return;
        }

    }


    // Display All Customers
    public void displayCustomers() {

        customerDAO.displayCustomer();

    }


    // Search Customer
    public void searchCustomer(int customerId) {

        if(customerId <= 0) {

            System.out.println("Invalid Customer ID");
            return;
        }

        customerDAO.searchCustomer(customerId);

        customer c = customerDS.searchGuest(customerId);

        if(c != null) {
            System.out.println("Customer Found in Array");
            System.out.println(c);
        }

    }



    public int customerLogin(String name, String password) {

        if(name == null || name.trim().isEmpty()) {

            System.out.println("Customer Name cannot be empty.");
            return -1;

        }


        if(password == null || password.trim().isEmpty()) {

            System.out.println("Password cannot be empty.");
            return -1;

        }


        if(password.contains(" ")) {

            System.out.println("Password cannot contain spaces.");
            return -1;

        }


        if(password.length() < 6 || password.length() > 15) {

            System.out.println("Password must be between 6 and 15 characters.");
            return -1;

        }


        return customerDAO.customerLogin(name, password);

    }
    public boolean checkCustomerExists(String name) {

        return customerDAO.checkCustomerExists(name);

    }
    // ================= SEARCH CUSTOMER BY NAME =================

    public void searchCustomerByName(String name) {

        customerDAO.searchCustomerByName(name);

    }
    public int getFirstLoginStatus(int customerId) {

        return customerDAO.getFirstLoginStatus(customerId);

    }

    public void updatePassword(int customerId, String password) {

        customerDAO.updatePassword(customerId, password);

    }
    public String getCustomerPassword(int customerId) {

        return customerDAO.getCustomerPassword(customerId);

    }
    public String getPasswordByCustomerId(int customerId) {

        return customerDAO.getPasswordByCustomerId(customerId);

    }
    public int getCustomerIdByName(String name) {

        return customerDAO.getCustomerIdByName(name);

    }
    public void deleteCustomer(int id) {

        boolean deleted = customerDAO.deleteCustomer(id);

        if (deleted) {

            System.out.println("Customer Deleted Successfully.");

        }

    }
    public void updateCustomer(int id, int choice, String value) {

        boolean updated = customerDAO.updateCustomer(id, choice, value);

        if (updated) {

            System.out.println("Customer Updated Successfully.");

        }

    }
    public boolean checkPhoneExists(String phone) {

        return customerDAO.checkPhoneExists(phone);

    }

    public boolean checkPhoneExistsExcept(int customerId, String phone) {

        return customerDAO.checkPhoneExistsExcept(customerId, phone);

    }
    public boolean verifyCustomerPhone(int customerId, String phone) {

        return customerDAO.verifyCustomerPhone(customerId, phone);

    }


}