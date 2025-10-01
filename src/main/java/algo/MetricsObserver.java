package algo;

public class MetricsObserver {
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
