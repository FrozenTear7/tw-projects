package lab4.ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static lab4.ex1.ex4_1.middlemanSize;

public class Buffer {
    private int size;
    private List<String> itemList = new ArrayList<>(size);
    private int itemCount = 0;
    private int writeIndex = 0;
    private int readIndex = 0;
    private Lock lock = new ReentrantLock();
    private Condition bufferEmptyCondition = lock.newCondition();
    private Condition bufferFullCondition = lock.newCondition();
    private Condition myTurnCondition = lock.newCondition();
    private int currentTurn = 0;

    public Buffer(int size) {
        this.size = size;
    }

    public void produce(String item) {
        lock.lock();

        try {
            while(itemCount == size) {
                bufferFullCondition.await();
            }

            itemList.add(writeIndex, item);

            writeIndex++;

            if(writeIndex == size)
                writeIndex = 0;

            itemCount++;

            bufferEmptyCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public String consume() {
        String item = "PROBLEM XD";

        lock.lock();

        try {
            while(currentTurn != middlemanSize) {
                myTurnCondition.await();
            }

            item = itemList.remove(readIndex);

            readIndex++;

            if(readIndex == size)
                readIndex = 0;

            itemCount--;
            bufferFullCondition.signal();

            currentTurn = 0;
            myTurnCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return item;
    }

    public void modify(int id) {
        lock.lock();

        try {
            while(itemCount == 0) {
                bufferEmptyCondition.await();
            }

            while(currentTurn != id) {
                myTurnCondition.await();
            }

            itemList.set(readIndex, itemList.get(readIndex) + " " + id);

            currentTurn++;
            myTurnCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
