package lab2.ex1;

public class Sem {
    private boolean state;

    Sem(boolean state) {
        this.state = state;
    }

    public synchronized void acquire() {
        try {
            while (!state)
                wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        state = false;

        notifyAll();
    }

    public synchronized void release() {
        state = true;

        notifyAll();
    }
}
