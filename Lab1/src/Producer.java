public class Producer implements Runnable {
    private final Buffer buffer;
    private final int ILOSC;

    public Producer(Buffer buffer, int ILOSC) {
        this.buffer = buffer;
        this.ILOSC = ILOSC;
    }

    public void run() {
        for(int i = 1;  i <= ILOSC;   i++) {
            try {
                buffer.put("message " + i);
                System.out.println("Producer put message: \"message "+i+"\" and overall I have created \t\t"+i+" messages");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}