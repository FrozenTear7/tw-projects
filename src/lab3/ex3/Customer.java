package lab3.ex3;

public class Customer implements Runnable {
    private String name;
    private Pair pair;
    private int id;

    public Customer(String name, Pair pair, int id) {
        this.name = name;
        this.pair = pair;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long) (Math.random() * 2000) + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pair.ready++;
        pair.sit();
    }

    public void eat() {
        try {
            System.out.println(name + " started eating");
            Thread.sleep((long) (Math.random() * 2000) + 1);
            System.out.println(name + " finished eating");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pair.ready++;
        pair.finish();
    }
}
