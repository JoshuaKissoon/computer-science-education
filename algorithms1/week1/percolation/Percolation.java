import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Class that provides functions for percolation
 */
public class Percolation {

    private int size = 0;

    /**
     * An array to store open & closed sites
     * - -1: Closed
     * - 1: Open
     */
    private int[] N;
    private int n;
    private WeightedQuickUnionUF uf;
    private int top; // Artificial cell at the top
    private int bottom; // Artificial cell at the bottom

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.uf = new WeightedQuickUnionUF(n);
        this.n = n;
        this.top = 0;
        this.bottom = this.size + 1;

        this.size = n * n;
        this.N = new int[this.size];

        /* Let's initialize the grid */
        for (int i = 0; i < this.size; i++) {
            this.N[i] = -1;
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
        int cell = this.getIndex(row, col);
        this.N[cell] = 1;

        /* Virtual Top Cell */
        if(row == 1) {
            this.uf.union(cell, this.top);
        }
        /* Virtual Bottom Cell */
        if(row == this.n) {
            this.uf.union(cell, this.bottom);
        }

        /* Top Neighbor */
        if (row > 1 && this.isOpen(row - 1, col)) {
            int topNeighbor = this.getIndex(row - 1, col);
            this.uf.union(cell, topNeighbor);
        }

        /* Bottom Neighbor */
        if (row < this.n && this.isOpen(row + 1, col)) {
            int bottomNeighbor = this.getIndex(row + 1, col);
            this.uf.union(cell, bottomNeighbor);
        }

        /* Right Neighbor */
        if (col < this.n && this.isOpen(row, col + 1)) {
            int rightNeighbor = this.getIndex(row, col + 1);
            this.uf.union(cell, rightNeighbor);
        }

        /* Left Neighbor */
        this.isWithinBounds(row, col - 1);
        if (col > 0 && this.isOpen(row, col - 1)) {
            int leftNeighbor = this.getIndex(row, col - 1);
            this.uf.union(cell, leftNeighbor);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        this.isWithinBounds(row, col);
        int position = this.getIndex(row, col);
        return this.N[position] != -1;
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
        if(!this.isOpen(row, col)) {
            return false;
        }
        int position = this.getIndex(row, col);

        return this.uf.connected(position, this.top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int openSitesCount = 0;

        for (int i = 0; i < this.size; i++) {
            if (this.N[i] != -1) {
                openSitesCount++;
            }
        }

        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.uf.connected(this.top, this.bottom);
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    private int getIndex(int row, int col) {
        return this.n * (row - 1) + col - 1;
    }

    private boolean isWithinBounds(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IndexOutOfBoundsException();
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