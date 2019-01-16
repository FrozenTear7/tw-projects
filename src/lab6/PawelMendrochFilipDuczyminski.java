package lab6;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class EpicQueueue {
    List<Object> epicList;
    Lock lock = new ReentrantLock();
    Condition fullCondition = lock.newCondition();
    Condition emptyCondition = lock.newCondition();
    int size = 0;

    public EpicQueueue(int size) {
        this.size = size;
        epicList = new ArrayList<>(size);
    }

    public void add(List<Object> itemList) {
        lock.lock();

        try {
            while(epicList.size() + itemList.size() > size) {
                fullCondition.await();
            }

            epicList.addAll(itemList);
        } catch (InterruptedException e) {
            System.out.println("BRUH");
        } finally {
            emptyCondition.signalAll();
            lock.unlock();
        }

    }

    public List<Object> get(int eleQuant) {
        List<Object> tmpList = new ArrayList<>();

        lock.lock();

        try {
            while(epicList.size() - eleQuant < 0) {
                emptyCondition.await();
            }

            tmpList = epicList.subList(0, eleQuant);
            epicList.subList(0, eleQuant).clear();
        } catch (InterruptedException e) {
            System.out.println("BRUH");
        } finally {
            fullCondition.signalAll();
            lock.unlock();
        }


        return tmpList;
    }
}

//class Buffer {
//    BlockingQueue queue;
//    List<Object> listEl;
//    public Buffer(List<Object> listEl) {
//        queue = new ArrayBlockingQueue(4000);
//        this.listEl = listEl;
//    }
//    public void put(Integer numberOfElements) throws Exception{
//        for(int i=0 ; i<numberOfElements ; ++i){
//            queue.put(listEl.get(i));
//        }
//    }
//    public int take(Integer numberOfElements) throws Exception{
//        for(int i=0 ; i<numberOfElements ; ++i){
//            queue.take();
//        }
//        return 0;
//    }
//}


class Buffer {
    EpicQueueue epicQueueue = new EpicQueueue(4000);
    List<Object> listEl;

    public Buffer(List<Object> listEl) {
        this.listEl = listEl;
    }

    public void put(Integer numberOfElements) throws Exception {
        epicQueueue.add(listEl.subList(0, numberOfElements));
    }

    public int take(Integer numberOfElements) throws Exception {
        epicQueueue.get(numberOfElements);

        return 0;
    }
}

class Producer implements Runnable {
    private Buffer buffer;
    private List<Integer> list;

    public Producer(Buffer buffer, List<Integer> list) {
        this.buffer = buffer;
        this.list = list;
    }

    public void run() {
        for (int i = 0; i < list.size(); i++) {
            try {
                buffer.put(list.get(i));
            } catch (Exception e) {

            }
        }
    }
}

class Consumer implements Runnable {
    private Buffer buffer;
    private List<Integer> list;

    public Consumer(Buffer buffer, List<Integer> list) {
        this.buffer = buffer;
        this.list = list;
    }

    public void run() {
        for (int i = 0; i < list.size(); i++) {
            try {
                buffer.take(list.get(i));
            } catch (Exception e) {

            }
        }
    }
}

public class PawelMendrochFilipDuczyminski {
    public static void main(String[] argv) throws Exception {
        List<Integer> list = new ArrayList<>();
        List<Object> listEl = new ArrayList<>();
        Random rand = new Random();
        rand.setSeed(1);
        for (int i = 0; i < 6000; i++) list.add(rand.nextInt(2000));
        for (int i = 0; i < 2000; i++) listEl.add(new Object());

        Buffer buff = new Buffer(listEl);

        List<Thread> prod = new ArrayList<Thread>();
        List<Thread> cons = new ArrayList<Thread>();
        for (int i = 0; i < 10; ++i) {
            prod.add(new Thread(new Producer(buff, list)));
            cons.add(new Thread(new Consumer(buff, list)));
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; ++i) {
            Thread p = prod.get(i);
            p.start();
            Thread c = cons.get(i);
            c.start();
        }
        for (int i = 0; i < 10; ++i) {
            Thread p = prod.get(i);
            p.join();
            Thread c = cons.get(i);
            c.join();
        }
        long estimatedTime = System.currentTimeMillis() - startTime;

        System.out.println("Time is " + estimatedTime);
    }
}