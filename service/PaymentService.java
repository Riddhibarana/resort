package service;

import database.PaymentDAO;
import model.Payment;
import Module.ReceiptFile;
import java.util.Scanner;

public class PaymentService {

    private PaymentDAO dao;


    // Constructor
    public PaymentService() {
        dao = new PaymentDAO();
    }

    // ================= MAKE PAYMENT =================

    public void makePayment(int customerId, int choice) {

        String paymentMethod = "";

        switch (choice) {

            case 1:
                paymentMethod = "Cash";
                break;

            case 2:
                paymentMethod = "Card";
                break;

            case 3:
                paymentMethod = "Online";
                break;

            default:
                System.out.println("Invalid Payment Method.");
                return;
        }

        Payment payment = dao.generatePayment(customerId, paymentMethod);

        if (payment == null) {
            return;
        }

        // Show Receipt Before Payment
        dao.displayReceipt(payment);

        Scanner sc = new Scanner(System.in);

        System.out.print("\nDo You Want To Pay? (Y/N) : ");
        String ans = sc.nextLine().trim();

        if (!ans.equalsIgnoreCase("Y")) {

            System.out.println("Payment Cancelled.");
            return;

        }

        System.out.println("\nPress Enter To Pay...");
        sc.nextLine();

        dao.savePayment(payment);

        System.out.println("\n==============================");
        System.out.println(" Payment Successful ");
        System.out.println("==============================");
        System.out.println("Payment Method : " + payment.getPaymentMethod());
        System.out.println("Payment Date   : " + payment.getPaymentDate());
        System.out.println("Payment Status : " + payment.getPaymentStatus());
        System.out.println("==========================================");
        System.out.println(" THANK YOU FOR VISITING OUR RESORT ");
        System.out.println("==========================================");

        // ===== Receipt Option =====

        System.out.print("\nDo You Want To Print Receipt? (Y/N) : ");
        String receiptChoice = sc.nextLine();

        if (receiptChoice.equalsIgnoreCase("Y")) {

            ReceiptFile receipt = new ReceiptFile();
            receipt.generateReceipt(payment);

        } else {

            System.out.println("Receipt Not Generated.");

        }
    }

    // ================= CHECK CUSTOMER =================

    public boolean checkCustomer(String customerName) {

        if (customerName == null || customerName.trim().isEmpty()) {
            return false;
        }

        return dao.getCustomerId(customerName) != -1;
    }

    // ================= PAYMENT HISTORY =================

    public void displayPayments() {
        dao.displayPayments();
    }

    // ================= PAYMENT DONE =================

    public boolean isPaymentDone(int customerId) {
        return dao.isPaymentDone(customerId);
    }

    // ================= PAYMENT STATUS =================

    public void checkPaymentStatus(int customerId) {

        if (dao.isPaymentDone(customerId)) {

            System.out.println("Payment Already Completed.");

        } else {

            System.out.println("Payment Pending.");

        }
    }

    // ================= MY TRANSACTION =================

    public void showMyTransaction(int customerId) {
        dao.showMyTransaction(customerId);
    }
    public boolean hasPendingPayment(int customerId) {

        return dao.hasPendingPayment(customerId);

    }
}