package lab5.ex1;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Mandelbrot extends JFrame {
    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;
    public static int NTHREADS = 10;

    public Mandelbrot() {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);

        int i = 0;
        int count = getWidth() / NTHREADS;
        int startY = -1;

        for (int y = 0; y < getHeight(); y++) {
            if (startY == -1) {
                startY = y;
            }

            if (++i == count || y == getHeight() - 1) {
                final int a = startY, b = y + 1;
                Future<Integer> future = executor.submit(() -> {
                    return getPixel(a, b);
                });

                int iter = 0;

                try {
                    iter = future.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                startY = -1;
            }

            i = i % count;
        }

        executor.shutdown();
    }

    public Integer getPixel(int y, int endY) {
        int iter = 0;

        for (; y < endY; y++) {
            for (int x = 0; x < getWidth(); x++) {
                zx = zy = 0;
                cX = (x - 400) / ZOOM;
                cY = (y - 300) / ZOOM;
                iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }

                I.setRGB(x, y, iter | (iter << 8));
            }
        }

        return iter;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {
        long start = System.nanoTime();
        new Mandelbrot().setVisible(true);
        long elapsedTime = System.nanoTime() - start;
        System.out.println("Ilość wątków: " + NTHREADS + ", czas w sekundach: " + elapsedTime / 1000000000.0);
    }
}