package lab1.ex1;

public class Counter {
    private int counter;

    public Counter () {
        this.counter = 0;
    }

    public void increment () {
        this.counter++;
    }

    public void decrement () {
        this.counter--;
    }

    public void printCounter () {
        System.out.println(this.counter);
    }
}
