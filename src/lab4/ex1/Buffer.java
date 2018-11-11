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
    private List<Lock> lockList = new ArrayList<>(size);
    private List<Condition> conditionList = new ArrayList<>(size);
    private List<Integer> indexList = new ArrayList<>(middlemanSize + 2);

    public Buffer(int size) {
        this.size = size;

        for (int i = 0; i < size; i++) {
            itemList.add(i, -1);
            lockList.add(i, new ReentrantLock());
            conditionList.add(i, lockList.get(i).newCondition());
            indexList.add(i, 0);
        }
    }

    public void produce() {
        int lockIndex = indexList.get(0);

        lockList.get(lockIndex).lock();

        try {
            while(itemList.get(indexList.get(0)) != -1)
                conditionList.get(lockIndex).await();

            itemList.set(indexList.get(0), 0);

            indexList.set(0, (indexList.get(0) + 1) % size);

            conditionList.get(lockIndex).signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lockList.get(lockIndex).unlock();
        }
    }

    public void consume() {
        int lockIndex = indexList.get(middlemanSize + 1);

        lockList.get(lockIndex).lock();

        try {
            while(itemList.get(indexList.get(middlemanSize + 1)) != middlemanSize)
                conditionList.get(lockIndex).await();

            System.out.println("Item at: " + indexList.get(middlemanSize + 1) + " = " + itemList.get(indexList.get(middlemanSize + 1)));

            itemList.set(indexList.get(middlemanSize + 1), -1);

            indexList.set(middlemanSize + 1, (indexList.get(middlemanSize + 1) + 1) % size);

            conditionList.get(lockIndex).signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lockList.get(lockIndex).unlock();
        }
    }

    public void modify(int id) {
        int lockIndex = indexList.get(id + 1);

        lockList.get(lockIndex).lock();

        try {
            while(itemList.get(indexList.get(id + 1)) != id)
                conditionList.get(lockIndex).await();

            itemList.set(indexList.get(id + 1), id + 1);

            indexList.set(id + 1, (indexList.get(id + 1) + 1) % size);

            conditionList.get(lockIndex).signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lockList.get(lockIndex).unlock();
        }
    }
}
