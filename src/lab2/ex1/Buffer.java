package lab2.ex1;

public class Buffer {
    private int item;
    private Sem semCon = new Sem(false);

    private Sem semProd = new Sem(true);

    void take() {
        semCon.acquire();

        System.out.println("Consumer consumed: " + item);

        semProd.release();
    }

    void put(int item) {
        semProd.acquire();

        this.item = item;

        System.out.println("Producer produced: " + item);

        semCon.release();
    }
}
