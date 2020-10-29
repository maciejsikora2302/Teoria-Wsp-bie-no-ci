public class FatConsumer extends Thread{
    private int value;
    private BufforZad2 buffor;
    public FatConsumer(BufforZad2 buffor, int fatness){
        this.value = fatness;
        this.buffor = buffor;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.buffor.get(this.value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
