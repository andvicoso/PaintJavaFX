package paint.model.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

/**
 *
 * @author Anderson
 */
public class LineTool extends ShapeTool<Line> {

    public LineTool(ColorPicker colorPicker) {
        super(new Line(), colorPicker);
    }

    @Override
    public void onMousePressed(GraphicsContext gc, MouseEvent e) {
        super.onMousePressed(gc, e);
        shape.setStartX(e.getX());
        shape.setStartY(e.getY());
    }

    @Override
    public void onMouseReleased(GraphicsContext gc, MouseEvent e) {
        shape.setEndX(e.getX());
        shape.setEndY(e.getY());
        gc.strokeLine(shape.getStartX(), shape.getStartY(), shape.getEndX(), shape.getEndY());
    }

    @Override
    public void onMouseDragged(GraphicsContext gc, MouseEvent e) {
    }

}
