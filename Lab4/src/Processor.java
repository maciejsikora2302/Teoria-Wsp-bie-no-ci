public class Processor extends Thread {
    public int position;
    public int takes;
    public int gives;
    private SharedMemory sm;

    public Processor(SharedMemory sm, int takes, int gives) {
        this.position = 0;
        this.takes = takes;
        this.gives = gives;
        this.sm = sm;
    }

    public void runProcess() throws InterruptedException {
        long sleepTime = (long) (1000 * Math.random());
        System.out.println("PROCESSOR(" + this.takes +")->("+this.gives+") is processing something for " + sleepTime + "ms");
        Thread.sleep(sleepTime);
    }

    @Override
    public void run() {
        while (true) {
            try {
                sm.process(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
