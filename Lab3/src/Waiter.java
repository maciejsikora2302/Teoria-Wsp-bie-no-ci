import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {
    private HashMap<Integer, Condition> awaitingPeople = new HashMap<>();
    private Lock lock = new ReentrantLock();

    private Condition tableFull = lock.newCondition();
    private int atTable = 0;
    private boolean firstTime = true;

    public void reserve(int pairNumber) throws InterruptedException {
        lock.lock();
        if(!awaitingPeople.containsKey(pairNumber)){
            awaitingPeople.put(pairNumber, lock.newCondition());
            awaitingPeople.get(pairNumber).await();
        }else{
            if(!firstTime) tableFull.await();
            else firstTime = false;
            awaitingPeople.get(pairNumber).signal();
            awaitingPeople.remove(pairNumber);
        }
        lock.unlock();
    }

    public void release(){
        lock.lock();
        if (atTable == 2){
            atTable -= 1;
        }else {
            atTable -= 1;
            tableFull.signal();
        }
        lock.unlock();
    }
}
