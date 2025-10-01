package algo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Benchmark {
    private static final int TRIALS = 100;
    private static final int[] SIZES = {100, 500, 1000, 5000, 10000};
    private static final Random R = new Random();

    public static void main(String[] args) throws IOException {
        try (FileWriter fw = new FileWriter("results.csv")) {
            fw.write("trial,algorithm,n,timeMs,comparisons,allocations,depth\n");

            for (int n : SIZES) {
                System.out.println("Starting benchmarks for n = " + n);
                for (int t = 1; t <= TRIALS; t++) {
                    
                    int[] base = R.ints(n, 0, 1_000_000).toArray();

                    // --- MergeSort ---
                    {
                        int[] arr = base.clone();
                        MetricsObserver obs = new MetricsObserver();
                        long start = System.nanoTime();
                        MergeSort.sort(arr, obs);              
                        long end = System.nanoTime();
                        fw.write(String.format("%d,MergeSort,%d,%.3f,%d,%d,%d\n",
                                t, n, (end - start) / 1e6,
                                obs.getComparisons(), obs.getAllocations(), obs.getMaxDepth()));
                    }

                    // --- QuickSort ---
                    {
                        int[] arr = base.clone();
                        MetricsObserver obs = new MetricsObserver();
                        long start = System.nanoTime();
                        QuickSort.sort(arr, obs);           
                        long end = System.nanoTime();
                        fw.write(String.format("%d,QuickSort,%d,%.3f,%d,%d,%d\n",
                                t, n, (end - start) / 1e6,
                                obs.getComparisons(), obs.getAllocations(), obs.getMaxDepth()));
                    }

                    // --- Select (Median of Medians) ---
                    {
                        int[] arr = base.clone();
                        MetricsObserver obs = new MetricsObserver();
                        int k = n / 2; // median
                        long start = System.nanoTime();
                        SelectMoM.select(arr, k, obs);        
                        long end = System.nanoTime();
                        fw.write(String.format("%d,SelectMoM,%d,%.3f,%d,%d,%d\n",
                                t, n, (end - start) / 1e6,
                                obs.getComparisons(), obs.getAllocations(), obs.getMaxDepth()));
                    }

                
                    {
                        // build Point[] as your ClosestPair expects Point[]
                        Point[] pts = new Point[n];
                        for (int i = 0; i < n; i++) {
                            pts[i] = new Point(R.nextDouble() * 10000, R.nextDouble() * 10000);
                        }
                        MetricsObserver obs = new MetricsObserver();
                        ClosestPair cp = new ClosestPair(obs);  
                        long start = System.nanoTime();
                        cp.findClosest(pts);
                        long end = System.nanoTime();
                        fw.write(String.format("%d,ClosestPair,%d,%.3f,%d,%d,%d\n",
                                t, n, (end - start) / 1e6,
                                obs.getComparisons(), obs.getAllocations(), obs.getMaxDepth()));
                    }

                    
                    if (t % 10 == 0) fw.flush();
                }
                System.out.println("Finished n = " + n);
            }

            fw.flush();
        }

        System.out.println("All benchmarks finished. results.csv created.");
    }
}
