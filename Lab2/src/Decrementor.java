public class Decrementor extends Thread{
    private Integer a;
    private final MySem sem;
    private final int times;
    public Decrementor(Integer a, MySem semaphore, int times){
        this.a = a;
        this.sem = semaphore;
        this.times = times;
    }
    @Override
    public void run() {
        for(int i=0;i<times;i++){
            try {
                sem.P();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.print("-");
            a -= 1;

            try {
                sem.V();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

