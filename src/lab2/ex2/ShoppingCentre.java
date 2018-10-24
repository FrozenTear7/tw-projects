package lab2.ex2;

public class ShoppingCentre {
    private static Sem sem = new Sem(3);

    public void getCart() {
        sem.acquire();
    }

    public void sayGoodbye() {
        sem.release();
    }
}
