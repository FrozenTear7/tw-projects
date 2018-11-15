package lab4.ex3;

import java.util.ArrayList;
import java.util.List;

public class ex4_3 {
    static int M = 10;
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
        List<Thread> producerThreads = new ArrayList<>();
        List<Thread> consumerThreads = new ArrayList<>();

        for (int i = 0; i < producers; i++) {
            producerList.add(i, new Producer(buffer, i));
            producerThreads.add(new Thread(producerList.get(i)));
        }

        for (int i = 0; i < consumers; i++) {
            consumerList.add(i, new Consumer(buffer, i));
            consumerThreads.add(new Thread(consumerList.get(i)));
        }

        for (Thread thread : producerThreads) {
            thread.start();
        }

        for (Thread thread : consumerThreads) {
            thread.start();
        }

        for (Thread thread : producerThreads) {
            thread.join();
        }

        for (Thread thread : consumerThreads) {
            thread.join();
        }

        System.out.println("Producers: " + producers);
        System.out.println("Consumers: " + consumers);
        System.out.println("M: " + M);
        System.out.println("Mean team producing: " + meanTimeProduce / producingActions);
        System.out.println("Mean team consuming: " + meanTimeConsume / consumingActions);
    }
}
