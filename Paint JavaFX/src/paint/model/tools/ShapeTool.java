package paint.model.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

/**
 *
 * @author Anderson
 * @param <S> shape
 */
public abstract class ShapeTool<S extends Shape> extends ColloredTool {

    protected final S shape;

    public ShapeTool(S shape, ColorPicker colorPicker) {
        super(colorPicker);
        this.shape = shape;
    }

    @Override
    public void onMousePressed(GraphicsContext gc, MouseEvent e) {
        gc.setFill(colorPicker.getValue());
        gc.setStroke(colorPicker.getValue());
    }
}
