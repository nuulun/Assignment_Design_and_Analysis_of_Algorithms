package algo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Benchmark {
    private static final Random R = new Random();

    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("results.csv");
        fw.write("algorithm,n,trial,comparisons,allocations,depth\n");

        int[] sizes = {100, 500, 1000, 5000, 10000};

        // MergeSort и QuickSort
        for (int n : sizes) {
            for (int t = 0; t < 100; t++) {
                int[] arr1 = R.ints(n, 0, 100000).toArray();
                int[] arr2 = arr1.clone();

                // MergeSort
                MetricsObserver obs1 = new MetricsObserver();
                MergeSort.sort(arr1, obs1);
                fw.write("MergeSort," + n + "," + t + "," +
                        obs1.getComparisons() + "," +
                        obs1.getAllocations() + "," +
                        obs1.getMaxDepth() + "\n");

                // QuickSort
                MetricsObserver obs2 = new MetricsObserver();
                QuickSort.sort(arr2, obs2);
                fw.write("QuickSort," + n + "," + t + "," +
                        obs2.getComparisons() + "," +
                        obs2.getAllocations() + "," +
                        obs2.getMaxDepth() + "\n");
            }
        }

        // Select Median-of-Medians
        for (int n : sizes) {
            for (int t = 0; t < 100; t++) {
                int[] arr = R.ints(n, 0, 100000).toArray();
                MetricsObserver obs = new MetricsObserver();
                int k = n / 2;
                SelectMoM.select(arr, k, obs);
                fw.write("SelectMoM," + n + "," + t + "," +
                        obs.getComparisons() + "," +
                        obs.getAllocations() + "," +
                        obs.getMaxDepth() + "\n");
            }
        }

        // Closest Pair of Points
        for (int n : sizes) {
            for (int t = 0; t < 100; t++) {
                double[][] points = new double[n][2];
                for (int i = 0; i < n; i++) {
                    points[i][0] = R.nextDouble() * 1000;
                    points[i][1] = R.nextDouble() * 1000;
                }
                MetricsObserver obs = new MetricsObserver();
                ClosestPair.closestPair(points, obs);
                fw.write("ClosestPair," + n + "," + t + "," +
                        obs.getComparisons() + "," +
                        obs.getAllocations() + "," +
                        obs.getMaxDepth() + "\n");
            }
        }

        fw.close();
      
    }
}
