package lab3.ex2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintingHouse implements Runnable{

    private Lock lock = new ReentrantLock();

    public void run() {

        lock.lock();
    }
}
