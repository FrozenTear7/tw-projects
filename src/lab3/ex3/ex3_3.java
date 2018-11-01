package lab3.ex3;

import java.util.ArrayList;
import java.util.List;

public class ex3_3 {
    public static void main(String[] args) throws InterruptedException {
        Table table = new Table();

        List<Pair> pairs = new ArrayList<>(3);
        List<Customer> customers = new ArrayList<>(6);
        List<Thread> threads = new ArrayList<>(6);

        pairs.add(0, new Pair(table, 0));
        pairs.add(1, new Pair(table, 1));
        pairs.add(2, new Pair(table, 2));

        customers.add(0, new Customer("Klemens", pairs.get(0), 0));
        customers.add(1, new Customer("Busio", pairs.get(0), 1));
        customers.add(2, new Customer("Juzek", pairs.get(1), 0));
        customers.add(3, new Customer("Arko", pairs.get(1), 1));
        customers.add(4, new Customer("Fafruch", pairs.get(2), 0));
        customers.add(5, new Customer("Pablo", pairs.get(2), 1));

        pairs.get(0).setPair(customers.get(0), customers.get(1));
        pairs.get(1).setPair(customers.get(2), customers.get(3));
        pairs.get(2).setPair(customers.get(4), customers.get(5));

        for(Customer customer : customers) {
            threads.add(new Thread(customer));
        }

        for(Thread thread : threads) {
            thread.start();
        }

        for(Thread thread : threads) {
            thread.join();
        }
    }
}
