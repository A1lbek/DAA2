package cli;

import algorithms.KadaneAlgorithm;
import models.SubarrayResult;
import metrics.PerformanceTracker;

import java.util.Random;
import java.util.Scanner;

/**
 * Benchmark runner with basic functionality
 */
public class BenchmarkRunner {
    private final KadaneAlgorithm kadane;
    private final Scanner scanner;
    private final Random random;

    public BenchmarkRunner() {
        this.kadane = new KadaneAlgorithm();
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    public void run() {
        System.out.println("=== Kadane's Algorithm Benchmark Runner ===");

        while (true) {
            printMenu();
            int choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1:
                    testWithCustomInput();
                    break;
                case 2:
                    runPredefinedTests();
                    break;
                case 3:
                    runQuickBenchmark();
                    break;
                case 4:
                    compareWithNaive();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Benchmark Menu ---");
        System.out.println("1. Test with custom input");
        System.out.println("2. Run predefined tests");
        System.out.println("3. Run quick benchmark");
        System.out.println("4. Compare with naive O(n²) algorithm");
        System.out.println("5. Exit");
    }

    private void testWithCustomInput() {
        System.out.println("\n--- Custom Input Test ---");
        System.out.println("Enter array elements separated by spaces:");

        scanner.nextLine();
        String input = scanner.nextLine();
        String[] elements = input.split("\\s+");

        int[] nums = new int[elements.length];
        for (int i = 0; i < elements.length; i++) {
            nums[i] = Integer.parseInt(elements[i]);
        }

        executeAndPrintResult(nums);
    }

    private void runPredefinedTests() {
        System.out.println("\n--- Predefined Tests ---");

        int[][] testCases = {
                {},
                {5},
                {-3},
                {1, 2, 3, 4, 5},
                {-1, -2, -3, -4, -5},
                {-2, 1, -3, 4, -1, 2, 1, -5, 4}
        };

        String[] descriptions = {
                "Empty array",
                "Single positive element",
                "Single negative element",
                "All positive elements",
                "All negative elements",
                "Standard case from example"
        };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("\nTest " + (i + 1) + ": " + descriptions[i]);
            System.out.print("Array: ");
            printArray(testCases[i]);
            executeAndPrintResult(testCases[i]);
        }
    }

    private void runQuickBenchmark() {
        System.out.println("\n--- Quick Benchmark ---");

        int[] sizes = {100, 1000, 5000};
        int iterations = 2;

        for (int size : sizes) {
            System.out.printf("%n--- Testing size: %,d ---%n", size);

            long totalTime = 0;
            int totalComparisons = 0;
            int totalAccesses = 0;

            for (int i = 0; i < iterations; i++) {
                int[] nums = generateRandomArray(size, -100, 100);

                long startTime = System.nanoTime();
                SubarrayResult result = kadane.findMaximumSubarray(nums);
                long endTime = System.nanoTime();

                long executionTime = endTime - startTime;
                totalTime += executionTime;
                totalComparisons += result.getComparisons();
                totalAccesses += result.getArrayAccesses();
            }

            long avgTime = totalTime / iterations;
            int avgComparisons = totalComparisons / iterations;
            int avgAccesses = totalAccesses / iterations;

            System.out.printf("Average Time: %,d ns%n", avgTime);
            System.out.printf("Average Comparisons: %,d%n", avgComparisons);
            System.out.printf("Average Array Accesses: %,d%n", avgAccesses);
            System.out.printf("Time per element: %.2f ns%n", (double)avgTime / size);
        }
    }

    private void compareWithNaive() {
        System.out.println("\n--- Comparison with Naive O(n²) Algorithm ---");

        int[] sizes = {100, 500, 1000};

        for (int size : sizes) {
            System.out.printf("%n--- Array size: %,d ---%n", size);
            int[] nums = generateRandomArray(size, -100, 100);

            // Time Kadane's algorithm
            long startTime = System.nanoTime();
            SubarrayResult kadaneResult = kadane.findMaximumSubarray(nums);
            long kadaneTime = System.nanoTime() - startTime;

            // Time naive algorithm (for smaller sizes)
            if (size <= 1000) {
                startTime = System.nanoTime();
                SubarrayResult naiveResult = naiveMaximumSubarray(nums);
                long naiveTime = System.nanoTime() - startTime;

                System.out.printf("Kadane: %,d ns | Naive: %,d ns | Speedup: %.1fx%n",
                        kadaneTime, naiveTime, (double)naiveTime / kadaneTime);
            }
        }
    }

    private SubarrayResult naiveMaximumSubarray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new SubarrayResult(0, -1, -1, 0, 0);
        }

        int maxSum = nums[0];
        int start = 0;
        int end = 0;
        int comparisons = 0;
        int accesses = 0;

        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];
                accesses++;
                comparisons++;
                if (currentSum > maxSum) {
                    maxSum = currentSum;
                    start = i;
                    end = j;
                }
            }
        }

        return new SubarrayResult(maxSum, start, end, comparisons, accesses);
    }

    private void executeAndPrintResult(int[] nums) {
        try {
            long startTime = System.nanoTime();
            SubarrayResult result = kadane.findMaximumSubarray(nums);
            long endTime = System.nanoTime();

            long executionTime = endTime - startTime;

            System.out.println("Result: " + result);
            System.out.printf("Execution Time: %,d ns%n", executionTime);

            if (!result.isEmptySubarray()) {
                System.out.print("Maximum Subarray: [");
                for (int i = result.getStartIndex(); i <= result.getEndIndex(); i++) {
                    System.out.print(nums[i]);
                    if (i < result.getEndIndex()) System.out.print(", ");
                }
                System.out.println("]");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int[] generateRandomArray(int size, int min, int max) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(max - min + 1) + min;
        }
        return array;
    }

    private void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        BenchmarkRunner runner = new BenchmarkRunner();
        runner.run();
    }
}