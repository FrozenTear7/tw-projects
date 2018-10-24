package lab2.ex2;

import java.util.concurrent.TimeUnit;

public class Customer implements Runnable {
    private ShoppingCentre shoppingCentre;
    private int myNumber;

    Customer(ShoppingCentre shoppingCentre, int myNumber) {
        this.shoppingCentre = shoppingCentre;
        this.myNumber = myNumber;
    }

    public void run() {
        try {
            shoppingCentre.getCart();
            System.out.println("Customer " + myNumber + " got a cart");
            TimeUnit.SECONDS.sleep((long)(Math.random() * 5 + 2));
            System.out.println("Customer " + myNumber + " finished shopping");
            shoppingCentre.sayGoodbye();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
