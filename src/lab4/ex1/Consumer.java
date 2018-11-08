package lab4.ex1;

import static lab4.ex1.ex4_1.actions;

public class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for(int i = 0; i < actions; i++) {
            buffer.consume();
        }
    }
}
