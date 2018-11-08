package lab4.ex2;

import static lab4.ex2.ex4_2.*;

public class Consumer implements Runnable {
    private Buffer buffer;
    private int id;

    public Consumer(Buffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < Math.random() * M; i++) {
            long start = System.nanoTime();
            buffer.consume(id);
            long elapsedTime = System.nanoTime() - start;
            meanTimeConsume += elapsedTime;
            consumingActions++;
        }
    }
}
