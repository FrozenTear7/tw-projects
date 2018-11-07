package lab3.ex1;

public class Producer implements Runnable {
    private Buffer buffer;
    private int id;

    Producer(Buffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    public void run() {
        for (int i = 0; i < 5; i++)
            buffer.put(i, id);
    }
}