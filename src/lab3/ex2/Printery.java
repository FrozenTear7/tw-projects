package lab3.ex2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static lab3.ex2.ex3_2.printers;

public class Printery {
    private List<Printer> printerList;
    private List<Lock> lockList;
    private List<Condition> conditionList;
    private List<Boolean> takenList;

    public Printery(List<Printer> printerList) {
        this.printerList = printerList;
        this.lockList = new ArrayList<>(printerList.size());
        this.conditionList = new ArrayList<>(printerList.size());
        this.takenList = new ArrayList<>(printerList.size());

        for (int i = 0; i < printerList.size(); i++) {
            lockList.add(i, new ReentrantLock());
            conditionList.add(i, lockList.get(i).newCondition());
            takenList.add(i, false);
        }
    }

    public void print(int number) {
        lockList.get(number % printers).lock();

        try {
            while(takenList.get(number % printers)) {
                conditionList.get(number % printers).await();
            }

            takenList.set(number % printers, true);

            System.out.println("Printer " + number % printers + " gon be printing: " + number);

            printerList.get(number % printers).print();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Printer " + number % printers + " finished printing: " + number);

            takenList.set(number % printers, false);
            conditionList.get(number % printers).signal();
            lockList.get(number % printers).unlock();
        }
    }
}
