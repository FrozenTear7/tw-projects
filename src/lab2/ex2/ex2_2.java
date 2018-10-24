package lab2.ex2;

public class ex2_2 {
    public static void main(String[] args) throws InterruptedException {
        ShoppingCentre shoppingCentre = new ShoppingCentre();

        Thread customer1 = new Thread(new Customer(shoppingCentre, 1));
        Thread customer2 = new Thread(new Customer(shoppingCentre, 2));
        Thread customer3 = new Thread(new Customer(shoppingCentre, 3));
        Thread customer4 = new Thread(new Customer(shoppingCentre, 4));
        Thread customer5 = new Thread(new Customer(shoppingCentre, 5));

        customer1.start();
        customer2.start();
        customer3.start();
        customer4.start();
        customer5.start();

        customer1.join();
        customer2.join();
        customer3.join();
        customer4.join();
        customer5.join();
    }
}
