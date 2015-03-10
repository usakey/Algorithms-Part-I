/*
 * Compilation: javac-algs4 PercolationStats.java
 * Execution: java-algs4 PercolationStats 20 100
 *
 * This program performs a series of computational experiments
 * to get the mean, standard deviation and 95% confidence interval
 * for percolation threshold.
 *
 *  @author: TriStone
 *  @date:   07/03/2015
 *
 */
public class PercolationStats {
  private double[] fraction;

  public PercolationStats(int N, int T) { // perform T independent experiments on an N-by-N grid
    if (N <= 0 || T <= 0)
      throw new IllegalArgumentException("Illegal input size or times!");
    fraction = new double[T];
    for(int i=0; i<T; i++){
      Percolation perc = new Percolation(N);
      int count = 0;
      while(!perc.percolates()){
        int x = StdRandom.uniform(1, N+1);
        int y = StdRandom.uniform(1, N+1);
        if(!perc.isOpen(x, y)){
          perc.open(x, y);
          count++;
        }
      }
      fraction[i] = (double)count/(N*N);
    }
  }

  public double mean() { // sample mean of percolation threshold
    return StdStats.mean(fraction);
  }

  public double stddev() { // sample standard deviation of percolation threshold
    return StdStats.stddev(fraction);
  }

  public double confidenceLo() { // low endpoint of 95% confidence interval
    return mean() - 1.96*stddev()/Math.sqrt(fraction.length);
  }

  public double confidenceHi() { // high endpoint of 95% confidence interval
    return mean() + 1.96*stddev()/Math.sqrt(fraction.length);
  }

  public static void main(String[] args) { // test client (described below)
    int N = Integer.parseInt(args[0]);
    int T = Integer.parseInt(args[1]);

    PercolationStats perc=new PercolationStats(N, T);
    StdOut.print("mean = "+perc.mean()+"\n");
    StdOut.print("std dev = "+perc.stddev()+"\n");
    StdOut.print("95% confidence interval = "+perc.confidenceLo()+", "+perc.confidenceHi());
  }
}
