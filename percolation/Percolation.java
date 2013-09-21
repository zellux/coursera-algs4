public class Percolation {
    // 0: source, n + 1: sink
    private WeightedQuickUnionUF percolationUF;
    private WeightedQuickUnionUF fullUF;
    private boolean[][] opened;
    private int n;
    private boolean empty = true;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int n) {
        this.percolationUF = new WeightedQuickUnionUF(n * n + 2);
        this.fullUF = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 0; i < n; i++) {
            percolationUF.union(0, i + 1);
            fullUF.union(0, i + 1);
            percolationUF.union(n * n + 1, n * n - i);
        }
        this.opened = new boolean[n][n];
        this.n = n;
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (isOpen(i, j))
            return;
        empty = false;
        opened[i - 1][j - 1] = true;
        link(i, j, i - 1, j);
        link(i, j, i, j - 1);
        link(i, j, i + 1, j);
        link(i, j, i, j + 1);
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n)
            throw new IndexOutOfBoundsException();
        return opened[i - 1][j - 1];
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        return isOpen(i, j) && fullUF.connected(0, (i - 1) * n + j);
    }
    
    // does the system percolate?
    public boolean percolates() {
        return !empty && percolationUF.connected(0, n * n + 1);
    }

    private void dumpBoard() {
        System.out.println("=========");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (opened[i][j])
                    System.out.print("X");
                else
                    System.out.print("O");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void link(int x1, int y1, int x2, int y2) {
        if (x2 < 1 || x2 > n || y2 < 1 || y2 > n || !opened[x2 - 1][y2 - 1])
            return;
        int pos1 = (x1 - 1) * n + y1;
        int pos2 = (x2 - 1) * n + y2;
        percolationUF.union(pos1, pos2);
        fullUF.union(pos1, pos2);
    }

    // public static void main(String[] args) {
    //     Percolation p = new Percolation(6);
    //     p.open(1, 1);
    //     p.open(2, 1);
    //     p.open(3, 1);
    //     p.open(4, 1);
    //     p.open(5, 1);
    //     p.open(6, 1);
    //     p.open(6, 3);
    //     System.out.println(p.isFull(6, 3));

    //     Percolation p2 = new Percolation(1);
    //     System.out.println(p2.percolates());
    // }
}
