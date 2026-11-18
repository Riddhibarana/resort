package threading;

import java.util.LinkedList;
import java.util.Queue;

public class BookingBuffer {

    private Queue<String> queue = new LinkedList<>();
    private final int SIZE = 5;

    public synchronized void produce(String booking) throws InterruptedException {

        while (queue.size() == SIZE) {
            wait();
        }

        queue.add(booking);
        System.out.println("Booking Added : " + booking);

        notify();
    }

    public synchronized void consume() throws InterruptedException {

        while (queue.isEmpty()) {
            wait();
        }

        String booking = queue.remove();

        System.out.println("Booking Processed : " + booking);

        notify();
    }
}