import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;

    public Mandelbrot() throws ExecutionException, InterruptedException {
        super("Mandelbrot Set");
        concurrencyPerPixel();

    }

    private void concurrencyPerPixel() throws ExecutionException, InterruptedException {
        MandelbrotOriginal original = new MandelbrotOriginal();
        original.setVisible(false);

        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        ExecutorService pool = Executors.newFixedThreadPool(12);
        Set<Future<ArrayList<Integer>>> answers = new HashSet<>();
        long start = System.nanoTime();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Callable<ArrayList<Integer>> worker = new IsInMandelbrotSetPixel(x, y, MAX_ITER, ZOOM);
                answers.add(pool.submit(worker));
            }
        }
        for(Future<ArrayList<Integer>> future: answers){
            ArrayList<Integer> tmp = future.get();
            I.setRGB(tmp.get(0), tmp.get(1), tmp.get(2));
        }
        long end = System.nanoTime();
        long resTime = end-start;
        System.out.printf("Time: %d, diff (should be positive for decreased run time): %d \n", resTime, original.operationTime - (resTime) );
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        new MandelbrotOriginal().setVisible(false);
        new Mandelbrot().setVisible(true);

    }
}