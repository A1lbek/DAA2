package algorithms;

import models.SubarrayResult;
import metrics.PerformanceTracker;

/**
 * Implementation of Kadane's Algorithm for finding maximum subarray sum
 * with position tracking and comprehensive metrics collection.
 *
 * Time Complexity: Θ(n), O(n), Ω(n)
 * Space Complexity: O(1) auxiliary space
 */
public class KadaneAlgorithm {
    private int comparisons;
    private int arrayAccesses;
    private PerformanceTracker performanceTracker;

    public KadaneAlgorithm() {
        this.performanceTracker = new PerformanceTracker("KadaneAlgorithm");
    }

    /**
     * Finds the contiguous subarray with maximum sum
     */
    public SubarrayResult findMaximumSubarray(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }
        if (nums.length == 0) {
            return new SubarrayResult(0, -1, -1, 0, 0);
        }

        comparisons = 0;
        arrayAccesses = 0;

        long startTime = System.nanoTime();

        int maxSoFar = nums[0];
        int maxEndingHere = nums[0];
        int start = 0;
        int end = 0;
        int tempStart = 0;

        arrayAccesses += 2;

        for (int i = 1; i < nums.length; i++) {
            arrayAccesses++;
            comparisons++;

            if (maxEndingHere + nums[i] < nums[i]) {
                maxEndingHere = nums[i];
                tempStart = i;
            } else {
                maxEndingHere += nums[i];
            }

            arrayAccesses++;
            comparisons++;
            if (maxEndingHere > maxSoFar) {
                maxSoFar = maxEndingHere;
                start = tempStart;
                end = i;
            }
        }

        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;

        performanceTracker.recordExecution(nums.length, executionTime, comparisons,
                arrayAccesses, 0, "standard");

        return new SubarrayResult(maxSoFar, start, end, comparisons, arrayAccesses);
    }

    /**
     * Enhanced version with detailed metrics collection
     */
    public SubarrayResult findMaximumSubarrayWithMetrics(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }
        if (nums.length == 0) {
            return new SubarrayResult(0, -1, -1, 0, 0);
        }

        comparisons = 0;
        arrayAccesses = 0;

        long startTime = System.nanoTime();

        int maxSoFar = nums[0];
        int maxEndingHere = nums[0];
        int start = 0;
        int end = 0;
        int tempStart = 0;

        arrayAccesses += 2;

        for (int i = 1; i < nums.length; i++) {
            arrayAccesses++;
            comparisons++;

            int candidate1 = maxEndingHere + nums[i];
            int candidate2 = nums[i];
            arrayAccesses += 2;

            if (candidate1 < candidate2) {
                maxEndingHere = candidate2;
                tempStart = i;
            } else {
                maxEndingHere = candidate1;
            }

            arrayAccesses++;
            comparisons++;
            if (maxEndingHere > maxSoFar) {
                maxSoFar = maxEndingHere;
                start = tempStart;
                end = i;
            }
        }

        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;

        performanceTracker.recordExecution(nums.length, executionTime, comparisons,
                arrayAccesses, 0, "detailed");

        return new SubarrayResult(maxSoFar, start, end, comparisons, arrayAccesses);
    }

    /**
     * Alternative implementation that handles all-negative arrays differently
     */
    public SubarrayResult findMaximumSubarrayAllowEmpty(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new SubarrayResult(0, -1, -1, 0, 0);
        }

        comparisons = 0;
        arrayAccesses = 0;

        int maxSoFar = 0;
        int maxEndingHere = 0;
        int start = -1;
        int end = -1;
        int tempStart = 0;

        for (int i = 0; i < nums.length; i++) {
            arrayAccesses++;
            comparisons++;

            if (maxEndingHere + nums[i] <= 0) {
                maxEndingHere = 0;
                tempStart = i + 1;
            } else {
                maxEndingHere += nums[i];
            }

            arrayAccesses++;
            comparisons++;
            if (maxEndingHere > maxSoFar) {
                maxSoFar = maxEndingHere;
                start = tempStart;
                end = i;
            }
        }

        return new SubarrayResult(maxSoFar, start, end, comparisons, arrayAccesses);
    }

    /**
     * Returns performance summary for the last execution
     */
    public String getPerformanceSummary() {
        return String.format("Comparisons: %,d | Array Accesses: %,d | Operations per element: %.2f",
                comparisons, arrayAccesses,
                (double)(comparisons + arrayAccesses) / (comparisons > 0 ? comparisons / 2 + 1 : 1));
    }

    public PerformanceTracker getPerformanceTracker() {
        return performanceTracker;
    }

    public int getLastComparisons() {
        return comparisons;
    }

    public int getLastArrayAccesses() {
        return arrayAccesses;
    }
}