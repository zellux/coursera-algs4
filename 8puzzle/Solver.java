import java.util.List;
import java.util.LinkedList;
import java.util.HashSet;

public class Solver {
    private List<Board> steps;
    private HashSet<String> history;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> queue = new MinPQ<SearchNode>();
        queue.insert(new SearchNode(null, initial));
        queue.insert(new SearchNode(null, initial.twin()));
        history = new HashSet<String>();
        SearchNode last = null;
        while (!queue.isEmpty()) {
            SearchNode current = queue.delMin();
            // System.out.println(current);
            if (current.board.isGoal()) {
                last = current;
                break;
            }
            history.add(current.board.toString());
            if (current.equals(last))
                continue;
            last = current;
            for (Board b : current.board.neighbors()) {
                if (!history.contains(b.toString()))
                // if (current.parent == null || !current.parent.board.equals(b))
                    queue.insert(new SearchNode(current, b));
            }
        }
        LinkedList<Board> result = new LinkedList<Board>();
        while (last != null) {
            result.addFirst(last.getBoard());
            last = last.getParent();
        }
        if (result.getFirst().equals(initial))
            steps = result;
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return steps != null;
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (isSolvable())
            return steps.size() - 1;
        else
            return -1;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        return steps;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode parent;
        private int depth;
        private Board board;

        SearchNode(SearchNode parent, Board board) {
            this.parent = parent;
            this.board = board;
            if (parent == null)
                depth = 0;
            else
                depth = parent.getDepth() + 1;
        }
        
        public SearchNode getParent() {
            return parent;
        }

        public int getDepth() {
            return depth;
        }

        public Board getBoard() {
            return board;
        }

        public int compareTo(SearchNode that) {
            return (depth + board.manhattan())
                - (that.getDepth() + that.getBoard().manhattan());
        }

        public String toString() {
            return "Depth: " + depth + "\n" + board;
        }
    }
}
