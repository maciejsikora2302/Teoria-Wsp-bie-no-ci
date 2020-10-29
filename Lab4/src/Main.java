import java.util.ArrayList;

public class Main {
    public static void main(String[] arg) throws InterruptedException {
        int buffSize = 10;
        int numberOfProducers = 1;
        int numberOfConsumers = 1;
        int numberOfProcessors = 5;

        SharedMemory sharedMemory = new SharedMemory(buffSize);

        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();
        ArrayList<Processor> processors = new ArrayList<>();

        for(int i=0;i<numberOfProducers;i++) producers.add(new Producer(sharedMemory));
        for(int i=0;i<numberOfConsumers;i++) consumers.add(new Consumer(sharedMemory, numberOfProcessors));
        for(int i=0;i<numberOfProcessors;i++) processors.add(new Processor(sharedMemory, i, i+1));

        for(int i=0;i<numberOfProducers;i++) producers.get(i).start();
        for(int i=0;i<numberOfConsumers;i++) consumers.get(i).start();
        for(int i=0;i<numberOfProcessors;i++) processors.get(i).start();

        for(int i=0;i<numberOfProducers;i++) producers.get(i).join();
        for(int i=0;i<numberOfConsumers;i++) consumers.get(i).join();
        for(int i=0;i<numberOfProcessors;i++) processors.get(i).join();

    }
}
