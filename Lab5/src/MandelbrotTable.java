import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MandelbrotTable extends JFrame {
    private final int x = 800;
    private final int y = 600;
    private final int[][] pixelIncluded = new int[x][y];
    BufferedImage I;

    public MandelbrotTable(){
        super("Mandelbrot Set");
    }

    public void setPixelIncluded(int x, int y, int value){
        pixelIncluded[x][y] = value;
    }

    public void drawPicture() {
        setBounds(100, 100, x, y);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                I.setRGB(x, y, pixelIncluded[x][y]);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {
        new MandelbrotOriginal().setVisible(true);
    }
}
