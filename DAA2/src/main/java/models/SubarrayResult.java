package models;

/**
 * Represents the result of maximum subarray search
 * Contains the maximum sum, subarray boundaries, and performance metrics
 */
public class SubarrayResult {
    private final int maxSum;
    private final int startIndex;
    private final int endIndex;
    private final int comparisons;
    private final int arrayAccesses;

    public SubarrayResult(int maxSum, int startIndex, int endIndex,
                          int comparisons, int arrayAccesses) {
        this.maxSum = maxSum;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.comparisons = comparisons;
        this.arrayAccesses = arrayAccesses;
    }

    // Getters
    public int getMaxSum() { return maxSum; }
    public int getStartIndex() { return startIndex; }
    public int getEndIndex() { return endIndex; }
    public int getComparisons() { return comparisons; }
    public int getArrayAccesses() { return arrayAccesses; }

    /**
     * @return Length of the maximum subarray
     */
    public int getSubarrayLength() {
        if (startIndex == -1 || endIndex == -1) return 0;
        return endIndex - startIndex + 1;
    }

    /**
     * @return true if no valid subarray was found (empty array or all negative with empty allowed)
     */
    public boolean isEmptySubarray() {
        return startIndex == -1 || endIndex == -1;
    }

    @Override
    public String toString() {
        if (isEmptySubarray()) {
            return String.format("Max Sum: %d (Empty subarray) | Comparisons: %d | Array Accesses: %d",
                    maxSum, comparisons, arrayAccesses);
        }
        return String.format("Max Sum: %d | Subarray [%d:%d] | Length: %d | Comparisons: %d | Array Accesses: %d",
                maxSum, startIndex, endIndex, getSubarrayLength(), comparisons, arrayAccesses);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        SubarrayResult that = (SubarrayResult) obj;
        return maxSum == that.maxSum &&
                startIndex == that.startIndex &&
                endIndex == that.endIndex;
    }
}