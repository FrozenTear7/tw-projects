package lab1.ex2;

import java.util.concurrent.ThreadLocalRandom;

public class Consumer implements Runnable {
    private Buffer buffer;
    private String name;
    private int ILOSC;

    public Consumer(Buffer buffer, String name, int ILOSC) {
        this.buffer = buffer;
        this.name = name;
        this.ILOSC = ILOSC;
    }

    public void run() {
        for (int i = 0; i < ILOSC; i++) {
            String message = buffer.take();
            System.out.println("Consumer: " + name + ", received: " + message);

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}