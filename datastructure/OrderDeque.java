package datastructure;

public class OrderDeque {

    private String[] orderQueue;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    // Constructor
    public OrderDeque(int capacity) {

        this.capacity = capacity;
        orderQueue = new String[capacity];

        front = 0;
        rear = -1;
        size = 0;
    }

    // Producer : Add Order
    public synchronized void addOrder(String order) {

        while (size == capacity) {

            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        rear = (rear + 1) % capacity;
        orderQueue[rear] = order;
        size++;

        System.out.println("Order Added : " + order);

        notifyAll();
    }

    // Consumer : Remove Order
    public synchronized String removeOrder() {

        while (size == 0) {

            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        String order = orderQueue[front];
        orderQueue[front] = null;

        front = (front + 1) % capacity;
        size--;

        System.out.println("Order Processed : " + order);

        notifyAll();

        return order;
    }

    // Display Orders
    public synchronized void displayOrders() {

        if (size == 0) {
            System.out.println("No Orders Available");
            return;
        }

        System.out.println("----- Order Queue -----");

        int index = front;

        for (int i = 0; i < size; i++) {
            System.out.println(orderQueue[index]);
            index = (index + 1) % capacity;
        }
    }

    // Current Queue Size
    public synchronized int getSize() {
        return size;
    }

    // Check Empty
    public synchronized boolean isEmpty() {
        return size == 0;
    }
}