package paint.model.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

/**
 *
 * @author Anderson
 */
public class CircleTool extends ShapeTool<Circle> {

    public CircleTool(ColorPicker colorPicker) {
        super(new Circle(), colorPicker);
    }

    @Override
    public void onMousePressed(GraphicsContext gc, MouseEvent e) {
        super.onMousePressed(gc, e);
        shape.setCenterX(e.getX());
        shape.setCenterY(e.getY());
    }

    @Override
    public void onMouseReleased(GraphicsContext gc, MouseEvent e) {
        shape.setRadius((Math.abs(e.getX() - shape.getCenterX()) + Math.abs(e.getY() - shape.getCenterY())) / 2);

        if (shape.getCenterX() > e.getX()) {
            shape.setCenterX(e.getX());
        }
        if (shape.getCenterY() > e.getY()) {
            shape.setCenterY(e.getY());
        }
        gc.fillOval(shape.getCenterX(), shape.getCenterY(), shape.getRadius(), shape.getRadius());
    }

    @Override
    public void onMouseDragged(GraphicsContext gc, MouseEvent e) {
    }

}
