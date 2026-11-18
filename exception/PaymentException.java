package exception;

public class PaymentException extends Exception {


    // Default Constructor
    public PaymentException() {

        super();
    }


    // Parameterized Constructor
    public PaymentException(String message) {

        super(message);
    }


    // Custom Exception Message
    @Override
    public String getMessage() {

        return super.getMessage();
    }
}