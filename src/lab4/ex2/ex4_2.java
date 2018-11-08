package lab4.ex2;

import java.util.ArrayList;
import java.util.List;

public class ex4_2 {
    static int M = 100;
    static int bufferSize = 2 * M;
    static int producers = 10;
    static int consumers = 10;
    static long meanTimeProduce = 0;
    static long meanTimeConsume = 0;
    static long producingActions = 0;
    static long consumingActions = 0;

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer(bufferSize);
        List<Producer> producerList = new ArrayList<>(producers);
        List<Consumer> consumerList = new ArrayList<>(consumers);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < producers; i++) {
            producerList.add(i, new Producer(buffer, i));
            threads.add(new Thread(producerList.get(i)));
        }

        for (int i = 0; i < consumers; i++) {
            consumerList.add(i, new Consumer(buffer, i));
            threads.add(new Thread(consumerList.get(i)));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Mean team producing: " + meanTimeProduce / producingActions);
        System.out.println("Mean team consuming: " + meanTimeConsume / consumingActions);
    }
}
