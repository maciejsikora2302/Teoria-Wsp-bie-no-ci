import java.util.Random;

public class PrintingThread extends Thread{
    private final PrinterMonitor pm;
    private int to_print;
    public PrintingThread(PrinterMonitor pm){
        this.pm = pm;
    }

    @Override
    public void run() {
        try {
            int printer = pm.reserve();
            create_task_to_print();
            System.out.println("Printing: " + to_print+ " on printer with number(" + printer +")");
            pm.release(printer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void create_task_to_print(){
        int num = 0;
        Random gen = new Random();
        for(int i=0;i<5;i++){
            num += gen.nextInt(10);
            num *= 10;
        }
        this.to_print = num/10;
    }
}
