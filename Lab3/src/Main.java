import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int number_of_threads = 10;
        int number_of_printers = 5;

        ArrayList<Thread> threads_to_print = new ArrayList<>();
        PrinterMonitor pm = new PrinterMonitor(number_of_printers);

        for(int i=0;i<number_of_threads;i++) threads_to_print.add(new PrintingThread(pm));
        for(int i=0;i<number_of_threads;i++) threads_to_print.get(i).start();
        for(int i=0;i<number_of_threads;i++) threads_to_print.get(i).join();

        System.out.println("\n\nSecond task\n");


        Waiter waiter = new Waiter();
        ArrayList<Person> pairs = new ArrayList<>();

        int number_of_pairs = 10;

        for(int i=0;i<number_of_pairs;i++) pairs.add(new Person(waiter, i+1, 1));
        for(int i=0;i<number_of_pairs;i++) pairs.add(new Person(waiter, i+1, 1));
        for(int i=0;i<2*number_of_pairs;i++) pairs.get(i).start();
        for(int i=0;i<2*number_of_pairs;i++) pairs.get(i).join();

    }
}
