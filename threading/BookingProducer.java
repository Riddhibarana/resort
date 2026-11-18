package threading;

public class BookingProducer extends Thread {

    BookingBuffer buffer;

    public BookingProducer(BookingBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        try {

            for (int i = 1; i <= 10; i++) {

                buffer.produce("Booking-" + i);

                Thread.sleep(1000);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}