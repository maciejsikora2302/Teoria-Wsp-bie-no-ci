public class BinarySemaphore implements MySem{
    private int innerCounter;

    public BinarySemaphore(boolean available){
        if(available){
            innerCounter = 1;
        }else{
            innerCounter = 0;
        }
    }

    synchronized public void P() throws InterruptedException {
        while (innerCounter == 0){
            wait();
        }
        innerCounter -= 1;
    }

    synchronized public void V() throws InterruptedException {
        innerCounter += 1;
        notifyAll();
    }
}
