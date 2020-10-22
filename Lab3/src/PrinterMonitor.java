import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor {
    private final Lock lock = new ReentrantLock();
    private final boolean[] printers;
    private final Condition allBusy = lock.newCondition();
    private final int number_of_printers;
    public PrinterMonitor(int number_of_printers){
        this.number_of_printers = number_of_printers;
        this.printers= new boolean[number_of_printers];
        for(int i=0; i<number_of_printers; i++){
            this.printers[i] = false;
        }
    }

    public int reserve() throws InterruptedException {
        this.lock.lock();

        while (true){
            for(int i=0; i<this.number_of_printers; i++){
                if(!this.printers[i]){
                    this.printers[i] = true;
                    this.lock.unlock();
                    return i;
                }
            }

            this.allBusy.await();
        }
    }

    public void release(int printer_number){
        this.lock.lock();
        if(!this.printers[printer_number]){
            System.out.print("Wątek chciał zwolnić nieużywaną drukarnę -> błąd.\n");
        }else{
            this.printers[printer_number] = false;

            this.allBusy.signal();
        }
        this.lock.unlock();
    }
}
