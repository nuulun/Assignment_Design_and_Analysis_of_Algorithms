package algo;

public class MetricsObserver {
    private long comparisons = 0;
    private long allocations = 0;
    private int maxDepth = 0;

    public void onComparison() {
        comparisons++;
    }

    public void onAllocation() {
        allocations++;
    }

    public void onRecursionDepth(int depth) {
        maxDepth = Math.max(maxDepth, depth);
    }


    public long getComparisons() {
        return comparisons;
    }

    public long getAllocations() {
        return allocations;
    }

    public int getMaxDepth() {
        return maxDepth;
    }


    public void reset() {
        comparisons = 0;
        allocations = 0;
        maxDepth = 0;
    }
}
