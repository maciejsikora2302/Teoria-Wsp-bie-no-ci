public class CounterSynchronised implements Counter{
    public CounterSynchronised() {
        internalCounter = 0;
    }
    private int internalCounter;

    synchronized public void incrementCounter(){
        internalCounter += 1;
    }

    synchronized public void decrementCounter(){
        internalCounter -= 1;
    }

    public int getInternalCounter(){
        return internalCounter;
    }

    public void printCounter(){
        System.out.println("I'm synchronised");
    }
}
