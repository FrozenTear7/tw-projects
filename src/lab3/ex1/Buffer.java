package lab3.ex1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private int item;
    private boolean isItem = false;
    private Lock lock = new ReentrantLock();
    private Condition conditionFull  = lock.newCondition();
    private Condition conditionEmpty  = lock.newCondition();

    void take(int id) {
        lock.lock();

        try {
            while(!isItem)
                conditionEmpty.await();

            isItem = false;

            System.out.println("Consumer " + id + " consumed: " + item);

            conditionFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void put(int item, int id) {
        lock.lock();

        try {
            while(isItem)
                conditionFull.await();

            this.item = item;

            isItem = true;

            System.out.println("Producer " + id + " produced: " + item);

            conditionEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
