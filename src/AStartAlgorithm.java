import org.opencv.core.Mat;
import org.opencv.core.Point;

import java.util.*;

import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class AStartAlgorithm {
    String grid_window = "Drawing: Grid";
    private static final int W = 800;
    private Node now;
    private List<Node> openList;
    private List<Node> closeList;
    private List<Node> path;
    private int xStart, yStart, xEnd, yEnd;
    private int[][] maze;

    public AStartAlgorithm(int[][] maze, int xStart, int yStart, int xEnd, int yEnd) {
        this.openList = new ArrayList<>();
        this.closeList = new ArrayList<>();
        this.path = new ArrayList<>();
        this.maze = maze;
        this.now = new Node(null, xStart, yStart, 0, 0);
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }

    private static boolean findNeighborInList(List<Node> array, Node node) {
        return array.stream().noneMatch((n) -> (n.x == node.x && n.y == node.y));
    }

    // add Neighborhood To openList
    private void addNeighBorToOpenList(GeometricDrawingRun demo, Mat grid_image, int width, int height) {
        /*Generating all the 8 successor of this cell

                N.W   N   N.E
                  \   |   /
                   \  |  /
                W----Cell----E
                     / | \
                   /   |  \
                S.W    S   S.E

            Cell-->Popped Cell (i, j)
            N -->  North       (i-1, j)
            S -->  South       (i+1, j)
            E -->  East        (i, j+1)
            W -->  West           (i, j-1)
            N.E--> North-East  (i-1, j+1)
            N.W--> North-West  (i-1, j-1)
            S.E--> South-East  (i+1, j+1)
            S.W--> South-West  (i+1, j-1)*/

        // To store the 'g', 'h' and 'f' of the 8 successors
        Node node;
        Point point;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                node = new Node(this.now, this.now.x + x, this.now.y + y, this.now.g, calculateHValue(x, y));
                if (findNeighborInList(this.openList, node)
                && findNeighborInList(this.closeList, node)
                && !isValid(this.now.x + x, this.now.y + y)
                && !isUnBlock(this.now.x + x, this.now.y + y)) {
                    node.g += 1.0;
                    // update g while diagonal location
                    if (x != 0 && y != 0) {
                        node.g += 0.4;
                    }

                    this.openList.add(node);
                    System.out.println(node.x + "-" + node.y);
                    point = new Point(node.y *1.0 * W/width + W/(2.0*width), node.x*1.0* W/height + W/(2.0*height));
                    demo.myFilledCircle(grid_image, point);
                }
            }
        }
        Collections.sort(this.openList);
    }

    public void aStartAlgorithm(GeometricDrawingRun demo, Mat grid_image, int[][] maze) {
        int height = maze.length;
        int width = maze[0].length;
        // if source is out of range
        if (isValid(this.xStart, this.yStart)) {
            System.out.println("Source is invalid");
            return;
        }

        // if destination is out of range
        if (isValid(this.xEnd, this.yEnd)) {
            System.out.println("destination is invalid");
            return;
        }

        // Either the source or the destination is blocked
        if (isUnBlock(this.xStart, this.yStart)
                && isUnBlock(this.xEnd, this.yEnd)) {
            System.out.println("Source or the destination is blocked\n");
            return;
        }

        // If the destination is the same as source
        if (isDestination(this.xStart, this.yStart)) {
            System.out.println("We are already at the destination\n");
            return;
        }

        addNeighBorToOpenList(demo, grid_image, width, height);
        this.closeList.add(this.now);

        while (this.now.x != this.xEnd || this.now.y != this.yEnd) {
            if (this.openList.isEmpty()) { // nothing to examine
                System.out.println("Not find the path");
                return;
            }

            this.now = this.openList.get(0); // get the first node lowest f score
            this.openList.remove(0); // remove it
            this.closeList.add(this.now); // add it to close list
            addNeighBorToOpenList(demo, grid_image, width, height);
        }

        this.path.add(0, this.now);
        while (this.now.x != this.xStart || this.now.y != this.yStart) {
            this.now = this.now.parent;
            this.path.add(0, this.now);
        }
        Point point;
        for (Node pa : path) {
            point = new Point(pa.y *1.0 * W/width + W/(2.0*width), pa.x*1.0* W/height + W/(2.0*height));
            demo.myPathCircle(grid_image, point);
            System.out.println("[" + pa.x+"]["+pa.y+"]");
        }
    }

    // if the source is out of range
    private boolean isValid(int x, int y) {
        return (x < 0) || (x >= maze[0].length) || (y < 0) || (y >= maze.length);
    }

    // function to check rec block
    private boolean isUnBlock(int x, int y) {
        return this.maze.length <= x || this.maze[0].length <= y || this.maze[x][y] == 0;
    }

    // function to check whether destination Node have been reach or not
    private boolean isDestination(int x, int y) {
        return (x == this.xEnd) && (y == this.yEnd);
    }

    // function to calculate the 'h' heuristics
    private double calculateHValue(int x, int y) {
        return sqrt((this.xEnd - (this.now.x + x)) * (this.xEnd - (this.now.x + x)) + (this.yEnd - (this.now.y + y)) * (this.yEnd - (this.now.y + y)));
    }
}
//    public static void main(String[] args) {
//        int grid[][] =
//        {
//            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 },
//            { 1, 1, 1, 0, 1, 1, 1, 0, 1, 1 },
//            { 1, 1, 1, 0, 1, 1, 0, 1, 0, 1 },
//            { 0, 0, 1, 0, 1, 0, 0, 0, 0, 1 },
//            { 1, 1, 1, 0, 1, 1, 1, 0, 1, 0 },
//            { 1, 0, 1, 1, 1, 1, 0, 1, 0, 0 },
//            { 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 },
//            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 },
//            { 1, 1, 1, 0, 0, 0, 1, 0, 0, 1 }
//        };
//
//        AStartAlgorithm algorithm = new AStartAlgorithm(grid, 0, 0, 9, 8);
//        algorithm.aStartAlgorithm();

//        if (algorithm.path != null) {
//            algorithm.path.forEach((n) -> {
//                System.out.print("[" + n.x + ", " + n.y + "] ");
//            });
//        }
//    }
//}