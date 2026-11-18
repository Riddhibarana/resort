package exception;

public class RoomNotAvailableException extends Exception {

    // Default Constructor
    public RoomNotAvailableException() {
        super();
    }


    // Parameterized Constructor
    public RoomNotAvailableException(String message) {
        super(message);
    }


    // Display Exception Message
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}