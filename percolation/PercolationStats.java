import java.util.Random;

public class PercolationStats {
    private int n;
    private int t;
    private double[] fractions;
    private double sampleMean;
    private double sampleStddev;
    private double sampleConfidenceLo;
    private double sampleConfidenceHi;
    
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 1)
            throw new IllegalArgumentException();
        this.n = n;
        this.t = t;
        this.fractions = new double[t];
        emulate();
    }

    private void emulate() {
        Random random = new Random();

        double sum = 0;
        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(n);
            int count = 0;
            while (!p.percolates()) {
                int x, y;
                do {
                    x = random.nextInt(n) + 1;
                    y = random.nextInt(n) + 1;
                } while (p.isOpen(x, y));
                p.open(x, y);
                count++;
            }
            fractions[i] = ((double) count) / n / n;
            sum += fractions[i];
        }
        sampleMean = sum / t;
        double sigma = 0;
        for (int i = 0; i < t; i++) {
            sigma += (fractions[i] - sampleMean) * (fractions[i] - sampleMean);
        }
        sampleStddev = Math.sqrt(sigma / (t - 1));
        sampleConfidenceLo = sampleMean - 1.96 * sampleStddev / Math.sqrt(t);
        sampleConfidenceHi = sampleMean + 1.96 * sampleStddev / Math.sqrt(t);
    }

    // sample mean of percolation threshold
    public double mean() {
        return sampleMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return sampleStddev;
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return sampleConfidenceLo;
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return sampleConfidenceHi;
    }
    
    // test client, described below
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(5, 100);
        System.out.printf("mean                    = %f\n", stats.mean());
        System.out.printf("stddev                  = %f\n", stats.stddev());
        System.out.printf("95%% confidence interval = %f, %f\n",
                          stats.confidenceLo(), stats.confidenceHi());
    }
}
