**Kadane's Algorithm**

Done by: Albek Gusmanov SE-2425

**Project Overview**

This project implements Kadane's Algorithm with the following features:

- **O(n) time complexity** - single-pass algorithm
- **O(1) space complexity** - constant auxiliary space
- **Position tracking** - identifies exact subarray boundaries
- **Performance metrics** - real-time tracking of comparisons and array accesses
- **Automated reporting** - comprehensive test results and benchmarks
- **Interactive CLI** - user-friendly testing interface

**Algorithm Description**

The algorithm works by maintaining two variables:

1. **maxEndingHere**: Maximum sum ending at current position
1. **maxSoFar**: Overall maximum sum found so far

For each element, we decide whether to extend the previous subarray or start a new one from the current element.

**Complexity Analysis**

- **Time Complexity**: Θ(n), O(n), Ω(n) - Single pass through the array
- **Space Complexity**: Θ(1), O(1), Ω(1) - Uses only constant extra space
- **Comparisons**: ~2n comparisons in worst case
- **Array Accesses**: ~3n array accesses in worst case
- **Memory**: O(1) auxiliary space (only stores sum and index variables)

**Features**

- Clean, documented Java implementation (Java 11+)
- Comprehensive unit tests with JUnit 5
- Performance metrics collection (comparisons, array accesses, timing, memory)
- CLI interface for benchmarking with configurable input sizes
- Automated report generation with comprehensive analysis
- Multiple implementations (standard, empty-subarray-allowed, detailed metrics)
- Enhanced edge case handling
- Input validation and error handling
- Position tracking for subarray boundaries
- Statistical performance analysis
- Comparison with naive O(n²) algorithm
- CSV export of benchmark results for analysis

**Build and Run**

**Prerequisites**

- Java 11+
- Maven 3.6+

**Building**

mvn clean compile

**Running Tests**

**Comprehensive Unit Tests**

mvn test

**Interactive Testing with CLI**

mvn exec:java

**Run Specific Test Scenarios**

**# Run only predefined tests**

mvn exec:java -Dexec.mainClass="cli.BenchmarkRunner" -Dexec.args="2"

\# Run performance benchmarks

mvn exec:java -Dexec.mainClass="cli.BenchmarkRunner" -Dexec.args="3"

**Performance Benchmarking**

**Quick Benchmark** 

mvn exec:java -Dexec.mainClass="main.Main"

**Comprehensive Benchmark with Reports**

mvn exec:java -Dexec.mainClass="utils.ReportGenerator"

**Compare with Naive O(n²) Algorithm**

mvn exec:java -Dexec.mainClass="cli.BenchmarkRunner" -Dexec.args="6"

**Usage Examples**

**Basic Usage**

KadaneAlgorithm kadane = new KadaneAlgorithm();

int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

SubarrayResult result = kadane.findMaximumSubarray(nums); 

// Returns: Max Sum: 6 | Subarray [3:6] | Length: 4

**With Performance Tracking**

KadaneAlgorithm kadane = new KadaneAlgorithm();

int[] nums = {1, -2, 3, -4, 5, -6, 7};

SubarrayResult result = kadane.findMaximumSubarray(nums);

System.out.println(kadane.getPerformanceSummary());

// Output: Comparisons: 12 | Array Accesses: 18 | Operations per element: 2.50

**Allow Empty Subarray (Returns 0 for all-negative arrays)**

SubarrayResult result = kadane.findMaximumSubarrayAllowEmpty(nums);

// Returns 0 for arrays like [-1, -2, -3]

**With Detailed Metrics**

SubarrayResult result = kadane.findMaximumSubarrayWithMetrics(nums);

System.out.println(result);

// Output with comprehensive metrics

**Testing Coverage**

**Edge Cases Handled**

- Empty arrays (returns sum 0, empty subarray)
- Single element arrays (positive/negative)
- All positive elements (returns entire array)
- All negative elements (returns least negative element)
- Arrays with zeros
- Multiple maximum subarrays
- Null input validation
- Mixed positive/negative numbers

**Test Scenarios Verified**

- **Standard Case**: [-2, 1, -3, 4, -1, 2, 1, -5, 4] → sum 6, [3:6]
- **All Positive**: [1, 2, 3, 4, 5] → sum 15, [0:4]
- **All Negative**: [-5, -4, -3, -2, -1] → sum -1, [4:4]
- **Single Element**: [5] → sum 5, [0:0]
- **Empty Array**: [] → sum 0, empty subarray

**Input Distributions Tested**

- **Random**: Randomly distributed positive/negative numbers
- **All Positive**: Maximum sum is sum of all elements
- **All Negative**: Maximum sum is the least negative element
- **Alternating**: Positive and negative numbers alternating
- **Edge Cases**: Empty, single element, all zeros

**Performance Characteristics**

**Theoretical Analysis**

- **Best Case**: Θ(n) - consistent performance regardless of input
- **Worst Case**: Θ(n) - always makes exactly one pass
- **Average Case**: Θ(n) - predictable linear performance
- **Space**: Always O(1) - only stores current and max sums with indices

**Empirical Validation**

The benchmarking system validates:

- **Linear time complexity** through correlation analysis
- **Constant space usage** via memory tracking
- **Consistent performance** across different input distributions
- **Theoretical vs practical alignment** with statistical analysis

**Benchmark Metrics Collected**

- **Execution Time**: Nanosecond precision timing
- **Comparisons**: Number of element comparisons
- **Array Accesses**: Memory access patterns
- **Memory Usage**: Heap usage tracking
- **Statistical Analysis**: Mean, standard deviation, correlations

**Output Files**

**Generated Reports (Auto-created on startup)**

- reports/TIMESTAMP/TEST\_RESULTS.md - Detailed test execution results
- reports/TIMESTAMP/PERFORMANCE\_REPORT.md - Performance analysis and metrics
- reports/TIMESTAMP/BENCHMARK\_RESULTS.md - Comprehensive benchmarking data
- reports/TIMESTAMP/COMPLEXITY\_ANALYSIS.md - Theoretical and empirical complexity analysis
- reports/TIMESTAMP/PROJECT\_SUMMARY.md - Implementation status and features
- reports/TIMESTAMP/README\_WITH\_RESULTS.md - Complete documentation with results

**CSV Export Format**

algorithm,input\_size,avg\_time\_ns,std\_dev\_ns,min\_time\_ns,max\_time\_ns,avg\_comparisons,avg\_array\_accesses,iterations,input\_type

KadaneAlgorithm,100,1234,45,1189,1300,198,297,10,random

KadaneAlgorithm,1000,12567,234,12300,13100,1998,2997,10,random

**License**

Educational Use - Algorithm Analysis Assignment






