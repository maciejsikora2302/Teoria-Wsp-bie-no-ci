import java.util.ArrayList;
import java.util.concurrent.Callable;

public class IsInMandelbrotSetPixel implements Callable {
    private double zx;
    private double zy;
    private final double cX;
    private final double cY;
    private int iter;
    public int x;
    public int y;
    public IsInMandelbrotSetPixel(int x, int y, int MAX_ITER, double ZOOM){
        this.x= x;
        this.y = y;
        zx = zy = 0;
        cX = (x - 400) / ZOOM;
        cY = (y - 300) / ZOOM;
        iter = MAX_ITER;
    }
    @Override
    public ArrayList<Integer> call() throws Exception {
        while (zx * zx + zy * zy < 4 && iter > 0) {
            double tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        ArrayList<Integer> toRet = new ArrayList<>();
        toRet.add(this.x);
        toRet.add(this.y);
        toRet.add(iter | (iter << 8));
        return toRet;
    }
}
