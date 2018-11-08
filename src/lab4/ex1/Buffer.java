package lab4.ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static lab4.ex1.ex4_1.middlemanSize;

public class Buffer {
    private int size;
    private List<Integer> itemList = new ArrayList<>(size);
    private List<Lock> lockList = new ArrayList<>(size);
    private Lock lock = new ReentrantLock();
    private int produced = 0;
    private int consumed = 0;

    public Buffer(int size) {
        this.size = size;

        for (int i = 0; i < size; i++) {
            itemList.add(i, -1);
        }

        for (int i = 0; i < size; i++) {
            lockList.add(i, new ReentrantLock());
        }
    }

    public void produce() {
        while (produced < 10) {
            for (int i = 0; i < size; i++) {
                if (itemList.get(i) == -1) {
                    lockList.get(i).lock();
                    lock.lock();

                    itemList.set(i, 0);
                    produced++;

                    lock.unlock();
                    lockList.get(i).unlock();
                }
            }
        }
    }

    public void consume() {
        while (consumed < 10) {
            for (int i = 0; i < size; i++) {
                if (itemList.get(i) == middlemanSize) {
                    lockList.get(i).lock();
                    lock.lock();

                    System.out.println("Item at: " + i + " = " + itemList.get(i));
                    itemList.set(i, -1);
                    consumed++;

                    lock.unlock();
                    lockList.get(i).unlock();
                }
            }
        }
    }

    public void modify(int id) {
        while (consumed < 10) {
            for (int i = 0; i < size; i++) {
                if (itemList.get(i) == id) {
                    lockList.get(i).lock();

                    itemList.set(i, itemList.get(i) + 1);

                    lockList.get(i).unlock();
                }
            }
        }
    }
}
