package exception;

public class BookingException extends Exception {


    // Default Constructor
    public BookingException() {

        super();
    }


    // Parameterized Constructor
    public BookingException(String message) {

        super(message);
    }


    // Custom Exception Message
    @Override
    public String getMessage() {

        return super.getMessage();
    }
}