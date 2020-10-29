public class FatProducer extends Thread{
    private int value;
    private BufforZad2 buffor;
    public FatProducer(BufforZad2 buffor, int fatness){
        this.value = fatness;
        this.buffor = buffor;
    }

    @Override
    public void run() {
        while(true){
            try {
                this.buffor.put(this.value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
