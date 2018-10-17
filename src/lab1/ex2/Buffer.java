package lab1.ex2;

import java.util.ArrayList;

public class Buffer {
    private String buffer;
    private boolean flag = true;

    public synchronized String take() {
        while (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        flag = true;
        notifyAll();
        return buffer;
    }

    public synchronized void put(String msg) {
        while (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        flag = false;
        buffer = msg;
        notifyAll();
    }
}
