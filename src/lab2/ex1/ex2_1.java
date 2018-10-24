package lab2.ex1;

public class ex2_1 {
    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer();

        Thread consumerThread = new Thread(new Consumer(buffer));
        Thread producerThread = new Thread(new Producer(buffer));
        Thread consumerThread2 = new Thread(new Consumer(buffer));
        Thread producerThread2 = new Thread(new Producer(buffer));

        producerThread.start();
        consumerThread.start();
        producerThread2.start();
        consumerThread2.start();

        producerThread.join();
        consumerThread.join();
        producerThread2.join();
        consumerThread2.join();
    }
}
