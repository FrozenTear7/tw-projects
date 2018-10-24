package lab3.ex2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Printer {
    private Lock lock = new ReentrantLock();

    public void print(int myNumber) {
        lock.lock();

        try {
            System.out.println("Printer " + myNumber + " gon be printing");

            TimeUnit.SECONDS.sleep((long)(Math.random() * 5 + 2));

            System.out.println("Printer " + myNumber + " finished printing");;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
