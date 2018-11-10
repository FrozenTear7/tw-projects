package lab4.ex1;

import static lab4.ex1.ex4_1.actions;

public class Middleman implements Runnable{
    private Buffer buffer;
    private int id;

    public Middleman(Buffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < actions; i++)
            buffer.modify(id);
    }
}
