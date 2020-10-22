public class CountingSemaphore implements MySem{
    private int innerCounter;

    public CountingSemaphore(int initialValue){
        this.innerCounter = initialValue;
    }

    synchronized public void P() throws InterruptedException {
        while (innerCounter == 0){
            wait();
        }
        innerCounter -= 1;
    }

    synchronized public void V(){
        innerCounter += 1;
        notifyAll();
    }
}
