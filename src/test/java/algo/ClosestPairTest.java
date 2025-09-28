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
        Random rnd = new Random(123);
        int n = 100_000;
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new Point(rnd.nextDouble() * 1e6, rnd.nextDouble() * 1e6);
        }

        MetricsObserver metrics = new MetricsObserver();
        ClosestPair cp = new ClosestPair(metrics);

        double fast = cp.findClosest(pts);
        System.out.println("Closest distance = " + fast);
        System.out.println("Comparisons: " + metrics.comparisons + ", MaxDepth: " + metrics.maxDepth);
    }
}
