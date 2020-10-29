import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedMemory {
    private final int[] innerMemory;
    private final int size;
    private Lock lock = new ReentrantLock();
    private final HashMap<Integer, Condition> awaiting = new HashMap<>();
    private ArrayList<Lock> locks;

    public SharedMemory(int size) {
        this.innerMemory = new int[size];
        this.size = size;
        for (int i = 0; i < size; i++) {
            innerMemory[i] = -1;
        }
        locks = new ArrayList<>();
        for(int i=0;i<size;i++){
            locks.add(new ReentrantLock());
        }
    }

    public void produce(Producer producer) throws InterruptedException {
        lock.lock();
        if (innerMemory[producer.position] == -1) {
            producer.runProduction();
            innerMemory[producer.position] = 0;

            if (awaiting.containsKey(0)) {
                awaiting.get(0).signal();
                awaiting.remove(0);
            }

            producer.position++;
            if (producer.position == this.size) producer.position = 0;
        } else {
            awaiting.put(-1, lock.newCondition());
            awaiting.get(-1).await();
        }
        lock.unlock();
    }

    public void process(Processor processor) throws InterruptedException {
        lock.lock();
        if (innerMemory[processor.position] == processor.takes) {
            processor.runProcess();
            innerMemory[processor.position] = processor.gives;

            if (awaiting.containsKey(processor.gives)) {
                awaiting.get(processor.gives).signal();
                awaiting.remove(processor.gives);
            }

            processor.position++;
            if (processor.position == this.size) processor.position = 0;

        } else {
            awaiting.put(processor.takes, lock.newCondition());
            awaiting.get(processor.takes).await();
        }
        lock.unlock();
    }

    public void consume(Consumer consumer) throws InterruptedException {
        lock.lock();
        if (innerMemory[consumer.position] == consumer.eating) {
            consumer.runConsume();
            innerMemory[consumer.position] = -1;

            if (awaiting.containsKey(-1)) {
                awaiting.get(-1).signal();
                awaiting.remove(-1);
            }

            consumer.position++;
            if (consumer.position == this.size) consumer.position = 0;
        } else {
            awaiting.put(consumer.eating, lock.newCondition());
            awaiting.get(consumer.eating).await();
        }
        lock.unlock();
    }
}
