package lab3.ex1;

public class ex3_1 {
    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer();

        Thread consumerThread = new Thread(new Consumer(buffer));
        Thread producerThread = new Thread(new Producer(buffer));

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();
    }
}
