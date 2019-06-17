package paint.model.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Anderson
 */
public abstract class ColloredTool implements CanvasTool {

    protected final ColorPicker colorPicker;

    public ColloredTool(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }

    @Override
    public void onMousePressed(GraphicsContext gc, MouseEvent e) {
        gc.setStroke(colorPicker.getValue());
        gc.setFill(colorPicker.getValue());
    }
}
