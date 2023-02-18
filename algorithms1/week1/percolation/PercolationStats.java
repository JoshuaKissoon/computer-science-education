import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private Percolation[] percolations;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.percolations = new Percolation[trials];

        for (int i = 0; i < n; i++) {
            Percolation p = new Percolation(n);
            this.runToPercolate(p, n);
            this.percolations[i] = p;
        }
    }

    /**
     * Run this percolation until it percolates
     * 
     * @param p
     */
    private void runToPercolate(Percolation p, int n) {
        while (!p.percolates()) {
            int row = StdRandom.uniformInt(n) + 1;
            int col = StdRandom.uniformInt(n) + 1;
            p.open(row, col);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return 0.0;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0.0;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0.0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0.0;
    }

    // test client (see below)
    public static void main(String[] args) {

    }

}