import java.math.BigInteger;

public class FatProducer extends Thread{
    private int value;

    private GeneralBuffor buffor;
    private int timesToRepeat;
    private BigInteger sum = new BigInteger("0");
    private BigInteger div = new BigInteger("0");
    public FatProducer(GeneralBuffor buffor, int fatness, int timesToRepeat){
        this.value = fatness;
        this.buffor = buffor;
        this.timesToRepeat = timesToRepeat;
    }
    @Override
    public void run() {
        for(int i=0;i<this.timesToRepeat;i++){
            long start = System.nanoTime();
            try {
                this.buffor.put(this.value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = System.nanoTime();
            sum = sum.add(new BigInteger(String.valueOf(end-start)));
            div = div.add(BigInteger.ONE);
//            System.out.printf("FatProducer with fatness %d took %sns. Current average %d.\n", this.value, end-start, sum.divide(div));
        }
    }

    public BigInteger getAvg(){
        return this.sum.divide(this.div);
    }

    public int getValue() {
        return value;
    }
}
