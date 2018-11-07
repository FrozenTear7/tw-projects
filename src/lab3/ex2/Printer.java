package lab3.ex2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Printer {
    private int myNumber;
    private Lock lock = new ReentrantLock();
    private Condition freeCondition = lock.newCondition();
    private boolean free = true;

    public Printer(int myNumber) {
        this.myNumber = myNumber;
    }

    public void print(int toPrintNumber) {
        lock.lock();

        try {
            while(!free) {
                freeCondition.await();
            }

            free = false;

            System.out.println("Printer " + myNumber + " gon be printing: " + toPrintNumber);

            TimeUnit.SECONDS.sleep((long)(Math.random() * 5 + 2));

            System.out.println("Printer " + myNumber + " finished printing: " + toPrintNumber);

            free = true;
            freeCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
