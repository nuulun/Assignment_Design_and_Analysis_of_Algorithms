package algo;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

public class SelectMoMTest {
    private static final Random R = new Random(42);

    @Test
    void testCorrectnessRandom() {
        for (int trial = 0; trial < 100; trial++) {
            int n = 200 + R.nextInt(800);
            int[] a = R.ints(n, 0, 100000).toArray();
            int[] copy = Arrays.copyOf(a, a.length);
            Arrays.sort(copy);

            int k = R.nextInt(n);

            MetricsObserver metrics = new MetricsObserver();
            long start = System.nanoTime();

            int sel = SelectMoM.select(a, k, metrics);
            long end = System.nanoTime();

            assertEquals(copy[k], sel, "Mismatch at trial " + trial + " for k=" + k);
            System.out.println("MergeSort Metrics:");
            System.out.println("Array size      : " + n);
            System.out.println("Time (ms)       : " + (end - start) / 1_000_000.0);
            System.out.println("Comparisons     : " + metrics.comparisons);
            System.out.println("Allocations     : " + metrics.allocations);
            System.out.println("Max recursion depth: " + metrics.maxDepth);
        }
    }

    @Test
    void testSmallArrays() {
        int[] arr = {7, 2, 5, 1, 9};
        MetricsObserver obs = new MetricsObserver();
        assertEquals(1, SelectMoM.select(arr.clone(), 0, obs)); // min
        assertEquals(5, SelectMoM.select(arr.clone(), 2, obs)); // median
        assertEquals(9, SelectMoM.select(arr.clone(), 4, obs)); // max
    }
}

