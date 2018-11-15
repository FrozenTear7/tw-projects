package lab4.ex3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static lab4.ex3.ex4_3.M;
import static lab4.ex3.ex4_3.bufferSize;

public class Buffer {
    private int size;
    private List<Integer> itemList = new ArrayList<>(size);
    private Lock lock = new ReentrantLock();
    private Condition bufferEmptyCondition = lock.newCondition();
    private Condition bufferFullCondition = lock.newCondition();
    private int writeIndex = 0;
    private int readIndex = 0;
    private int itemCount = 0;
    private List<Integer> producerWaitingList = new ArrayList<>();
    private List<Integer> consumerWaitingList = new ArrayList<>();
    private int producerIndex = 0;
    private int consumerIndex = 0;

    public Buffer(int size) {
        this.size = size;

        for (int i = 0; i < size; i++) {
            itemList.add(i, -1);
        }
    }

    public void produce(int id) {
        lock.lock();

        try {
            for (int i = 0; i < Math.random() * M; i++) {
                int myTicket = ++producerIndex;
                producerWaitingList.add(myTicket);

                while (itemCount == bufferSize || myTicket != producerWaitingList.get(producerWaitingList.indexOf(Collections.min(producerWaitingList)))) {
                    bufferFullCondition.await();
                }

                itemList.set(writeIndex, id);

                itemCount++;

                writeIndex++;

                if (writeIndex == bufferSize)
                    writeIndex = 0;

                producerWaitingList.remove(producerWaitingList.indexOf(Collections.min(producerWaitingList)));

                bufferEmptyCondition.signalAll();
                bufferFullCondition.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void consume(int id) {
        lock.lock();

        try {
            for (int i = 0; i < Math.random() * M; i++) {
                int myTicket = ++consumerIndex;
                consumerWaitingList.add(myTicket);

                while (itemCount == 0 || myTicket != consumerWaitingList.get(consumerWaitingList.indexOf(Collections.min(consumerWaitingList)))) {
                    bufferEmptyCondition.await();
                }

                //System.out.println("Consumer id: " + id + " - item: "+  itemList.get(readIndex));

                itemList.set(readIndex, -1);

                itemCount--;

                readIndex++;

                if (readIndex == bufferSize)
                    readIndex = 0;

                consumerWaitingList.remove(consumerWaitingList.indexOf(Collections.min(consumerWaitingList)));

                bufferEmptyCondition.signalAll();
                bufferFullCondition.signalAll();
            }
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }
    }
}
