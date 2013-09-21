import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        In input = new In(args[0]);
        int n = input.readInt();
        Point[] points = new Point[n];
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        for (int i = 0; i < n; i++) {
            points[i] = new Point(input.readInt(), input.readInt());
            points[i].draw();
        }

        Arrays.sort(points);
        for (int p = 0; p < n; p++) {
            for (int q = p + 1; q < n; q++) {
                double slope = points[p].slopeTo(points[q]);
                for (int r = q + 1; r < n; r++) {
                    if (points[q].slopeTo(points[r]) != slope)
                        continue;
                    for (int s = r + 1; s < n; s++) {
                        if (points[r].slopeTo(points[s]) != slope)
                            continue;
                        points[p].drawTo(points[s]);
                        System.out.println(points[p].toString() + " -> "
                                           + points[q] + " -> " + points[r]
                                           + " -> " + points[s]);
                    }
                }
            }
        }

        StdDraw.show(0);
    }
}
