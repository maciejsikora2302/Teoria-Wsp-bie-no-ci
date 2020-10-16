import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        //kod rozwiązujący zadanie 1 i 2
        System.out.println("Task 1 and 2 \n");
        //test funkcji bez synchonizacji monitorem
        System.out.println("Testing on not synchronised Counter...");

        int timesToOperate = 10000;
        testCounters(false, timesToOperate);

        //test funkcji z synchronizacją monitorem
        System.out.println("\nTesting on synchronised Counter...");
        testCounters(true, timesToOperate);


        //kod rozwiązujący zadanie 3

        int numberOfProducers = 3;
        int numberOfConsumers = 3;
        //Konsumenci w sumie mogą zjeść tylko tyle ile jest producentów * ilość wyprodukowanych produktów per producent
        int amountOfProductsPerProducer = 5;

        System.out.println("\nTask 3, " +
                "Producers(" + numberOfProducers + "), " +
                "Consumers(" + numberOfConsumers + "), " +
                "Product per Producer(" + amountOfProductsPerProducer + ") \n");

        Buffer mainBuffer = new Buffer();

        ArrayList<Producer> producers = new ArrayList<>();
        for (int i = 0; i < numberOfProducers; i++) {
            producers.add(new Producer(mainBuffer, amountOfProductsPerProducer));
        }

        ArrayList<Consumer> consumers = new ArrayList<>();
        for (int i = 0; i < numberOfConsumers; i++) {
            consumers.add(new Consumer(mainBuffer, (numberOfProducers * amountOfProductsPerProducer) / numberOfConsumers));
        }

        ArrayList<Thread> allThreads = new ArrayList<>();

        for (Producer p : producers) {
            Thread t = new Thread(p);
            t.start();
            allThreads.add(t);
        }

        for (Consumer c : consumers) {
            Thread t = new Thread(c);
            t.start();
            allThreads.add(t);
        }


        for (Thread t : allThreads) {
            t.join();
//            System.out.println("Thread joined");
        }

    }

    //funkcja testująca asynchroniczny dostęp do zasobu
    private static void testCounters(boolean synchronised, int timesToOperate) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Counter myCounter;
            if (!synchronised) {
                myCounter = new CounterNotSynchronised();
            } else {
                myCounter = new CounterSynchronised();
            }
            MyThread t1 = new MyThread(myCounter, true, timesToOperate);
            MyThread t2 = new MyThread(myCounter, false, timesToOperate);
            t1.start();
            t2.start();

            t1.join();
            t2.join();

            System.out.println("Counter value after joining threads: " + myCounter.getInternalCounter());
        }
    }


}
