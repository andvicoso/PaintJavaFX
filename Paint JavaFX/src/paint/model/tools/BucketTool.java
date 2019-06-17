package paint.model.tools;

import java.util.Stack;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import paint.model.Point;
import paint.utils.Utils;

/**
 *
 * @author Anderson
 */
public class BucketTool extends ColloredTool {

    public BucketTool(ColorPicker colorPicker) {
        super(colorPicker);
    }

    @Override
    public void onMouseReleased(GraphicsContext gc, MouseEvent e) {
        startDrawing(gc, new Point(e.getX(), e.getY()));
    }

    @Override
    public void onMouseDragged(GraphicsContext gc, MouseEvent e) {
    }

    private void startDrawing(GraphicsContext gc, Point currentMousePoint) {
        WritableImage canvasSnapshot = Utils.getCanvasSnapshot(gc.getCanvas());

        Task<Void> task = new FloodFill(canvasSnapshot, currentMousePoint, (Color) gc.getFill());
        task.setOnSucceeded(event -> gc.drawImage(canvasSnapshot, 0, 0));

        new Thread(task).start();
    }

    private class FloodFill extends Task<Void> {

        private final WritableImage canvas;
        private final Point click;
        private final Color newColor;

        public FloodFill(WritableImage canvasSnapshot, Point clickPoint, Color newColor) {
            this.canvas = canvasSnapshot;
            this.click = clickPoint;
            this.newColor = newColor;
        }

        @Override
        protected Void call() throws Exception {
            Stack<Point> stack = new Stack<>();
            PixelReader pixelReader = canvas.getPixelReader();
            PixelWriter pixelWriter = canvas.getPixelWriter();
            Color targetColor = pixelReader.getColor(click.getX(), click.getY());

            if (newColor.equals(targetColor)) {
                return null;
            }

            stack.push(click);
            while (!stack.isEmpty()) {
                Point pixel = stack.pop();
                //Is point inside canvas?
                if (pixel.getX() > 0 && pixel.getX() < canvas.getWidth()
                        && pixel.getY() > 0 && pixel.getY() < canvas.getHeight()) {
                    Color pixelColor = pixelReader.getColor(pixel.getX(), pixel.getY());

                    if (sameColor(pixelColor, targetColor)) {
                        pixelWriter.setColor(pixel.getX(), pixel.getY(), newColor);
                        //check neigbourhood
                        stack.add(new Point(pixel.getX() - 1, pixel.getY() - 1));
                        stack.add(new Point(pixel.getX() - 1, pixel.getY()));
                        stack.add(new Point(pixel.getX() - 1, pixel.getY() + 1));
                        stack.add(new Point(pixel.getX(), pixel.getY() - 1));
                        stack.add(new Point(pixel.getX(), pixel.getY() + 1));
                        stack.add(new Point(pixel.getX() + 1, pixel.getY() - 1));
                        stack.add(new Point(pixel.getX() + 1, pixel.getY()));
                        stack.add(new Point(pixel.getX() + 1, pixel.getY() + 1));
                    }
                }
            }

            return null;
        }

        private boolean sameColor(Color a, Color b) {
            double delta = 0.1;
            return equals(a.getRed(), b.getRed(), delta)
                    && equals(a.getGreen(), b.getGreen(), delta)
                    && equals(a.getBlue(), b.getBlue(), delta);
        }

        private boolean equals(double a, double b, double epsilon) {
            return Math.abs(a - b) < epsilon;
        }
    }

}
