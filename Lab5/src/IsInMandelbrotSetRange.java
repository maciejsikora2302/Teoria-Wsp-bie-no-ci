import java.util.ArrayList;
import java.util.concurrent.Callable;

public class IsInMandelbrotSetRange implements Callable {
    private int iter;
    private int xStart, yStart, xEnd, yEnd, width, height;
    @Override
    public ArrayList<Integer> call() throws Exception {
        for (int y = ; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                zx = zy = 0;
                cX = (x - 400) / ZOOM;
                cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                I.setRGB(x, y, iter | (iter << 8));
            }
        }
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
