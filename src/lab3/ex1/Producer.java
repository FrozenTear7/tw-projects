package lab3.ex1;

public class Producer implements Runnable {
    private Buffer buffer;

    Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < 5; i++)
            buffer.put(i);
    }
}