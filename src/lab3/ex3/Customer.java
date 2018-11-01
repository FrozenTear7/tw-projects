package lab3.ex3;

public class Customer implements Runnable {
    private String name;
    private Pair pair;
    public int id;

    public Customer(String name, Pair pair, int id) {
        this.name = name;
        this.pair = pair;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long) (Math.random() * 5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pair.ready++;
        pair.sit();

        try {
            System.out.println(name + " started eating");
            Thread.sleep((long) (Math.random() * 5000));
            System.out.println(name + " finished eating");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pair.ready++;
        pair.finish();
    }
}
