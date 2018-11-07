package lab3.ex1;

public class ex3_1 {
    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer();

        Thread consumerThread = new Thread(new Consumer(buffer, 1));
        Thread producerThread = new Thread(new Producer(buffer, 1));
        Thread consumerThread2 = new Thread(new Consumer(buffer, 2));
        Thread producerThread2 = new Thread(new Producer(buffer, 2));

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
