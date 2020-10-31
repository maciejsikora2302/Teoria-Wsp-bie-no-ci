public interface GeneralBuffor {
    boolean put(int howMuch) throws InterruptedException ;
    boolean get(int howMuch) throws InterruptedException ;
}
