//package lab5.ex1;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.*;
//
//public class Mandelbrot extends JFrame {
//    private final int MAX_ITER = 570;
//    private final double ZOOM = 150;
//    private BufferedImage I;
//    private double zx, zy, cX, cY, tmp;
//    public static int NTHREADS = 8;
//
//    public Mandelbrot() {
//        super("Mandelbrot Set");
//        setBounds(100, 100, 800, 600);
//        setResizable(false);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
//
//        ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);
//
//        long start = System.nanoTime();
//
//        List<Future<Integer>> futureList = new ArrayList<>();
//
//        int i = 0;
//        int count = getWidth() / NTHREADS;
//        int startY = -1;
//
//        for (int y = 0; y < getHeight(); y++) {
//            if (startY == -1) {
//                startY = y;
//            }
//
//            if (++i == count || y == getHeight() - 1) {
//                final int a = startY, b = y + 1;
//                futureList.add(executor.submit(() -> {
//                    return getPixel(a, b);
//                }));
//
//                startY = -1;
//            }
//
//            i = i % count;
//        }
//
//        for (Future<Integer> future : futureList) {
//            int iter = 0;
//
//            try {
//                iter = future.get();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        long elapsedTime = System.nanoTime() - start;
//        System.out.println("Ilość wątków: " + NTHREADS + ", czas w sekundach: " + elapsedTime / 1000000000.0);
//
//        executor.shutdown();
//    }
//
//    public Integer getPixel(int y, int endY) {
//        int iter = 0;
//
//        for (; y < endY; y++) {
//            for (int x = 0; x < getWidth(); x++) {
//                zx = zy = 0;
//                cX = (x - 400) / ZOOM;
//                cY = (y - 300) / ZOOM;
//                iter = MAX_ITER;
//                while (zx * zx + zy * zy < 4 && iter > 0) {
//                    tmp = zx * zx - zy * zy + cX;
//                    zy = 2.0 * zx * zy + cY;
//                    zx = tmp;
//                    iter--;
//                }
//
//                I.setRGB(x, y, iter | (iter << 8));
//            }
//        }
//
//        return iter;
//    }
//
//    @Override
//    public void paint(Graphics g) {
//        g.drawImage(I, 0, 0, this);
//    }
//
//    public static void main(String[] args) {
//        new Mandelbrot().setVisible(true);
//    }
//}

package lab5.ex1;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Mandelbrot extends JFrame {

    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;
    public static int NTHREADS = 8;

    public Mandelbrot() {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        List<Future<Integer>> futureList = new ArrayList<>();

        long start = System.nanoTime();

        ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                final int a = x, b = y;

                futureList.add(executor.submit(() -> {
                    return getPixel(a, b);
                }));
            }
        }

        for (Future<Integer> future : futureList) {
            int iter = 0;

            try {
                iter = future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long elapsedTime = System.nanoTime() - start;
        System.out.println("Ilość wątków: " + NTHREADS + ", czas w sekundach: " + elapsedTime / 1000000000.0);

        executor.shutdown();
    }

    public Integer getPixel(int x, int y) {
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

        return iter;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {
        new Mandelbrot().setVisible(true);
    }
}