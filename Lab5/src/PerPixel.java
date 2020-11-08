import java.util.ArrayList;

public class PerPixel implements Runnable {

    private final MandelbrotTable table;
    private double zx;
    private double zy;
    private final double cX;
    private final double cY;
    private int iter;
    public int x;
    public int y;

    public PerPixel(MandelbrotTable table, int x, int y, int MAX_ITER, double ZOOM){
        this.x= x;
        this.y = y;
        this.table = table;
        zx = zy = 0;
        cX = (x - 400) / ZOOM;
        cY = (y - 300) / ZOOM;
        iter = MAX_ITER;
    }


    @Override
    public void run() {
        while (zx * zx + zy * zy < 4 && iter > 0) {
            double tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        table.setPixelIncluded(x, y, (iter|iter<<8));
    }
}
