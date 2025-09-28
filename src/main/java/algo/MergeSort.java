package algo;

public class MergeSort {
    private static final int CUTOFF = 16;

    public static void sort(int[] arr, MetricsObserver observer) {
        int[] buffer = new int[arr.length];
        observer.onAllocation();
        mergeSort(arr, buffer, 0, arr.length - 1, 1, observer);
    }

    private static void mergeSort(int[] arr, int[] buffer, int left, int right, int depth, MetricsObserver observer) {
        observer.onRecursionDepth(depth);

        if (right - left <= CUTOFF) {
            insertionSort(arr, left, right, observer);
            return;
        }

        int mid = (left + right) / 2;
        mergeSort(arr, buffer, left, mid, depth + 1, observer);
        mergeSort(arr, buffer, mid + 1, right, depth + 1, observer);

        if (arr[mid] <= arr[mid + 1]) return;
        merge(arr, buffer, left, mid, right, observer);
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right, MetricsObserver observer) {
        for (int i = left; i <= right; i++) {
            buffer[i] = arr[i];
        }

        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (i > mid) {
                arr[k] = buffer[j++];
            } else if (j > right) {
                arr[k] = buffer[i++];
            } else {
                observer.onComparison();
                if (buffer[i] <= buffer[j]) {
                    arr[k] = buffer[i++];
                } else {
                    arr[k] = buffer[j++];
                }
            }
        }
    }

    private static void insertionSort(int[] arr, int left, int right, MetricsObserver observer) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left) {
                observer.onComparison();
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                } else break;
            }
            arr[j + 1] = key;
        }
    }
}
