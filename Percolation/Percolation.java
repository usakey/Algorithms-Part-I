/*
 * To test this program, it's better to combine with
 * PercolationVisualizer.java,
 * which is a client for testing.
 *
 * Compilation: javac-algs4 PercolationVisualizer.java
 * Execution: java-algs4 PercolationVisualizer input20.txt
 *
 * This is intended for modeling a percolation system
 * with N-by-N grid of sites.
 *
 *  @author: TriStone
 *  @date:   07/03/2015
 *
 */
public class Percolation {
  private final int sizeGrid; // dimension of grid
  private final int[][] grid;

  private WeightedQuickUnionUF wqUF;

  public Percolation(int N) { // create N-by-N grid, with all sites blocked
    if (N <= 0)
      throw new IllegalArgumentException("Grid dimension N<=0! Please re-enter an input for size!");
    else {
      this.sizeGrid = N;
      grid = new int[sizeGrid][sizeGrid];  // initialize as blocked(0)
      wqUF = new WeightedQuickUnionUF(sizeGrid * sizeGrid + 2);  // include one top and one bottom
    }
  }

  public void open(int i, int j) { // open site (row i, column j) if it is not open already
    checkIndex(i, j);
    if (isOpen(i, j))
      return; // in case of open, skip following steps
    grid[i - 1][j - 1] = 1; // set (i, j) as open

    if (i == 1)
      wqUF.union(0, xyTo1D(i, j)); // if on the top row, connect to the virtual-top
    if (i == sizeGrid)
      wqUF.union(sizeGrid * sizeGrid + 1, xyTo1D(i, j)); // if on the bottom, connect to the virtual-bottom

    if (i > 1 && isOpen(i - 1, j))
      wqUF.union(xyTo1D(i, j), xyTo1D(i - 1, j));
    if (i < sizeGrid && isOpen(i + 1, j))
      wqUF.union(xyTo1D(i, j), xyTo1D(i + 1, j));
    if (j > 1 && isOpen(i, j - 1))
      wqUF.union(xyTo1D(i, j), xyTo1D(i, j - 1));
    if (j < sizeGrid && isOpen(i, j + 1))
      wqUF.union(xyTo1D(i, j), xyTo1D(i, j + 1));
  }

  public boolean isOpen(int i, int j) { // is site (row i, column j) open?
    checkIndex(i, j);
    return grid[i - 1][j - 1] == 1;
  }

  public boolean isFull(int i, int j) { // is site (row i, column j) full?
    checkIndex(i, j);
    return wqUF.connected(0, xyTo1D(i, j));  // test if it can connect to the top
  }

  public boolean percolates() { // does the system percolate?
    return wqUF.connected(0, sizeGrid * sizeGrid + 1);
  }

  private void checkIndex(int i, int j) {
    if (i <= 0 || i > sizeGrid)
      throw new IndexOutOfBoundsException("index " + i + " is not between 1 and " + sizeGrid);
    if (j <= 0 || j > sizeGrid)
      throw new IndexOutOfBoundsException("index " + j + " is not between 1 and " + sizeGrid);
  }

  private int xyTo1D(int i, int j) {
    checkIndex(i, j);
    return (i - 1) * sizeGrid + j; // as the upper-left is (1,1)
  }

  public static void main(String[] args) { // test client (optional)
    int size = 20;
    Percolation perc = new Percolation(size);
    int row = 1;
    int col = 2;

    if (perc.isOpen(row, col))
      System.out.println("site:(" + row + ", " + col + ") is Open!");
    else
      System.out.println("site:(" + row + ", " + col + ") is Blocked!");

    perc.open(row, col);
    if (perc.isOpen(row, col))
      System.out.println("site:(" + row + ", " + col + ") is Open!");
    else
      System.out.println("site:(" + row + ", " + col + ") is Blocked!");
  }
}
