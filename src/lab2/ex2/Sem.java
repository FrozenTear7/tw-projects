package lab2.ex2;

public class Sem {
    private int state;

    Sem(int state) {
        this.state = state;
    }

    public synchronized void acquire() {
        try {
            while (state == 0)
                wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        state--;

        notifyAll();
    }

    public synchronized void release() {
        state++;

        notifyAll();
    }
}
