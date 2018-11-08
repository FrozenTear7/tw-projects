package lab4.ex2;

import static lab4.ex2.ex4_2.*;

public class Producer implements Runnable {
    private Buffer buffer;
    private int id;

    public Producer(Buffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < Math.random() * M; i++) {
            long start = System.nanoTime();
            buffer.produce(id);
            long elapsedTime = System.nanoTime() - start;
            meanTimeProduce += elapsedTime;
            producingActions++;
        }
    }
}
