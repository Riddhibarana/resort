package threading;

public class PaymentThread extends Thread {

    private int paymentId;


    public PaymentThread(int paymentId) {

        this.paymentId = paymentId;

    }


    @Override
    public void run() {


        System.out.println("Payment processing started...");


        try {

            Thread.sleep(2000);

        }
        catch(Exception e) {

            e.printStackTrace();

        }


        System.out.println("Payment ID " + paymentId + " completed successfully");


    }

}