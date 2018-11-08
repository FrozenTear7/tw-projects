package lab4.ex1;

import static lab4.ex1.ex4_1.actions;

public class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for(int i = 0; i < actions; i++) {
            buffer.produce(0);
        }
    }
}
