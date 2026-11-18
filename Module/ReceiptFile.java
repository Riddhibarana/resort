package Module;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.Payment;

public class ReceiptFile {

    public void generateReceipt(Payment payment) {

        try {

            // Unique file name
            String timeStamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            File folder = new File("C:\\Users\\Riddhi\\OneDrive\\Desktop\\Recipt");
            System.out.println("Folder Path : " + folder.getAbsolutePath());
            System.out.println("Folder Exists : " + folder.exists());
            System.out.println("Folder Created : " + folder.mkdirs());
            if (!folder.exists()) {

                folder.mkdirs();

            }

            File file = new File(folder,
                    "Receipt_" + payment.getCustomerId() + "_" + timeStamp + ".txt");
            FileWriter writer = new FileWriter(file);

            writer.write("==========================================\n");
            writer.write("         RESORT PAYMENT RECEIPT\n");
            writer.write("==========================================\n");
            writer.write("Customer ID         : " + payment.getCustomerId() + "\n");
            writer.write("Customer Name       : " + payment.getCustomerName() + "\n");
            writer.write("Booking ID          : " + payment.getBookingId() + "\n");
            writer.write("------------------------------------------\n");
            writer.write("Room Charges        : ₹" + payment.getRoomCharges() + "\n");
            writer.write("Restaurant Charges  : ₹" + payment.getRestaurantCharges() + "\n");
            writer.write("Game Charges        : ₹" + payment.getGameCharges() + "\n");
            writer.write("------------------------------------------\n");
            writer.write("Sub Total           : ₹" + payment.getSubTotal() + "\n");
            writer.write("GST (18%)           : ₹" + payment.getGst() + "\n");
            writer.write("------------------------------------------\n");
            writer.write("Grand Total         : ₹" + payment.getTotalAmount() + "\n");
            writer.write("Payment Method      : " + payment.getPaymentMethod() + "\n");
            writer.write("Payment Date        : " + payment.getPaymentDate() + "\n");
            writer.write("Payment Status      : " + payment.getPaymentStatus() + "\n");
            writer.write("==========================================\n");
            writer.write("        THANK YOU FOR VISITING\n");
            writer.write("==========================================\n");

            writer.flush();
            writer.close();

            System.out.println("\n==========================================");
            System.out.println("Receipt Generated Successfully.");
            System.out.println("Saved At : " + file.getAbsolutePath());
            System.out.println("==========================================");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}