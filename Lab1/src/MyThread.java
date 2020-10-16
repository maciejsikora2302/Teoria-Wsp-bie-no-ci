public class MyThread extends Thread {

    private final Counter internalCounter;
    private final boolean increment;
    private final int timesToOperate;


    public MyThread(Counter counter, boolean inc, int timesToOperate) {
        this.internalCounter = counter;
        this.increment = inc;
        this.timesToOperate = timesToOperate;

    }

    public void run() {
        for (int i = 0; i < timesToOperate; i++) {
            if (increment) {
                internalCounter.incrementCounter();
            } else {
                internalCounter.decrementCounter();
            }
        }
    }
}
