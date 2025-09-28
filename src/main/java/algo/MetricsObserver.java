package algo;

public class MetricsObserver  {
    public long comparisons = 0;
    public long allocations = 0;
    public int maxDepth = 0;

    public void onComparison() {
        comparisons++;
    }

    public void onAllocation() {
        allocations++;
    }

    public void onRecursionDepth(int depth) {
        maxDepth = Math.max(maxDepth, depth);
    }
}
