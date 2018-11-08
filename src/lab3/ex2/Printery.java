package lab3.ex2;

import java.util.List;
import java.util.concurrent.Semaphore;

import static lab3.ex2.ex3_2.printers;

public class Printery {
    private List<Printer> printerList;
    private Semaphore semaphore = new Semaphore(printers);

    public Printery (List<Printer> printerList) {
        this.printerList = printerList;
    }

    public void print(int number) {
        try {
            semaphore.acquire();
            System.out.println("Printer " + number % printers + " gon be printing: " + number);
            printerList.get(number % printers).print();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
