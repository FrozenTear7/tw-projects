package lab3.ex1;

public class Consumer implements Runnable {
    private Buffer buffer;
    private int id;

    Consumer(Buffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    public void run() {
        for (int i = 0; i < 5; i++)
            buffer.take(id);
    }
}