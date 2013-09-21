import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Fast {
    private static HashMap<Point, HashSet<Point>> connected;

    private static void markConnected(Point p1, Point p2) {
        if (!connected.containsKey(p1)) {
            HashSet<Point> set = new HashSet<Point>();
            set.add(p2);
            connected.put(p1, set);
        } else {
            HashSet<Point> set = connected.get(p1);
            set.add(p2);
        }
    }

    private static boolean isConnected(Point p1, Point p2) {
        if (!connected.containsKey(p1))
            return false;
        HashSet<Point> set = connected.get(p1);
        return set.contains(p2);
    }
    
    private static void printPoints(Point head, Point[] pts, int start, int end) {
        if (isConnected(pts[end], pts[end - 1]))
            return;
        head.drawTo(pts[end]);
        System.out.print("" + head + " -> " + pts[start]);
        for (int i = start + 1; i <= end; i++) {
            System.out.print(" -> " + pts[i]);
        }
        System.out.println();
        markConnected(pts[end], pts[end - 1]);
    }
    
    public static void main(String[] args) {
        In input = new In(args[0]);
        int n = input.readInt();
        Point[] points = new Point[n];
        Point[] pts = new Point[n - 1];
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        for (int i = 0; i < n; i++) {
            points[i] = new Point(input.readInt(), input.readInt());
            points[i].draw();
        }
        Arrays.sort(points);

        connected = new HashMap<Point, HashSet<Point>>();
        for (int i = 0; i < n - 3; i++) {
            for (int j = 0; j < n - i - 1; j++)
                pts[j] = points[i + j + 1];
            Arrays.sort(pts, 0, n - i - 1, points[i].SLOPE_ORDER);
            double lastSlope = pts[0].slopeTo(points[i]);
            int count = 1;
            for (int j = 1; j < n - i - 1; j++) {
                double slope = pts[j].slopeTo(points[i]);
                if (slope == lastSlope) {
                    count++;
                } else {
                    // Duplcation check on tail only
                    if (count > 2)
                        printPoints(points[i], pts, j - count, j - 1);
                    lastSlope = slope;
                    count = 1;
                }
            }
            if (count > 2)
                printPoints(points[i], pts, n - i - 1 - count, n - i - 2);
        }

        StdDraw.show(0);
    }
}
