package lab3.ex3;

import java.util.ArrayList;
import java.util.List;

public class Pair {
    private Table table;
    private List<Customer> pair = new ArrayList<>(2);
    public int id;
    public int ready = 0;

    public Pair(Table table, int id) {
        this.table = table;
        this.id = id;
    }

    public void setPair(Customer customer1, Customer customer2) {
        pair.add(0, customer1);
        pair.add(1, customer2);
    }

    public void sit() {
        if (ready == 2) {
            ready = 0;
            table.sitPair(this);
        }
    }

    public void eat() {
        pair.get(0).eat();
        pair.get(1).eat();
    }

    public void finish() {
        if (ready == 2)
            table.finish();
    }
}
