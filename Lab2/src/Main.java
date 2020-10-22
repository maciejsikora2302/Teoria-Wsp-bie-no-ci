import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws InterruptedException {


        int numberOfClients = 30;
        int numberOfCarts = 5;

        CountingSemaphore cs = new CountingSemaphore(numberOfCarts);

        ArrayList<Client> clients = new ArrayList<>();

        for(int i=0;i<numberOfClients; i++) clients.add(new Client(cs, i));
        for(int i=0;i<numberOfClients; i++) clients.get(i).start();
        for(int i=0;i<numberOfClients; i++) clients.get(i).join();














//        Integer counter = 0;
//        BinarySemaphore mySemaphore = new BinarySemaphore(true);
//
//
//        int decrementingThreads = 10;
//        int incrementingThreads = 10;
//
//        int timesToTryAccess = 10000;
//
//
//        ArrayList<Incrementor> incThreads = new ArrayList<>();
//        ArrayList<Decrementor> decThreads = new ArrayList<>();
//
//        for(int i =0 ;i< incrementingThreads;i++){
//            incThreads.add(new Incrementor(counter, mySemaphore, timesToTryAccess));
//        }
//        for(int i=0; i< decrementingThreads;i++){
//            decThreads.add(new Decrementor(counter, mySemaphore, timesToTryAccess));
//        }
//
//        for(Incrementor i: incThreads){
//            i.start();
//        }
//
//        for (Decrementor d: decThreads){
//            d.start();
//        }
//
//        for(Incrementor i: incThreads){
//            i.join();
//        }
//
//        for (Decrementor d: decThreads){
//            d.join();
//        }
//
//        System.out.println("\nValue of a counter at the end: " + counter);
//
//
//        System.out.println("\n=========================");
//
//
//
//        Integer counter1 = 0;
//        CountingSemaphore countingSemaphore = new CountingSemaphore(1);
//
//
//        int decrementingThreads1 = 10;
//        int incrementingThreads1 = 10;
//
//        int timesToTryAccess1 = 10000;
//
//
//        ArrayList<Incrementor> incThreads1 = new ArrayList<>();
//        ArrayList<Decrementor> decThreads1 = new ArrayList<>();
//
//        for(int i =0 ;i< incrementingThreads1;i++){
//            incThreads1.add(new Incrementor(counter1, countingSemaphore, timesToTryAccess1));
//        }
//        for(int i=0; i< decrementingThreads1;i++){
//            decThreads1.add(new Decrementor(counter, countingSemaphore, timesToTryAccess1));
//        }
//
//        for(Incrementor i: incThreads1){
//            i.start();
//        }
//
//        for (Decrementor d: decThreads1){
//            d.start();
//        }
//
//        for(Incrementor i: incThreads1){
//            i.join();
//        }
//
//        for (Decrementor d: decThreads1){
//            d.join();
//        }
//
//        System.out.println("\nValue of a counter at the end: " + counter);
    }

}
