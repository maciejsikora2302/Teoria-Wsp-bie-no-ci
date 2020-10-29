import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main2 {
    public static void main(String[] arg) throws InterruptedException {
        int buffSize = 1000;
        int numberOfProducers = 5;
        int numberOfConsumers = 5;

        BufforZad2 buffor = new BufforZad2(buffSize);

        ArrayList<FatProducer> producers = new ArrayList<>();
        ArrayList<FatConsumer> consumers = new ArrayList<>();

        for(int i=0;i<numberOfProducers;i++) producers.add(new FatProducer(buffor, ThreadLocalRandom.current().nextInt(1, buffSize + 1)));
        for(int i=0;i<numberOfConsumers;i++) consumers.add(new FatConsumer(buffor, ThreadLocalRandom.current().nextInt(1, buffSize + 1)));

        for(int i=0;i<numberOfProducers;i++) producers.get(i).start();
        for(int i=0;i<numberOfConsumers;i++) consumers.get(i).start();

        for(int i=0;i<numberOfProducers;i++) producers.get(i).join();
        for(int i=0;i<numberOfConsumers;i++) consumers.get(i).join();
    }
}
