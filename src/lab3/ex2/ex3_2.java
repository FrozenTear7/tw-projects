package lab3.ex2;

import java.util.ArrayList;
import java.util.List;

public class ex3_2 {
    public static void main(String[] args) throws InterruptedException {
        int printers = 4;
        int toPrint = 10;

        List<Printer> printerList = new ArrayList<>();
        List<Thread> toPrintList = new ArrayList<>();

        for (int i = 0; i < printers; i++) {
            printerList.add(new Printer(i));
        }

        for (int i = 0; i < toPrint; i++) {
            toPrintList.add(new Thread(new ToPrint(printerList.get(i % printers), i)));
        }

        for (int i = 0; i < toPrint; i++) {
            toPrintList.get(i).start();
        }

        for (int i = 0; i < toPrint; i++) {
            toPrintList.get(i).join();
        }
    }
}
