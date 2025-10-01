package algo;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

public class SelectMoMTest {
    private static final Random R = new Random(42);

    @Test
    void testCorrectnessRandom() {
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
        n = 20000;
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

    @Test
    void testSmallArrays() {
        int[] arr = {7, 2, 5, 1, 9};
        MetricsObserver obs = new MetricsObserver();
        assertEquals(1, SelectMoM.select(arr.clone(), 0, obs)); // min
        assertEquals(5, SelectMoM.select(arr.clone(), 2, obs)); // median
        assertEquals(9, SelectMoM.select(arr.clone(), 4, obs)); // max
    }

    void test(int n) {
        int[] a = R.ints(n, 0, 100000).toArray();
        int[] copy = Arrays.copyOf(a, a.length);
        Arrays.sort(copy);

        int k = R.nextInt(n);

        MetricsObserver metrics = new MetricsObserver();
        long start = System.nanoTime();

        int sel = SelectMoM.select(a, k, metrics);
        long end = System.nanoTime();

        assertEquals(copy[k], sel);
        System.out.println("Array size      : " + n);
        System.out.println("Time (ms)       : " + (end - start) / 1_000_000.0);
        System.out.println("Comparisons     : " + metrics.comparisons);
        System.out.println("Allocations     : " + metrics.allocations);
        System.out.println("Max recursion depth: " + metrics.maxDepth);
    }
}

