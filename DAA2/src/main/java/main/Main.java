package main;

import cli.BenchmarkRunner;
import utils.ReportGenerator;

/**
 * Main class with automatic report generation on startup
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("🎯 Kadane's Algorithm Project - Starting...");
        System.out.println("📊 Generating comprehensive reports...");

        // Generate reports automatically
        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.generateAllReports();

        System.out.println("🚀 Starting interactive CLI...");

        // Start interactive CLI
        BenchmarkRunner runner = new BenchmarkRunner();
        runner.run();
    }
}