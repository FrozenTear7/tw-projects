package lab3.ex2;

import java.util.ArrayList;
import java.util.List;

public class ex3_2 {
    public static int printers = 1;
    public static int toPrint = 5;
    public static void main(String[] args) throws InterruptedException {
        List<Printer> printerList = new ArrayList<>();

        for (int i = 0; i < printers; i++) {
            printerList.add(new Printer(i));
        }

        Printery printery = new Printery(printerList);
        List<Thread> toPrintList = new ArrayList<>();

        for (int i = 0; i < toPrint; i++) {
            toPrintList.add(new Thread(new ToPrint(printery, i)));
        }

        for (int i = 0; i < toPrint; i++) {
            toPrintList.get(i).start();
        }

        for (int i = 0; i < toPrint; i++) {
            toPrintList.get(i).join();
        }
    }
}
