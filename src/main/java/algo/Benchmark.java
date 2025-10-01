package algo;

import java.util.Random;

public class Benchmark {
    private static final int TRIALS = 100;
    private static final int[] SIZES = {100, 500, 1000, 5000, 10000};

    private static Random random = new Random();

    public static void main(String[] args) {
        for (int n : SIZES) {
            runBenchmarks(n);
        }
    }

    private static void runBenchmarks(int n) {
        System.out.println("=== Benchmark for n = " + n + " ===");

        // ----- MergeSort -----
        long totalComparisons = 0, totalAllocations = 0;
        int totalDepth = 0;
        for (int t = 0; t < TRIALS; t++) {
            int[] arr = random.ints(n, 0, 100000).toArray();
            MetricsObserver metrics = new MetricsObserver();
            MergeSort sorter = new MergeSort(metrics);
            sorter.sort(arr);
            totalComparisons += metrics.comparisons;
            totalAllocations += metrics.allocations;
            totalDepth += metrics.maxDepth;
        }
        System.out.println("MergeSort: comps=" + totalComparisons / TRIALS +
                " allocs=" + totalAllocations / TRIALS +
                " depth=" + totalDepth / TRIALS);

        // ----- QuickSort -----
        totalComparisons = 0;
        totalAllocations = 0;
        totalDepth = 0;
        for (int t = 0; t < TRIALS; t++) {
            int[] arr = random.ints(n, 0, 100000).toArray();
            MetricsObserver metrics = new MetricsObserver();
            QuickSort sorter = new QuickSort(metrics);
            sorter.sort(arr);
            totalComparisons += metrics.comparisons;
            totalAllocations += metrics.allocations;
            totalDepth += metrics.maxDepth;
        }
        System.out.println("QuickSort: comps=" + totalComparisons / TRIALS +
                " allocs=" + totalAllocations / TRIALS +
                " depth=" + totalDepth / TRIALS);

        // ----- Median of Medians (Selection) -----
        totalComparisons = 0;
        totalAllocations = 0;
        totalDepth = 0;
        for (int t = 0; t < TRIALS; t++) {
            int[] arr = random.ints(n, 0, 100000).toArray();
            MetricsObserver metrics = new MetricsObserver();
            MedianOfMedians selector = new MedianOfMedians(metrics);
            int k = n / 2;
            selector.select(arr, k);
            totalComparisons += metrics.comparisons;
            totalAllocations += metrics.allocations;
            totalDepth += metrics.maxDepth;
        }
        System.out.println("MedianOfMedians: comps=" + totalComparisons / TRIALS +
                " allocs=" + totalAllocations / TRIALS +
                " depth=" + totalDepth / TRIALS);

        // ----- Closest Pair of Points -----
        totalComparisons = 0;
        totalAllocations = 0;
        totalDepth = 0;
        for (int t = 0; t < TRIALS; t++) {
            Point[] pts = new Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new Point(random.nextDouble() * 10000, random.nextDouble() * 10000);
            }
            MetricsObserver metrics = new MetricsObserver();
            ClosestPair cp = new ClosestPair(metrics);
            cp.findClosest(pts);
            totalComparisons += metrics.comparisons;
            totalAllocations += metrics.allocations;
            totalDepth += metrics.maxDepth;
        }
        System.out.println("ClosestPair: comps=" + totalComparisons / TRIALS +
                " allocs=" + totalAllocations / TRIALS +
                " depth=" + totalDepth / TRIALS);

        System.out.println();
    }
}
