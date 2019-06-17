package paint.model.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Anderson
 */
public class RectangleTool extends ShapeTool<Rectangle> {

    public RectangleTool(ColorPicker colorPicker) {
        super(new Rectangle(), colorPicker);
    }

    @Override
    public void onMousePressed(GraphicsContext gc, MouseEvent e) {
        super.onMousePressed(gc, e);
        shape.setX(e.getX());
        shape.setY(e.getY());
    }

    @Override
    public void onMouseReleased(GraphicsContext gc, MouseEvent e) {
        shape.setWidth(Math.abs((e.getX() - shape.getX())));
        shape.setHeight(Math.abs((e.getY() - shape.getY())));

        if (shape.getX() > e.getX()) {
            shape.setX(e.getX());
        }
        if (shape.getY() > e.getY()) {
            shape.setY(e.getY());
        }
        gc.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
    }

    @Override
    public void onMouseDragged(GraphicsContext gc, MouseEvent e) {
    }
}
