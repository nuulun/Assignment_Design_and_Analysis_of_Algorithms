package algo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

public class QuickSortTest {
    private static final Random R = new Random(42);

    @Test
    void testCorrectnessAndDepth() {
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

    private boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i - 1] > a[i]) return false;
        return true;
    }

    private int floorLog2(int n) {
        return 31 - Integer.numberOfLeadingZeros(n);
    }

    void test(int n) {
        int[] a = R.ints(n, 0, 1_000_000).toArray();
        int[] copy = Arrays.copyOf(a, a.length);

        MetricsObserver metrics = new MetricsObserver();
        long start = System.nanoTime();

        QuickSort.sort(copy, metrics);
        long end = System.nanoTime();

        assertTrue(isSorted(copy), "Array not sorted for n=" + n);

        int bound = 2 * floorLog2(Math.max(1, n)) + 20;
        assertTrue(metrics.maxDepth <= bound,
                "Recursion depth too large for n=" + n +
                        ": got " + metrics.maxDepth + " > bound " + bound);

        System.out.println("Array size      : " + n);
        System.out.println("Time (ms)       : " + (end - start) / 1_000_000.0);
        System.out.println("Comparisons     : " + metrics.comparisons);
        System.out.println("Allocations     : " + metrics.allocations);
        System.out.println("Max recursion depth: " + metrics.maxDepth);
    }
}
