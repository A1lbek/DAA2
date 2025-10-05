package algorithms;

import models.SubarrayResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for Kadane's Algorithm implementation
 */
class KadaneAlgorithmTest {
    private KadaneAlgorithm kadane;

    @BeforeEach
    void setUp() {
        kadane = new KadaneAlgorithm();
    }

    @Test
    void testEmptyArray() {
        int[] nums = {};
        SubarrayResult result = kadane.findMaximumSubarray(nums);

        assertEquals(0, result.getMaxSum());
        assertEquals(-1, result.getStartIndex());
        assertEquals(-1, result.getEndIndex());
        assertTrue(result.isEmptySubarray());
    }

    @Test
    void testSinglePositiveElement() {
        int[] nums = {5};
        SubarrayResult result = kadane.findMaximumSubarray(nums);

        assertEquals(5, result.getMaxSum());
        assertEquals(0, result.getStartIndex());
        assertEquals(0, result.getEndIndex());
        assertEquals(1, result.getSubarrayLength());
    }

    @Test
    void testSingleNegativeElement() {
        int[] nums = {-3};
        SubarrayResult result = kadane.findMaximumSubarray(nums);

        assertEquals(-3, result.getMaxSum());
        assertEquals(0, result.getStartIndex());
        assertEquals(0, result.getEndIndex());
    }

    @Test
    void testAllPositiveElements() {
        int[] nums = {1, 2, 3, 4, 5};
        SubarrayResult result = kadane.findMaximumSubarray(nums);

        assertEquals(15, result.getMaxSum()); // Sum of all elements
        assertEquals(0, result.getStartIndex());
        assertEquals(4, result.getEndIndex());
    }

    @Test
    void testAllNegativeElements() {
        int[] nums = {-5, -4, -3, -2, -1};
        SubarrayResult result = kadane.findMaximumSubarray(nums);

        // Should return the least negative element
        assertEquals(-1, result.getMaxSum());
        assertEquals(4, result.getStartIndex());
        assertEquals(4, result.getEndIndex());
    }

    @Test
    void testStandardCase() {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        SubarrayResult result = kadane.findMaximumSubarray(nums);

        assertEquals(6, result.getMaxSum()); // [4, -1, 2, 1]
        assertEquals(3, result.getStartIndex());
        assertEquals(6, result.getEndIndex());
    }

    @Test
    void testAlternatingPositiveNegative() {
        int[] nums = {1, -2, 3, -4, 5, -6, 7};
        SubarrayResult result = kadane.findMaximumSubarray(nums);

        assertEquals(7, result.getMaxSum()); // [7]
        assertEquals(6, result.getStartIndex());
        assertEquals(6, result.getEndIndex());
    }

    @Test
    void testArrayWithZero() {
        int[] nums = {-1, -2, -3, 0, -4, -5};
        SubarrayResult result = kadane.findMaximumSubarray(nums);

        assertEquals(0, result.getMaxSum()); // [0]
        assertEquals(3, result.getStartIndex());
        assertEquals(3, result.getEndIndex());
    }

    @Test
    void testMultipleMaximumSubarrays() {
        int[] nums = {2, -1, 2, -1, 2, -1, 2};
        SubarrayResult result = kadane.findMaximumSubarray(nums);

        // Should find the entire array as maximum
        assertEquals(5, result.getMaxSum()); // 2-1+2-1+2-1+2 = 5
        assertEquals(0, result.getStartIndex());
        assertEquals(6, result.getEndIndex());
    }

    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            kadane.findMaximumSubarray(null);
        });
    }

    @Test
    void testAllowEmptySubarray() {
        int[] nums = {-5, -3, -8, -2};
        SubarrayResult result = kadane.findMaximumSubarrayAllowEmpty(nums);

        assertEquals(0, result.getMaxSum()); // Empty subarray
        assertEquals(-1, result.getStartIndex());
        assertEquals(-1, result.getEndIndex());
        assertTrue(result.isEmptySubarray());
    }

    @Test
    void testPerformanceMetrics() {
        int[] nums = {1, -2, 3, -4, 5, -6, 7};
        SubarrayResult result = kadane.findMaximumSubarray(nums);

        // Should perform exactly n-1 comparisons for main loop
        assertTrue(result.getComparisons() > 0);
        assertTrue(result.getArrayAccesses() > 0);

        // For array of length 7, we expect approximately 2*(n-1) comparisons
        // and 3*(n-1) array accesses
        int expectedMinComparisons = nums.length - 1;
        assertTrue(result.getComparisons() >= expectedMinComparisons);
    }

    @Test
    void testResultEquality() {
        int[] nums1 = {1, 2, 3};
        int[] nums2 = {1, 2, 3};

        SubarrayResult result1 = kadane.findMaximumSubarray(nums1);
        SubarrayResult result2 = kadane.findMaximumSubarray(nums2);

        assertEquals(result1, result2);
    }
}