import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

public class GeometricDrawingRun {
    private static final int W = 800;
    String grid_window = "Drawing: Grid";
    public void drawGrid(int[][] maze,Mat grid_image){
        int width = maze[0].length;
        int height = maze.length;
        int i;
        for (i = 0; i < height; i++) {
            MyLine( grid_image, new Point( 0, i*1.0*W/height ), new Point( W, i*1.0*W/height ) );
        }

        for (i = 0; i < width; i++) {
            MyLine( grid_image, new Point( i*1.0*W/width, 0 ), new Point( i*1.0*W/width, W ) );
        }

        for (i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 0) {
                    Imgproc.rectangle(
                            grid_image,
                            new Point(j * 1.0*W/width, i*1.0 * W/height),
                            new Point((j+1)*1.0*W/width, (i+1)*1.0*W/height),
                            new Scalar(125, 125, 125),
                            -1,
                            8,
                            0
                    );
                }

            }
        }
    }

    public void myFilledCircle( Mat img, Point point ) {
        int thickness = -1;
        int lineType = 8;
        int shift = 0;
        Imgproc.circle( img,
                point,
                W/64,
                new Scalar( 0, 125, 255 ),
                thickness,
                lineType,
                shift );
        HighGui.imshow( grid_window, img );
        HighGui.waitKey( 100 );
    }

    public void myPathCircle( Mat img, Point point ) {
        int thickness = -1;
        int lineType = 8;
        int shift = 0;
        Imgproc.circle( img,
                point,
                W/64,
                new Scalar( 125, 0, 255 ),
                thickness,
                lineType,
                shift );
        HighGui.imshow( grid_window, img );
        HighGui.waitKey( 100 );
    }

    private void MyLine( Mat img, Point start, Point end ) {
        int thickness = 2;
        int lineType = 8;
        int shift = 0;
        Imgproc.line( img,
                start,
                end,
                new Scalar( 255, 255, 0 ),
                thickness,
                lineType,
                shift );
    }
}

