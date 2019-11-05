import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;

public class Main {
    private static final int W = 800;


    public static void main(String[] args) {
        int[][] maze = {
                { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 , 1},
                { 1, 1, 1, 0, 1, 1, 1, 0, 1, 1 ,0},
                { 1, 1, 1, 0, 1, 1, 0, 1, 0, 1 ,1},
                { 0, 0, 1, 0, 1, 0, 0, 0, 0, 1 ,1},
                { 1, 1, 1, 0, 1, 1, 1, 0, 1, 0 ,0},
                { 1, 0, 1, 1, 1, 1, 0, 1, 0, 0 ,1},
                { 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 ,1},
                { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 ,1},
                { 1, 1, 1, 0, 0, 0, 1, 0, 0, 1 ,0},
                { 1, 1, 1, 0, 0, 0, 1, 0, 0, 1 ,1},
                { 1, 1, 1, 0, 1, 1, 1, 0, 1, 1 ,0},
                { 1, 1, 1, 0, 1, 1, 0, 1, 0, 1 ,1},
                { 0, 0, 1, 0, 1, 0, 0, 0, 0, 1 ,1},
                { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 , 1},
                { 1, 1, 1, 0, 1, 1, 1, 0, 1, 1 ,0},
                { 1, 1, 1, 0, 1, 1, 0, 1, 0, 1 ,1}

        };
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        String grid_window = "Drawing: Grid";
        Mat grid_image = Mat.zeros( W, W, CvType.CV_8UC3 );
        GeometricDrawingRun demo = new GeometricDrawingRun();
        demo.drawGrid(maze, grid_image);
        AStartAlgorithm algorithm = new AStartAlgorithm(maze, 3, 2, 9, 9);
        algorithm.aStartAlgorithm(demo, grid_image, maze);

        HighGui.moveWindow( grid_window, 0, 200 );
        HighGui.imshow( grid_window, grid_image );
        HighGui.waitKey( 0 );
        System.exit(0);
    }
}