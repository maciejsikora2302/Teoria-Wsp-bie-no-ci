public class Producer extends Thread {
    public int position;
    private final SharedMemory sm;

    public Producer(SharedMemory sharedMemory) {
        this.sm = sharedMemory;
        this.position = 0;
    }


    public void runProduction() throws InterruptedException {
        long sleepTime = (long) (1000 * Math.random());
        System.out.println("Producer is producing something for " + sleepTime + "ms");
        Thread.sleep(sleepTime);
    }

    @Override
    public void run() {
        while (true) {
            try {
                sm.produce(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep((long) (10*Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
