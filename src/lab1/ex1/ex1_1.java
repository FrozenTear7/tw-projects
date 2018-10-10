package lab1.ex1;

class ex1_1 {

    private static void runThreads(Counter counter, ThreadInc threadInc, ThreadDec threadDec) throws Exception {
        threadInc.start();
        threadDec.start();
        Thread.sleep(1000);
        counter.printCounter();
    }

    public static void main(String[] args) throws Exception {
        Counter counter = new Counter();
        ThreadInc threadInc = new ThreadInc(counter);
        ThreadDec threadDec = new ThreadDec(counter);
        runThreads(counter, threadInc, threadDec);
    }
}

class ThreadDec extends Thread {
    private Counter counter;

    public ThreadDec(Counter counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < 10000000; i++)
            synchronized (counter) {
                counter.decrement();
            }
    }
}

class ThreadInc extends Thread {
    private Counter counter;

    public ThreadInc(Counter counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < 10000000; i++)
            synchronized (counter) {
                counter.increment();
            }
    }
}
