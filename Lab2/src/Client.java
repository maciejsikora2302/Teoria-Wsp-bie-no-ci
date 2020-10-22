public class Client extends Thread {
    private CountingSemaphore cs;
    private int number;
    public Client(CountingSemaphore cs, int number){
        this.cs = cs;
        this.number = number;
    }

    @Override
    public void run() {
        try {
            cs.P();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long sleep_time = (long) (Math.random() * 1000);
        System.out.println("Client number " + number + " starts shopping and it takes " + sleep_time + "ms.");
        try {
            Thread.sleep(sleep_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cs.V();
    }
}
