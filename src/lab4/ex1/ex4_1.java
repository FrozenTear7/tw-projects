package lab4.ex1;

import java.util.ArrayList;
import java.util.List;

public class ex4_1 {
    static int actions = 3;
    static int bufferSize = 1;
    static int middlemanSize = 1;

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer(bufferSize);
        Producer producer = new Producer(buffer);
        List<Middleman> middlemanList = new ArrayList<>(middlemanSize);
        Consumer consumer = new Consumer(buffer);
        List<Thread> threads = new ArrayList<>(middlemanSize + 2);

        for (int i = 0; i < middlemanSize; i++) {
            middlemanList.add(i, new Middleman(buffer, i));
        }

        threads.add(0, new Thread(producer));

        for (int i = 1; i < middlemanSize + 1; i++) {
            threads.add(i, new Thread(middlemanList.get(i - 1)));
        }

        threads.add(middlemanSize + 1, new Thread(consumer));

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }
}
