package algo;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClosestPairTest {

    private double bruteForce(Point[] pts) {
        double d = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                d = Math.min(d, Math.hypot(pts[i].x - pts[j].x, pts[i].y - pts[j].y));
            }
        }
        return d;
    }

    @Test
    public void smallRandomTest() {
        Random rnd = new Random(42);
        for (int n = 2; n <= 200; n++) {
            Point[] pts = new Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new Point(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
            }

            MetricsObserver metrics = new MetricsObserver();
            ClosestPair cp = new ClosestPair(metrics);

            double fast = cp.findClosest(pts);
            double slow = bruteForce(pts);

            assertEquals(slow, fast, 1e-9, "Mismatch at n=" + n);
        }
    }

    @Test
    public void largeTest() {
        int n = 1000;
        int trials = 100;
        for (int t = 0; t < trials/3+1; t++) {
            test(n);
        }
        n = 10000;
        for (int t = 0; t < trials/3+1; t++) {
            test(n);
        }
        n = 100000;
        for (int t = 0; t < trials/3+1; t++) {
            test(n);
        }
    }



    void test(int n){
        Random rnd = new Random(123);

        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new Point(rnd.nextDouble() * 1e6, rnd.nextDouble() * 1e6);
        }

        MetricsObserver metrics = new MetricsObserver();
        ClosestPair cp = new ClosestPair(metrics);
        long start = System.nanoTime();

        double fast = cp.findClosest(pts);
        long end = System.nanoTime();

        System.out.println("Closest distance = " + fast);
        System.out.println("Array size      : " + n);
        System.out.println("Time (ms)       : " + (end - start) / 1_000_000.0);
        System.out.println("Comparisons     : " + metrics.comparisons);
        System.out.println("Allocations     : " + metrics.allocations);
        System.out.println("Max recursion depth: " + metrics.maxDepth);
    }
}
