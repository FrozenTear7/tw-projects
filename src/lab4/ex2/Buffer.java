package lab4.ex2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static lab4.ex2.ex4_2.bufferSize;

public class Buffer {
    private int size;
    private List<Integer> itemList = new ArrayList<>(size);
    private Lock lock = new ReentrantLock();
    private Condition bufferEmptyCondition = lock.newCondition();
    private Condition bufferFullCondition = lock.newCondition();
    private int writeIndex = 0;
    private int readIndex = 0;
    private int itemCount = 0;

    public Buffer(int size) {
        this.size = size;

        for (int i = 0; i < size; i++) {
            itemList.add(i, -1);
        }
    }

    public void produce(int id) {
        lock.lock();

        try {
            while(itemCount == bufferSize) {
                bufferFullCondition.await();
            }

            itemList.set(writeIndex, id);

            itemCount++;

            writeIndex++;

            if(writeIndex == bufferSize)
                writeIndex = 0;

            bufferEmptyCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void consume(int id) {
        lock.lock();

        try {
            while(itemCount == 0) {
                bufferEmptyCondition.await();
            }

            //System.out.println("Consumer id: " + id + " - item: "+  itemList.get(readIndex));

            itemList.set(readIndex, -1);

            itemCount--;

            readIndex++;

            if(readIndex == bufferSize)
                readIndex = 0;

            bufferFullCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
