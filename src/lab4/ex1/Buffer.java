package lab4.ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static lab4.ex1.ex4_1.actions;
import static lab4.ex1.ex4_1.middlemanSize;

public class Buffer {
    private int size;
    private List<Integer> itemList = new ArrayList<>(size);
    private Lock lock = new ReentrantLock();
    private int writeIndex = 0;
    private int readIndex = 0;
    private Condition myIndexCondition = lock.newCondition();

    public Buffer(int size) {
        this.size = size;

        for (int i = 0; i < size; i++) {
            itemList.add(i, -1);
        }
    }

    public void produce() {
        lock.lock();

        try {
            while(itemList.get(writeIndex) != -1)
                myIndexCondition.await();

            itemList.set(writeIndex, 0);

            writeIndex = ++writeIndex % size;

            myIndexCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void consume() {
        lock.lock();

        try {
            while(itemList.get(readIndex) != middlemanSize)
                myIndexCondition.await();

            System.out.println("Item at: " + readIndex + " = " + itemList.get(readIndex));

            itemList.set(readIndex, -1);

            readIndex = ++readIndex % size;

            myIndexCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void modify(int id) {
        lock.lock();

        try {
            while(itemList.get(readIndex) != id)
                myIndexCondition.await();

            itemList.set(readIndex, id + 1);

            myIndexCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
