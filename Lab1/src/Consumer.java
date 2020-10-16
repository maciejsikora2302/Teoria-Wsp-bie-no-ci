public class Consumer implements Runnable {
    private final Buffer buffer;
    private final int ILOSC;

    public Consumer(Buffer buffer, int ILOSC) {
        this.buffer = buffer;
        this.ILOSC = ILOSC;
    }

    public void run() {
        for(int i = 1;  i <= ILOSC;   i++) {
            try {
                String taken = buffer.take();
                System.out.println("Consumer ate message: \""+taken+"\" and overall I have eaten \t\t\t"+i+" messages");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
