public class PerRange implements Runnable{
    private MandelbrotTable table;
    private int maxIter;
    private final double ZOOM;
    private final int xStart, yStart, xEnd, yEnd;

    public PerRange(MandelbrotTable table, int xStart, int yStart, int xEnd, int yEnd, int MAX_ITER, double ZOOM){
        this.table = table;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.maxIter = MAX_ITER;
        this.ZOOM = ZOOM;
    }


    @Override
    public void run() {
        for(int y = yStart; y<yEnd; y++)
            for(int x = xStart; x<xEnd; x++){
                double zy;
                double zx = zy = 0;
                double cX = (x - 400) / ZOOM;
                double cY = (y - 300) / ZOOM;
                int iter = maxIter;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                table.setPixelIncluded(x, y, (iter|iter<<8));
            }

    }
}
