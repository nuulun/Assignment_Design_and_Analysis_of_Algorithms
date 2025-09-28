package algo;


import java.util.Arrays;

public class SelectMoM {
    public static int select(int[] a, int k, MetricsObserver obs) {
        return select(a, 0, a.length - 1, k, obs, 1);
    }

    private static int select(int[] a, int left, int right, int k,
                              MetricsObserver obs, int depth) {
        obs.onRecursionDepth(depth);

        while (true) {
            if (left == right) return a[left];

            int pivotIndex = medianOfMedians(a, left, right, obs);
            int pivotNewIndex = partition(a, left, right, pivotIndex, obs);

            if (k == pivotNewIndex) {
                return a[k];
            } else if (k < pivotNewIndex) {
                right = pivotNewIndex - 1;
                obs.onRecursionDepth(depth + 1);
            } else {
                left = pivotNewIndex + 1;
                obs.onRecursionDepth(depth + 1);
            }
        }
    }

    private static int medianOfMedians(int[] a, int left, int right, MetricsObserver obs) {
        int n = right - left + 1;
        if (n <= 5) {
            Arrays.sort(a, left, right + 1);
            return left + n / 2;
        }

        int numMedians = 0;
        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right);
            Arrays.sort(a, i, subRight + 1);
            int median = i + (subRight - i) / 2;
            swap(a, left + numMedians, median);
            numMedians++;
        }

        return medianOfMedians(a, left, left + numMedians - 1, obs);
    }

    private static int partition(int[] a, int left, int right, int pivotIndex, MetricsObserver obs) {
        int pivot = a[pivotIndex];
        swap(a, pivotIndex, right);
        int store = left;
        for (int i = left; i < right; i++) {
            obs.onComparison();
            if (a[i] < pivot) {
                swap(a, i, store);
                store++;
            }
        }
        swap(a, store, right);
        return store;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
