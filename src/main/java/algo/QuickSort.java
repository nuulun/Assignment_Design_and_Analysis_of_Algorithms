

package algo;

import java.util.Random;

public class QuickSort {
    private static final Random R = new Random();

    public static void sort(int[] a, MetricsObserver obs) {
        quickSort(a, 0, a.length - 1, 1, obs);
    }

    private static void quickSort(int[] a, int left, int right, int depth, MetricsObserver obs) {
        while (left < right) {
            obs.onRecursionDepth(depth);

            int pivotIndex = left + R.nextInt(right - left + 1);
            int pivot = a[pivotIndex];

            int i = left, j = right;
            while (i <= j) {
                while (a[i] < pivot) { obs.onComparison(); i++; }
                while (a[j] > pivot) { obs.onComparison(); j--; }
                if (i <= j) {
                    int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
                    i++; j--;
                }
            }

            int leftSize = j - left + 1;
            int rightSize = right - i + 1;

            if (leftSize < rightSize) {
                if (left < j) quickSort(a, left, j, depth + 1, obs);
                left = i;
            } else {
                if (i < right) quickSort(a, i, right, depth + 1, obs);
                right = j;
            }
        }
    }
}

