package lab3.ex2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Printer {
    private int myNumber;

    public Printer(int myNumber) {
        this.myNumber = myNumber;
    }

    public void print() {
        try {
            TimeUnit.SECONDS.sleep((long) (Math.random() * 2 + 2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
