public class Person extends Thread{
    public int pairNumber;
    private int eatHowManyTimes;
    private Waiter waiter;

    public Person(Waiter waiter, int num, int eatTimes){
        this.waiter = waiter;
        this.pairNumber = num;
        this.eatHowManyTimes = eatTimes;
    }

    @Override
    public void run() {
        for(int i=0;i<this.eatHowManyTimes; i++){
            System.out.println("Person from pair " + this.pairNumber + " is trying to get to table.");
            try {
                waiter.reserve(this.pairNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long sleep_time = (long) (Math.random() * 1000);
//            long sleep_time = 150;
            System.out.println("Person from pair " + this.pairNumber + " is eating for " + sleep_time + "ms.");


            try {
                Thread.sleep(sleep_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            waiter.release();
        }
    }
}
