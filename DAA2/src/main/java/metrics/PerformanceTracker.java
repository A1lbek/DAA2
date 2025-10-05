package metrics;

import algorithms.KadaneAlgorithm;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced performance tracker with statistical analysis and JMH-style benchmarking
 */
public class PerformanceTracker {
    private final String algorithmName;
    private final List<PerformanceRecord> records;
    private final List<BenchmarkResult> benchmarkResults;

    public PerformanceTracker(String algorithmName) {
        this.algorithmName = algorithmName;
        this.records = new ArrayList<>();
        this.benchmarkResults = new ArrayList<>();
    }

    public static class PerformanceRecord {
        public final int inputSize;
        public final long executionTimeNs;
        public final int comparisons;
        public final int arrayAccesses;
        public final long memoryUsed;
        public final String testCase;
        public final long timestamp;

        public PerformanceRecord(int inputSize, long executionTimeNs,
                                 int comparisons, int arrayAccesses,
                                 long memoryUsed, String testCase) {
            this.inputSize = inputSize;
            this.executionTimeNs = executionTimeNs;
            this.comparisons = comparisons;
            this.arrayAccesses = arrayAccesses;
            this.memoryUsed = memoryUsed;
            this.testCase = testCase;
            this.timestamp = System.currentTimeMillis();
        }
    }

    public static class BenchmarkResult {
        public final int inputSize;
        public final double avgTimeNs;
        public final double stdDevTimeNs;
        public final int minTimeNs;
        public final int maxTimeNs;
        public final double avgComparisons;
        public final double avgArrayAccesses;
        public final int iterations;
        public final String inputType;

        public BenchmarkResult(int inputSize, double avgTimeNs, double stdDevTimeNs,
                               int minTimeNs, int maxTimeNs, double avgComparisons,
                               double avgArrayAccesses, int iterations, String inputType) {
            this.inputSize = inputSize;
            this.avgTimeNs = avgTimeNs;
            this.stdDevTimeNs = stdDevTimeNs;
            this.minTimeNs = minTimeNs;
            this.maxTimeNs = maxTimeNs;
            this.avgComparisons = avgComparisons;
            this.avgArrayAccesses = avgArrayAccesses;
            this.iterations = iterations;
            this.inputType = inputType;
        }
    }

    public void recordExecution(int inputSize, long executionTimeNs,
                                int comparisons, int arrayAccesses,
                                long memoryUsed, String testCase) {
        records.add(new PerformanceRecord(inputSize, executionTimeNs, comparisons,
                arrayAccesses, memoryUsed, testCase));
    }

    public void addBenchmarkResult(BenchmarkResult result) {
        benchmarkResults.add(result);
    }

    public void runComprehensiveBenchmark(int[] sizes, int warmupIterations, int measurementIterations) {
        System.out.println("\n=== Running Comprehensive Benchmark ===");

        for (int size : sizes) {
            System.out.printf("\nBenchmarking size: %,d%n", size);

            // Warmup phase
            System.out.print("Warmup: ");
            for (int i = 0; i < warmupIterations; i++) {
                int[] nums = generateRandomArray(size, -1000, 1000);
                performGarbageCollection();
                long startTime = System.nanoTime();
                // Simulate algorithm execution
                KadaneAlgorithm kadane = new KadaneAlgorithm();
                kadane.findMaximumSubarray(nums);
                long endTime = System.nanoTime();
                if (i % 5 == 0) System.out.print(".");
            }
            System.out.println(" Done");

            // Measurement phase
            long[] times = new long[measurementIterations];
            int[] comparisons = new int[measurementIterations];
            int[] accesses = new int[measurementIterations];

            System.out.print("Measurement: ");
            for (int i = 0; i < measurementIterations; i++) {
                int[] nums = generateRandomArray(size, -1000, 1000);
                performGarbageCollection();

                Runtime runtime = Runtime.getRuntime();
                long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

                long startTime = System.nanoTime();
                KadaneAlgorithm kadane = new KadaneAlgorithm();
                kadane.findMaximumSubarray(nums);
                long endTime = System.nanoTime();

                long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
                long memoryUsed = memoryAfter - memoryBefore;

                times[i] = endTime - startTime;
                comparisons[i] = kadane.getLastComparisons();
                accesses[i] = kadane.getLastArrayAccesses();

                recordExecution(size, times[i], comparisons[i], accesses[i],
                        memoryUsed, "random");

                if (i % 5 == 0) System.out.print(".");
            }
            System.out.println(" Done");

            // Calculate statistics
            BenchmarkResult result = calculateStatistics(size, times, comparisons, accesses,
                    "random", measurementIterations);
            addBenchmarkResult(result);
            printBenchmarkResult(result);
        }
    }

    private BenchmarkResult calculateStatistics(int size, long[] times, int[] comparisons,
                                                int[] accesses, String inputType, int iterations) {
        // Time statistics
        double avgTime = calculateAverage(times);
        double stdDevTime = calculateStandardDeviation(times, avgTime);
        long minTime = findMin(times);
        long maxTime = findMax(times);

        // Operation statistics
        double avgComparisons = calculateAverage(comparisons);
        double avgAccesses = calculateAverage(accesses);

        return new BenchmarkResult(size, avgTime, stdDevTime, (int)minTime, (int)maxTime,
                avgComparisons, avgAccesses, iterations, inputType);
    }

    private double calculateAverage(long[] values) {
        long sum = 0;
        for (long value : values) {
            sum += value;
        }
        return (double) sum / values.length;
    }

