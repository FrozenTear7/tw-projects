package lab1.ex2;

public class ex1_2 {
    public static void main(String[] args) throws InterruptedException {
        int ILOSC = 5;

        Buffer buffer = new Buffer();
        Thread consumerThread = new Thread(new Consumer(buffer, "1", ILOSC));
        Thread producerThread = new Thread(new Producer(buffer, "1", ILOSC));
        Thread consumerThread2 = new Thread(new Consumer(buffer, "2", ILOSC));
        Thread producerThread2 = new Thread(new Producer(buffer, "2", ILOSC));

        producerThread.start();
        producerThread2.start();
        consumerThread.start();
        consumerThread2.start();

        producerThread.join();
        producerThread2.join();
        consumerThread.join();
        consumerThread2.join();
    }
}
