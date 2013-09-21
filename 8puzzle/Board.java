import java.util.ArrayList;
import java.util.List;

public class Board {
    private static int[] DELTA_X = new int[] { -1, 1, 0, 0 };
    private static int[] DELTA_Y = new int[] { 0, 0, -1, 1 };
    private short[][] board;
    private int n;
    private int hammingDist = -1;
    private int manhattanDist = -1;
    private int blankX, blankY;
    private String stringRepr;
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        n = blocks.length;
        board = new short[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = (short) blocks[i][j];
                if (board[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                }
            }
        }
    }

    // board dimension N
    public int dimension() {
        return n;
    }
    
    // number of blocks out of place
    public int hamming() {
        if (hammingDist >= 0)
            return hammingDist;
        
        int count = 0;
        
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (board[i][j] != 0 && board[i][j] != i * n + j + 1)
                    count++;
        
        hammingDist = count;
        return hammingDist;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattanDist >= 0)
            return manhattanDist;
        
        int sum = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0)
                    continue;
                int deltaX = Math.abs(i - (board[i][j] - 1) / n);
                int deltaY = Math.abs(j - (board[i][j] - 1) % n);
                sum += deltaX + deltaY;
            }
        }

        manhattanDist = sum;
        return manhattanDist;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }
    
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int[][] blocks = cloneBoard(board);
        int x = (blankX + 1) % n;
        exchange(blocks, x, 0, x, 1);
        return new Board(blocks);
    }

    // does this board equal y?    
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;
        if (y == this)
            return true;
        return toString().equals(y.toString());
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> q = new ArrayList<Board>();
        int[][] blocks = cloneBoard(board);
        for (int i = 0; i < 4; i++) {
            int x = blankX + DELTA_X[i];
            int y = blankY + DELTA_Y[i];
            if (x >= 0 && x < n && y >= 0 && y < n) {
                exchange(blocks, x, y, blankX, blankY);
                q.add(new Board(blocks));
                exchange(blocks, x, y, blankX, blankY);
            }
        }
        return q;
    }
    
    // string representation of the board (in the output format specified below)
    public String toString() {
        if (stringRepr != null)
            return stringRepr;
        
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        stringRepr = s.toString();
        return stringRepr;
    }

    private void exchange(int[][] blocks, int x1, int y1, int x2, int y2) {
        int tmp = blocks[x1][y1];
        blocks[x1][y1] = blocks[x2][y2];
        blocks[x2][y2] = tmp;
    }

    private int[][] cloneBoard(short[][] blocks) {
        int[][] cloned = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                cloned[i][j] = blocks[i][j];
        return cloned;
    }
}
