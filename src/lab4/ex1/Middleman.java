package lab4.ex1;

public class Middleman implements Runnable{
    private Buffer buffer;
    private int id;

    public Middleman(Buffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public void run() {
        buffer.modify(id);
    }
}
