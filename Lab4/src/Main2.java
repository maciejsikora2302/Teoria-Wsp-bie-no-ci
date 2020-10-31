import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class Main2 {
    public static void main(String[] arg) throws InterruptedException, IOException, PythonExecutionException {
        int buffSize = 1000;
        int numberOfProducers = 5;
        int numberOfConsumers = 5;
        int times_to_repeat = 1000;

        BufforZad2 buffor = new BufforZad2(buffSize, true);
        OptimalBuffor buffor2 = new OptimalBuffor(buffSize, false);

        ArrayList<FatProducer> producers = new ArrayList<>();
        ArrayList<FatConsumer> consumers = new ArrayList<>();

        for(int i=0;i<numberOfProducers;i++) producers.add(new FatProducer(buffor, ThreadLocalRandom.current().nextInt(1, buffSize + 1), times_to_repeat));
        for(int i=0;i<numberOfConsumers;i++) consumers.add(new FatConsumer(buffor, ThreadLocalRandom.current().nextInt(1, buffSize + 1), times_to_repeat));

//        for(int i=0;i<numberOfProducers;i++) producers.add(new FatProducer(buffor2, ThreadLocalRandom.current().nextInt(1, buffSize + 1), times_to_repeat));
//        for(int i=0;i<numberOfConsumers;i++) consumers.add(new FatConsumer(buffor2, ThreadLocalRandom.current().nextInt(1, buffSize + 1), times_to_repeat));

//        for(int i=1;i<=numberOfProducers;i++) producers.add(new FatProducer(buffor2, (i*5)%buffSize, times_to_repeat));
//        for(int i=1;i<=numberOfConsumers;i++) consumers.add(new FatConsumer(buffor2, (i*5)%buffSize, times_to_repeat));

        for(int i=0;i<numberOfProducers;i++) producers.get(i).start();
        for(int i=0;i<numberOfConsumers;i++) consumers.get(i).start();

        for(int i=0;i<numberOfProducers;i++) producers.get(i).join();
        for(int i=0;i<numberOfConsumers;i++) consumers.get(i).join();

        for(int i=0;i<numberOfProducers;i++) System.out.printf("Producer(%d) had avg %dns\n", producers.get(i).getValue(), producers.get(i).getAvg());
        for(int i=0;i<numberOfConsumers;i++) System.out.printf("Consumer(%d) had avg %dns\n", consumers.get(i).getValue(), consumers.get(i).getAvg());


        ArrayList<Integer> producerX = new ArrayList<>();
        ArrayList<Integer> producerY = new ArrayList<>();
        for(int i=0;i<numberOfProducers;i++){
            producerX.add(producers.get(i).getValue());
            producerY.add(producers.get(i).getAvg().intValue());
        }
        Collections.sort(producerX);

        ArrayList<Integer> consumerX = new ArrayList<>();
        ArrayList<Integer> consumerY = new ArrayList<>();
        ArrayList<ArrayList<Integer>> consumerData = new ArrayList<>();
        for(int i=0;i<numberOfConsumers;i++){
            ArrayList<Integer> tmp = new ArrayList<>();
            tmp.add(consumers.get(i).getValue());
            tmp.add(consumers.get(i).getAvg().intValue());
            consumerData.add(tmp);
        }

        class CompareData implements Comparator<ArrayList<Integer>>{

            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                return o1.get(0) - o2.get(0);
            }
        }

        consumerData.sort(new CompareData());
        for(int i=0;i<numberOfConsumers;i++) consumerX.add(consumerData.get(i).get(0));
        for(int i=0;i<numberOfConsumers;i++) consumerY.add(consumerData.get(i).get(1));

        System.out.println(consumerX);
        System.out.println(consumerY);


        Plot plt = Plot.create();
        plt.plot()
                .add(consumerX, consumerY)
                .label("Producers")
                .linestyle("--");
        plt.xlabel("xlabel");
        plt.ylabel("ylabel");
        plt.text(0.5, 0.2, "text");
        plt.title("Title!");
        plt.legend();
        plt.show();
    }
}
