package threading;

public class BookingConsumer extends Thread {

    BookingBuffer buffer;

    public BookingConsumer(BookingBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        try {

            for (int i = 1; i <= 10; i++) {

                buffer.consume();

                Thread.sleep(1500);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}