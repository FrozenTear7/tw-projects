package lab1.ex2;

import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {
    private Buffer buffer;
    private String name;
    private int ILOSC;

    public Producer(Buffer buffer, String name, int ILOSC) {
        this.buffer = buffer;
        this.name = name;
        this.ILOSC = ILOSC;
    }

    public void run() {
        for (int i = 0; i < ILOSC; i++) {
            buffer.put("Producer: " + name + ", message: " + i);

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}