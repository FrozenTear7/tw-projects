package lab3.ex3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Table {
    private Lock lock = new ReentrantLock();
    private Condition tableTakenCondition = lock.newCondition();
    private boolean tableTaken = false;

    public void sitPair(Pair pair) {
        lock.lock();

        try {
            while (tableTaken) {
                tableTakenCondition.await();
            }

            System.out.println("Table taken by pair " + pair.id);
            tableTaken = true;
            pair.eat();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void finish(Pair pair) {
        System.out.println("Table freed by by pair " + pair.id);
        tableTakenCondition.signal();
        tableTaken = false;
        lock.unlock();
    }
}
