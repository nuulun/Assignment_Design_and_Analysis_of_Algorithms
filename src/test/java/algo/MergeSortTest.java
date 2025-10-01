package algo;

import org.junit.jupiter.api.Test;
import java.util.Random;

public class MergeSortTest {

    @Test
    void testMetrics() {
        int n = 1000;
        int trials = 100;
        for (int t = 0; t < trials/6+1; t++) {
           test(n);
        }
        n = 5000;
        for (int t = 0; t < trials/6+1; t++) {
            test(n);
        }
        n = 10000;
        for (int t = 0; t < trials/6+1; t++) {
            test(n);
        }
        n = 20000:
        for (int t = 0; t < trials/6+1; t++) {
            test(n);
        }
        n = 50000;
        for (int t = 0; t < trials/6+1; t++) {
            test(n);
        }
        n = 100000;
        for (int t = 0; t < trials/6+1; t++) {
            test(n);
        }
    }
    void test(int n) {
        int[] arr = new Random(42).ints(n, 0, 1_000_000).toArray();

        MetricsObserver metrics = new MetricsObserver();

        long start = System.nanoTime();
        MergeSort.sort(arr, metrics);
        long end = System.nanoTime();

        System.out.println("Algorithm       : " + "MergeSort");
        System.out.println("Array size      : " + n);
        System.out.println("Time (ms)       : " + (end - start) / 1_000_000.0);
        System.out.println("Comparisons     : " + metrics.comparisons);
        System.out.println("Allocations     : " + metrics.allocations);
        System.out.println("Max recursion depth: " + metrics.maxDepth);
    }
}
