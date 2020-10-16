public class CounterNotSynchronised implements Counter{
    public CounterNotSynchronised() {
        internalCounter = 0;
    }
    private int internalCounter;

    public void incrementCounter(){
        internalCounter += 1;
    }

    public void decrementCounter(){
        internalCounter -= 1;
    }

    public int getInternalCounter(){
        return internalCounter;
    }

    public void printCounter(){
        System.out.println("I'm not synchronised");
    }

}
