package lab3.ex3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pair {
    private Table table;
    private List<Customer> pair = new ArrayList<>(2);
    public int id;
    public int ready = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public boolean readyToEat = false;

    public Pair(Table table, int id) {
        this.table = table;
        this.id = id;
    }

    public void setPair(Customer customer1, Customer customer2) {
        pair.add(0, customer1);
        pair.add(1, customer2);
    }

    public void sit() {
        lock.lock();

        if (ready == 2) {
            ready = 0;
            table.sitPair(this);
        }

        try {
            while (!readyToEat)
                condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void eat() {
        lock.lock();
        readyToEat = true;
        condition.signalAll();
        lock.unlock();
    }

    public void finish() {
        if (ready == 2)
            table.finish(this);
    }
}
