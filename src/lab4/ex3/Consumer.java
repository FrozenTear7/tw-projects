package lab4.ex3;

import static lab4.ex3.ex4_3.*;

public class Consumer implements Runnable {
    private Buffer buffer;
    private int id;

    public Consumer(Buffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public void run() {
        long start = System.nanoTime();
        buffer.consume(id);
        long elapsedTime = System.nanoTime() - start;
        meanTimeConsume += elapsedTime;
        consumingActions++;
    }
}