    private double calculateAverage(int[] values) {
        long sum = 0;
        for (int value : values) {
            sum += value;
        }
        return (double) sum / values.length;
    }

    private double calculateStandardDeviation(long[] values, double mean) {
        double sumSquaredDiffs = 0;
        for (long value : values) {
            double diff = value - mean;
            sumSquaredDiffs += diff * diff;
        }
        return Math.sqrt(sumSquaredDiffs / values.length);
    }

    private long findMin(long[] values) {
        long min = Long.MAX_VALUE;
        for (long value : values) {
            if (value < min) min = value;
        }
        return min;
    }

    private long findMax(long[] values) {
        long max = Long.MIN_VALUE;
        for (long value : values) {
            if (value > max) max = value;
        }
        return max;
    }

    private int[] generateRandomArray(int size, int min, int max) {
        int[] array = new int[size];
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(max - min + 1) + min;
        }
        return array;
    }

    private void performGarbageCollection() {
        System.gc();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void printBenchmarkResult(BenchmarkResult result) {
        System.out.printf("%n=== Benchmark Results (n=%,d) ===%n", result.inputSize);
        System.out.printf("Time: %.2f ns ± %.2f ns (min: %,d, max: %,d)%n",
                result.avgTimeNs, result.stdDevTimeNs, result.minTimeNs, result.maxTimeNs);
        System.out.printf("Operations: %.1f comparisons, %.1f array accesses%n",
                result.avgComparisons, result.avgArrayAccesses);
        System.out.printf("Iterations: %d, Input Type: %s%n",
                result.iterations, result.inputType);

        // Calculate operations per element
        double timePerElement = result.avgTimeNs / result.inputSize;
        double comparisonsPerElement = result.avgComparisons / result.inputSize;
        double accessesPerElement = result.avgArrayAccesses / result.inputSize;

        System.out.printf("Per Element: %.3f ns, %.2f comparisons, %.2f accesses%n",
                timePerElement, comparisonsPerElement, accessesPerElement);
    }

    public void exportBenchmarkResults(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Write benchmark results header
            writer.write("algorithm,input_size,avg_time_ns,std_dev_ns,min_time_ns,max_time_ns," +
                    "avg_comparisons,avg_array_accesses,iterations,input_type\n");

            // Write benchmark data
            for (BenchmarkResult result : benchmarkResults) {
                writer.write(String.format("%s,%d,%.2f,%.2f,%d,%d,%.2f,%.2f,%d,%s\n",
                        algorithmName,
                        result.inputSize,
                        result.avgTimeNs,
                        result.stdDevTimeNs,
                        result.minTimeNs,
                        result.maxTimeNs,
                        result.avgComparisons,
                        result.avgArrayAccesses,
                        result.iterations,
                        result.inputType
                ));
            }

            System.out.println("Benchmark results exported to: " + filename);

        } catch (IOException e) {
            System.err.println("Error exporting benchmark results: " + e.getMessage());
        }
    }

    public void generateComplexityAnalysis() {
        System.out.println("\n=== Complexity Analysis ===");

        if (benchmarkResults.isEmpty()) {
            System.out.println("No benchmark data available for analysis.");
            return;
        }

        // Calculate time complexity coefficients
        System.out.printf("%-12s %-15s %-12s %-15s%n",
                "Input Size", "Avg Time (ns)", "Time/n", "(Time/n)/log(n)");
        System.out.println("--------------------------------------------------------");

        for (BenchmarkResult result : benchmarkResults) {
            double timePerElement = result.avgTimeNs / result.inputSize;
            double normalizedTime = timePerElement / Math.log(result.inputSize);

            System.out.printf("%-12d %-15.0f %-12.3f %-15.6f%n",
                    result.inputSize,
                    result.avgTimeNs,
                    timePerElement,
                    normalizedTime
            );
        }

        // Verify linear complexity
        verifyLinearComplexity();
    }

    private void verifyLinearComplexity() {
        if (benchmarkResults.size() < 2) return;

        System.out.println("\n=== Linear Complexity Verification ===");

        double[] sizes = new double[benchmarkResults.size()];
        double[] times = new double[benchmarkResults.size()];

        for (int i = 0; i < benchmarkResults.size(); i++) {
            BenchmarkResult result = benchmarkResults.get(i);
            sizes[i] = result.inputSize;
            times[i] = result.avgTimeNs;
        }

        // Calculate correlation coefficient
        double correlation = calculateCorrelation(sizes, times);
        System.out.printf("Time vs Size Correlation: %.6f%n", correlation);

        if (correlation > 0.99) {
            System.out.println("✓ Strong linear relationship confirmed (O(n))");
        } else if (correlation > 0.95) {
            System.out.println("~ Good linear relationship (approximately O(n))");
        } else {
            System.out.println("? Weak linear relationship - investigate further");
        }
    }

    private double calculateCorrelation(double[] x, double[] y) {
        if (x.length != y.length || x.length == 0) return 0;

        double sumX = 0, sumY = 0, sumXY = 0;
        double sumX2 = 0, sumY2 = 0;
        int n = x.length;

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumX2 += x[i] * x[i];
            sumY2 += y[i] * y[i];
        }

        double numerator = n * sumXY - sumX * sumY;
        double denominator = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));

        return denominator == 0 ? 0 : numerator / denominator;
    }

    public void clear() {
        records.clear();
        benchmarkResults.clear();
    }

    // Getters
    public List<PerformanceRecord> getRecords() { return new ArrayList<>(records); }
    public List<BenchmarkResult> getBenchmarkResults() { return new ArrayList<>(benchmarkResults); }
    public String getAlgorithmName() { return algorithmName; }
}