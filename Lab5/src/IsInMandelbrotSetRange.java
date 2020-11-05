import java.util.ArrayList;
import java.util.concurrent.Callable;

public class IsInMandelbrotSetRange implements Callable {
    private int iter;
    private int xStart, yStart, xEnd, yEnd;
    private double ZOOM;
    private int MAX_ITER;

    public IsInMandelbrotSetRange(int iter, int xStart, int yStart, int xEnd, int yEnd, double ZOOM, int MAX_ITER) {
        this.iter = iter;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.ZOOM = ZOOM;
        this.MAX_ITER = MAX_ITER;
    }



    @Override
    public ArrayList<Integer> call() throws Exception {
        ArrayList<Integer> toRet = new ArrayList<>();
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                double zx = 0;
                double zy = 0;
                double cX = (x - 400) / ZOOM;
                double cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                double tmp;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }

                toRet.add(x);
                toRet.add(y);
                toRet.add(iter | (iter << 8));
            }
        }
        return toRet;
    }
}
