import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Let's generate some percolation stats
 */
public class PercolationStats {

    private double[] thresholds;

    /**
     * perform independent trials on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        this.thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            this.runToPercolate(p, n, i);
        }
    }

    /**
     * Run this percolation until it percolates
     * 
     * @param p
     */
    private void runToPercolate(Percolation p, int n, int trialNumber) {
        int threshold = 0;
        while (!p.percolates()) {
            int row = StdRandom.uniformInt(n) + 1;
            int col = StdRandom.uniformInt(n) + 1;
            p.open(row, col);
            threshold++;
        }

        this.thresholds[trialNumber] = threshold;
    }

    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(this.thresholds);
    }

    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(this.thresholds);
    }

    /**
     * low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return 0.0;
    }

    /**
     * high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return 0.0;
    }

    /**
     * test client (see below)
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.mean());
    }

}