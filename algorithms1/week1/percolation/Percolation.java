import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Class that provides functions for percolation
 */
public class Percolation {

    /**
     * An array to store open & closed sites
     * - -1: Closed
     * - 1: Open
     */
    private byte[] matrix;
    private int n;
    private WeightedQuickUnionUF ufPerc;
    private WeightedQuickUnionUF ufWithoutBackwash;
    private int top; // Artificial cell at the top
    private int bottom; // Artificial cell at the bottom
    private int openSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        int size = n * n;

        this.ufPerc = new WeightedQuickUnionUF(size + 2);
        this.ufWithoutBackwash = new WeightedQuickUnionUF(size + 2);
        this.top = n * n;
        this.bottom = n * n + 1;

        this.matrix = new byte[size];

        /* Let's initialize the grid */
        for (int i = 0; i < size; i++) {
            this.matrix[i] = -1;
        }
    }

    /**
     * opens the site (row, col) if it is not open already
     * 
     * We'd do this by setting the site value to
     * - the value of it's open neighbor with the lowest parent number
     * 
     * We find the value of each neighbor, and assign the lowest neighbor value as
     * the parent
     */
    public void open(int row, int col) {
        this.isWithinBounds(row, col);
        if(this.isOpen(row, col)) {
            return;
        }
        
        this.isWithinBounds(row, col);
        int cell = this.getIndex(row, col);
        this.matrix[cell] = 1;
        this.openSites++;

        /* Virtual Top Cell */
        if (row == 1) {
            this.ufPerc.union(cell, this.top);
            this.ufWithoutBackwash.union(cell, this.top);
        }
        /* Virtual Bottom Cell */
        if (row == this.n) {
            this.ufPerc.union(cell, this.bottom);
        }

        /* Top Neighbor */
        if (row > 1 && this.isOpen(row - 1, col)) {
            int topNeighbor = this.getIndex(row - 1, col);
            this.ufPerc.union(cell, topNeighbor);
            this.ufWithoutBackwash.union(cell, topNeighbor);
        }

        /* Bottom Neighbor */
        if (row < this.n && this.isOpen(row + 1, col)) {
            int bottomNeighbor = this.getIndex(row + 1, col);
            this.ufPerc.union(cell, bottomNeighbor);
            this.ufWithoutBackwash.union(cell, bottomNeighbor);
        }

        /* Right Neighbor */
        if (col < this.n && this.isOpen(row, col + 1)) {
            int rightNeighbor = this.getIndex(row, col + 1);
            this.ufPerc.union(cell, rightNeighbor);
            this.ufWithoutBackwash.union(cell, rightNeighbor);
        }

        /* Left Neighbor */
        if (col > 1 && this.isOpen(row, col - 1)) {
            int leftNeighbor = this.getIndex(row, col - 1);
            this.ufPerc.union(cell, leftNeighbor);
            this.ufWithoutBackwash.union(cell, leftNeighbor);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        this.isWithinBounds(row, col);
        int position = this.getIndex(row, col);
        return this.matrix[position] != -1;
    }

    /**
     * is the site (row, col) full?
     * 
     * A full site is an open site that can be connected to an open site in the top
     * row via a chain of neighboring (left, right, up, down) open sites
     * 
     * Top row has positions <= n, so check if the value of this position is <= n
     */
    public boolean isFull(int row, int col) {
        this.isWithinBounds(row, col);
        if (!this.isOpen(row, col)) {
            return false;
        }
        int position = this.getIndex(row, col);

        return this.ufWithoutBackwash.find(this.top) == this.ufWithoutBackwash.find(position);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.ufPerc.find(this.top) == this.ufPerc.find(this.bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        
    }

    private int getIndex(int row, int col) {
        return this.n * (row - 1) + col - 1;
    }

    private boolean isWithinBounds(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException();
        }

        return true;
    }
}

/**
 * [1,1] [1,2] [1,3] [1,4]
 * [2,1] [2,2] [2,3] [2,4]
 * [3,1] [3,2] [3,3] [3,4]
 * [4,1] [4,2] [4,3] [4,4]
 */