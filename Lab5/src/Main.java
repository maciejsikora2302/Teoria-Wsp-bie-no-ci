import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private static final int MAX_ITER = 570;
    private static final double ZOOM = 150;

    public static void main(String[] args){


        ArrayList<Long> originalTimeArr = new ArrayList<>();
        ArrayList<Long> sameTasksAsThreads = new ArrayList<>();
        ArrayList<Long> tenTimesMoreTasks = new ArrayList<>();
        ArrayList<Long> onePixelOneTask = new ArrayList<>();
        ArrayList<Long> muchMoreTasks = new ArrayList<>();
        ArrayList<Long> betweenTenAndOne = new ArrayList<>();

        boolean printEveryResult = false;
        int numberOfRepeatToGetAverage = 10;

        for(int i=0;i<numberOfRepeatToGetAverage;i++){
            MandelbrotOriginal original = new MandelbrotOriginal();
            original.setVisible(false);
            long originalTime = original.operationTime;
            long perPixelTime = perPixelSolution(12, false);
            if (printEveryResult) System.out.printf("\nPerPixel time(%dns), original time(%dns), difference (negative is time gain): %dns", perPixelTime, originalTime, perPixelTime-originalTime);
            long perRangeTime = perRangeSolution(12, 12, false);
            if (printEveryResult) System.out.printf("\nPerRange time(%dns), original time(%dns), difference (negative is time gain): %dns", perRangeTime, originalTime, perRangeTime-originalTime);
            long perRangeTimeTenTimes = perRangeSolution(12, 120, false);
            if (printEveryResult) System.out.printf("\nperRangeTimeTenTimes time(%dns), original time(%dns), difference (negative is time gain): %dns", perRangeTimeTenTimes, originalTime, perRangeTimeTenTimes-originalTime);
            long perRangeTimeMuchMoreThreads = perRangeSolution(12, 400, false);
            if (printEveryResult) System.out.printf("\nperRangeTimeMuchMoreThreads time(%dns), original time(%dns), difference (negative is time gain): %dns", perRangeTimeMuchMoreThreads, originalTime, perRangeTimeMuchMoreThreads-originalTime);
            long perRangeTimeBetweenTenAndOne = perRangeSolution(12, 60, false);
            if (printEveryResult) System.out.printf("\nperRangeTimeBetweenTenAndOne time(%dns), original time(%dns), difference (negative is time gain): %dns", perRangeTimeBetweenTenAndOne, originalTime, perRangeTimeBetweenTenAndOne-originalTime);
            if (printEveryResult)System.out.print("\n--");

            originalTimeArr.add(originalTime);
            sameTasksAsThreads.add(perRangeTime);
            tenTimesMoreTasks.add(perRangeTimeTenTimes);
            onePixelOneTask.add(perPixelTime);
            muchMoreTasks.add(perRangeTimeMuchMoreThreads);
            betweenTenAndOne.add(perRangeTimeBetweenTenAndOne);
        }

        System.out.printf(
                "\nResults of task5 are as follows:\n" +
                "%dms -> Average time of original function\n" +
                "%dms (gain: %dms) -> Average time of solution one task per pixel\n" +
                "%dms (gain: %dms) -> Average time of solution number of tasks is the same as number of threads\n" +
                "%dms (gain: %dms) -> Average time of solution number of tasks is ten times higher than number of threads\n" +
                "%dms (gain: %dms) -> Average time of solution number of tasks is much higher than number of threads(but not as much as per pixel)\n" +
                "%dms (gain: %dms) -> Average time of solution number of tasks is between ten times and one task per thread\n",
                getAvg(originalTimeArr)/1000000,
                getAvg(onePixelOneTask)/1000000, getAvg(originalTimeArr)/1000000 - getAvg(onePixelOneTask)/1000000,
                getAvg(sameTasksAsThreads)/1000000, getAvg(originalTimeArr)/1000000 - getAvg(sameTasksAsThreads)/1000000,
                getAvg(tenTimesMoreTasks)/1000000, getAvg(originalTimeArr)/1000000 - getAvg(tenTimesMoreTasks)/1000000,
                getAvg(muchMoreTasks)/1000000, getAvg(originalTimeArr)/1000000 - getAvg(muchMoreTasks)/1000000,
                getAvg(betweenTenAndOne)/1000000, getAvg(originalTimeArr)/1000000 - getAvg(betweenTenAndOne)/1000000);

    }

    private static long getAvg(ArrayList<Long> arr){
        long sum = 0;
        for(Long number: arr){
            sum += number;
        }
        return sum/arr.size();
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
//                System.out.printf("Doing while loop for the %s time.\n", loopRepeated);
            }
        }
        long end = System.nanoTime();

        table.drawPicture();
        table.setVisible(visibility);
        pool.shutdown();

        return end-start;
    }

    public static long perRangeSolution(int threads, int numberOfTasks, boolean visibility){
        long start = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        Set<Future<Object>> tasks = new HashSet<>();
        MandelbrotTable table = new MandelbrotTable();
        int numberOfTasksCounter = 0;
        int intervalX = 800/numberOfTasks;
        int intervalY = 600/numberOfTasks;
        for (int y = 0; y+intervalY <= 600; y+=intervalY) {
            for (int x = 0; x+intervalX <= 800; x+=intervalX) {

                int xEnd = x+intervalX;
                int yEnd = y+intervalY;
                if(xEnd+intervalX > 800){
                    xEnd = 800;
                }
                if(yEnd+intervalY > 600){
                    yEnd = 600;
                }

                Runnable task = new PerRange(table, x, y, xEnd, yEnd, MAX_ITER, ZOOM);
                tasks.add((Future<Object>) pool.submit(task));
                numberOfTasksCounter++;
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
            if (numberOfTasksCheck == numberOfTasksCounter) {
                break;
            } else {
                numberOfTasksCheck = 0;
//                System.out.printf("\nDoing while loop for the %s time.", loopRepeated);
            }
        }
        long end = System.nanoTime();

        table.drawPicture();
        table.setVisible(visibility);
        pool.shutdown();

        return end-start;
    }


}
