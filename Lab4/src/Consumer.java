public class Consumer extends Thread {
    public int position;
    public int eating;
    private SharedMemory sm;

    public Consumer(SharedMemory sm, int maxProducers) {
        this.eating = maxProducers;
        this.sm = sm;
        this.position = 0;
    }

    public void runConsume() throws InterruptedException {
        long sleepTime = (long) (1000 * Math.random());
        System.out.println("Consumer is consuming something for " + sleepTime + "ms");
        Thread.sleep(sleepTime);
    }

    @Override
    public void run() {
        while (true) {
            try {
                sm.consume(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
