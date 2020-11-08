import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private static final int MAX_ITER = 570;
    private static final double ZOOM = 150;

    public static void main(String[] args){

        MandelbrotOriginal original = new MandelbrotOriginal();
        original.setVisible(false);
        long originalTime = original.operationTime/1000000;
        long perPixelTime = perPixelSolution(12, false)/1000000;
        System.out.printf("Perpixel time(%dms), original time(%dms), difference (negative is time gain): %dms", perPixelTime, originalTime, perPixelTime-originalTime);

    }

    public static long perPixelSolution(int threads, boolean visibility){
        long start = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        Set<Future<Object>> tasks = new HashSet<>();
        MandelbrotTable table = new MandelbrotTable();
        int numberOfTasks = 0;
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 600; j++) {
                Runnable task = new PerPixel(table, i, j, MAX_ITER, ZOOM);
                tasks.add((Future<Object>) pool.submit(task));
                numberOfTasks++;
            }
        }

        int numberOfTasksCheck = 0;
        int loopRepeated = 1;
        while (true) {
            loopRepeated++;
            for (Future<Object> task : tasks) {
                if (task.isDone()) {
                    numberOfTasksCheck++;
                }
            }
            if (numberOfTasksCheck == numberOfTasks) {
                break;
            } else {
                numberOfTasksCheck = 0;
                System.out.printf("Doing while loop for the %s time.\n", loopRepeated);
            }
        }
        long end = System.nanoTime();

        table.drawPicture();
        table.setVisible(visibility);
        pool.shutdown();

        return end-start;
    }


}
