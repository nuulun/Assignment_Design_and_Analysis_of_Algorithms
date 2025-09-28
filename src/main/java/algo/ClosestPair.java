package algo;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    private final MetricsObserver metrics;

    public ClosestPair(MetricsObserver metrics) {
        this.metrics = metrics;
    }



    public double findClosest(Point[] points) {
        Point[] ptsSortedX = points.clone();
        Arrays.sort(ptsSortedX, Comparator.comparingDouble(p -> p.x));
        Point[] aux = new Point[ptsSortedX.length];
        return closestRec(ptsSortedX, aux, 0, ptsSortedX.length - 1, 0);
    }

    private double closestRec(Point[] pts, Point[] aux, int lo, int hi, int depth) {
        metrics.onRecursionDepth(depth);

        if (hi - lo <= 3) {
            return bruteForce(pts, lo, hi);
        }

        int mid = (lo + hi) / 2;
        double midX = pts[mid].x;

        double d1 = closestRec(pts, aux, lo, mid, depth + 1);
        double d2 = closestRec(pts, aux, mid + 1, hi, depth + 1);
        double d = Math.min(d1, d2);

        mergeByY(pts, aux, lo, mid, hi);

        int m = 0;
        for (int i = lo; i <= hi; i++) {
            if (Math.abs(pts[i].x - midX) < d) {
                aux[m++] = pts[i];
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m && (aux[j].y - aux[i].y) < d; j++) {
                d = Math.min(d, dist(aux[i], aux[j]));
                metrics.onComparison();
            }
        }

        return d;
    }

    private double bruteForce(Point[] pts, int lo, int hi) {
        double d = Double.POSITIVE_INFINITY;
        for (int i = lo; i <= hi; i++) {
            for (int j = i + 1; j <= hi; j++) {
                d = Math.min(d, dist(pts[i], pts[j]));
                metrics.onComparison();
            }
        }
        Arrays.sort(pts, lo, hi + 1, Comparator.comparingDouble(p -> p.y));
        return d;
    }

    private void mergeByY(Point[] pts, Point[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid + 1, k = 0;
        while (i <= mid && j <= hi) {
            if (pts[i].y <= pts[j].y) {
                aux[k++] = pts[i++];
            } else {
                aux[k++] = pts[j++];
            }
        }
        while (i <= mid) aux[k++] = pts[i++];
        while (j <= hi) aux[k++] = pts[j++];

        System.arraycopy(aux, 0, pts, lo, k);
    }

    private double dist(Point a, Point b) {
        metrics.onComparison();
        return Math.hypot(a.x - b.x, a.y - b.y);
    }
}


