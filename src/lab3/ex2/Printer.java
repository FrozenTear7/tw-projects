package lab3.ex2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Printer {
    private int myNumber;
    private Lock lock = new ReentrantLock();

    public Printer(int myNumber) {
        this.myNumber = myNumber;
    }

    public void print(int toPrintNumber) {
        lock.lock();

        try {
            System.out.println("Printer " + myNumber + " gon be printing: " + toPrintNumber);

            TimeUnit.SECONDS.sleep((long)(Math.random() * 5 + 2));

            System.out.println("Printer " + myNumber + " finished printing: " + toPrintNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
